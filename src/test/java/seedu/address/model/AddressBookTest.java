package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.relationship.Relationship;
import seedu.address.model.relationship.exceptions.RelationshipNotFoundException;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.RelationshipBuilder;

public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getPersonList());
        assertEquals(FXCollections.observableArrayList(), addressBook.getRelationshipList()); // Changed check
        assertEquals(FXCollections.observableArrayList(), addressBook.getEventList()); // Changed check
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        AddressBookStub newData = new AddressBookStub(newPersons);

        assertThrows(DuplicatePersonException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        addressBook.addPerson(ALICE);
        assertTrue(addressBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(addressBook.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getPersonList().remove(0));
    }

    @Test
    public void getRelationshipList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getRelationshipList().remove(0));
    }

    @Test
    public void getEventList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getEventList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = AddressBook.class.getCanonicalName()
                + "{persons=" + addressBook.getPersonList()
                + ", relationships=" + addressBook.getRelationshipList()
                + ", events=" + addressBook.getEventList() + "}";
        assertEquals(expected, addressBook.toString());
    }

    @Test
    public void hasRelationship_nullRelationship_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasRelationship((Relationship) null));
    }

    @Test
    public void hasRelationship_relationshipNotInAddressBook_returnsFalse() {
        Relationship relationship = new RelationshipBuilder().build();
        assertFalse(addressBook.hasRelationship(relationship));
    }

    @Test
    public void hasRelationship_relationshipInAddressBook_returnsTrue() {
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();
        addressBook.addPerson(alice);
        addressBook.addPerson(bob);

        Relationship relationship = new RelationshipBuilder()
                .withUser1Id(alice.getId())
                .withUser2Id(bob.getId())
                .build();
        addressBook.addRelationship(relationship);
        assertTrue(addressBook.hasRelationship(relationship));
    }

    @Test
    public void hasRelationshipByIds_nullParameters_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasRelationship(null, "2", "Friend"));
        assertThrows(NullPointerException.class, () -> addressBook.hasRelationship("1", null, "Friend"));
        assertThrows(NullPointerException.class, () -> addressBook.hasRelationship("1", "2", null));
    }

    @Test
    public void hasRelationshipByIds_relationshipNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasRelationship("1", "2", "Friend"));
    }

    @Test
    public void hasRelationshipByIds_relationshipInAddressBook_returnsTrue() {
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();
        addressBook.addPerson(alice);
        addressBook.addPerson(bob);

        Relationship relationship = new RelationshipBuilder()
                .withUser1Id(alice.getId())
                .withUser2Id(bob.getId())
                .build();
        addressBook.addRelationship(relationship);
        assertTrue(addressBook.hasRelationship(alice.getId(), bob.getId(), RelationshipBuilder.DEFAULT_FORWARD_NAME));
    }

    @Test
    public void removeRelationship_existingRelationship_removesRelationship() {
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();
        addressBook.addPerson(alice);
        addressBook.addPerson(bob);

        Relationship relationship = new RelationshipBuilder()
                .withUser1Id(alice.getId())
                .withUser2Id(bob.getId())
                .build();
        addressBook.addRelationship(relationship);

        addressBook.removeRelationship(alice.getId(), bob.getId(), RelationshipBuilder.DEFAULT_FORWARD_NAME);
        assertFalse(addressBook.hasRelationship(relationship));
    }

    @Test
    public void removeRelationship_nonExistingRelationship_throwsRelationshipNotFoundException() {
        assertThrows(RelationshipNotFoundException.class, () -> addressBook.removeRelationship("1", "2",
                RelationshipBuilder.DEFAULT_FORWARD_NAME));
    }

    @Test
    public void getPersonById_existingId_returnsPerson() {
        Person alice = new PersonBuilder().withName("Alice").build();
        addressBook.addPerson(alice);

        assertEquals(alice, addressBook.getPersonById(alice.getId()));
    }

    @Test
    public void getPersonById_nonExistingId_returnsNull() {
        assertNull(addressBook.getPersonById("nonexistent"));
    }

    /**
     * A stub ReadOnlyAddressBook whose persons list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private final ObservableList<Relationship> relationships = FXCollections.observableArrayList();
        private final ObservableList<Event> events = FXCollections.observableArrayList();

        AddressBookStub(Collection<Person> persons) {
            this.persons.setAll(persons);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }

        @Override
        public ObservableList<Relationship> getRelationshipList() {
            return relationships;
        }

        @Override
        public ObservableList<Event> getEventList() {
            return events;
        }
    }
}
