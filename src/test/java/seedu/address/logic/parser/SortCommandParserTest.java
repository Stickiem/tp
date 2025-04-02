package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SortCommand;

public class SortCommandParserTest {
    private final SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, " ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsSortCommand() {
        // Single field
        List<String> fields = Arrays.asList("name");
        SortCommand expectedCommand = new SortCommand(false, fields);
        assertParseSuccess(parser, "name", expectedCommand);

        // Multiple fields
        fields = Arrays.asList("name", "phone", "email");
        expectedCommand = new SortCommand(false, fields);
        assertParseSuccess(parser, "name phone email", expectedCommand);

        // With reverse flag
        fields = Arrays.asList("name");
        expectedCommand = new SortCommand(true, fields);
        assertParseSuccess(parser, "-r name", expectedCommand);

        // With multiple whitespaces
        fields = Arrays.asList("name", "address");
        expectedCommand = new SortCommand(false, fields);
        assertParseSuccess(parser, " \n name \n \t address \t", expectedCommand);
    }

    @Test
    public void parse_invalidField_throwsParseException() {
        assertParseFailure(parser, "invalid", "Invalid field: invalid");
        assertParseFailure(parser, "name invalid", "Invalid field: invalid");
        assertParseFailure(parser, "-r invalid", "Invalid field: invalid");
    }
}




