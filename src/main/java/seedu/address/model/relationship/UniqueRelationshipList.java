package seedu.address.model.relationship;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.relationship.exceptions.DuplicateRelationshipException;
import seedu.address.model.relationship.exceptions.RelationshipNotFoundException;

/**
 * A list of relationships that enforces uniqueness between its elements and does not allow nulls.
 * A relationship is considered unique by comparing using {@code Relationship#isSameRelationship(Relationship)}.
 * Supports a minimal set of list operations.
 */
public class UniqueRelationshipList implements Iterable<Relationship> {

    private final ObservableList<Relationship> internalList = FXCollections.observableArrayList();
    private final ObservableList<Relationship> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Checks if the list contains an equivalent relationship.
     * Equivalent relationships are relationships with the same user IDs and name.
     *
     * @param toCheck The relationship to check.
     * @return True if the list contains an equivalent relationship, False otherwise.
     */
    public boolean contains(Relationship toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameRelationship);
    }

    /**
     * Checks if the list contains a relationship with the given user IDs and name.
     *
     * @param userId1 The user ID of the first user in the relationship.
     * @param userId2 The user ID of the second user in the relationship.
     * @param relationshipName The name of the relationship.
     * @return True if the list contains a relationship with the given user IDs and name, False otherwise.
     */
    public boolean contains(String userId1, String userId2, String relationshipName) {
        return internalList.stream()
                .anyMatch(r -> r.isSameRelationship(userId1, userId2, relationshipName));
    }

    /**
     * Adds a relationship to the list.
     * The relationship must not already exist in the list.
     *
     * @param toAdd The relationship to add.
     * @throws DuplicateRelationshipException If the relationship already exists in the list.
     */
    public void add(Relationship toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateRelationshipException();
        }
        internalList.add(toAdd);
    }

    /**
     * Removes the relationship with the given user IDs and name from the list.
     * The relationship must exist in the list.
     *
     * @param userId1 The user ID of the first user.
     * @param userId2 The user ID of the second user.
     * @param relationshipName The name of the relationship.
     * @throws RelationshipNotFoundException If no such relationship could be found in the list.
     */
    public void remove(String userId1, String userId2, String relationshipName) {
        requireAllNonNull(userId1, userId2, relationshipName);

        for (int i = 0; i < internalList.size(); i++) {
            Relationship relationship = internalList.get(i);
            if (relationship.isSameRelationship(userId1, userId2, relationshipName)) {
                internalList.remove(i);
                return;
            }
        }
        throw new RelationshipNotFoundException();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     *
     * @return An unmodifiable view of the relationship list.
     */
    public ObservableList<Relationship> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    /**
     * Returns a list of relationships involving the given user ID.
     *
     * @param userId The user ID to search for.
     * @return A list of relationships that involve the given user ID.
     */
    public List<Relationship> getRelationshipsInvolvingUser(String userId) {
        return internalList.stream()
                .filter(r -> r.involvesUser(userId))
                .collect(Collectors.toList());
    }

    /**
     * Returns an iterator over the relationships in this list.
     *
     * @return An iterator over the relationships in this list.
     */
    @Override
    public Iterator<Relationship> iterator() {
        return internalList.iterator();
    }

    /**
     * Checks if this relationship list is the same as the given relationship list.
     *
     * @param other The object to compare with.
     * @return True if this relationship list is the same as the given relationship list, False otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniqueRelationshipList otherRelationshipList)) {
            return false;
        }

        return internalList.equals(otherRelationshipList.internalList);
    }

    /**
     * Returns the hash code of this relationship list.
     *
     * @return The hash code of this relationship list.
     */
    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}