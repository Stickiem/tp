package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindSocialCommand;
import seedu.address.model.predicate.SocialContainsKeywordsAsSubstringPredicate;

public class FindSocialCommandParserTest {
    private final FindSocialCommandParser parser = new FindSocialCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, " ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindSocialCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindSocialCommand expectedFindCommand = new FindSocialCommand(
                new SocialContainsKeywordsAsSubstringPredicate(Arrays.asList("twitter", "instagram")));
        assertParseSuccess(parser, "twitter instagram", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n twitter \n \t instagram \t", expectedFindCommand);
    }
}


