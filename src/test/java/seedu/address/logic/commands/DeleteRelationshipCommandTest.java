package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.relationship.Relationship;
import seedu.address.model.relationship.exceptions.RelationshipNotFoundException;
import seedu.address.testutil.ModelStub;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.RelationshipBuilder;

public class DeleteRelationshipCommandTest {

    @Test
    public void constructor_nullParameters_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeleteRelationshipCommand(null, "2", "Friend"));
        assertThrows(NullPointerException.class, () -> new DeleteRelationshipCommand("1", null, "Friend"));
        assertThrows(NullPointerException.class, () -> new DeleteRelationshipCommand("1", "2", null));
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        DeleteRelationshipCommand command = new DeleteRelationshipCommand("1", "2", "Friend");
        assertThrows(NullPointerException.class, () -> command.execute(null));
    }

    @Test
    public void execute_emptyParameters_throwsCommandException() {
        ModelStub modelStub = new ModelStubWithPersons(null, null);
        DeleteRelationshipCommand command = new DeleteRelationshipCommand("", "2", "Friend");
        DeleteRelationshipCommand finalCommand = command;
        assertThrows(CommandException.class, DeleteRelationshipCommand.MESSAGE_PERSONS_NOT_FOUND, () ->
                finalCommand.execute(modelStub));

        command = new DeleteRelationshipCommand("1", "", "Friend");
        DeleteRelationshipCommand finalCommand1 = command;
        assertThrows(CommandException.class, DeleteRelationshipCommand.MESSAGE_PERSONS_NOT_FOUND, () ->
                finalCommand1.execute(modelStub));

        command = new DeleteRelationshipCommand("1", "2", "  ");
        DeleteRelationshipCommand finalCommand2 = command;
        assertThrows(CommandException.class, DeleteRelationshipCommand.MESSAGE_PERSONS_NOT_FOUND, () ->
                finalCommand2.execute(modelStub));
    }

    @Test
    public void execute_personsNotFound_throwsCommandException() {
        // Both persons not found
        ModelStub modelStub = new ModelStubWithPersons(null, null);
        DeleteRelationshipCommand command = new DeleteRelationshipCommand("1", "2", "Friend");
        ModelStub finalModelStub = modelStub;
        assertThrows(CommandException.class, DeleteRelationshipCommand.MESSAGE_PERSONS_NOT_FOUND, () ->
                command.execute(finalModelStub));

        // First person not found
        Person secondPerson = new PersonBuilder().build();
        modelStub = new ModelStubWithPersons(null, secondPerson);
        ModelStub finalModelStub1 = modelStub;
        assertThrows(CommandException.class, DeleteRelationshipCommand.MESSAGE_PERSONS_NOT_FOUND, () ->
                command.execute(finalModelStub1));

        // Second person not found
        Person firstPerson = new PersonBuilder().build();
        modelStub = new ModelStubWithPersons(firstPerson, null);
        ModelStub finalModelStub2 = modelStub;
        assertThrows(CommandException.class, DeleteRelationshipCommand.MESSAGE_PERSONS_NOT_FOUND, () ->
                command.execute(finalModelStub2));
    }

    @Test
    public void execute_relationshipNotFound_throwsCommandException() {
        Person person1 = new PersonBuilder().withName("Alice").build();
        Person person2 = new PersonBuilder().withName("Bob").build();
        ModelStub modelStub = new ModelStubWithPersonsNoRelationship(person1, person2);

        DeleteRelationshipCommand command = new DeleteRelationshipCommand(
                person1.getId(), person2.getId(), "NonexistentRelationship");

        assertThrows(CommandException.class, DeleteRelationshipCommand.MESSAGE_RELATIONSHIP_NOT_FOUND, () ->
                command.execute(modelStub));
    }

    @Test
    public void execute_validRelationship_success() throws Exception {
        Person person1 = new PersonBuilder().withName("Alice").build();
        Person person2 = new PersonBuilder().withName("Bob").build();
        ModelStubWithRelationship modelStub = new ModelStubWithRelationship(person1, person2);

        DeleteRelationshipCommand command = new DeleteRelationshipCommand(
                person1.getId(), person2.getId(), "Friend");

        CommandResult result = command.execute(modelStub);
        assertEquals(String.format(DeleteRelationshipCommand.MESSAGE_SUCCESS,
                person1.getName(), person2.getName()), result.getFeedbackToUser());
        assertTrue(modelStub.isRelationshipDeleted);
    }

    @Test
    public void equals() {
        DeleteRelationshipCommand command1 = new DeleteRelationshipCommand("1", "2", "Friend");
        DeleteRelationshipCommand command2 = new DeleteRelationshipCommand("1", "2", "Friend");
        DeleteRelationshipCommand command3 = new DeleteRelationshipCommand("2", "3", "Colleague");

        // same object -> returns true
        assertEquals(command1, command1);

        // same values -> returns true
        assertEquals(command1, command2);

        // different values -> returns false
        assertNotEquals(command1, command3);

        // null -> returns false
        assertNotEquals(null, command1);
    }

    @Test
    public void toString_returnsCorrectFormat() {
        DeleteRelationshipCommand command = new DeleteRelationshipCommand("1", "2", "Friend");
        assertEquals("DeleteRelationshipCommand: Deleting relationship 'Friend' between 1 and 2",
                command.toString());
    }

    private static class ModelStubWithPersons extends ModelStub {
        private final Person person1;
        private final Person person2;

        ModelStubWithPersons(Person person1, Person person2) {
            this.person1 = person1;
            this.person2 = person2;
        }

        @Override
        public Person getPersonById(String id) {
            if (person1 != null && person1.getId().equals(id)) {
                return person1;
            }
            if (person2 != null && person2.getId().equals(id)) {
                return person2;
            }
            return null;
        }
    }

    private static class ModelStubWithPersonsNoRelationship extends ModelStubWithPersons {
        ModelStubWithPersonsNoRelationship(Person person1, Person person2) {
            super(person1, person2);
        }

        @Override
        public void deleteRelationship(String userId1, String userId2, String relationshipName)
                throws RelationshipNotFoundException {
            throw new RelationshipNotFoundException();
        }
    }

    private static class ModelStubWithRelationship extends ModelStubWithPersons {
        private boolean isRelationshipDeleted = false;

        ModelStubWithRelationship(Person person1, Person person2) {
            super(person1, person2);
            Relationship relationship = new RelationshipBuilder()
                    .withUser1Id(person1.getId())
                    .withUser2Id(person2.getId())
                    .build();
        }

        @Override
        public void deleteRelationship(String userId1, String userId2, String relationshipName) {
            requireNonNull(userId1);
            requireNonNull(userId2);
            requireNonNull(relationshipName);
            isRelationshipDeleted = true;
        }
    }
}
