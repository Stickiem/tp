package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteTagFromRelationshipCommand;

public class DeleteTagFromRelationshipCommandParserTest {
    private final DeleteTagFromRelationshipCommandParser parser = new DeleteTagFromRelationshipCommandParser();

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteTagFromRelationshipCommand.MESSAGE_USAGE);

        // Missing user IDs
        assertParseFailure(parser, " n/Friend t/colleague", expectedMessage);

        // Missing first user ID
        assertParseFailure(parser, " u/2 n/Friend t/colleague", expectedMessage);

        // Missing second user ID
        assertParseFailure(parser, " u/1 n/Friend t/colleague", expectedMessage);

        // Missing relationship name
        assertParseFailure(parser, " u/1 u/2 t/colleague", expectedMessage);

        // Missing tag
        assertParseFailure(parser, " u/1 u/2 n/Friend", expectedMessage);
    }

    @Test
    public void parse_invalidFormat_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteTagFromRelationshipCommand.MESSAGE_USAGE);

        // No prefix for userIds
        assertParseFailure(parser, " 1 2 n/Friend t/colleague", expectedMessage);

        // No prefix for name
        assertParseFailure(parser, " u/1 u/2 Friend t/colleague", expectedMessage);

        // No prefix for tag
        assertParseFailure(parser, " u/1 u/2 n/Friend colleague", expectedMessage);

        // Invalid preamble
        assertParseFailure(parser, "some random text u/1 u/2 n/Friend t/colleague", expectedMessage);
    }

    @Test
    public void parse_incorrectNumberOfUserIds_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteTagFromRelationshipCommand.MESSAGE_USAGE);

        // Only one user ID
        assertParseFailure(parser, " u/1 n/Friend t/colleague", expectedMessage);

        // More than two user IDs
        assertParseFailure(parser, " u/1 u/2 u/3 n/Friend t/colleague", expectedMessage);
    }
}
