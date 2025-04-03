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
    public static final String DEFAULT_FORWARD_NAME = "Boss of";
    public static final String DEFAULT_REVERSE_NAME = "Reports to";

    private String user1Id;
    private String user2Id;
    private String forwardName;
    private String reverseName;
    private Set<Tag> tags;

    /**
     * Creates a {@code RelationshipBuilder} with the default details.
     */
    public RelationshipBuilder() {
        user1Id = DEFAULT_USER1_ID;
        user2Id = DEFAULT_USER2_ID;
        forwardName = DEFAULT_FORWARD_NAME;
        reverseName = DEFAULT_REVERSE_NAME;
        tags = new HashSet<>();
    }

    /**
     * Initializes the RelationshipBuilder with the data of {@code relationshipToCopy}.
     */
    public RelationshipBuilder(Relationship relationshipToCopy) {
        user1Id = relationshipToCopy.getFirstUserId();
        user2Id = relationshipToCopy.getSecondUserId();
        forwardName = relationshipToCopy.getForwardName();
        reverseName = relationshipToCopy.getReverseName();
        tags = new HashSet<>(relationshipToCopy.getTags());
    }

    /**
     * Sets the {@code forwardName} of the {@code Relationship} that we are building.
     * @param name The name of the relationship from user1 to user2.
     * @return The RelationshipBuilder with the forwardName set.
     */
    public RelationshipBuilder withForwardName(String name) {
        this.forwardName = name;
        return this;
    }

    /**
     * Sets the {@code reverseName} of the {@code Relationship} that we are building.
     * @param name The name of the relationship from user2 to user1.
     * @return The RelationshipBuilder with the reverseName set.
     */
    public RelationshipBuilder withReverseName(String name) {
        this.reverseName = name;
        return this;
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
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Relationship} that we are building.
     */
    public RelationshipBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    public Relationship build() {
        return new Relationship(user1Id, user2Id, forwardName, reverseName, tags);
    }
}
