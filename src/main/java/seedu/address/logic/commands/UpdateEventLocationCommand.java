package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;

/**
 * Updates the location of an event.
 * The event is identified by its index in the filtered event list.
 */
public class UpdateEventLocationCommand extends Command {

    public static final String COMMAND_WORD = "updateEventLoc";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Updates the location of an event.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "l/NEW_LOCATION\n"
            + "Example: " + COMMAND_WORD + " 1 l/Conference Room";
    public static final String MESSAGE_UPDATE_LOCATION_SUCCESS = "Event location updated: %1$s";
    public static final String MESSAGE_EVENT_NOT_FOUND = "The event index provided is invalid.";

    private final Index eventIndex;
    private final String newLocation;

    /**
     * Creates an UpdateEventLocationCommand to update the location of the event at the specified index.
     *
     * @param eventIndex  the index of the event in the filtered event list.
     * @param newLocation the new location for the event.
     */
    public UpdateEventLocationCommand(Index eventIndex, String newLocation) {
        requireNonNull(eventIndex);
        requireNonNull(newLocation);
        this.eventIndex = eventIndex;
        this.newLocation = newLocation;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (eventIndex.getZeroBased() >= model.getFilteredEventList().size()) {
            throw new CommandException(MESSAGE_EVENT_NOT_FOUND);
        }
        Event originalEvent = model.getFilteredEventList().get(eventIndex.getZeroBased());
        Event updatedEvent = originalEvent.withUpdatedLocation(newLocation);
        model.updateEvent(originalEvent, updatedEvent);
        return new CommandResult(String.format(MESSAGE_UPDATE_LOCATION_SUCCESS, updatedEvent));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UpdateEventLocationCommand)) {
            return false;
        }
        UpdateEventLocationCommand o = (UpdateEventLocationCommand) other;
        return eventIndex.equals(o.eventIndex)
                && newLocation.equals(o.newLocation);
    }
}
