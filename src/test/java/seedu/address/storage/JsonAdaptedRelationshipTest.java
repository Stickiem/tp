package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.relationship.Relationship;
import seedu.address.testutil.RelationshipBuilder;

public class JsonAdaptedRelationshipTest {
    private static final String INVALID_NAME = " ";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_USER1_ID = "123456";
    private static final String VALID_USER2_ID = "654321";
    private static final String VALID_NAME = "Friend";
    private static final List<JsonAdaptedTag> VALID_TAGS = new RelationshipBuilder().build().getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validRelationshipDetails_returnsRelationship() throws Exception {
        Relationship relationship = new RelationshipBuilder().build();
        JsonAdaptedRelationship jsonRelationship = new JsonAdaptedRelationship(relationship);
        assertEquals(relationship, jsonRelationship.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedRelationship relationship =
                new JsonAdaptedRelationship(VALID_USER1_ID, VALID_USER2_ID, INVALID_NAME, VALID_TAGS);
        String expectedMessage = Relationship.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, relationship::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedRelationship relationship =
                new JsonAdaptedRelationship(VALID_USER1_ID, VALID_USER2_ID, null, VALID_TAGS);
        String expectedMessage = String.format(JsonAdaptedRelationship.MISSING_FIELD_MESSAGE_FORMAT, "Name");
        assertThrows(IllegalValueException.class, expectedMessage, relationship::toModelType);
    }

    @Test
    public void toModelType_nullUser1Id_throwsIllegalValueException() {
        JsonAdaptedRelationship relationship =
                new JsonAdaptedRelationship(null, VALID_USER2_ID, VALID_NAME, VALID_TAGS);
        String expectedMessage = String.format(JsonAdaptedRelationship.MISSING_FIELD_MESSAGE_FORMAT, "User 1 ID");
        assertThrows(IllegalValueException.class, expectedMessage, relationship::toModelType);
    }

    @Test
    public void toModelType_nullUser2Id_throwsIllegalValueException() {
        JsonAdaptedRelationship relationship =
                new JsonAdaptedRelationship(VALID_USER1_ID, null, VALID_NAME, VALID_TAGS);
        String expectedMessage = String.format(JsonAdaptedRelationship.MISSING_FIELD_MESSAGE_FORMAT, "User 2 ID");
        assertThrows(IllegalValueException.class, expectedMessage, relationship::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedRelationship relationship =
                new JsonAdaptedRelationship(VALID_USER1_ID, VALID_USER2_ID, VALID_NAME, invalidTags);
        assertThrows(IllegalValueException.class, relationship::toModelType);
    }
}
