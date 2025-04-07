package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;

/**
 * Deletes a contact from an event in the address book.
 * <p>
 * The event is identified by its index in the filtered event list.
 * The specified contact is removed from the event’s contacts using the event's {@code deleteContact} method.
 * </p>
 */
public class DeleteContactFromEventCommand extends Command {

    public static final String COMMAND_WORD = "deleteEventContact";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a contact from an event.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "c/CONTACT_NAME\n"
            + "Example: " + COMMAND_WORD + " 1 c/John Doe";
    public static final String MESSAGE_DELETE_CONTACT_SUCCESS = "Contact removed from event: %1$s";
    public static final String MESSAGE_CONTACT_NOT_FOUND = "This contact does not exist in the event";
    public static final String MESSAGE_EVENT_NOT_FOUND = "The event index provided is invalid";

    private final Index eventIndex;
    private final Person contact;

    /**
     * Creates a DeleteContactFromEventCommand to remove the specified contact from the event at the given index.
     *
     * @param eventIndex the index of the event in the filtered event list.
     * @param contact    the contact to remove.
     */
    public DeleteContactFromEventCommand(Index eventIndex, Person contact) {
        requireNonNull(eventIndex);
        requireNonNull(contact);
        this.eventIndex = eventIndex;
        this.contact = contact;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (eventIndex.getZeroBased() >= model.getFilteredEventList().size()) {
            throw new CommandException(MESSAGE_EVENT_NOT_FOUND);
        }
        Event event = model.getFilteredEventList().get(eventIndex.getZeroBased());
        // Check if the contact exists in the event.
        if (event.getContacts().stream().noneMatch(p -> p.equals(contact))) {
            throw new CommandException(MESSAGE_CONTACT_NOT_FOUND);
        }
        event.deleteContact(contact);

        model.updateEvent(event, event);
        return new CommandResult(String.format(MESSAGE_DELETE_CONTACT_SUCCESS, event));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof DeleteContactFromEventCommand otherCommand)) {
            return false;
        }
        return eventIndex.equals(otherCommand.eventIndex) && contact.equals(otherCommand.contact);
    }
}
