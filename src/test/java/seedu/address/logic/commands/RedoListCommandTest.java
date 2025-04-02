package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.RedoListCommand.MESSAGE_EMPTY;
import static seedu.address.logic.commands.RedoListCommand.MESSAGE_SUCCESS;

import java.util.Deque;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.commandhistory.CommandHistory;

/**
 * Tests for the RedoListCommand class.
 */
public class RedoListCommandTest {
    private Model model;
    private RedoListCommand redoListCommand;

    @BeforeEach
    public void setUp() {
        model = new ModelManager();
        redoListCommand = new RedoListCommand();
        // Reset the command history before each test
        Deque<String> commands = CommandHistory.getAllCommands();
        commands.clear();
    }

    @Test
    public void execute_emptyHistory_throwsCommandExceptionWithEmptyMessage() {
        CommandException exception = assertThrows(CommandException.class, () -> redoListCommand.execute(model));
        assertEquals(MESSAGE_EMPTY, exception.getMessage());
    }

    @Test
    public void execute_nonEmptyHistory_throwsCommandExceptionWithHistory() {
        // Add commands to history
        Deque<String> history = CommandHistory.getAllCommands();
        history.push("command3");
        history.push("command2");
        history.push("command1");

        // Expected output
        StringBuilder expectedOutput = new StringBuilder();
        expectedOutput.append("1. command1\n");
        expectedOutput.append("2. command2\n");
        expectedOutput.append("3. command3\n");

        // Execute and verify
        CommandException exception = assertThrows(CommandException.class, () -> redoListCommand.execute(model));
        assertEquals(String.format(MESSAGE_SUCCESS, expectedOutput.toString()), exception.getMessage());
    }

    @Test
    public void equals_sameType_true() {
        RedoListCommand anotherCommand = new RedoListCommand();
        assertTrue(redoListCommand.equals(redoListCommand));
        assertTrue(redoListCommand.equals(anotherCommand));
    }

    @Test
    public void equals_differentType_false() {
        assertFalse(redoListCommand.equals(null));
        assertFalse(redoListCommand.equals("different type"));
    }

    @Test
    public void toString_returnsCorrectFormat() {
        assertEquals("RedoListCommand{}", redoListCommand.toString());
    }
}

