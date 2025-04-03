package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERID;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.relationship.exceptions.RelationshipNotFoundException;

/**
 * Deletes an existing relationship between two persons in the address book.
 */
public class DeleteRelationshipCommand extends Command {
    public static final String COMMAND_WORD = "deleteRelationship";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes an existing relationship between two persons.\n"
            + "Parameters: "
            + PREFIX_USERID + "FIRST_USER_ID "
            + PREFIX_USERID + "SECOND_USER_ID "
            + PREFIX_NAME + "RELATIONSHIP_NAME\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_USERID + "12345678 "
            + PREFIX_USERID + "87654321 "
            + PREFIX_NAME + "Business Partner\n"
            + "Note: You can use either the forward or reverse relationship name.\n"
            + "The person's ID is shown in their contact card.";

    public static final String MESSAGE_SUCCESS = "Successfully deleted relationship between %s and %s";
    public static final String MESSAGE_RELATIONSHIP_NOT_FOUND = "No relationship found with the specified details";
    public static final String MESSAGE_PERSONS_NOT_FOUND = "One or both persons could not be found";
    public static final String MESSAGE_EMPTY_PARAMETERS = "User IDs and relationship name cannot be empty";

    private static final Logger logger = LogsCenter.getLogger(DeleteRelationshipCommand.class);

    private final String firstUserId;
    private final String secondUserId;
    private final String relationshipName;

    /**
     * Creates a command to delete the specified relationship.
     *
     * @param firstUserId First person's user ID
     * @param secondUserId Second person's user ID
     * @param relationshipName Name of the relationship to delete
     * @throws NullPointerException if any parameter is null
     */
    public DeleteRelationshipCommand(String firstUserId, String secondUserId, String relationshipName) {
        requireNonNull(firstUserId, "First user ID cannot be null");
        requireNonNull(secondUserId, "Second user ID cannot be null");
        requireNonNull(relationshipName, "Relationship name cannot be null");

        this.firstUserId = firstUserId;
        this.secondUserId = secondUserId;
        this.relationshipName = relationshipName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model, "Model cannot be null");

        validateParameters();

        Person firstPerson = findPerson(model, firstUserId);
        Person secondPerson = findPerson(model, secondUserId);

        if (firstPerson == null || secondPerson == null) {
            logger.warning("Attempted to delete relationship with non-existent person(s)");
            throw new CommandException(MESSAGE_PERSONS_NOT_FOUND);
        }

        try {
            model.deleteRelationship(firstUserId, secondUserId, relationshipName);
            logger.info(String.format("Deleted relationship between users %s and %s", firstUserId, secondUserId));
            return new CommandResult(String.format(MESSAGE_SUCCESS, firstPerson.getName(), secondPerson.getName()));
        } catch (RelationshipNotFoundException e) {
            logger.warning(String.format("Attempted to delete non-existent relationship between %s and %s",
                    firstUserId, secondUserId));
            throw new CommandException(MESSAGE_RELATIONSHIP_NOT_FOUND);
        }
    }

    private void validateParameters() throws CommandException {
        if (hasEmptyParameter(firstUserId) || hasEmptyParameter(secondUserId)
                || hasEmptyParameter(relationshipName)) {
            throw new CommandException(MESSAGE_EMPTY_PARAMETERS);
        }
    }

    private boolean hasEmptyParameter(String parameter) {
        return parameter.trim().isEmpty();
    }

    private Person findPerson(Model model, String userId) {
        return model.getPersonById(userId);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DeleteRelationshipCommand otherCommand)) {
            return false;
        }

        return firstUserId.equals(otherCommand.firstUserId)
                && secondUserId.equals(otherCommand.secondUserId)
                && relationshipName.equals(otherCommand.relationshipName);
    }

    @Override
    public String toString() {
        return String.format("DeleteRelationshipCommand: Deleting relationship '%s' between %s and %s",
                relationshipName, firstUserId, secondUserId);
    }
}
