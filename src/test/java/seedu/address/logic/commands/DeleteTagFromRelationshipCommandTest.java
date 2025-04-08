package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.relationship.Relationship;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.ModelStub;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.RelationshipBuilder;

public class DeleteTagFromRelationshipCommandTest {

    @Test
    public void constructor_nullParameters_throwsNullPointerException() {
        Tag validTag = new Tag("valid");

        // null userId1
        assertThrows(NullPointerException.class, () -> new DeleteTagFromRelationshipCommand(
                null, "2", "Friend", validTag));

        // null userId2
        assertThrows(NullPointerException.class, () -> new DeleteTagFromRelationshipCommand(
                "1", null, "Friend", validTag));

        // null relationshipName
        assertThrows(NullPointerException.class, () -> new DeleteTagFromRelationshipCommand(
                "1", "2", null, validTag));

        // null tag
        assertThrows(NullPointerException.class, () -> new DeleteTagFromRelationshipCommand(
                "1", "2", "Friend", null));
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        DeleteTagFromRelationshipCommand command = new DeleteTagFromRelationshipCommand(
                "1", "2", "Friend", new Tag("tag"));
        assertThrows(NullPointerException.class, () -> command.execute(null));
    }

    @Test
    public void execute_relationshipNotFound_throwsCommandException() {
        ModelStubWithoutRelationship modelStub = new ModelStubWithoutRelationship();
        DeleteTagFromRelationshipCommand command = new DeleteTagFromRelationshipCommand(
                "1", "2", "Friend", new Tag("tag"));

        assertThrows(CommandException.class,
                DeleteTagFromRelationshipCommand.MESSAGE_RELATIONSHIP_NOT_FOUND, () -> command.execute(modelStub));
    }

    @Test
    public void execute_tagNotFound_throwsCommandException() {
        Person person1 = new PersonBuilder().withName("Alice").build();
        Person person2 = new PersonBuilder().withName("Bob").build();
        Tag nonExistingTag = new Tag("nonexistent");

        Relationship relationship = new RelationshipBuilder()
                .withUser1Id(person1.getId())
                .withUser2Id(person2.getId())
                .withTags("existing")
                .build();

        ModelStubWithRelationship modelStub = new ModelStubWithRelationship(relationship);
        DeleteTagFromRelationshipCommand command = new DeleteTagFromRelationshipCommand(
                person1.getId(), person2.getId(), "Friend", nonExistingTag);

        assertThrows(CommandException.class,
                DeleteTagFromRelationshipCommand.MESSAGE_TAG_NOT_FOUND, () -> command.execute(modelStub));
    }

    @Test
    public void execute_validTag_success() throws Exception {
        Person person1 = new PersonBuilder().withName("Alice").build();
        Person person2 = new PersonBuilder().withName("Bob").build();
        Tag tagToRemove = new Tag("friend");

        Relationship relationship = new RelationshipBuilder()
                .withUser1Id(person1.getId())
                .withUser2Id(person2.getId())
                .withTags("friend", "colleague")
                .build();

        ModelStubWithRelationship modelStub = new ModelStubWithRelationship(relationship);
        DeleteTagFromRelationshipCommand command = new DeleteTagFromRelationshipCommand(
                person1.getId(), person2.getId(), "Friend", tagToRemove);

        CommandResult result = command.execute(modelStub);
        assertEquals(String.format(DeleteTagFromRelationshipCommand.MESSAGE_SUCCESS,
                relationship.withRemovedTag(tagToRemove)), result.getFeedbackToUser());
        assertTrue(modelStub.isRelationshipUpdated);
    }

    private static class ModelStubWithoutRelationship extends ModelStub {
    }

    private static class ModelStubWithRelationship extends ModelStub {
        private final Relationship relationship;
        private boolean isRelationshipUpdated = false;

        ModelStubWithRelationship(Relationship relationship) {
            this.relationship = relationship;
        }

        @Override
        public Relationship getRelationship(String userId1, String userId2, String relationshipName) {
            requireNonNull(userId1);
            requireNonNull(userId2);
            requireNonNull(relationshipName);
            return relationship;
        }

        @Override
        public void updateRelationship(Relationship target, Relationship updatedRelationship) {
            requireNonNull(target);
            requireNonNull(updatedRelationship);
            isRelationshipUpdated = true;
        }
    }
}
