package seedu.address.model.commandhistory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Deque;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class CommandHistoryTest {

    @BeforeEach
    public void setUp() {
        // Clear the command history before each test
        CommandHistory.getAllCommands().clear();
    }

    @Test
    public void addCommandToHistory_validCommand_addedToHistory() throws ParseException {
        CommandHistory.addCommandToHistory("list");
        assertEquals(1, CommandHistory.getAllCommands().size());
        assertEquals("list", CommandHistory.getCommandFromHistory(1));
    }

    @Test
    public void addCommandToHistory_emptyCommand_throwsParseException() {
        assertThrows(ParseException.class, () -> CommandHistory.addCommandToHistory(""));
    }

    @Test
    public void addCommandToHistory_invalidFormat_throwsParseException() {
        assertThrows(ParseException.class, () -> CommandHistory.addCommandToHistory(" "));
        assertThrows(ParseException.class, () ->
                        CommandHistory.addCommandToHistory("  "),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
    }

    @Test
    public void addCommandToHistory_redoCommand_notAddedToHistory() throws ParseException {
        CommandHistory.addCommandToHistory("redo 1");
        assertEquals(0, CommandHistory.getAllCommands().size());
    }

    @Test
    public void addCommandToHistory_multipleCommands_maintainsOrder() throws ParseException {
        CommandHistory.addCommandToHistory("list");
        CommandHistory.addCommandToHistory("findName TKN");
        CommandHistory.addCommandToHistory("delete 1");

        assertEquals(3, CommandHistory.getAllCommands().size());
        assertEquals("delete 1", CommandHistory.getCommandFromHistory(1));
        assertEquals("findName TKN", CommandHistory.getCommandFromHistory(2));
        assertEquals("list", CommandHistory.getCommandFromHistory(3));
    }

    @Test
    public void addCommandToHistory_exceedsMaxSize_removesOldestCommand() throws ParseException {
        // Add 11 commands to exceed the limit of 10
        for (int i = 1; i <= 11; i++) {
            CommandHistory.addCommandToHistory("command" + i);
        }

        assertEquals(10, CommandHistory.getAllCommands().size());
        assertEquals("command11", CommandHistory.getCommandFromHistory(1));

        // The oldest command should have been removed
        assertThrows(IndexOutOfBoundsException.class, () -> CommandHistory.getCommandFromHistory(11));
    }

    @Test
    public void getCommandFromHistory_validIndex_returnsCommand() throws ParseException {
        CommandHistory.addCommandToHistory("list");
        CommandHistory.addCommandToHistory("findName TKN");

        assertEquals("findName TKN", CommandHistory.getCommandFromHistory(1));
        assertEquals("list", CommandHistory.getCommandFromHistory(2));
    }

    @Test
    public void getCommandFromHistory_invalidIndex_throwsException() throws ParseException {
        CommandHistory.addCommandToHistory("list");

        // Index out of bounds
        assertThrows(IndexOutOfBoundsException.class, () -> CommandHistory.getCommandFromHistory(2));
        assertThrows(IndexOutOfBoundsException.class, () -> CommandHistory.getCommandFromHistory(0));
    }

    @Test
    public void getAllCommands_returnsAllCommands() throws ParseException {
        CommandHistory.addCommandToHistory("list");
        CommandHistory.addCommandToHistory("findName TKN");

        Deque<String> commands = CommandHistory.getAllCommands();
        assertEquals(2, commands.size());
        assertEquals("findName TKN", commands.getFirst());
        assertEquals("list", commands.getLast());
    }
}


