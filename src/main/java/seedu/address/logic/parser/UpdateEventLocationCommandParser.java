package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UpdateEventLocationCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new UpdateEventLocationCommand object.
 */
public class UpdateEventLocationCommandParser implements Parser<UpdateEventLocationCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UpdateEventLocationCommand
     * and returns an UpdateEventLocationCommand object for execution.
     *
     * @param userInput full user input string.
     * @return an UpdateEventLocationCommand object containing the parsed data.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    @Override
    public UpdateEventLocationCommand parse(String userInput) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(userInput, PREFIX_LOCATION);

        // The preamble should contain the event index.
        String preamble = argMultimap.getPreamble().trim();
        if (preamble.isEmpty()) {
            throw new ParseException("Event index is required.");
        }
        Index eventIndex = ParserUtil.parseIndex(preamble);

        if (!argMultimap.getValue(PREFIX_LOCATION).isPresent()) {
            throw new ParseException("New location is required.");
        }
        String newLocation = argMultimap.getValue(PREFIX_LOCATION).get();
        return new UpdateEventLocationCommand(eventIndex, newLocation);
    }
}
