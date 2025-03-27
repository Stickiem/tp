package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddTagToEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddTagToEventCommand object.
 */
public class AddTagToEventCommandParser implements Parser<AddTagToEventCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddTagToEventCommand
     * and returns an AddTagToEventCommand object for execution.
     *
     * @param userInput full user input string.
     * @return an AddTagToEventCommand object containing the parsed data.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    @Override
    public AddTagToEventCommand parse(String userInput) throws ParseException {
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

        return new AddTagToEventCommand(eventIndex, tag);
    }
}
