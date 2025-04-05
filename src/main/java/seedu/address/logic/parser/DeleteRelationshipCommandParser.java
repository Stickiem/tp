package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERID;

import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.DeleteRelationshipCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteRelationshipCommand object.
 */
public class DeleteRelationshipCommandParser implements Parser<DeleteRelationshipCommand> {
    private static final Logger logger = LogsCenter.getLogger(DeleteRelationshipCommandParser.class);

    private static final String ERROR_WRONG_USER_ID_COUNT = "Exactly two user IDs must be provided";
    private static final String ERROR_MISSING_RELATIONSHIP_NAME = "Relationship name must be provided";

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteRelationshipCommand
     * and returns a DeleteRelationshipCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format
     */
    public DeleteRelationshipCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = tokenizeArguments(args);
        validateArguments(argMultimap);

        String firstUserId = extractFirstUserId(argMultimap);
        String secondUserId = extractSecondUserId(argMultimap);
        String relationshipName = extractRelationshipName(argMultimap);

        logger.fine(String.format("Parsed delete relationship command for users %s and %s with name '%s'",
                firstUserId, secondUserId, relationshipName));

        return new DeleteRelationshipCommand(firstUserId, secondUserId, relationshipName);
    }

    /**
     * Tokenizes the command arguments into an ArgumentMultimap.
     */
    private ArgumentMultimap tokenizeArguments(String args) {
        return ArgumentTokenizer.tokenize(args, PREFIX_USERID, PREFIX_NAME);
    }

    /**
     * Validates the structure and presence of required arguments.
     *
     * @throws ParseException if the arguments are invalid
     */
    private void validateArguments(ArgumentMultimap argMultimap) throws ParseException {
        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteRelationshipCommand.MESSAGE_USAGE));
        }

        validateUserIdCount(argMultimap);
        validateRelationshipName(argMultimap);
    }

    private void validateUserIdCount(ArgumentMultimap argMultimap) throws ParseException {
        List<String> userIds = argMultimap.getAllValues(PREFIX_USERID);
        if (userIds.size() != 2) {
            throw new ParseException(ERROR_WRONG_USER_ID_COUNT);
        }
    }

    private void validateRelationshipName(ArgumentMultimap argMultimap) throws ParseException {
        if (argMultimap.getValue(PREFIX_NAME).isEmpty()) {
            throw new ParseException(ERROR_MISSING_RELATIONSHIP_NAME);
        }
    }

    private String extractFirstUserId(ArgumentMultimap argMultimap) {
        List<String> userIds = argMultimap.getAllValues(PREFIX_USERID);
        return userIds.get(0);
    }

    private String extractSecondUserId(ArgumentMultimap argMultimap) {
        List<String> userIds = argMultimap.getAllValues(PREFIX_USERID);
        return userIds.get(1);
    }

    private String extractRelationshipName(ArgumentMultimap argMultimap) {
        return argMultimap.getValue(PREFIX_NAME)
                .orElseThrow(() -> new IllegalStateException("Relationship name not found after validation"));
    }
}
