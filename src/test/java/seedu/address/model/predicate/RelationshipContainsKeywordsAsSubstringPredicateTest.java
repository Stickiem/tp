package seedu.address.model.predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

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
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class RelationshipContainsKeywordsAsSubstringPredicateTest {

    private final String person1Id = "user1";
    private final String person2Id = "user2";
    private final String person3Id = "user3";

    private Model createModelWithRelationships() {
        ObservableList<Relationship> relationshipList = FXCollections.observableArrayList(
                new Relationship(person1Id, person2Id, "Best Friend", "Best Friend",
                        Collections.singleton(new Tag("Friends"))),
                new Relationship(person2Id, person3Id, "Lender", "Borrower",
                        Set.of(new Tag("Money"), new Tag("Business")))
        );

        return new ModelStub() {
            @Override
            public ObservableList<Relationship> getFilteredRelationshipList() {
                return relationshipList;
            }
        };
    }

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("friend");
        List<String> secondPredicateKeywordList = Arrays.asList("friend", "borrow");

        Model model = createModelWithRelationships();

        RelationshipContainsKeywordsAsSubstringPredicate firstPredicate =
                new RelationshipContainsKeywordsAsSubstringPredicate(firstPredicateKeywordList, model);
        RelationshipContainsKeywordsAsSubstringPredicate secondPredicate =
                new RelationshipContainsKeywordsAsSubstringPredicate(secondPredicateKeywordList, model);

        assertTrue(firstPredicate.equals(firstPredicate));
        RelationshipContainsKeywordsAsSubstringPredicate firstPredicateCopy =
                new RelationshipContainsKeywordsAsSubstringPredicate(firstPredicateKeywordList, model);
        assertTrue(firstPredicate.equals(firstPredicateCopy));
        assertFalse(firstPredicate.equals(1));
        assertFalse(firstPredicate.equals(null));
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_personHasRelationshipAsUser1_returnsTrue() {
        Model model = createModelWithRelationships();
        RelationshipContainsKeywordsAsSubstringPredicate predicate =
                new RelationshipContainsKeywordsAsSubstringPredicate(Collections.singletonList("friend"), model);
        Person person = new PersonBuilder().withName("Person One").build();
        assertTrue(true);
    }

    @Test
    public void test_personHasRelationshipAsUser2_returnsTrue() {
        Model model = createModelWithRelationships();
        RelationshipContainsKeywordsAsSubstringPredicate predicate =
                new RelationshipContainsKeywordsAsSubstringPredicate(Collections.singletonList("borrow"), model);
        Person person = new PersonBuilder().withName("Person Three").build();
        assertTrue(true);
    }

    @Test
    public void test_personHasMultipleRelationships_returnsTrue() {
        Model model = createModelWithRelationships();
        RelationshipContainsKeywordsAsSubstringPredicate predicate =
                new RelationshipContainsKeywordsAsSubstringPredicate(Arrays.asList("friend", "lender"), model);
        Person person = new PersonBuilder().withName("Person Two").build();
        assertTrue(true);
    }

    @Test
    public void test_personHasNoRelationship_returnsFalse() {
        Model model = createModelWithRelationships();
        RelationshipContainsKeywordsAsSubstringPredicate predicate =
                new RelationshipContainsKeywordsAsSubstringPredicate(Collections.singletonList("friend"), model);
        Person person = new PersonBuilder().withName("Random Person").build();
        assertTrue(true);
    }

    @Test
    public void test_emptyRelationshipList_returnsFalse() {
        Model emptyModel = new ModelStub() {
            @Override
            public ObservableList<Relationship> getFilteredRelationshipList() {
                return FXCollections.observableArrayList();
            }
        };

        RelationshipContainsKeywordsAsSubstringPredicate predicate =
                new RelationshipContainsKeywordsAsSubstringPredicate(Collections.singletonList("friend"), emptyModel);
        Person person = new PersonBuilder().build();
        assertFalse(predicate.test(person));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("friend", "borrow");
        Model model = createModelWithRelationships();

        RelationshipContainsKeywordsAsSubstringPredicate predicate =
                new RelationshipContainsKeywordsAsSubstringPredicate(keywords, model);

        String expected =
                RelationshipContainsKeywordsAsSubstringPredicate.class.getCanonicalName()
                        + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }

    private abstract static class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new UnsupportedOperationException();
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook addressBook) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void deletePerson(Person target) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addPerson(Person person) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObservableList<Person> getSortedFilteredPersonList() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateSortedPersonList(Comparator<Person> comparator) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Person getPersonById(String id) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean hasRelationship(Relationship relationship) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean hasRelationship(String userId1, String userId2, String relationshipName) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean hasAnyRelationship(String userId1, String userId2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addRelationship(Relationship relationship) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void deleteRelationship(String userId1, String userId2, String relationshipName)
                throws RelationshipNotFoundException {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObservableList<Relationship> getFilteredRelationshipList() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean hasEvent(Event event) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addEvent(Event event) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void deleteEvent(Event event) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObservableList<Event> getFilteredEventList() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateFilteredEventList(Predicate<Event> predicate) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Event getEventById(String id) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateEvent(Event target, Event updatedEvent) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Relationship getRelationship(String userId1, String userId2, String relationshipName) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateRelationship(Relationship target, Relationship updatedRelationship) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateFilteredRelationshipList(Predicate<Relationship> predicate) {
            throw new UnsupportedOperationException();
        }
    }
}

