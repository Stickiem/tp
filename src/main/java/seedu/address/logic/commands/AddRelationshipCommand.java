package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FORWARD_RELATIONSHIP_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REVERSE_RELATIONSHIP_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERID;

import java.util.Set;

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

    public static final String MESSAGE_SUCCESS = "Relationship successfully added.";
    public static final String MESSAGE_DUPLICATE_RELATIONSHIP = "Error: Relationship already exists.";
    public static final String MESSAGE_PERSONS_NOT_FOUND = "Error: One or both contacts do not exist.";
    public static final String MESSAGE_SAME_PERSON = "Error: A contact cannot have a relationship with itself.";
    public static final String MESSAGE_EMPTY_NAME = "Error: Relationship name cannot be empty.";

    private final String userId1;
    private final String userId2;
    private final String forwardName;
    private final String reverseName;
    private final Set<Tag> tags;

    /**
     * Creates an AddRelationshipCommand to add the specified relationship.
     */
    public AddRelationshipCommand(String userId1, String userId2, String forwardName, String reverseName,
                                  Set<Tag> tags) {
        requireNonNull(userId1);
        requireNonNull(userId2);
        requireNonNull(forwardName);
        requireNonNull(reverseName);
        requireNonNull(tags);

        this.userId1 = userId1;
        this.userId2 = userId2;
        this.forwardName = forwardName;
        this.reverseName = reverseName;
        this.tags = tags;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (userId1.equals(userId2)) {
            throw new CommandException(MESSAGE_SAME_PERSON);
        }

        if (forwardName.trim().isEmpty() || reverseName.trim().isEmpty()) {
            throw new CommandException(MESSAGE_EMPTY_NAME);
        }

        // Check if both persons exist
        Person person1 = model.getPersonById(userId1);
        Person person2 = model.getPersonById(userId2);

        if (person1 == null || person2 == null) {
            throw new CommandException(MESSAGE_PERSONS_NOT_FOUND);
        }

        // Check if the same relationship already exists
        if (model.hasRelationship(userId1, userId2, forwardName)
                || model.hasRelationship(userId2, userId1, reverseName)) {
            throw new CommandException(MESSAGE_DUPLICATE_RELATIONSHIP);
        }

        Relationship relationship = new Relationship(userId1, userId2, forwardName, reverseName, tags);
        model.addRelationship(relationship);
        return new CommandResult(MESSAGE_SUCCESS);
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

        return userId1.equals(otherCommand.userId1)
                && userId2.equals(otherCommand.userId2)
                && forwardName.equals(otherCommand.forwardName)
                && reverseName.equals(otherCommand.reverseName)
                && tags.equals(otherCommand.tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("userId1", userId1)
                .add("userId2", userId2)
                .add("forwardName", forwardName)
                .add("reverseName", reverseName)
                .add("tags", tags)
                .toString();
    }
}
