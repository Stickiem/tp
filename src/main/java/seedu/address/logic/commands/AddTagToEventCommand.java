package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.tag.Tag;

/**
 * Adds a tag to an event in the address book.
 * <p>
 * The event is identified by its index in the filtered event list.
 * </p>
 */
public class AddTagToEventCommand extends Command {

    public static final String COMMAND_WORD = "addEventTag";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a tag to an event.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "t/TAG_NAME\n"
            + "Example: " + COMMAND_WORD + " 1 t/Finance";
    public static final String MESSAGE_ADD_TAG_SUCCESS = "Tag added to event: %1$s";
    public static final String MESSAGE_TAG_ALREADY_EXISTS = "This tag already exists in the event";
    public static final String MESSAGE_EVENT_NOT_FOUND = "The event index provided is invalid";

    private final Index eventIndex;
    private final Tag tag;

    /**
     * Creates an {@code AddTagToEventCommand} to add the specified tag to the event at the given index.
     *
     * @param eventIndex the index of the event in the filtered event list.
     * @param tag the tag to add.
     */
    public AddTagToEventCommand(Index eventIndex, Tag tag) {
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
        if (event.getTags().contains(tag)) {
            throw new CommandException(MESSAGE_TAG_ALREADY_EXISTS);
        }
        Event updatedEvent = event.withAddedTag(tag);
        model.updateEvent(event, updatedEvent);
        return new CommandResult(String.format(MESSAGE_ADD_TAG_SUCCESS, updatedEvent));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof AddTagToEventCommand)) {
            return false;
        }
        AddTagToEventCommand otherCommand = (AddTagToEventCommand) other;
        return eventIndex.equals(otherCommand.eventIndex)
                && tag.equals(otherCommand.tag);
    }
}
