package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;

/**
 * Updates the date of an event.
 * The event is identified by its index in the filtered event list.
 */
public class UpdateEventDateCommand extends Command {

    public static final String COMMAND_WORD = "updateEventDate";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Updates the date of an event.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "d/NEW_DATE\n"
            + "Example: " + COMMAND_WORD + " 1 d/2025-03-15T09:30";
    public static final String MESSAGE_UPDATE_DATE_SUCCESS = "Event date updated: %1$s";
    public static final String MESSAGE_EVENT_NOT_FOUND = "The event index provided is invalid.";

    private final Index eventIndex;
    private final LocalDateTime newDate;

    /**
     * Creates an UpdateEventDateCommand to update the date of the event at the specified index.
     *
     * @param eventIndex the index of the event in the filtered event list.
     * @param newDate    the new date for the event.
     */
    public UpdateEventDateCommand(Index eventIndex, LocalDateTime newDate) {
        requireNonNull(eventIndex);
        requireNonNull(newDate);
        this.eventIndex = eventIndex;
        this.newDate = newDate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (eventIndex.getZeroBased() >= model.getFilteredEventList().size()) {
            throw new CommandException(MESSAGE_EVENT_NOT_FOUND);
        }
        Event originalEvent = model.getFilteredEventList().get(eventIndex.getZeroBased());
        Event updatedEvent = originalEvent.withUpdatedDate(newDate);
        model.updateEvent(originalEvent, updatedEvent);
        return new CommandResult(String.format(MESSAGE_UPDATE_DATE_SUCCESS, updatedEvent));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UpdateEventDateCommand)) {
            return false;
        }
        UpdateEventDateCommand o = (UpdateEventDateCommand) other;
        return eventIndex.equals(o.eventIndex)
                && newDate.equals(o.newDate);
    }
}
