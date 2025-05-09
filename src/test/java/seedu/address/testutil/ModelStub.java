package seedu.address.testutil;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.function.Predicate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;
import seedu.address.model.relationship.Relationship;
import seedu.address.model.relationship.exceptions.RelationshipNotFoundException;

/**
 * A default model stub that has all of the methods failing.
 */
public class ModelStub implements Model {
    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public GuiSettings getGuiSettings() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Path getAddressBookFilePath() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addPerson(Person person) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setAddressBook(ReadOnlyAddressBook newData) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Relationship> getFilteredRelationshipList() {
        return FXCollections.observableArrayList();
    }


    @Override
    public ReadOnlyAddressBook getAddressBook() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasPerson(Person person) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deletePerson(Person target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Person> getSortedFilteredPersonList() {
        return null;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateSortedPersonList(Comparator<Person> comparator) {

    }

    @Override
    public Person getPersonById(String id) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasRelationship(Relationship relationship) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasRelationship(String userId1, String userId2, String relationshipName) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasAnyRelationship(String userId1, String userId2) {
        return false;
    }

    @Override
    public void addRelationship(Relationship relationship) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteRelationship(String userId1, String userId2, String relationshipName)
            throws RelationshipNotFoundException {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasEvent(Event event) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addEvent(Event event) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteEvent(Event event) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateEvent(Event target, Event updatedEvent) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Relationship getRelationship(String userId1, String userId2, String relationshipName) {
        return null;
    }

    @Override
    public void updateRelationship(Relationship target, Relationship updatedRelationship) {

    }

    @Override
    public void updateFilteredRelationshipList(Predicate<Relationship> predicate) {

    }

    @Override
    public ObservableList<Event> getFilteredEventList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredEventList(Predicate<Event> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Event getEventById(String id) {
        throw new AssertionError("This method should not be called.");
    }
}
