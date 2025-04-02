package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindAddressCommand;
import seedu.address.model.predicate.AddressContainsKeywordsAsSubstringPredicate;

public class FindAddressCommandParserTest {
    private final FindAddressCommandParser parser = new FindAddressCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, " ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindAddressCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindAddressCommand expectedFindCommand = new FindAddressCommand(
                new AddressContainsKeywordsAsSubstringPredicate(Arrays.asList("NUS", "Com1")));
        assertParseSuccess(parser, "NUS Com1", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n NUS \n \t Com1 \t", expectedFindCommand);
    }
}


