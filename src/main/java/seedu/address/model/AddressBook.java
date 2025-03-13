package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.Objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.relationship.Relationship;
import seedu.address.model.relationship.UniqueRelationshipList;
import seedu.address.model.relationship.exceptions.RelationshipNotFoundException;
import seedu.address.model.event.Event;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final UniqueRelationshipList relationships;
    // Changed events field to use a standard ObservableList instead of UniqueEventList.
    private final ObservableList<Event> events;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors.
     */
    {
        persons = new UniquePersonList();
        relationships = new UniqueRelationshipList();
        events = FXCollections.observableArrayList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }

    /**
     * Replaces the contents of the events list with {@code events}.
     * {@code events} must not contain duplicate events.
     */
    public void setEvents(List<Event> events) {
        this.events.setAll(events);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);
        setPersons(newData.getPersonList());
        setEvents(newData.getEventList());
    }

    //// Person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addPerson(Person p) {
        persons.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);
        persons.setPerson(target, editedPerson);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Person key) {
        persons.remove(key);
    }

    //// Relationship-level operations

    /**
     * Returns true if a relationship with the same identity fields as {@code relationship} exists in the address book.
     */
    public boolean hasRelationship(Relationship relationship) {
        requireNonNull(relationship);
        return relationships.contains(relationship);
    }

    /**
     * Returns true if a relationship with the given user IDs and name exists in the address book.
     */
    public boolean hasRelationship(String userId1, String userId2, String relationshipName) {
        requireAllNonNull(userId1, userId2, relationshipName);
        return relationships.contains(userId1, userId2, relationshipName);
    }

    /**
     * Adds a relationship to the address book.
     * The relationship must not already exist in the address book.
     */
    public void addRelationship(Relationship r) {
        relationships.add(r);
    }

    /**
     * Removes the relationship with the given user IDs and name from the address book.
     * The relationship must exist in the address book.
     */
    public void removeRelationship(String userId1, String userId2, String relationshipName)
            throws RelationshipNotFoundException {
        relationships.remove(userId1, userId2, relationshipName);
    }

    /**
     * Returns a person with the given ID, or null if not found.
     */
    public Person getPersonById(String id) {
        requireNonNull(id);
        return persons.asUnmodifiableObservableList().stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    /**
     * Returns an unmodifiable view of the relationships list.
     */
    public ObservableList<Relationship> getRelationshipList() {
        return relationships.asUnmodifiableObservableList();
    }

    //// Event-level operations

    /**
     * Returns true if an event with the same identity as {@code event} exists in the address book.
     */
    public boolean hasEvent(Event event) {
        requireNonNull(event);
        return events.stream().anyMatch(event::equals);
    }

    /**
     * Adds an event to the address book.
     * The event must not already exist in the address book.
     */
    public void addEvent(Event event) {
        events.add(event);
    }

    /**
     * Removes the given event from the address book.
     * The event must exist in the address book.
     */
    public void deleteEvent(Event event) {
        events.remove(event);
    }

    /**
     * Returns an unmodifiable view of the events list.
     */
    @Override
    public ObservableList<Event> getEventList() {
        return FXCollections.unmodifiableObservableList(events);
    }

    //// Util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("persons", persons)
                .add("relationships", relationships)
                .add("events", events)
                .toString();
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        // Update equals method to include relationships and events
        if (other == this) {
            return true;
        }
        if (!(other instanceof AddressBook otherAddressBook)) {
            return false;
        }
        return persons.equals(otherAddressBook.persons)
                && relationships.equals(otherAddressBook.relationships)
                && events.equals(otherAddressBook.events);
    }

    @Override
    public int hashCode() {
        return Objects.hash(persons, relationships, events);
    }
}
