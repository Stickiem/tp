package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindPhoneCommand;
import seedu.address.model.predicate.PhoneContainsKeywordsAsSubstringPredicate;

public class FindPhoneCommandParserTest {
    private final FindPhoneCommandParser parser = new FindPhoneCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, " ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPhoneCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindPhoneCommand expectedFindCommand = new FindPhoneCommand(
                new PhoneContainsKeywordsAsSubstringPredicate(Arrays.asList("12345", "98765")));
        assertParseSuccess(parser, "12345 98765", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n 12345 \n \t 98765 \t", expectedFindCommand);
    }
}

