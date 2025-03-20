package seedu.address.model.relationship;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Relationship between two persons in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Relationship {
    public static final String MESSAGE_CONSTRAINTS =
            "Relationship names can take any values, and it should not be blank";
    public static final String VALIDATION_REGEX = "\\S.*";

    private final String user1Id;
    private final String user2Id;
    private final String name;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Constructs a {@code Relationship}.
     * A relationship is a connection between two users in the address book. It has a name and can have tags.
     *
     * @param user1Id The user ID of the first user in the relationship.
     * @param user2Id The user ID of the second user in the relationship.
     * @param name The name of the relationship.
     * @param tags The tags associated with the relationship.
     */
    public Relationship(String user1Id, String user2Id, String name, Set<Tag> tags) {
        // null checks
        requireNonNull(user1Id);
        requireNonNull(user2Id);
        requireNonNull(name);
        requireNonNull(tags);
        checkArgument(isValidRelationshipName(name), MESSAGE_CONSTRAINTS);

        this.user1Id = user1Id;
        this.user2Id = user2Id;
        this.name = name;
        this.tags.addAll(tags);
    }

    /**
     * Validates the given relationship name.
     * A valid relationship name is any non-empty string.
     *
     * @param test The relationship name to validate.
     * @return True if the relationship name is valid, False otherwise.
     */
    public static boolean isValidRelationshipName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public String getUser1Id() {
        return user1Id;
    }

    public String getUser2Id() {
        return user2Id;
    }

    public String getName() {
        return name;
    }

    /**
     * Returns an immutable copy of the relationship's tags.
     *
     * @return An immutable copy of the relationship's tags.
     */
    public Set<Tag> getTags() {
        return new HashSet<>(tags);
    }

    /**
     * Checks if this relationship involves the given user ID.
     *
     * @param userId The user ID to check.
     * @return True if this relationship involves the given user ID, False otherwise.
     */
    public boolean involvesUser(String userId) {
        return user1Id.equals(userId) || user2Id.equals(userId);
    }

    /**
     * Checks if this relationship involves the given user IDs.
     *
     * @param userId1 The user ID of the first user.
     * @param userId2 The user ID of the second user.
     * @param relationshipName The name of the relationship.
     * @return True if this relationship involves the given user IDs and has the given name, False otherwise.
     */
    public boolean isSameRelationship(String userId1, String userId2, String relationshipName) {
        return (user1Id.equals(userId1) && user2Id.equals(userId2)
                || user1Id.equals(userId2) && user2Id.equals(userId1))
                && name.equals(relationshipName);
    }

    /**
     * Checks if this relationship is the same as the given relationship.
     *
     * @param otherRelationship The relationship to compare with.
     * @return True if this relationship is the same as the given relationship, False otherwise.
     */
    public boolean isSameRelationship(Relationship otherRelationship) {
        if (otherRelationship == this) {
            return true;
        }

        return otherRelationship != null
                && ((user1Id.equals(otherRelationship.user1Id) && user2Id.equals(otherRelationship.user2Id)
                || user1Id.equals(otherRelationship.user2Id) && user2Id.equals(otherRelationship.user1Id))
                && name.equals(otherRelationship.name));
    }

    /**
     * Checks if this relationship is the same as the given relationship. This method ignores the tags.
     *
     * @param other The relationship to compare with.
     * @return True if this relationship is the same as the given relationship, False otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Relationship otherRelationship)) {
            return false;
        }

        return (user1Id.equals(otherRelationship.user1Id) && user2Id.equals(otherRelationship.user2Id)
                || user1Id.equals(otherRelationship.user2Id) && user2Id.equals(otherRelationship.user1Id))
                && name.equals(otherRelationship.name)
                && tags.equals(otherRelationship.tags);
    }

    /**
     * Returns the hash code of this relationship.
     * The hash code is based on the user IDs of the two users in the relationship, the name of the relationship,
     * and the tags associated with the relationship.
     * @return The hash code of this relationship.
     */
    @Override
    public int hashCode() {
        // Use a combination of both user IDs (in a way that order doesn't matter)
        // along with the name and tags
        return Objects.hash(
                user1Id.compareTo(user2Id) < 0 ? user1Id + user2Id : user2Id + user1Id,
                name,
                tags
        );
    }

    /**
     * Returns the string representation of this relationship.
     * The string representation includes the name of the relationship, and the user IDs of the two users in the
     * relationship. If the relationship has tags, the string representation will also include the tags.
     *
     * @return The string representation of this relationship.
     */
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Between: ")
                .append(getUser1Id())
                .append(" and ")
                .append(getUser2Id());
        if (!tags.isEmpty()) {
            builder.append(" Tags: ");
            tags.forEach(builder::append);
        }
        return builder.toString();
    }
}
