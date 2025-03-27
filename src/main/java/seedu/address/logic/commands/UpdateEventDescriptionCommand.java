package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;

/**
 * Updates the description of an event.
 * The event is identified by its index in the filtered event list.
 */
public class UpdateEventDescriptionCommand extends Command {

    public static final String COMMAND_WORD = "updateEventDesc";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Updates the description of an event.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "desc/NEW_DESCRIPTION\n"
            + "Example: " + COMMAND_WORD + " 1 desc/New description for the event";
    public static final String MESSAGE_UPDATE_DESCRIPTION_SUCCESS = "Event description updated: %1$s";
    public static final String MESSAGE_EVENT_NOT_FOUND = "The event index provided is invalid.";

    private final Index eventIndex;
    private final String newDescription;

    /**
     * Creates an UpdateEventDescriptionCommand to update the description of the event at the specified index.
     *
     * @param eventIndex     the index of the event in the filtered event list.
     * @param newDescription the new description for the event.
     */
    public UpdateEventDescriptionCommand(Index eventIndex, String newDescription) {
        requireNonNull(eventIndex);
        requireNonNull(newDescription);
        this.eventIndex = eventIndex;
        this.newDescription = newDescription;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (eventIndex.getZeroBased() >= model.getFilteredEventList().size()) {
            throw new CommandException(MESSAGE_EVENT_NOT_FOUND);
        }
        Event originalEvent = model.getFilteredEventList().get(eventIndex.getZeroBased());
        Event updatedEvent = originalEvent.withUpdatedDescription(newDescription);
        model.updateEvent(originalEvent, updatedEvent);
        return new CommandResult(String.format(MESSAGE_UPDATE_DESCRIPTION_SUCCESS, updatedEvent));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UpdateEventDescriptionCommand)) {
            return false;
        }
        UpdateEventDescriptionCommand o = (UpdateEventDescriptionCommand) other;
        return eventIndex.equals(o.eventIndex) && newDescription.equals(o.newDescription);
    }
}
