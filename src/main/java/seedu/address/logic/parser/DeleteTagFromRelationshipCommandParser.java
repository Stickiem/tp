package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERID;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import seedu.address.logic.commands.DeleteTagFromRelationshipCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new DeleteTagFromRelationshipCommand object.
 */
public class DeleteTagFromRelationshipCommandParser implements Parser<DeleteTagFromRelationshipCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteTagFromRelationshipCommand
     * and returns a DeleteTagFromRelationshipCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format
     */
    @Override
    public DeleteTagFromRelationshipCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_USERID, PREFIX_NAME, PREFIX_TAG);

        // Check if all required prefixes are present
        if (!arePrefixesPresent(argMultimap, PREFIX_USERID, PREFIX_NAME, PREFIX_TAG)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTagFromRelationshipCommand.MESSAGE_USAGE));
        }

        // Get and validate user IDs
        List<String> userIds = argMultimap.getAllValues(PREFIX_USERID);
        if (userIds.size() != 2) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTagFromRelationshipCommand.MESSAGE_USAGE));
        }

        // Get relationship name
        Optional<String> relationshipNameOpt = argMultimap.getValue(PREFIX_NAME);
        if (relationshipNameOpt.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTagFromRelationshipCommand.MESSAGE_USAGE));
        }
        String relationshipName = relationshipNameOpt.get();

        // Get tag
        Optional<String> tagOpt = argMultimap.getValue(PREFIX_TAG);
        if (tagOpt.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTagFromRelationshipCommand.MESSAGE_USAGE));
        }
        Tag tag;
        try {
            tag = ParserUtil.parseTag(tagOpt.get());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTagFromRelationshipCommand.MESSAGE_USAGE), pe);
        }

        return new DeleteTagFromRelationshipCommand(userIds.get(0), userIds.get(1), relationshipName, tag);
    }

    /**
     * Returns true if all the prefixes are present in the ArgumentMultimap.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
