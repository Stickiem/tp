package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERID;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.relationship.exceptions.RelationshipNotFoundException;

/**
 * Deletes a relationship between two persons in the address book.
 */
public class DeleteRelationshipCommand extends Command {

    public static final String COMMAND_WORD = "deleteRelationship";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a relationship between two persons. "
            + "Parameters: "
            + PREFIX_USERID + "USER_ID_1 "
            + PREFIX_USERID + "USER_ID_2 "
            + PREFIX_NAME + "RELATIONSHIP_NAME\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_USERID + "12345678 "
            + PREFIX_USERID + "87654321 "
            + PREFIX_NAME + "Business Partner"
            + "Note: You can find a person's ID displayed in the contact card.";

    public static final String MESSAGE_SUCCESS = "Relationship successfully deleted.";
    public static final String MESSAGE_RELATIONSHIP_NOT_FOUND = "Error: Relationship not found.";
    public static final String MESSAGE_PERSONS_NOT_FOUND = "Error: One or both contacts do not exist.";
    public static final String MESSAGE_MISSING_PARAMETERS = "Error: Missing required parameters";

    private final String userId1;
    private final String userId2;
    private final String relationshipName;

    /**
     * Creates a DeleteRelationshipCommand to delete the specified relationship.
     */
    public DeleteRelationshipCommand(String userId1, String userId2, String relationshipName) {
        requireNonNull(userId1);
        requireNonNull(userId2);
        requireNonNull(relationshipName);

        this.userId1 = userId1;
        this.userId2 = userId2;
        this.relationshipName = relationshipName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (userId1.trim().isEmpty() || userId2.trim().isEmpty() || relationshipName.trim().isEmpty()) {
            throw new CommandException(MESSAGE_MISSING_PARAMETERS);
        }

        // Check if both persons exist
        Person person1 = model.getPersonById(userId1);
        Person person2 = model.getPersonById(userId2);

        if (person1 == null || person2 == null) {
            throw new CommandException(MESSAGE_PERSONS_NOT_FOUND);
        }

        try {
            model.deleteRelationship(userId1, userId2, relationshipName);
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (RelationshipNotFoundException e) {
            throw new CommandException(MESSAGE_RELATIONSHIP_NOT_FOUND);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteRelationshipCommand otherCommand)) {
            return false;
        }

        return userId1.equals(otherCommand.userId1)
                && userId2.equals(otherCommand.userId2)
                && relationshipName.equals(otherCommand.relationshipName);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("userId1", userId1)
                .add("userId2", userId2)
                .add("relationshipName", relationshipName)
                .toString();
    }
}
