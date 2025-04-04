package seedu.address.logic.commands;

import java.util.Deque;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.commandhistory.CommandHistory;

/**
 * Command to list the last 10 commands in the command history.
 * This command will always throw a CommandException for demonstration.
 */
public class RedoListCommand extends Command {

    public static final String COMMAND_WORD = "redoList";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists the all commands in the history.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Here are the last commands you used:\n%s";
    public static final String MESSAGE_EMPTY = "No history exists.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        StringBuilder history = new StringBuilder();
        int index = 1;
        Deque<String> commandHistory = CommandHistory.getAllCommands();
        for (String command : commandHistory) {
            history.append(index++).append(". ").append(command).append("\n");
        }

        if (history.isEmpty()) {
            throw new CommandException(MESSAGE_EMPTY);
        }

        throw new CommandException(String.format(MESSAGE_SUCCESS, history));
    }

    @Override
    public String toString() {
        return "RedoListCommand{}";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass();
    }
}
