package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.HashSet;
import java.util.Set;

import seedu.address.logic.commands.FindTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.predicate.TagContainsKeywordsAsSubstringPredicate;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new FindTagCommand object
 */
public class FindTagCommandParser implements Parser<FindTagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindTagCommand
     * and returns a FindTagCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindTagCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTagCommand.MESSAGE_USAGE));
        }

        String[] tagKeywords = trimmedArgs.split("\\s+");

        // Convert the keywords into a set of Tag objects
        Set<Tag> tagSet = new HashSet<>();
        for (String keyword : tagKeywords) {
            tagSet.add(new Tag(keyword));
        }

        return new FindTagCommand(new TagContainsKeywordsAsSubstringPredicate(tagSet));
    }
}
