package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindRelationshipCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.predicate.RelationshipContainsKeywordsAsSubstringPredicate;

/**
 * Parses input arguments and creates a new FindRelationshipCommand object
 */
public class FindRelationshipCommandParser implements Parser<FindRelationshipCommand> {

    public FindRelationshipCommand parse(String args) throws ParseException {
        throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindRelationshipCommand.MESSAGE_USAGE));
    }
    /**
     * Parses the given {@code String} of arguments in the context of the FindRelationshipCommand
     * and returns a FindRelationshipCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public FindRelationshipCommand parse(String args, Model model) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindRelationshipCommand.MESSAGE_USAGE));
        }

        String[] relationshipKeywords = trimmedArgs.split("\\s+");

        return new FindRelationshipCommand(new RelationshipContainsKeywordsAsSubstringPredicate(Arrays.asList(relationshipKeywords), model));
    }
}