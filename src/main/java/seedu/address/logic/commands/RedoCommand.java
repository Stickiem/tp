package seedu.address.logic.commands;

import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.commandhistory.CommandHistory;

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
            commandToRedo = CommandHistory.getCommandFromHistory(commandIndex);
        } catch (IndexOutOfBoundsException e) {
            throw new CommandException(MESSAGE_NOT_ENOUGH_COMMAND_HISTORY);
        }

        AddressBookParser parser = new AddressBookParser();
        Command command;
        try {
            command = parser.parseCommand(commandToRedo, model);
        } catch (ParseException e) {
            throw new CommandException("Error while re-executing the command: " + commandToRedo);
        }

        return command.execute(model);
    }
}
