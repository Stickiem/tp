package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;
import seedu.address.model.relationship.Relationship;
import seedu.address.model.relationship.exceptions.RelationshipNotFoundException;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Relationship> filteredRelationships;
    private final FilteredList<Event> filteredEvents;

    private Comparator<Person> sortComparator;
    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredRelationships = new FilteredList<>(this.addressBook.getRelationshipList());
        filteredEvents = new FilteredList<>(this.addressBook.getEventList()); // Initialize event list
        sortComparator = (person1, person2) -> 0;
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        addressBook.setPerson(target, editedPerson);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public ObservableList<Person> getSortedFilteredPersonList() {
        List<Person> personList = new ArrayList<>(filteredPersons);
        if (sortComparator != null) {
            personList.sort(sortComparator);
            logger.info("we have comparator");
        }

        return FXCollections.observableArrayList(personList);
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public void updateSortedPersonList(Comparator<Person> comparator) {
        requireNonNull(comparator);
        sortComparator = comparator;
        List<Person> personList = new ArrayList<>(addressBook.getPersonList());
        if (sortComparator != null) {
            personList.sort(sortComparator);
            System.out.println("we have comparator");
        }
        System.out.println("I changed it");
        addressBook.setPersons(personList);
        filteredPersons.setPredicate(PREDICATE_SHOW_ALL_PERSONS);
    }

    //=========== Relationship ================================================================================
    @Override
    public Person getPersonById(String id) {
        requireNonNull(id);
        return addressBook.getPersonById(id);
    }

    @Override
    public boolean hasRelationship(Relationship relationship) {
        requireNonNull(relationship);
        return addressBook.hasRelationship(relationship);
    }

    @Override
    public boolean hasRelationship(String userId1, String userId2, String relationshipName) {
        requireAllNonNull(userId1, userId2, relationshipName);
        return addressBook.hasRelationship(userId1, userId2, relationshipName);
    }

    @Override
    public void addRelationship(Relationship relationship) {
        addressBook.addRelationship(relationship);
    }

    @Override
    public void deleteRelationship(String userId1, String userId2, String relationshipName)
            throws RelationshipNotFoundException {
        addressBook.removeRelationship(userId1, userId2, relationshipName);
    }

    @Override
    public ObservableList<Relationship> getFilteredRelationshipList() {
        return filteredRelationships;
    }

    @Override
    public boolean hasEvent(Event event) {
        requireNonNull(event);
        return addressBook.hasEvent(event);
    }

    @Override
    public void addEvent(Event event) {
        addressBook.addEvent(event);
        updateFilteredEventList(Model.PREDICATE_SHOW_ALL_EVENTS);
    }

    @Override
    public void updateEvent(Event target, Event updatedEvent) {
        requireAllNonNull(target, updatedEvent);
        addressBook.setEvent(target, updatedEvent);
        updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
    }

    @Override
    public void deleteEvent(Event event) {
        addressBook.deleteEvent(event);
    }

    @Override
    public ObservableList<Event> getFilteredEventList() {
        return filteredEvents;
    }

    @Override
    public void updateFilteredEventList(Predicate<Event> predicate) {
        requireNonNull(predicate);
        filteredEvents.setPredicate(predicate);
    }

    @Override
    public Event getEventById(String id) {
        requireNonNull(id);
        for (Event event : filteredEvents) {
            if (event.getId().equals(id)) {
                return event;
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager otherModelManager)) {
            return false;
        }

        return addressBook.equals(otherModelManager.addressBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredPersons.equals(otherModelManager.filteredPersons)
                && filteredRelationships.equals(otherModelManager.filteredRelationships)
                && filteredEvents.equals(otherModelManager.filteredEvents);
    }

    @Override
    public Relationship getRelationship(String userId1, String userId2, String relationshipName) {
        return addressBook.getRelationship(userId1, userId2, relationshipName);
    }

    @Override
    public void updateRelationship(Relationship target, Relationship updatedRelationship) {
        requireAllNonNull(target, updatedRelationship);
        addressBook.updateRelationship(target, updatedRelationship);
    }
}
