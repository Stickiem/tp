package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.relationship.Relationship;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Relationship}.
 */
class JsonAdaptedRelationship {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Relationship's %s field is missing!";

    private final String user1Id;
    private final String user2Id;
    private final String forwardName;
    private final String reverseName;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedRelationship} with the given relationship details.
     */
    @JsonCreator
    public JsonAdaptedRelationship(
            @JsonProperty("user1Id") String user1Id,
            @JsonProperty("user2Id") String user2Id,
            @JsonProperty("forwardName") String forwardName,
            @JsonProperty("reverseName") String reverseName,
            @JsonProperty("tags") List<JsonAdaptedTag> tags) {
        this.user1Id = user1Id;
        this.user2Id = user2Id;
        this.forwardName = forwardName;
        this.reverseName = reverseName;
        if (tags != null) {
            this.tags.addAll(tags);
        }
    }

    /**
     * Converts a given {@code Relationship} into this class for Jackson use.
     */
    public JsonAdaptedRelationship(Relationship source) {
        user1Id = source.getFirstUserId();
        user2Id = source.getSecondUserId();
        forwardName = source.getForwardName();
        reverseName = source.getReverseName();
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .toList());
    }

    /**
     * Converts this Jackson-friendly adapted relationship object into the model's {@code Relationship} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted relationship.
     */
    public Relationship toModelType() throws IllegalValueException {
        final List<Tag> relationshipTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            relationshipTags.add(tag.toModelType());
        }

        if (user1Id == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "User 1 ID"));
        }

        if (user2Id == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "User 2 ID"));
        }

        if (forwardName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Forward Name"));
        }

        if (reverseName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Reverse Name"));
        }

        if (!Relationship.isValidRelationshipName(forwardName) || !Relationship.isValidRelationshipName(reverseName)) {
            throw new IllegalValueException(Relationship.MESSAGE_CONSTRAINTS);
        }

        final Set<Tag> modelTags = new HashSet<>(relationshipTags);
        return new Relationship(user1Id, user2Id, forwardName, reverseName, modelTags);
    }
}
