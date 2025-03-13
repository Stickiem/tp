package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.relationship.Relationship;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Relationship objects.
 */
public class RelationshipBuilder {

    public static final String DEFAULT_USER1_ID = "123456";
    public static final String DEFAULT_USER2_ID = "654321";
    public static final String DEFAULT_NAME = "Friend";

    private String user1Id;
    private String user2Id;
    private String name;
    private Set<Tag> tags;

    /**
     * Creates a {@code RelationshipBuilder} with the default details.
     */
    public RelationshipBuilder() {
        user1Id = DEFAULT_USER1_ID;
        user2Id = DEFAULT_USER2_ID;
        name = DEFAULT_NAME;
        tags = new HashSet<>();
    }

    /**
     * Initializes the RelationshipBuilder with the data of {@code relationshipToCopy}.
     */
    public RelationshipBuilder(Relationship relationshipToCopy) {
        user1Id = relationshipToCopy.getUser1Id();
        user2Id = relationshipToCopy.getUser2Id();
        name = relationshipToCopy.getName();
        tags = new HashSet<>(relationshipToCopy.getTags());
    }

    /**
     * Sets the {@code user1Id} of the {@code Relationship} that we are building.
     */
    public RelationshipBuilder withUser1Id(String user1Id) {
        this.user1Id = user1Id;
        return this;
    }

    /**
     * Sets the {@code user2Id} of the {@code Relationship} that we are building.
     */
    public RelationshipBuilder withUser2Id(String user2Id) {
        this.user2Id = user2Id;
        return this;
    }

    /**
     * Sets the {@code name} of the {@code Relationship} that we are building.
     */
    public RelationshipBuilder withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Relationship} that we are building.
     */
    public RelationshipBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    public Relationship build() {
        return new Relationship(user1Id, user2Id, name, tags);
    }
}