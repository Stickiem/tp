package seedu.address.logic.commands;

import java.util.Deque;
import java.util.LinkedList;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;


/**
 * Redo command that re-executes the x-th last command.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " COMMAND_NUMBER\n"
            + "Executes the x-th last command. \n"
            + "Command number must be between 1 and 5.";

    public static final String MESSAGE_INVALID_COMMAND_INDEX = "Invalid command index. Must be between 1 and 5.";
    public static final String MESSAGE_NOT_ENOUGH_COMMAND_HISTORY = "Not enough commands in history to redo.";
    /**
     * Deque to store the last 5 user inputs.
     * This will store the history of executed commands.
     */
    private static final Deque<String> lastCommands = new LinkedList<>();
    private final int commandIndex;

    public RedoCommand(int commandIndex) {
        this.commandIndex = commandIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (commandIndex < 1 || commandIndex > 5) {
            throw new CommandException(MESSAGE_INVALID_COMMAND_INDEX);
        }

        String commandToRedo;
        try {
            commandToRedo = getCommandFromHistory(commandIndex);
        } catch (IndexOutOfBoundsException e) {
            throw new CommandException(MESSAGE_NOT_ENOUGH_COMMAND_HISTORY);
        }

        AddressBookParser parser = new AddressBookParser();
        try {
            parser.parseCommand(commandToRedo, model);
        } catch (ParseException e) {
            throw new CommandException("Error while re-executing the command: " + commandToRedo);
        }

        return new CommandResult("Re-executed command: " + commandToRedo);
    }

    /**
     * Adds the given command input to the history of last 5 commands.
     * If the list exceeds 5, removes the oldest command.
     *
     * @param userInput The command to add to the history
     */
    public static void addCommandToHistory(String userInput) {
        if (lastCommands.size() == 5) {
            lastCommands.poll();
        }
        lastCommands.offer(userInput);
    }

    /**
     * Retrieves the command at the specified index from the lastCommands deque.
     *
     * @param index The index of the command in the deque (1-based).
     * @return The command string at the specified index.
     */
    private String getCommandFromHistory(int index) {
        int dequeIndex = 5 - index;

        if (lastCommands.size() <= dequeIndex) {
            throw new IndexOutOfBoundsException("Not enough commands in history to redo.");
        }

        return (String) lastCommands.toArray()[dequeIndex];
    }

    /**
     * Returns the last 5 commands (user inputs).
     *
     * @return list of last 5 commands.
     */
    public static Deque<String> getLastCommands() {
        return lastCommands;
    }
}
