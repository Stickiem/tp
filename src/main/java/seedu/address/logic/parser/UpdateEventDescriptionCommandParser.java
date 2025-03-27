package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UpdateEventDescriptionCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new UpdateEventDescriptionCommand object.
 */
public class UpdateEventDescriptionCommandParser implements Parser<UpdateEventDescriptionCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UpdateEventDescriptionCommand
     * and returns an UpdateEventDescriptionCommand object for execution.
     *
     * @param userInput full user input string.
     * @return an UpdateEventDescriptionCommand object containing the parsed data.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    @Override
    public UpdateEventDescriptionCommand parse(String userInput) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(userInput, PREFIX_DESCRIPTION);

        // The preamble should contain the event index.
        String preamble = argMultimap.getPreamble().trim();
        if (preamble.isEmpty()) {
            throw new ParseException("Event index is required.");
        }
        Index eventIndex = ParserUtil.parseIndex(preamble);

        if (!argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            throw new ParseException("New description is required.");
        }
        String newDescription = argMultimap.getValue(PREFIX_DESCRIPTION).get();
        return new UpdateEventDescriptionCommand(eventIndex, newDescription);
    }
}
