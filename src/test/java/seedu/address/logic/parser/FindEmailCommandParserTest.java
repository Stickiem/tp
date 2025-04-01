package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindEmailCommand;
import seedu.address.model.predicate.EmailContainsKeywordsAsSubstringPredicate;

public class FindEmailCommandParserTest {
    private final FindEmailCommandParser parser = new FindEmailCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, " ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindEmailCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindEmailCommand expectedFindCommand = new FindEmailCommand(
                new EmailContainsKeywordsAsSubstringPredicate(Arrays.asList("gmail", "hotmail")));
        assertParseSuccess(parser, "gmail hotmail", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n gmail \n \t hotmail \t", expectedFindCommand);
    }
}


