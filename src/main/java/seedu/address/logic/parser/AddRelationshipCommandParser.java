package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FORWARD_RELATIONSHIP_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REVERSE_RELATIONSHIP_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERID;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddRelationshipCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddRelationshipCommand object
 */
public class AddRelationshipCommandParser implements Parser<AddRelationshipCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddRelationshipCommand
     * and returns an AddRelationshipCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddRelationshipCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_USERID, PREFIX_FORWARD_RELATIONSHIP_NAME,
                        PREFIX_REVERSE_RELATIONSHIP_NAME, PREFIX_TAG);

        // Check if we have both user IDs and both relationship names
        if (!arePrefixesPresent(argMultimap, PREFIX_USERID, PREFIX_FORWARD_RELATIONSHIP_NAME,
                PREFIX_REVERSE_RELATIONSHIP_NAME)
                || argMultimap.getAllValues(PREFIX_USERID).size() != 2
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddRelationshipCommand.MESSAGE_USAGE));
        }

        // Get the user IDs
        List<String> userIds = argMultimap.getAllValues(PREFIX_USERID);
        String userId1 = userIds.get(0);
        String userId2 = userIds.get(1);

        // Get the relationship names
        String forwardName = argMultimap.getValue(PREFIX_FORWARD_RELATIONSHIP_NAME).get();
        String reverseName = argMultimap.getValue(PREFIX_REVERSE_RELATIONSHIP_NAME).get();

        // Get the tags, if any
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        return new AddRelationshipCommand(userId1, userId2, forwardName, reverseName, tagList);
    }

    /**
     * Returns true if all required prefixes are present and have non-empty values.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
