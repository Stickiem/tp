package seedu.address.model.relationship.exceptions;

/**
 * Signals that the operation is unable to find the specified relationship.
 */
public class RelationshipNotFoundException extends RuntimeException {
    public RelationshipNotFoundException() {
        super("Relationship not found");
    }
}
