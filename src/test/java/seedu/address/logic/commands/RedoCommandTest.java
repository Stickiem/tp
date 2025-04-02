// RedoCommandTest.java
package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Deque;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.commandhistory.CommandHistory;

/**
 * Tests for the RedoCommand class.
 */
public class RedoCommandTest {
    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager();
        // Reset the command history before each test
        Deque<String> commands = CommandHistory.getAllCommands();
        commands.clear();
    }

    @Test
    public void execute_invalidCommandIndex_throwsCommandException() {
        RedoCommand redoCommandTooLow = new RedoCommand(0);
        RedoCommand redoCommandTooHigh = new RedoCommand(11);
        assertThrows(CommandException.class, () -> redoCommandTooLow.execute(model));
        assertThrows(CommandException.class, () -> redoCommandTooHigh.execute(model));
    }

    @Test
    public void execute_emptyCommandHistory_throwsCommandException() {
        RedoCommand redoCommand = new RedoCommand(1);
        assertThrows(CommandException.class, () -> redoCommand.execute(model));
    }

    @Test
    public void equals_sameValues_true() {
        RedoCommand redoCommand1 = new RedoCommand(1);
        RedoCommand redoCommand1Copy = new RedoCommand(1);
        assertTrue(redoCommand1.equals(redoCommand1));
        assertTrue(redoCommand1.equals(redoCommand1Copy));
    }

    @Test
    public void equals_differentValues_false() {
        RedoCommand redoCommand1 = new RedoCommand(1);
        RedoCommand redoCommand2 = new RedoCommand(2);
        assertFalse(redoCommand1.equals(null));
        assertFalse(redoCommand1.equals("different type"));
        assertFalse(redoCommand1.equals(redoCommand2));
    }

    @Test
    public void toString_returnsCorrectFormat() {
        RedoCommand redoCommand = new RedoCommand(3);
        assertEquals("RedoCommand{commandIndex=3}", redoCommand.toString());
    }
}

