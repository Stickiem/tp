package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.tag.Tag;

/**
 * Deletes a tag from an event in the address book.
 * <p>
 * The event is identified by its index in the filtered event list.
 * </p>
 */
public class DeleteTagFromEventCommand extends Command {

    public static final String COMMAND_WORD = "deleteEventTag";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a tag from an event.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "t/TAG_NAME\n"
            + "Example: " + COMMAND_WORD + " 1 t/Finance";
    public static final String MESSAGE_DELETE_TAG_SUCCESS = "Tag removed from event: %1$s";
    public static final String MESSAGE_TAG_NOT_FOUND = "This tag does not exist in the event";
    public static final String MESSAGE_EVENT_NOT_FOUND = "The event index provided is invalid.";

    private final Index eventIndex;
    private final Tag tag;

    /**
     * Creates a {@code DeleteTagFromEventCommand} to remove the specified tag from the event at the given index.
     *
     * @param eventIndex the index of the event in the filtered event list.
     * @param tag the tag to remove.
     */
    public DeleteTagFromEventCommand(Index eventIndex, Tag tag) {
        requireNonNull(eventIndex);
        requireNonNull(tag);
        this.eventIndex = eventIndex;
        this.tag = tag;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (eventIndex.getZeroBased() >= model.getFilteredEventList().size()) {
            throw new CommandException(MESSAGE_EVENT_NOT_FOUND);
        }
        Event event = model.getFilteredEventList().get(eventIndex.getZeroBased());
        if (!event.getTags().contains(tag)) {
            throw new CommandException(MESSAGE_TAG_NOT_FOUND);
        }
        Event updatedEvent = event.withRemovedTag(tag);
        model.updateEvent(event, updatedEvent);
        return new CommandResult(String.format(MESSAGE_DELETE_TAG_SUCCESS, updatedEvent));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof DeleteTagFromEventCommand)) {
            return false;
        }
        DeleteTagFromEventCommand otherCommand = (DeleteTagFromEventCommand) other;
        return eventIndex.equals(otherCommand.eventIndex)
                && tag.equals(otherCommand.tag);
    }
}
