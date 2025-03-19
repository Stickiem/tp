package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.relationship.Relationship;
import seedu.address.testutil.ModelStub;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.RelationshipBuilder;

public class AddRelationshipCommandTest {

    @Test
    public void constructor_nullRelationship_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddRelationshipCommand(
                null,
                "2",
                "Friend",
                new HashSet<>()));
        assertThrows(NullPointerException.class, () -> new AddRelationshipCommand(
                "1",
                null,
                "Friend",
                new HashSet<>()));
        assertThrows(NullPointerException.class, () -> new AddRelationshipCommand(
                "1",
                "2",
                null,
                new HashSet<>()));
        assertThrows(NullPointerException.class, () -> new AddRelationshipCommand(
                "1",
                "2",
                "Friend",
                null));
    }

    @Test
    public void execute_relationshipAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingRelationshipAdded modelStub = new ModelStubAcceptingRelationshipAdded();
        Person person1 = new PersonBuilder().build();
        Person person2 = new PersonBuilder().withName("Bob").withPhone("87654321").build();
        modelStub.addPerson(person1);
        modelStub.addPerson(person2);

        Relationship validRelationship = new RelationshipBuilder()
                .withUser1Id(person1.getId())
                .withUser2Id(person2.getId())
                .build();

        CommandResult commandResult = new AddRelationshipCommand(
                person1.getId(), person2.getId(), "Friend", new HashSet<>()).execute(modelStub);

        assertEquals(AddRelationshipCommand.MESSAGE_SUCCESS, commandResult.getFeedbackToUser());
        assertEquals(validRelationship, modelStub.relationshipAdded);
    }

    @Test
    public void execute_duplicateRelationship_throwsCommandException() {
        Person person1 = new PersonBuilder().build();
        Person person2 = new PersonBuilder().withName("Bob").withPhone("87654321").build();

        ModelStub modelStub = new ModelStubWithPersonsAndRelationship(person1, person2);

        AddRelationshipCommand addRelationshipCommand = new AddRelationshipCommand(
                person1.getId(), person2.getId(), "Friend", new HashSet<>());

        assertThrows(CommandException.class,
                AddRelationshipCommand.MESSAGE_DUPLICATE_RELATIONSHIP, () -> addRelationshipCommand.execute(modelStub));
    }

    @Test
    public void execute_samePerson_throwsCommandException() {
        Person person = new PersonBuilder().build();

        ModelStub modelStub = new ModelStubWithPerson(person);

        AddRelationshipCommand addRelationshipCommand = new AddRelationshipCommand(
                person.getId(), person.getId(), "Friend", new HashSet<>());

        assertThrows(CommandException.class,
                AddRelationshipCommand.MESSAGE_SAME_PERSON, () -> addRelationshipCommand.execute(modelStub));
    }

    @Test
    public void execute_emptyName_throwsCommandException() {
        Person person1 = new PersonBuilder().build();
        Person person2 = new PersonBuilder().withName("Bob").withPhone("87654321").build();

        ModelStub modelStub = new ModelStubWithPersons(person1, person2);

        AddRelationshipCommand addRelationshipCommand = new AddRelationshipCommand(
                person1.getId(), person2.getId(), "", new HashSet<>());

        assertThrows(CommandException.class,
                AddRelationshipCommand.MESSAGE_EMPTY_NAME, () -> addRelationshipCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        String user1Id = "1";
        String user2Id = "2";
        String name1 = "Friend";
        String name2 = "BusinessPartner";

        AddRelationshipCommand addFriendCommand = new AddRelationshipCommand(
                user1Id, user2Id, name1, new HashSet<>());
        AddRelationshipCommand addBusinessPartnerCommand = new AddRelationshipCommand(
                user1Id, user2Id, name2, new HashSet<>());

        // same object -> returns true
        assertEquals(addFriendCommand, addFriendCommand);

        // same values -> returns true
        AddRelationshipCommand addFriendCommandCopy = new AddRelationshipCommand(
                user1Id, user2Id, name1, new HashSet<>());
        assertEquals(addFriendCommand, addFriendCommandCopy);

        // different types -> returns false
        assertNotEquals(1, addFriendCommand);

        // null -> returns false
        assertNotEquals(null, addFriendCommand);

        // different name -> returns false
        assertNotEquals(addFriendCommand, addBusinessPartnerCommand);
    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithPerson extends ModelStub {
        private final Person person;

        ModelStubWithPerson(Person person) {
            requireNonNull(person);
            this.person = person;
        }

        @Override
        public Person getPersonById(String id) {
            if (person.getId().equals(id)) {
                return person;
            }
            return null;
        }
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
    }

    /**
     * A Model stub that contains two persons and a relationship between them.
     */
    private class ModelStubWithPersonsAndRelationship extends ModelStubWithPersons {
        private final Relationship relationship;

        ModelStubWithPersonsAndRelationship(Person person1, Person person2) {
            super(person1, person2);
            this.relationship = new RelationshipBuilder()
                    .withUser1Id(person1.getId())
                    .withUser2Id(person2.getId())
                    .build();
        }

        @Override
        public boolean hasRelationship(String userId1, String userId2, String relationshipName) {
            return relationship.isSameRelationship(userId1, userId2, relationshipName);
        }
    }

    /**
     * A Model stub that always accepts the relationship being added.
     */
    private class ModelStubAcceptingRelationshipAdded extends ModelStub {
        private Relationship relationshipAdded;
        private final java.util.ArrayList<Person> persons = new java.util.ArrayList<>();

        @Override
        public boolean hasRelationship(String userId1, String userId2, String relationshipName) {
            requireNonNull(userId1);
            requireNonNull(userId2);
            requireNonNull(relationshipName);
            return relationshipAdded != null
                    && relationshipAdded.isSameRelationship(userId1, userId2, relationshipName);
        }

        @Override
        public void addRelationship(Relationship relationship) {
            requireNonNull(relationship);
            relationshipAdded = relationship;
        }

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
    }
}
