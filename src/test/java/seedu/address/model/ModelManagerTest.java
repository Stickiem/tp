package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EVENTS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_RELATIONSHIPS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.CARL;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.DateParserUtil;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.predicate.NameContainsKeywordsPredicate;
import seedu.address.model.relationship.Relationship;
import seedu.address.model.relationship.exceptions.RelationshipNotFoundException;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.RelationshipBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new AddressBook(), new AddressBook(modelManager.getAddressBook()));
        assertEquals(FXCollections.observableArrayList(), modelManager.getFilteredRelationshipList());
        assertEquals(FXCollections.observableArrayList(), modelManager.getFilteredEventList());
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setAddressBookFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setAddressBookFilePath(null));
    }

    @Test
    public void setAddressBookFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setAddressBookFilePath(path);
        assertEquals(path, modelManager.getAddressBookFilePath());
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        modelManager.addPerson(ALICE);
        assertTrue(modelManager.hasPerson(ALICE));
    }

    @Test
    public void deletePerson_personInAddressBookAndHasRelationship_deletesPersonAndRelationship() {
        Person alice = new PersonBuilder(ALICE).build();
        Person benson = new PersonBuilder(BENSON).build();
        Relationship relationship = new RelationshipBuilder()
                .withUser1Id(alice.getId()).withUser2Id(benson.getId()).build();

        modelManager.addPerson(alice);
        modelManager.addPerson(benson);
        modelManager.addRelationship(relationship);

        assertTrue(modelManager.hasPerson(alice));
        assertTrue(modelManager.hasRelationship(relationship));

        modelManager.deletePerson(alice);

        assertFalse(modelManager.hasPerson(alice));
    }

    @Test
    public void setPerson_validTargetAndEditedPerson_success() {
        modelManager.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress("New Address").build();
        modelManager.setPerson(ALICE, editedAlice);
        assertEquals(editedAlice, modelManager.getFilteredPersonList().get(0));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredPersonList().remove(0));
    }

    @Test
    public void updateSortedPersonList_validComparator_sortsList() throws CommandException {
        modelManager.addPerson(BENSON);
        modelManager.addPerson(ALICE);
        modelManager.addPerson(CARL);

        // Sort A-Z
        Comparator<Person> nameComparator = Comparator.comparing(p -> p.getName().toString());
        modelManager.updateSortedPersonList(nameComparator);
        ObservableList<Person> sortedList = modelManager.getSortedFilteredPersonList();
        assertEquals(ALICE, sortedList.get(0));
        assertEquals(BENSON, sortedList.get(1));
        assertEquals(CARL, sortedList.get(2));

        // Sort Z-A
        modelManager.updateSortedPersonList(nameComparator.reversed());
        sortedList = modelManager.getSortedFilteredPersonList();
        assertEquals(CARL, sortedList.get(0));
        assertEquals(BENSON, sortedList.get(1));
        assertEquals(ALICE, sortedList.get(2));
    }

    @Test
    public void hasRelationship_nullRelationship_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasRelationship(null));
    }

    @Test
    public void hasRelationship_relationshipNotInAddressBook_returnsFalse() {
        Relationship r = new RelationshipBuilder().build();
        assertFalse(modelManager.hasRelationship(r));
    }

    @Test
    public void hasRelationship_relationshipInAddressBook_returnsTrue() {
        Person p1 = new PersonBuilder().build();
        Person p2 = new PersonBuilder(BOB).build();
        Relationship r = new RelationshipBuilder().withUser1Id(p1.getId()).withUser2Id(p2.getId()).build();
        modelManager.addPerson(p1);
        modelManager.addPerson(p2);
        modelManager.addRelationship(r);
        assertTrue(modelManager.hasRelationship(r));
    }

    @Test
    public void hasRelationshipByIds_nullParams_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasRelationship(null, "id2", "friend"));
        assertThrows(NullPointerException.class, () -> modelManager.hasRelationship("id1", null, "friend"));
        assertThrows(NullPointerException.class, () -> modelManager.hasRelationship("id1", "id2", null));
    }

    @Test
    public void hasRelationshipByIds_relationshipExists_returnsTrue() {
        Person p1 = new PersonBuilder().build();
        Person p2 = new PersonBuilder(BOB).build();
        Relationship r = new RelationshipBuilder().withUser1Id(p1.getId()).withUser2Id(p2.getId()).build();
        modelManager.addPerson(p1);
        modelManager.addPerson(p2);
        modelManager.addRelationship(r);

        assertTrue(modelManager.hasRelationship(p1.getId(), p2.getId(), r.getForwardName()));
        assertTrue(modelManager.hasRelationship(p2.getId(), p1.getId(), r.getReverseName()));
    }

    @Test
    public void hasRelationshipByIds_relationshipDoesNotExist_returnsFalse() {
        assertFalse(modelManager.hasRelationship("id1", "id2", "friend"));
    }

    @Test
    public void hasAnyRelationship_nullParams_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasAnyRelationship(null, "id2"));
        assertThrows(NullPointerException.class, () -> modelManager.hasAnyRelationship("id1", null));
    }

    @Test
    public void hasAnyRelationship_relationshipExists_returnsTrue() {
        Person p1 = new PersonBuilder().build();
        Person p2 = new PersonBuilder(BOB).build();
        Relationship r = new RelationshipBuilder().withUser1Id(p1.getId()).withUser2Id(p2.getId()).build();
        modelManager.addPerson(p1);
        modelManager.addPerson(p2);
        modelManager.addRelationship(r);

        assertTrue(modelManager.hasAnyRelationship(p1.getId(), p2.getId()));
        assertTrue(modelManager.hasAnyRelationship(p2.getId(), p1.getId()));
    }

    @Test
    public void hasAnyRelationship_relationshipDoesNotExist_returnsFalse() {
        assertFalse(modelManager.hasAnyRelationship("id1", "id2"));
    }

    @Test
    public void deleteRelationship_relationshipExists_success() throws RelationshipNotFoundException {
        Person p1 = new PersonBuilder().build();
        Person p2 = new PersonBuilder(BOB).build();
        Relationship r = new RelationshipBuilder().withUser1Id(p1.getId()).withUser2Id(p2.getId()).build();
        modelManager.addPerson(p1);
        modelManager.addPerson(p2);
        modelManager.addRelationship(r);

        assertTrue(modelManager.hasRelationship(r));
        modelManager.deleteRelationship(p1.getId(), p2.getId(), r.getForwardName());
        assertFalse(modelManager.hasRelationship(r));
    }

    @Test
    public void deleteRelationship_relationshipDoesNotExist_throwsRelationshipNotFoundException() {
        assertThrows(RelationshipNotFoundException.class, () ->
                modelManager.deleteRelationship("id1", "id2", "friend"));
    }

    @Test
    public void getRelationship_relationshipExists_returnsRelationship() {
        Person p1 = new PersonBuilder().build();
        Person p2 = new PersonBuilder(BOB).build();
        Relationship r = new RelationshipBuilder().withUser1Id(p1.getId()).withUser2Id(p2.getId()).build();
        modelManager.addPerson(p1);
        modelManager.addPerson(p2);
        modelManager.addRelationship(r);

        assertEquals(r, modelManager.getRelationship(p1.getId(), p2.getId(), r.getForwardName()));
        assertEquals(r, modelManager.getRelationship(p2.getId(), p1.getId(), r.getReverseName()));
    }

    @Test
    public void getRelationship_relationshipDoesNotExist_returnsNull() {
        assertNull(modelManager.getRelationship("id1", "id2", "friend"));
    }

    @Test
    public void updateRelationship_validTargetAndEdited_success() {
        Person p1 = new PersonBuilder().build();
        Person p2 = new PersonBuilder(BOB).build();
        Relationship originalRel = new RelationshipBuilder().withUser1Id(p1.getId()).withUser2Id(p2.getId()).build();
        modelManager.addPerson(p1);
        modelManager.addPerson(p2);
        modelManager.addRelationship(originalRel);

        Relationship editedRel = new RelationshipBuilder(originalRel).withForwardName("Supervisor").build();
        modelManager.updateRelationship(originalRel, editedRel);

        assertTrue(modelManager.hasRelationship(editedRel));
        assertFalse(modelManager.hasRelationship(originalRel));
    }

    @Test
    public void updateFilteredRelationshipList_nullPredicate_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.updateFilteredRelationshipList(null));
    }

    @Test
    public void updateFilteredRelationshipList_validPredicate_filtersList() {
        Person p1 = new PersonBuilder().build();
        Person p2 = new PersonBuilder(BOB).build();
        Person p3 = new PersonBuilder(CARL).build();
        Relationship r1 = new RelationshipBuilder().withUser1Id(p1.getId()).withUser2Id(p2.getId())
                .withForwardName("Friend").withReverseName("Friend").build();
        Relationship r2 = new RelationshipBuilder().withUser1Id(p1.getId()).withUser2Id(p3.getId())
                .withForwardName("Colleague").withReverseName("Colleague").build();
        modelManager.addPerson(p1);
        modelManager.addPerson(p2);
        modelManager.addPerson(p3);
        modelManager.addRelationship(r1);
        modelManager.addRelationship(r2);

        assertEquals(2, modelManager.getFilteredRelationshipList().size());

        Predicate<Relationship> friendPredicate = r -> r.getForwardName().equals("Friend");
        modelManager.updateFilteredRelationshipList(friendPredicate);
        assertEquals(1, modelManager.getFilteredRelationshipList().size());
        assertEquals(r1, modelManager.getFilteredRelationshipList().get(0));
    }

    private Event createSampleEvent(String name) {
        try {
            return new Event(name, DateParserUtil.parseDate("2025-01-01"), null, null, null, new UniquePersonList());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void hasEvent_nullEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasEvent(null));
    }

    @Test
    public void hasEvent_eventNotInAddressBook_returnsFalse() {
        Event event = createSampleEvent("Meeting");
        assertFalse(modelManager.hasEvent(event));
    }

    @Test
    public void hasEvent_eventInAddressBook_returnsTrue() {
        Event event = createSampleEvent("Meeting");
        modelManager.addEvent(event);
        assertTrue(modelManager.hasEvent(event));
    }

    @Test
    public void addEvent_validEvent_success() {
        Event event = createSampleEvent("Team Lunch");
        modelManager.addEvent(event);
        assertTrue(modelManager.hasEvent(event));
        assertEquals(1, modelManager.getFilteredEventList().size());
    }

    @Test
    public void updateEvent_nullParameters_throwsNullPointerException() {
        Event event = createSampleEvent("Meeting");
        assertThrows(NullPointerException.class, () -> modelManager.updateEvent(null, event));
        assertThrows(NullPointerException.class, () -> modelManager.updateEvent(event, null));
    }

    @Test
    public void deleteEvent_eventExists_success() {
        Event event = createSampleEvent("Meeting");
        modelManager.addEvent(event);
        assertTrue(modelManager.hasEvent(event));

        modelManager.deleteEvent(event);
        assertFalse(modelManager.hasEvent(event));
        assertTrue(modelManager.getFilteredEventList().isEmpty());
    }

    @Test
    public void getFilteredEventList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () ->
                modelManager.getFilteredEventList().remove(0));
    }

    @Test
    public void updateFilteredEventList_nullPredicate_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.updateFilteredEventList(null));
    }

    @Test
    public void getEventById_nullId_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.getEventById(null));
    }

    @Test
    public void getEventById_eventExists_returnsEvent() {
        Event event = createSampleEvent("Meeting");
        modelManager.addEvent(event);
        assertEquals(event, modelManager.getEventById(event.getId()));
    }

    @Test
    public void getEventById_eventDoesNotExist_returnsNull() {
        assertNull(modelManager.getEventById("nonexistent-id"));
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        AddressBook differentAddressBook = new AddressBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(addressBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBook, userPrefs);
        assertEquals(modelManager, modelManagerCopy);

        // same object -> returns true
        assertEquals(modelManager, modelManager);

        // null -> returns false
        assertNotEquals(null, modelManager);

        // different addressBook -> returns false
        assertNotEquals(modelManager, new ModelManager(differentAddressBook, userPrefs));

        // different filteredPersonList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertNotEquals(modelManager, new ModelManager(addressBook, userPrefs));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // different filteredRelationshipList -> returns false
        Relationship r1 = new RelationshipBuilder().withUser1Id(ALICE.getId()).withUser2Id(BENSON.getId()).build();
        modelManager.addRelationship(r1);
        ModelManager modelManagerWithRelationship = new ModelManager(addressBook, userPrefs);
        modelManagerWithRelationship.addRelationship(r1);
        modelManager.updateFilteredRelationshipList(rel -> false);
        assertNotEquals(modelManager, modelManagerWithRelationship);
        modelManager.updateFilteredRelationshipList(PREDICATE_SHOW_ALL_RELATIONSHIPS);

        // different filteredEventList -> returns false
        Event event1 = createSampleEvent("Event 1");
        modelManager.addEvent(event1);
        ModelManager modelManagerWithEvent = new ModelManager(addressBook, userPrefs);
        modelManagerWithEvent.addRelationship(r1);
        modelManagerWithEvent.addEvent(event1);
        modelManager.updateFilteredEventList(evt -> false);
        assertNotEquals(modelManager, modelManagerWithEvent);
        modelManager.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);

        // different GUI settings -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertNotEquals(modelManager, new ModelManager(addressBook, differentUserPrefs));
    }
}
