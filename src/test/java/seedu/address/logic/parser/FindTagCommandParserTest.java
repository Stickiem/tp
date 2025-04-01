package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindTagCommand;
import seedu.address.model.predicate.TagContainsKeywordsAsSubstringPredicate;
import seedu.address.model.tag.Tag;

public class FindTagCommandParserTest {
    private final FindTagCommandParser parser = new FindTagCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, " ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(new Tag("friends"));
        tagSet.add(new Tag("colleagues"));

        FindTagCommand expectedFindCommand = new FindTagCommand(
                new TagContainsKeywordsAsSubstringPredicate(tagSet));
        assertParseSuccess(parser, "friends colleagues", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n friends \n \t colleagues \t", expectedFindCommand);
    }
}


