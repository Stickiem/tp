package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FORWARD_RELATIONSHIP_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REVERSE_RELATIONSHIP_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERID;

import java.util.Set;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.relationship.Relationship;
import seedu.address.model.tag.Tag;

/**
 * Adds a relationship between two persons in the address book.
 */
public class AddRelationshipCommand extends Command {

    public static final String COMMAND_WORD = "addRelationship";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a relationship between two persons. "
            + "Parameters: "
            + PREFIX_USERID + "USER_ID_1 "
            + PREFIX_USERID + "USER_ID_2 "
            + PREFIX_FORWARD_RELATIONSHIP_NAME + "FORWARD_RELATIONSHIP_NAME "
            + PREFIX_REVERSE_RELATIONSHIP_NAME + "REVERSE_RELATIONSHIP_NAME "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_USERID + "12345678 "
            + PREFIX_USERID + "87654321 "
            + PREFIX_FORWARD_RELATIONSHIP_NAME + "Boss of "
            + PREFIX_REVERSE_RELATIONSHIP_NAME + "Reports to "
            + PREFIX_TAG + "Work" + "\n"
            + "Note: You can find a person's ID displayed in the contact card.";

    public static final String MESSAGE_SUCCESS = "New relationship created between %s and %s";
    public static final String MESSAGE_DUPLICATE_RELATIONSHIP = "Error: Relationship already exists.";
    public static final String MESSAGE_PERSONS_NOT_FOUND = "Error: One or both contacts do not exist.";
    public static final String MESSAGE_SAME_PERSON = "Error: A contact cannot have a relationship with itself.";
    public static final String MESSAGE_EMPTY_NAME = "Error: Relationship name cannot be empty.";

    private static final Logger logger = LogsCenter.getLogger(AddRelationshipCommand.class);

    private final String firstUserId;
    private final String secondUserId;
    private final String forwardName;
    private final String reverseName;
    private final Set<Tag> tags;

    /**
     * Creates an AddRelationshipCommand to add the specified relationship.
     */
    public AddRelationshipCommand(String firstUserId, String secondUserId, String forwardName, String reverseName,
                                  Set<Tag> tags) {
        requireNonNull(firstUserId, "First user ID cannot be null");
        requireNonNull(secondUserId, "Second user ID cannot be null");
        requireNonNull(forwardName, "Forward relationship name cannot be null");
        requireNonNull(reverseName, "Reverse relationship name cannot be null");
        requireNonNull(tags, "Tags set cannot be null");

        this.firstUserId = firstUserId;
        this.secondUserId = secondUserId;
        this.forwardName = forwardName;
        this.reverseName = reverseName;
        this.tags = tags;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model, "Model cannot be null");

        if (firstUserId.equals(secondUserId)) {
            throw new CommandException(MESSAGE_SAME_PERSON);
        }

        if (forwardName.trim().isEmpty() || reverseName.trim().isEmpty()) {
            throw new CommandException(MESSAGE_EMPTY_NAME);
        }

        // Check if both persons exist
        Person firstPerson = model.getPersonById(firstUserId);
        Person secondPerson = model.getPersonById(secondUserId);

        if (firstPerson == null || secondPerson == null) {
            throw new CommandException(MESSAGE_PERSONS_NOT_FOUND);
        }

        if (hasExistingRelationship(model)) {
            throw new CommandException(MESSAGE_DUPLICATE_RELATIONSHIP);
        }

        Relationship relationship = new Relationship(firstUserId, secondUserId, forwardName, reverseName, tags);
        model.addRelationship(relationship);

        logger.info(String.format("Created relationship between users %s and %s", firstUserId, secondUserId));
        return new CommandResult(String.format(MESSAGE_SUCCESS, firstPerson.getName(), secondPerson.getName()));
    }

    /**
     * Checks if the relationship already exists in the model.
     *
     * @param model The model to check against.
     * @return True if the relationship already exists, false otherwise.
     */
    private boolean hasExistingRelationship(Model model) {
        return model.hasRelationship(firstUserId, secondUserId, forwardName)
                || model.hasRelationship(secondUserId, firstUserId, reverseName);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddRelationshipCommand otherCommand)) {
            return false;
        }

        return firstUserId.equals(otherCommand.firstUserId)
                && secondUserId.equals(otherCommand.secondUserId)
                && forwardName.equals(otherCommand.forwardName)
                && reverseName.equals(otherCommand.reverseName)
                && tags.equals(otherCommand.tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("userId1", firstUserId)
                .add("userId2", secondUserId)
                .add("forwardName", forwardName)
                .add("reverseName", reverseName)
                .add("tags", tags)
                .toString();
    }
}
