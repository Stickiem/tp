package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SortCommand object.
 */
public class SortCommandParser implements Parser<SortCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns a SortCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format
     */
    public SortCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        boolean isReverse = false;

        // Check for reverse flag
        if (trimmedArgs.startsWith("-r")) {
            isReverse = true;
            trimmedArgs = trimmedArgs.substring(2).trim();
        }

        String[] argsParts = trimmedArgs.split("\\s+");

        List<String> fields = new ArrayList<>(Arrays.asList(argsParts));

        if (fields.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        for (String field : fields) {
            if (!isValidField(field)) {
                throw new ParseException("Invalid field: " + field);
            }
        }

        return new SortCommand(isReverse, fields);
    }

    private boolean isValidField(String field) {
        List<String> validFields = Arrays.asList("name", "phone", "email", "address", "tags", "socials");
        return validFields.contains(field.toLowerCase());
    }
}
