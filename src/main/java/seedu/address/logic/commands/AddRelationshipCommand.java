package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
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
            + PREFIX_NAME + "RELATIONSHIP_NAME "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_USERID + "12345678 "
            + PREFIX_USERID + "87654321 "
            + PREFIX_NAME + "Business Partner "
            + PREFIX_TAG + "Investment";

    public static final String MESSAGE_SUCCESS = "Relationship successfully added.";
    public static final String MESSAGE_DUPLICATE_RELATIONSHIP = "Error: Relationship already exists.";
    public static final String MESSAGE_PERSONS_NOT_FOUND = "Error: One or both contacts do not exist.";
    public static final String MESSAGE_SAME_PERSON = "Error: A contact cannot have a relationship with itself.";
    public static final String MESSAGE_EMPTY_NAME = "Error: Relationship name cannot be empty.";

    private final String userId1;
    private final String userId2;
    private final String relationshipName;
    private final Set<Tag> tags;

    /**
     * Creates an AddRelationshipCommand to add the specified relationship.
     */
    public AddRelationshipCommand(String userId1, String userId2, String relationshipName, Set<Tag> tags) {
        requireNonNull(userId1);
        requireNonNull(userId2);
        requireNonNull(relationshipName);
        requireNonNull(tags);

        this.userId1 = userId1;
        this.userId2 = userId2;
        this.relationshipName = relationshipName;
        this.tags = tags;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (userId1.equals(userId2)) {
            throw new CommandException(MESSAGE_SAME_PERSON);
        }

        if (relationshipName.trim().isEmpty()) {
            throw new CommandException(MESSAGE_EMPTY_NAME);
        }

        // Check if both persons exist
        Person person1 = model.getPersonById(userId1);
        Person person2 = model.getPersonById(userId2);

        if (person1 == null || person2 == null) {
            throw new CommandException(MESSAGE_PERSONS_NOT_FOUND);
        }

        // Check if the same relationship already exists
        if (model.hasRelationship(userId1, userId2, relationshipName)) {
            throw new CommandException(MESSAGE_DUPLICATE_RELATIONSHIP);
        }

        Relationship relationship = new Relationship(userId1, userId2, relationshipName, tags);
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
                && relationshipName.equals(otherCommand.relationshipName)
                && tags.equals(otherCommand.tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("userId1", userId1)
                .add("userId2", userId2)
                .add("relationshipName", relationshipName)
                .add("tags", tags)
                .toString();
    }
}