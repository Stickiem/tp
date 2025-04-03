package seedu.address.model.relationship;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.relationship.exceptions.DuplicateRelationshipException;
import seedu.address.model.relationship.exceptions.RelationshipNotFoundException;

/**
 * A list of relationships that enforces uniqueness between its elements and does not allow nulls.
 * A relationship is considered unique by comparing using {@code Relationship#isSameRelationship(Relationship)}.
 * Supports a minimal set of list operations.
 */
public class UniqueRelationshipList implements Iterable<Relationship> {
    private static final Logger logger = LogsCenter.getLogger(UniqueRelationshipList.class);

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
        requireNonNull(toCheck, "Relationship to check cannot be null");
        return internalList.stream().anyMatch(toCheck::isSameRelationship);
    }

    /**
     * Checks if the list contains a relationship with the given user IDs and name.
     *
     * @param firstUserId The user ID of the first user in the relationship.
     * @param secondUserId The user ID of the second user in the relationship.
     * @param relationshipName The name of the relationship.
     * @return True if the list contains a relationship with the given user IDs and name, False otherwise.
     */
    public boolean contains(String firstUserId, String secondUserId, String relationshipName) {
        requireAllNonNull(firstUserId, secondUserId, relationshipName);

        return internalList.stream()
                .anyMatch(r -> r.isSameRelationship(firstUserId, secondUserId, relationshipName));
    }

    /**
     * Checks if the list contains any relationship between the given users, regardless of the relationship names.
     * @param firstUserId The user ID of the first user.
     * @param secondUserId The user ID of the second user.
     * @return True if the list contains any relationship between the given users, False otherwise.
     */
    public boolean hasAnyRelationshipBetween(String firstUserId, String secondUserId) {
        requireAllNonNull(firstUserId, secondUserId);

        return internalList.stream()
                .anyMatch(r -> isRelationshipBetweenUsers(r, firstUserId, secondUserId));
    }

    /**
     * Checks if the given relationship is between the two specified users.
     *
     * @param relationship The relationship to check.
     * @param firstUserId The user ID of the first user.
     * @param secondUserId The user ID of the second user.
     * @return True if the relationship is between the two specified users, False otherwise.
     */
    private boolean isRelationshipBetweenUsers(Relationship relationship, String firstUserId, String secondUserId) {
        return (relationship.getFirstUserId().equals(firstUserId)
                && relationship.getSecondUserId().equals(secondUserId))
                || (relationship.getFirstUserId().equals(secondUserId)
                && relationship.getSecondUserId().equals(firstUserId));
    }

    /**
     * Adds a relationship to the list.
     * The relationship must not already exist in the list.
     *
     * @param toAdd The relationship to add.
     * @throws DuplicateRelationshipException If the relationship already exists in the list.
     */
    public void add(Relationship toAdd) {
        requireNonNull(toAdd, "Relationship to add cannot be null");

        if (contains(toAdd)) {
            throw new DuplicateRelationshipException();
        }

        internalList.add(toAdd);
        logger.fine("Added relationship: " + toAdd);
    }

    /**
     * Removes the relationship with the given user IDs and name from the list.
     * The relationship must exist in the list.
     *
     * @param firstUserId The user ID of the first user.
     * @param secondUserId The user ID of the second user.
     * @param relationshipName The name of the relationship.
     * @throws RelationshipNotFoundException If no such relationship could be found in the list.
     */
    public void remove(String firstUserId, String secondUserId, String relationshipName) {
        requireAllNonNull(firstUserId, secondUserId, relationshipName);

        boolean wasRemoved = internalList.removeIf(relationship ->
                relationship.isSameRelationship(firstUserId, secondUserId, relationshipName));

        if (!wasRemoved) {
            throw new RelationshipNotFoundException();
        }

        logger.fine(String.format("Removed relationship between %s and %s", firstUserId, secondUserId));
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

    /**
     * Returns the relationship with the given user IDs and name.
     * @param userId1 The user ID of the first user.
     * @param userId2 The user ID of the second user.
     * @param relationshipName The name of the relationship.
     * @return The relationship with the given user IDs and name, or null if not found.
     */
    public Relationship getRelationship(String userId1, String userId2, String relationshipName) {
        return internalList.stream()
                .filter(r -> r.isSameRelationship(userId1, userId2, relationshipName))
                .findFirst()
                .orElse(null);
    }

    /**
     * Replaces the relationship {@code target} in the list with {@code editedRelationship}.
     * @param target The relationship to replace.
     * @param editedRelationship The edited relationship.
     */
    public void setRelationship(Relationship target, Relationship editedRelationship) {
        requireNonNull(editedRelationship);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new RelationshipNotFoundException();
        }

        internalList.set(index, editedRelationship);
    }

    @Override
    public String toString() {
        return internalList.toString();
    }
}
