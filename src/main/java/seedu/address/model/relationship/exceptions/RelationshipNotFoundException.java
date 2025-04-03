package seedu.address.model.relationship.exceptions;

/**
 * Signals that the operation is unable to find the specified relationship.
 */
public class RelationshipNotFoundException extends RuntimeException {
    public RelationshipNotFoundException() {
        super("No relationship found matching the specified criteria.");
    }
}
