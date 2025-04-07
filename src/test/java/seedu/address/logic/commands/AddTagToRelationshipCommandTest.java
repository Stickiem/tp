package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.relationship.Relationship;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.ModelStub;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.RelationshipBuilder;

public class AddTagToRelationshipCommandTest {

    @Test
    public void constructor_nullParameters_throwsNullPointerException() {
        // null user1Id
        assertThrows(NullPointerException.class, () -> new AddTagToRelationshipCommand(
                null, "2", "Friend", new Tag("tag")));

        // null user2Id
        assertThrows(NullPointerException.class, () -> new AddTagToRelationshipCommand(
                "1", null, "Friend", new Tag("tag")));

        // null relationshipName
        assertThrows(NullPointerException.class, () -> new AddTagToRelationshipCommand(
                "1", "2", null, new Tag("tag")));

        // null tag
        assertThrows(NullPointerException.class, () -> new AddTagToRelationshipCommand(
                "1", "2", "Friend", null));
    }

    @Test
    public void execute_relationshipNotFound_throwsCommandException() {
        ModelStubWithoutRelationship modelStub = new ModelStubWithoutRelationship();
        AddTagToRelationshipCommand command = new AddTagToRelationshipCommand(
                "1", "2", "Friend", new Tag("tag"));

        assertThrows(CommandException.class,
                AddTagToRelationshipCommand.MESSAGE_RELATIONSHIP_NOT_FOUND, () -> command.execute(modelStub));
    }

    @Test
    public void execute_duplicateTag_throwsCommandException() {
        Person person1 = new PersonBuilder().build();
        Person person2 = new PersonBuilder().withName("Bob").build();
        Tag existingTag = new Tag("existing");
        Set<Tag> tags = new HashSet<>();
        tags.add(existingTag);

        Relationship relationship = new RelationshipBuilder()
                .withUser1Id(person1.getId())
                .withUser2Id(person2.getId())
                .withTags("existing")
                .build();

        ModelStubWithRelationship modelStub = new ModelStubWithRelationship(relationship);
        AddTagToRelationshipCommand command = new AddTagToRelationshipCommand(
                person1.getId(), person2.getId(), "Friend", existingTag);

        assertThrows(CommandException.class,
                AddTagToRelationshipCommand.MESSAGE_DUPLICATE_TAG, () -> command.execute(modelStub));
    }

    @Test
    public void execute_validTag_success() throws Exception {
        Person person1 = new PersonBuilder().build();
        Person person2 = new PersonBuilder().withName("Bob").build();
        Tag newTag = new Tag("new");

        Relationship relationship = new RelationshipBuilder()
                .withUser1Id(person1.getId())
                .withUser2Id(person2.getId())
                .build();

        ModelStubWithRelationship modelStub = new ModelStubWithRelationship(relationship);
        AddTagToRelationshipCommand command = new AddTagToRelationshipCommand(
                person1.getId(), person2.getId(), "Friend", newTag);

        CommandResult result = command.execute(modelStub);
        assertEquals(String.format(AddTagToRelationshipCommand.MESSAGE_SUCCESS,
                relationship.withAddedTag(newTag)), result.getFeedbackToUser());
        assertTrue(modelStub.isRelationshipUpdated);
    }

    @Test
    public void equals() {
        String userId1 = "user1";
        String userId2 = "user2";
        String relationshipName = "Friend";
        Tag tag1 = new Tag("tag1");
        Tag tag2 = new Tag("tag2");

        AddTagToRelationshipCommand command1 = new AddTagToRelationshipCommand(userId1, userId2, relationshipName,
                tag1);
        AddTagToRelationshipCommand command1Copy = new AddTagToRelationshipCommand(userId1, userId2, relationshipName,
                tag1);
        AddTagToRelationshipCommand command2 = new AddTagToRelationshipCommand(userId1, userId2, relationshipName,
                tag2);
        AddTagToRelationshipCommand commandDifferentUser1 = new AddTagToRelationshipCommand("other", userId2,
                relationshipName, tag1);
        AddTagToRelationshipCommand commandDifferentUser2 = new AddTagToRelationshipCommand(userId1, "other",
                relationshipName, tag1);
        AddTagToRelationshipCommand commandDifferentName = new AddTagToRelationshipCommand(userId1, userId2, "Other",
                tag1);

        // same object -> returns true
        assertEquals(command1, command1);

        // same values -> returns true
        assertEquals(command1, command1Copy);

        // null -> returns false
        assertNotEquals(null, command1);

        // different tag -> returns false
        assertNotEquals(command1, command2);

        // different user ID 1 -> returns false
        assertNotEquals(command1, commandDifferentUser1);

        // different user ID 2 -> returns false
        assertNotEquals(command1, commandDifferentUser2);

        // different relationship name -> returns false
        assertNotEquals(command1, commandDifferentName);
    }

    @Test
    public void hashCode_test() {
        String userId1 = "user1";
        String userId2 = "user2";
        String relationshipName = "Friend";
        Tag tag1 = new Tag("tag1");
        Tag tag2 = new Tag("tag2");

        AddTagToRelationshipCommand command1 = new AddTagToRelationshipCommand(userId1, userId2, relationshipName,
                tag1);
        AddTagToRelationshipCommand command1Copy = new AddTagToRelationshipCommand(userId1, userId2, relationshipName,
                tag1);
        AddTagToRelationshipCommand command2 = new AddTagToRelationshipCommand(userId1, userId2, relationshipName,
                tag2);

        // Same values -> same hashcode
        assertEquals(command1.hashCode(), command1Copy.hashCode());

        // Different tag -> different hashcode
        assertNotEquals(command1.hashCode(), command2.hashCode());

        // Different user ID 1 -> different hashcode
        AddTagToRelationshipCommand commandDifferentUser1 = new AddTagToRelationshipCommand("other", userId2,
                relationshipName, tag1);
        assertNotEquals(command1.hashCode(), commandDifferentUser1.hashCode());

        // Different user ID 2 -> different hashcode
        AddTagToRelationshipCommand commandDifferentUser2 = new AddTagToRelationshipCommand(userId1, "other",
                relationshipName, tag1);
        assertNotEquals(command1.hashCode(), commandDifferentUser2.hashCode());

        // Different relationship name -> different hashcode
        AddTagToRelationshipCommand commandDifferentName = new AddTagToRelationshipCommand(userId1, userId2, "Other",
                tag1);
        assertNotEquals(command1.hashCode(), commandDifferentName.hashCode());
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
            return relationship;
        }

        @Override
        public void updateRelationship(Relationship target, Relationship updatedRelationship) {
            isRelationshipUpdated = true;
        }
    }
}
