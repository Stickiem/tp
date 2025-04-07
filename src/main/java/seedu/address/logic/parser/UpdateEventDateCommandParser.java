package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

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

        List<String> dateStrings = argMultimap.getAllValues(PREFIX_DATE);
        if (dateStrings.size() != 1) {
            throw new ParseException("Please provide exactly one new date.");
        }
        String dateString = dateStrings.get(0);
        LocalDateTime newDate;
        try {
            newDate = LocalDateTime.parse(dateString);
        } catch (DateTimeParseException e) {
            throw new ParseException("Invalid date format. Expected format: yyyy-MM-ddTHH:mm", e);
        }
        return new UpdateEventDateCommand(eventIndex, newDate);
    }
}
