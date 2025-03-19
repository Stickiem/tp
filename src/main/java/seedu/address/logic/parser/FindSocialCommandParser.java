package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindSocialCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.predicate.SocialContainsKeywordsAsSubstringPredicate;

/**
 * Parses input arguments and creates a new FindSocialCommand object
 */
public class FindSocialCommandParser implements Parser<FindSocialCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindSocialCommand
     * and returns a FindSocialCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public FindSocialCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindSocialCommand.MESSAGE_USAGE));
        }

        String[] socialKeywords = trimmedArgs.split("\\s+");

        return new FindSocialCommand(new SocialContainsKeywordsAsSubstringPredicate(Arrays.asList(socialKeywords)));
    }
}
