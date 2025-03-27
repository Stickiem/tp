package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteTagFromEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new DeleteTagFromEventCommand object.
 */
public class DeleteTagFromEventCommandParser implements Parser<DeleteTagFromEventCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteTagFromEventCommand
     * and returns a DeleteTagFromEventCommand object for execution.
     *
     * @param userInput full user input string.
     * @return a DeleteTagFromEventCommand object containing the parsed data.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    @Override
    public DeleteTagFromEventCommand parse(String userInput) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(userInput, PREFIX_TAG);

        // The preamble should contain the event index.
        String indexString = argMultimap.getPreamble().trim();
        if (indexString.isEmpty()) {
            throw new ParseException("Event index is required.");
        }
        Index eventIndex = ParserUtil.parseIndex(indexString);

        if (!argMultimap.getValue(PREFIX_TAG).isPresent()) {
            throw new ParseException("Tag information is required.");
        }
        String tagString = argMultimap.getValue(PREFIX_TAG).get();
        Tag tag = ParserUtil.parseTag(tagString);

        return new DeleteTagFromEventCommand(eventIndex, tag);
    }
}
