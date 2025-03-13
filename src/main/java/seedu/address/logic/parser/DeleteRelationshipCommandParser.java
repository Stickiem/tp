package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERID;

import java.util.List;
import java.util.stream.Stream;

import seedu.address.logic.commands.DeleteRelationshipCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteRelationshipCommand object
 */
public class DeleteRelationshipCommandParser implements Parser<DeleteRelationshipCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteRelationshipCommand
     * and returns a DeleteRelationshipCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteRelationshipCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_USERID, PREFIX_NAME);

        // Check if we have both user IDs and relationship name
        if (!arePrefixesPresent(argMultimap, PREFIX_USERID, PREFIX_NAME)
                || argMultimap.getAllValues(PREFIX_USERID).size() != 2
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteRelationshipCommand.MESSAGE_USAGE));
        }

        // Get the user IDs
        List<String> userIds = argMultimap.getAllValues(PREFIX_USERID);
        String userId1 = userIds.get(0);
        String userId2 = userIds.get(1);

        // Get the relationship name
        String relationshipName = argMultimap.getValue(PREFIX_NAME).get();

        return new DeleteRelationshipCommand(userId1, userId2, relationshipName);
    }

    /**
     * Returns true if the prefix is present and has a non-empty value in the given
     * ArgumentMultimap.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}