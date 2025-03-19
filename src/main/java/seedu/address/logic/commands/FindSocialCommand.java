package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.predicate.SocialContainsKeywordsAsSubstringPredicate;

/**
 * Finds and lists all persons in address book whose social value contains any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class FindSocialCommand extends Command {

    public static final String COMMAND_WORD = "findsocial";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose social value contains any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " facebook twitter linkedin";

    private final SocialContainsKeywordsAsSubstringPredicate predicate;

    public FindSocialCommand(SocialContainsKeywordsAsSubstringPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof FindSocialCommand)) {
            return false;
        }

        FindSocialCommand otherFindSocialCommand = (FindSocialCommand) other;
        return predicate.equals(otherFindSocialCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
