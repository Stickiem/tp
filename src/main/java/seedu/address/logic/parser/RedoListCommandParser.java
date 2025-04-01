package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.RedoListCommand;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new RedoListCommand object
 */
public class RedoListCommandParser implements Parser<RedoListCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RedoListCommand
     * and returns a RedoListCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RedoListCommand parse(String args) throws ParseException {
        if (!args.trim().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RedoListCommand.MESSAGE_USAGE));
        }

        return new RedoListCommand();
    }
}
