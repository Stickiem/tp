package seedu.address.model.relationship.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class DuplicateRelationshipExceptionTest {

    @Test
    public void constructor_defaultConstructor_correctMessage() {
        DuplicateRelationshipException exception = new DuplicateRelationshipException();
        assertEquals("Operation would result in duplicate relationships between the same users.",
                exception.getMessage());
    }
}
