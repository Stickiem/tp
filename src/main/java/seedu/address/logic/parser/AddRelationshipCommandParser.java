package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FORWARD_RELATIONSHIP_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REVERSE_RELATIONSHIP_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERID;

import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Stream;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AddRelationshipCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddRelationshipCommand object
 */
public class AddRelationshipCommandParser implements Parser<AddRelationshipCommand> {
    private static final Logger logger = LogsCenter.getLogger(AddRelationshipCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the AddRelationshipCommand
     * and returns an AddRelationshipCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public AddRelationshipCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = tokenizeArguments(args);
        validateRequiredArguments(argMultimap);

        try {
            String firstUserId = extractFirstUserId(argMultimap);
            String secondUserId = extractSecondUserId(argMultimap);
            String forwardName = extractForwardName(argMultimap);
            String reverseName = extractReverseName(argMultimap);
            Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

            logger.fine(String.format("Parsed relationship command: %s-%s->%s, %s-%s->%s",
                    firstUserId, forwardName, secondUserId, secondUserId, reverseName, firstUserId));

            return new AddRelationshipCommand(firstUserId, secondUserId, forwardName, reverseName, tagList);
        } catch (IllegalArgumentException e) {
            throw new ParseException(e.getMessage());
        }
    }

    private ArgumentMultimap tokenizeArguments(String args) {
        return ArgumentTokenizer.tokenize(args, PREFIX_USERID, PREFIX_FORWARD_RELATIONSHIP_NAME,
                PREFIX_REVERSE_RELATIONSHIP_NAME, PREFIX_TAG);
    }

    private void validateRequiredArguments(ArgumentMultimap argMultimap) throws ParseException {
        if (!areAllRequiredPrefixesPresent(argMultimap)
                || !hasExactlyTwoUserIds(argMultimap)
                || !hasEmptyPreamble(argMultimap)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddRelationshipCommand.MESSAGE_USAGE));
        }
    }

    private boolean areAllRequiredPrefixesPresent(ArgumentMultimap argumentMultimap) {
        return Stream.of(PREFIX_USERID, PREFIX_FORWARD_RELATIONSHIP_NAME, PREFIX_REVERSE_RELATIONSHIP_NAME)
                .allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    private boolean hasExactlyTwoUserIds(ArgumentMultimap argMultimap) {
        return argMultimap.getAllValues(PREFIX_USERID).size() == 2;
    }

    private boolean hasEmptyPreamble(ArgumentMultimap argMultimap) {
        return argMultimap.getPreamble().isEmpty();
    }

    private String extractFirstUserId(ArgumentMultimap argMultimap) {
        List<String> userIds = argMultimap.getAllValues(PREFIX_USERID);
        return userIds.get(0);
    }

    private String extractSecondUserId(ArgumentMultimap argMultimap) {
        List<String> userIds = argMultimap.getAllValues(PREFIX_USERID);
        return userIds.get(1);
    }

    private String extractForwardName(ArgumentMultimap argMultimap) {
        return argMultimap.getValue(PREFIX_FORWARD_RELATIONSHIP_NAME)
                .orElseThrow(() -> new IllegalArgumentException("Forward relationship name is required"));
    }

    private String extractReverseName(ArgumentMultimap argMultimap) {
        return argMultimap.getValue(PREFIX_REVERSE_RELATIONSHIP_NAME)
                .orElseThrow(() -> new IllegalArgumentException("Reverse relationship name is required"));
    }
}
