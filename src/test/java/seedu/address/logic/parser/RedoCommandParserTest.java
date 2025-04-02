package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class RedoCommandParserTest {
    private final RedoCommandParser parser = new RedoCommandParser();

    @Test
    public void parse_validArgs_returnsRedoCommand() throws ParseException {
        // Valid index

        assertParseSuccess(parser, "1", new RedoCommand(1));
        assertParseSuccess(parser, "3", new RedoCommand(3));
        assertParseSuccess(parser, "5", new RedoCommand(5));

        // Valid index with leading/trailing spaces
        assertParseSuccess(parser, " 2 ", new RedoCommand(2));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Empty args
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RedoCommand.MESSAGE_USAGE));

        // Non-numeric index
        assertParseFailure(parser, "abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RedoCommand.MESSAGE_USAGE));

        // Index out of range (< 1)
        assertParseFailure(parser, "0",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RedoCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "-1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RedoCommand.MESSAGE_USAGE));

        // Index out of range (> 5)
        assertParseFailure(parser, "6",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RedoCommand.MESSAGE_USAGE));
    }
}




