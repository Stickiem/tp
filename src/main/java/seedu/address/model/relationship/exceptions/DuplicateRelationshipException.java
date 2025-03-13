package seedu.address.model.relationship.exceptions;

/**
 * Signals that the operation will result in duplicate Relationships.
 */
public class DuplicateRelationshipException extends RuntimeException {
    public DuplicateRelationshipException() {
        super("Operation would result in duplicate relationships");
    }
}