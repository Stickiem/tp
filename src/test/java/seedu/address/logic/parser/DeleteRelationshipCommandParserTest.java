package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteRelationshipCommand;

public class DeleteRelationshipCommandParserTest {
    private final DeleteRelationshipCommandParser parser = new DeleteRelationshipCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        assertParseSuccess(parser, " u/1 u/2 n/Friend",
                new DeleteRelationshipCommand("1", "2", "Friend"));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = "Exactly two user IDs must be provided";

        // Missing user ID prefixes
        assertParseFailure(parser, " n/Friend", expectedMessage);

        // Only one user ID
        assertParseFailure(parser, " u/1 n/Friend", expectedMessage);

        // Missing name prefix
        assertParseFailure(parser, " u/1 u/2", "Relationship name must be provided");

        // Missing all prefixes
        assertParseFailure(parser, "", expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // Non-empty preamble
        assertParseFailure(parser, "some random string u/1 u/2 n/Friend",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteRelationshipCommand.MESSAGE_USAGE));
    }
}
