package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.RedoListCommand;

public class RedoListCommandParserTest {
    private final RedoListCommandParser parser = new RedoListCommandParser();

    @Test
    public void parse_emptyArgs_returnsRedoListCommand() {
        // Empty args
        assertParseSuccess(parser, "", new RedoListCommand());

        // Whitespace only
        assertParseSuccess(parser, "   ", new RedoListCommand());
    }

    @Test
    public void parse_nonEmptyArgs_throwsParseException() {
        // Non-empty args
        assertParseFailure(parser, "1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RedoListCommand.MESSAGE_USAGE));

        // Other text
        assertParseFailure(parser, "list",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RedoListCommand.MESSAGE_USAGE));
    }
}


