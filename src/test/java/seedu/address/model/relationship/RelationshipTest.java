package seedu.address.model.relationship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.RelationshipBuilder;

public class RelationshipTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Relationship(null, "2", "Friend", "Reports to",
                new HashSet<>()));
        assertThrows(NullPointerException.class, () -> new Relationship("1", null, "Friend", "Reports to",
                new HashSet<>()));
        assertThrows(NullPointerException.class, () -> new Relationship("1", "2", null, "Reports to", new HashSet<>()));
        assertThrows(NullPointerException.class, () -> new Relationship("1", "2", "Friend", null, new HashSet<>()));
        assertThrows(NullPointerException.class, () -> new Relationship("1", "2", "Friend", "Reports to", null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Relationship("1", "2", "", "Reports to",
                new HashSet<>()));
        assertThrows(IllegalArgumentException.class, () -> new Relationship("1", "2", "Friend", "", new HashSet<>()));
        assertThrows(IllegalArgumentException.class, () -> new Relationship("1", "2", " ", "Reports to",
                new HashSet<>()));
        assertThrows(IllegalArgumentException.class, () -> new Relationship("1", "2", "Friend", " ", new HashSet<>()));
    }

    @Test
    public void isSameRelationship() {
        Relationship relationship = new RelationshipBuilder().build();

        // same object -> returns true
        assertTrue(relationship.isSameRelationship(relationship));

        // null -> returns false
        assertFalse(relationship.isSameRelationship(null));

        // different forward name -> returns false
        Relationship editedRelationship = new RelationshipBuilder().withForwardName("Different").build();
        assertFalse(relationship.isSameRelationship(editedRelationship));

        // different reverse name -> returns false
        editedRelationship = new RelationshipBuilder().withReverseName("Different").build();
        assertFalse(relationship.isSameRelationship(editedRelationship));

        // same names, same user1Id, different user2Id -> returns false
        editedRelationship = new RelationshipBuilder().withUser2Id("different").build();
        assertFalse(relationship.isSameRelationship(editedRelationship));

        // same names, different user1Id, same user2Id -> returns false
        editedRelationship = new RelationshipBuilder().withUser1Id("different").build();
        assertFalse(relationship.isSameRelationship(editedRelationship));

        // same names, switched user IDs -> returns true
        editedRelationship = new RelationshipBuilder()
                .withUser1Id(RelationshipBuilder.DEFAULT_USER2_ID)
                .withUser2Id(RelationshipBuilder.DEFAULT_USER1_ID)
                .withForwardName(RelationshipBuilder.DEFAULT_REVERSE_NAME)
                .withReverseName(RelationshipBuilder.DEFAULT_FORWARD_NAME)
                .build();
        assertTrue(relationship.isSameRelationship(editedRelationship));
    }

    @Test
    public void isSameRelationshipWithStrings() {
        Relationship relationship = new RelationshipBuilder().build();

        // same parameters -> returns true
        assertTrue(relationship.isSameRelationship(
                RelationshipBuilder.DEFAULT_USER1_ID,
                RelationshipBuilder.DEFAULT_USER2_ID,
                RelationshipBuilder.DEFAULT_FORWARD_NAME));

        assertTrue(relationship.isSameRelationship(
                RelationshipBuilder.DEFAULT_USER2_ID,
                RelationshipBuilder.DEFAULT_USER1_ID,
                RelationshipBuilder.DEFAULT_REVERSE_NAME));

        // switched user IDs, but wrong name -> returns true
        assertTrue(relationship.isSameRelationship(
                RelationshipBuilder.DEFAULT_USER2_ID,
                RelationshipBuilder.DEFAULT_USER1_ID,
                RelationshipBuilder.DEFAULT_FORWARD_NAME));

        // different name -> returns false
        assertFalse(relationship.isSameRelationship(
                RelationshipBuilder.DEFAULT_USER1_ID,
                RelationshipBuilder.DEFAULT_USER2_ID,
                "Different"));

        // different user IDs -> returns false
        assertFalse(relationship.isSameRelationship(
                "different1",
                "different2",
                RelationshipBuilder.DEFAULT_FORWARD_NAME));
    }

    @Test
    public void involvesUser() {
        Relationship relationship = new RelationshipBuilder().build();

        // user1Id -> returns true
        assertTrue(relationship.involvesUser(RelationshipBuilder.DEFAULT_USER1_ID));

        // user2Id -> returns true
        assertTrue(relationship.involvesUser(RelationshipBuilder.DEFAULT_USER2_ID));

        // different user -> returns false
        assertFalse(relationship.involvesUser("differentUser"));
    }

    @Test
    public void equals() {
        Relationship relationship = new RelationshipBuilder().build();

        // same values -> returns true
        Relationship relationshipCopy = new RelationshipBuilder().build();
        assertEquals(relationship, relationshipCopy);

        // same object -> returns true
        assertEquals(relationship, relationship);

        // null -> returns false
        assertNotEquals(null, relationship);

        // different type -> returns false
        assertNotEquals(5, relationship);

        // different forwardName -> returns false
        Relationship editedRelationship = new RelationshipBuilder().withForwardName("Different").build();
        assertNotEquals(relationship, editedRelationship);

        // different reverseName -> returns false
        editedRelationship = new RelationshipBuilder().withReverseName("Different").build();
        assertNotEquals(relationship, editedRelationship);

        // different user1Id -> returns false
        editedRelationship = new RelationshipBuilder().withUser1Id("different").build();
        assertNotEquals(relationship, editedRelationship);

        // different user2Id -> returns false
        editedRelationship = new RelationshipBuilder().withUser2Id("different").build();
        assertNotEquals(relationship, editedRelationship);

        // different tags -> returns false
        editedRelationship = new RelationshipBuilder().withTags("Different").build();
        assertNotEquals(relationship, editedRelationship);
    }

    @Test
    public void toStringMethod() {
        Relationship relationship = new RelationshipBuilder().build();
        String expected = "[Forward: " + relationship.getForwardName() + ", Reverse: " + relationship.getReverseName()
                + "] Between: " + relationship.getFirstUserId() + " and " + relationship.getSecondUserId();
        assertEquals(expected, relationship.toString());

        // with tags
        relationship = new RelationshipBuilder().withTags("Tag1", "Tag2").build();
        expected = "[Forward: " + relationship.getForwardName() + ", Reverse: " + relationship.getReverseName()
                + "] Between: " + relationship.getFirstUserId() + " and " + relationship.getSecondUserId()
                + " Tags: [Tag1][Tag2]";
        assertEquals(expected, relationship.toString());
    }
}
