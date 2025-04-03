package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UpdateEventDateCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new UpdateEventDateCommand object.
 */
public class UpdateEventDateCommandParser implements Parser<UpdateEventDateCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UpdateEventDateCommand
     * and returns an UpdateEventDateCommand object for execution.
     *
     * @param userInput full user input string.
     * @return an UpdateEventDateCommand object containing the parsed data.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    @Override
    public UpdateEventDateCommand parse(String userInput) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(userInput, PREFIX_DATE);

        // The preamble should contain the event index.
        String preamble = argMultimap.getPreamble().trim();
        if (preamble.isEmpty()) {
            throw new ParseException("Event index is required.");
        }
        Index eventIndex = ParserUtil.parseIndex(preamble);

        if (!argMultimap.getValue(PREFIX_DATE).isPresent()) {
            throw new ParseException("New date is required.");
        }
        String dateString = argMultimap.getValue(PREFIX_DATE).get();
        LocalDateTime newDate;
        try {
            newDate = LocalDateTime.parse(dateString);
        } catch (DateTimeParseException e) {
            throw new ParseException("Invalid date format. Expected format: yyyy-MM-ddTHH:mm", e);
        }
        return new UpdateEventDateCommand(eventIndex, newDate);
    }
}
