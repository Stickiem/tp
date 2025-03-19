package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Deletes an event from the address book.
 */
public class DeleteEventCommand extends Command {

    public static final String COMMAND_WORD = "deleteEvent";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes an event from the address book. "
            + "Parameters: u/[EVENT ID]\n"
            + "Example: " + COMMAND_WORD + " u/98765432";

    public static final String MESSAGE_SUCCESS = "Event successfully deleted.";
    public static final String MESSAGE_EVENT_NOT_FOUND = "Error: Event not found.";

    private final String eventId;

    /**
     * Creates a DeleteEventCommand to delete the event with the specified {@code eventId}.
     *
     * @param eventId The unique identifier of the event to delete.
     */
    public DeleteEventCommand(String eventId) {
        requireNonNull(eventId);
        this.eventId = eventId;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Event eventToDelete = model.getEventById(eventId);
        if (eventToDelete == null) {
            throw new CommandException(MESSAGE_EVENT_NOT_FOUND);
        }
        model.deleteEvent(eventToDelete);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteEventCommand
                && eventId.equals(((DeleteEventCommand) other).eventId));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("eventId", eventId)
                .toString();
    }
}
