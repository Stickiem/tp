package seedu.address.model.relationship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.relationship.exceptions.DuplicateRelationshipException;
import seedu.address.model.relationship.exceptions.RelationshipNotFoundException;
import seedu.address.testutil.RelationshipBuilder;

public class UniqueRelationshipListTest {
    private final UniqueRelationshipList uniqueRelationshipList = new UniqueRelationshipList();

    @Test
    public void contains_nullRelationship_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueRelationshipList.contains(null));
    }

    @Test
    public void contains_nullParameters_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueRelationshipList.contains(null, "2", "Friend"));
        assertThrows(NullPointerException.class, () -> uniqueRelationshipList.contains("1", null, "Friend"));
        assertThrows(NullPointerException.class, () -> uniqueRelationshipList.contains("1", "2", null));
    }

    @Test
    public void hasAnyRelationshipBetween_nullParameters_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueRelationshipList.hasAnyRelationshipBetween(null, "2"));
        assertThrows(NullPointerException.class, () -> uniqueRelationshipList.hasAnyRelationshipBetween("1", null));
    }

    @Test
    public void contains_relationshipNotInList_returnsFalse() {
        assertFalse(uniqueRelationshipList.contains(new RelationshipBuilder().build()));
    }

    @Test
    public void contains_relationshipInList_returnsTrue() {
        Relationship relationship = new RelationshipBuilder().build();
        uniqueRelationshipList.add(relationship);
        assertTrue(uniqueRelationshipList.contains(relationship));
    }

    @Test
    public void contains_relationshipInListWithDifferentDirection_returnsTrue() {
        Relationship relationship = new RelationshipBuilder()
                .withUser1Id("1")
                .withUser2Id("2")
                .withForwardName("Boss")
                .withReverseName("Employee")
                .build();
        uniqueRelationshipList.add(relationship);

        // Check reverse direction
        assertTrue(uniqueRelationshipList.contains("2", "1", "Employee"));
    }

    @Test
    public void hasAnyRelationshipBetween_existingRelationship_returnsTrue() {
        Relationship relationship = new RelationshipBuilder()
                .withUser1Id("1")
                .withUser2Id("2")
                .build();
        uniqueRelationshipList.add(relationship);

        assertTrue(uniqueRelationshipList.hasAnyRelationshipBetween("1", "2"));
        assertTrue(uniqueRelationshipList.hasAnyRelationshipBetween("2", "1")); // Reverse order
    }

    @Test
    public void add_nullRelationship_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueRelationshipList.add(null));
    }

    @Test
    public void add_duplicateRelationship_throwsDuplicateRelationshipException() {
        Relationship relationship = new RelationshipBuilder().build();
        uniqueRelationshipList.add(relationship);
        assertThrows(DuplicateRelationshipException.class, () -> uniqueRelationshipList.add(relationship));
    }

    @Test
    public void remove_nullParameters_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                uniqueRelationshipList.remove(null, "2", "Friend"));
        assertThrows(NullPointerException.class, () ->
                uniqueRelationshipList.remove("1", null, "Friend"));
        assertThrows(NullPointerException.class, () ->
                uniqueRelationshipList.remove("1", "2", null));
    }

    @Test
    public void remove_relationshipNotInList_throwsRelationshipNotFoundException() {
        assertThrows(RelationshipNotFoundException.class, () ->
                uniqueRelationshipList.remove("1", "2", "Friend"));
    }

    @Test
    public void setRelationship_nullEditedRelationship_throwsNullPointerException() {
        Relationship relationship = new RelationshipBuilder().build();
        assertThrows(NullPointerException.class, () ->
                uniqueRelationshipList.setRelationship(relationship, null));
    }

    @Test
    public void setRelationship_targetNotInList_throwsRelationshipNotFoundException() {
        Relationship target = new RelationshipBuilder().build();
        Relationship editedRelationship = new RelationshipBuilder().withForwardName("NewName").build();
        assertThrows(RelationshipNotFoundException.class, () ->
                uniqueRelationshipList.setRelationship(target, editedRelationship));
    }

    @Test
    public void setRelationship_validTarget_success() {
        Relationship original = new RelationshipBuilder().build();
        uniqueRelationshipList.add(original);
        Relationship edited = new RelationshipBuilder().withForwardName("NewName").build();
        uniqueRelationshipList.setRelationship(original, edited);

        UniqueRelationshipList expectedList = new UniqueRelationshipList();
        expectedList.add(edited);
        assertEquals(expectedList, uniqueRelationshipList);
    }

    @Test
    public void setRelationships_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                uniqueRelationshipList.setRelationships(null));
    }

    @Test
    public void removeRelationshipsInvolvingUser_userHasRelationships_returnsTrue() {
        Relationship r1 = new RelationshipBuilder()
                .withUser1Id("1")
                .withUser2Id("2")
                .build();
        Relationship r2 = new RelationshipBuilder()
                .withUser1Id("1")
                .withUser2Id("3")
                .build();
        uniqueRelationshipList.add(r1);
        uniqueRelationshipList.add(r2);

        assertTrue(uniqueRelationshipList.removeRelationshipsInvolvingUser("1"));
        assertEquals(0, uniqueRelationshipList.asUnmodifiableObservableList().size());
    }

    @Test
    public void removeRelationshipsInvolvingUser_userHasNoRelationships_returnsFalse() {
        Relationship r = new RelationshipBuilder()
                .withUser1Id("1")
                .withUser2Id("2")
                .build();
        uniqueRelationshipList.add(r);

        assertFalse(uniqueRelationshipList.removeRelationshipsInvolvingUser("3"));
        assertEquals(1, uniqueRelationshipList.asUnmodifiableObservableList().size());
    }

    @Test
    public void getRelationship_existingRelationship_returnsRelationship() {
        Relationship r = new RelationshipBuilder().build();
        uniqueRelationshipList.add(r);

        assertEquals(r, uniqueRelationshipList.getRelationship(
                r.getFirstUserId(), r.getSecondUserId(), r.getForwardName()));
    }

    @Test
    public void getRelationship_nonExistentRelationship_returnsNull() {
        assertFalse(uniqueRelationshipList.contains("1", "2", "Friend"));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () ->
                uniqueRelationshipList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void iterator() {
        Relationship r1 = new RelationshipBuilder()
                .withUser1Id("1")
                .withUser2Id("2")
                .build();
        Relationship r2 = new RelationshipBuilder()
                .withUser1Id("2")
                .withUser2Id("3")
                .build();

        uniqueRelationshipList.add(r1);
        uniqueRelationshipList.add(r2);

        List<Relationship> expectedList = new ArrayList<>();
        expectedList.add(r1);
        expectedList.add(r2);

        List<Relationship> actualList = new ArrayList<>();
        uniqueRelationshipList.iterator().forEachRemaining(actualList::add);

        assertEquals(expectedList, actualList);
    }

    @Test
    public void toString_nonEmptyList_returnsStringRepresentation() {
        Relationship r = new RelationshipBuilder().build();
        uniqueRelationshipList.add(r);
        assertEquals("[" + r.toString() + "]", uniqueRelationshipList.toString());
    }
}
