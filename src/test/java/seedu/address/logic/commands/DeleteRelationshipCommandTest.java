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
    public void execute_relationshipDeletedByModel_deleteSuccessful() throws Exception {
        ModelStubAcceptingRelationshipDeleted modelStub = new ModelStubAcceptingRelationshipDeleted();
        Person person1 = new PersonBuilder().build();
        Person person2 = new PersonBuilder().withName("Bob").withPhone("87654321").build();
        modelStub.addPerson(person1);
        modelStub.addPerson(person2);

        Relationship relationship = new RelationshipBuilder()
                .withUser1Id(person1.getId())
                .withUser2Id(person2.getId())
                .build();
        modelStub.addRelationship(relationship);

        CommandResult commandResult = new DeleteRelationshipCommand(
                person1.getId(), person2.getId(), "Friend").execute(modelStub);

        assertEquals(DeleteRelationshipCommand.MESSAGE_SUCCESS, commandResult.getFeedbackToUser());
        assertTrue(modelStub.relationshipDeleted);
    }

    @Test
    public void execute_relationshipNotFound_throwsCommandException() {
        Person person1 = new PersonBuilder().build();
        Person person2 = new PersonBuilder().withName("Bob").withPhone("87654321").build();

        ModelStub modelStub = new ModelStubWithPersons(person1, person2);

        DeleteRelationshipCommand deleteRelationshipCommand = new DeleteRelationshipCommand(
                person1.getId(), person2.getId(), "Friend");

        assertThrows(CommandException.class,
                DeleteRelationshipCommand.MESSAGE_RELATIONSHIP_NOT_FOUND,
                () -> deleteRelationshipCommand.execute(modelStub));
    }

    @Test
    public void execute_missingParameters_throwsCommandException() {
        Person person1 = new PersonBuilder().build();
        Person person2 = new PersonBuilder().withName("Bob").withPhone("87654321").build();

        ModelStub modelStub = new ModelStubWithPersons(person1, person2);

        DeleteRelationshipCommand deleteRelationshipCommand = new DeleteRelationshipCommand(
                person1.getId(), person2.getId(), "");

        assertThrows(CommandException.class,
                DeleteRelationshipCommand.MESSAGE_MISSING_PARAMETERS,
                () -> deleteRelationshipCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        String user1Id = "1";
        String user2Id = "2";
        String name1 = "Friend";
        String name2 = "BusinessPartner";

        DeleteRelationshipCommand deleteFriendCommand = new DeleteRelationshipCommand(
                user1Id, user2Id, name1);
        DeleteRelationshipCommand deleteBusinessPartnerCommand = new DeleteRelationshipCommand(
                user1Id, user2Id, name2);

        // same object -> returns true
        assertEquals(deleteFriendCommand, deleteFriendCommand);

        // same values -> returns true
        DeleteRelationshipCommand deleteFriendCommandCopy = new DeleteRelationshipCommand(
                user1Id, user2Id, name1);
        assertEquals(deleteFriendCommand, deleteFriendCommandCopy);

        // different types -> returns false
        assertNotEquals(1, deleteFriendCommand);

        // null -> returns false
        assertNotEquals(null, deleteFriendCommand);

        // different name -> returns false
        assertNotEquals(deleteFriendCommand, deleteBusinessPartnerCommand);
    }

    /**
     * A Model stub that contains two persons.
     */
    private class ModelStubWithPersons extends ModelStub {
        private final Person person1;
        private final Person person2;

        ModelStubWithPersons(Person person1, Person person2) {
            requireNonNull(person1);
            requireNonNull(person2);
            this.person1 = person1;
            this.person2 = person2;
        }

        @Override
        public Person getPersonById(String id) {
            if (person1.getId().equals(id)) {
                return person1;
            } else if (person2.getId().equals(id)) {
                return person2;
            }
            return null;
        }

        @Override
        public void deleteRelationship(String userId1, String userId2, String relationshipName)
                throws RelationshipNotFoundException {
            throw new RelationshipNotFoundException();
        }
    }

    /**
     * A Model stub that always accepts the relationship being deleted.
     */
    private class ModelStubAcceptingRelationshipDeleted extends ModelStub {
        private final java.util.ArrayList<Person> persons = new java.util.ArrayList<>();
        private final java.util.ArrayList<Relationship> relationships = new java.util.ArrayList<>();
        private boolean relationshipDeleted = false;

        @Override
        public Person getPersonById(String id) {
            requireNonNull(id);
            return persons.stream()
                    .filter(p -> p.getId().equals(id))
                    .findFirst()
                    .orElse(null);
        }

        @Override
        public void addPerson(Person person) {
            requireNonNull(person);
            persons.add(person);
        }

        @Override
        public void addRelationship(Relationship relationship) {
            requireNonNull(relationship);
            relationships.add(relationship);
        }

        @Override
        public void deleteRelationship(String userId1, String userId2, String relationshipName)
                throws RelationshipNotFoundException {
            requireNonNull(userId1);
            requireNonNull(userId2);
            requireNonNull(relationshipName);

            boolean found = false;
            for (Relationship r : relationships) {
                if (r.isSameRelationship(userId1, userId2, relationshipName)) {
                    found = true;
                    break;
                }
            }

            if (!found) {
                throw new RelationshipNotFoundException();
            }

            relationshipDeleted = true;
        }
    }
}