package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;

/**
 * Adds an event to the address book.
 * <p>
 * This command also ensures that any contacts specified as part of the event are added to the address book's
 * person list if they do not already exist. The contacts are added to the event using the event's
 * {@code addContact} method.
 * </p>
 */
public class AddEventCommand extends Command {

    public static final String COMMAND_WORD = "addEvent";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an event to the address book. "
            + "Parameters: "
            + PREFIX_EVENT_NAME + "EVENT_NAME "
            + PREFIX_DATE + "DATE (absolute or relative date) "
            + "[" + PREFIX_LOCATION + "LOCATION] "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION] "
            + "[" + PREFIX_TAG + "TAG]... "
            + "[" + PREFIX_CONTACT + "CONTACT]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_EVENT_NAME + "Annual Investor Meetup "
            + PREFIX_DATE + "2025-03-15 "
            + PREFIX_LOCATION + "Singapore "
            + PREFIX_DESCRIPTION + "Networking session for investors "
            + PREFIX_TAG + "Finance "
            + PREFIX_CONTACT + "John Doe";

    public static final String MESSAGE_SUCCESS = "New event added: %1$s";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in the address book";
    public static final String MESSAGE_CONTACT_NOT_FOUND = "Contact %s does not exist in the address book. "
        + "Please add the contact first.";

    private final Event toAdd;
    private final List<Person> contactsToAdd;

    /**
     * Creates an {@code AddEventCommand} to add the specified {@code Event} to the address book.
     * <p>
     * The list of contacts provided will be added to the event using {@code addContact} and, if not already present,
     * added to the model's person list.
     * </p>
     *
     * @param event         the event to be added; must not be null.
     * @param contactsToAdd the list of contacts to add to the event; must not be null.
     */
    public AddEventCommand(Event event, List<Person> contactsToAdd) {
        requireNonNull(event);
        requireNonNull(contactsToAdd);
        toAdd = event;
        this.contactsToAdd = contactsToAdd;
    }

    /**
     * Executes the add event command.
     * <p>
     * The command first checks if the event already exists. If not, it verifies that all provided contacts
     * exist in the model. If any contact is missing, it warns the user and aborts the command.
     * Otherwise, it adds the event to the model and associates the contacts with the event.
     * </p>
     *
     * @param model {@code Model} which the command should operate on.
     * @return the result of executing the command.
     * @throws CommandException if the event already exists or a specified contact is not found.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasEvent(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        }

        // Check that all provided contacts exist in the model
        for (Person contact : contactsToAdd) {
            if (!model.hasPerson(contact)) {
                throw new CommandException(String.format(MESSAGE_CONTACT_NOT_FOUND, contact.getName().fullName));
            }
        }

        model.addEvent(toAdd);

        // Add each provided contact to the event
        for (Person contact : contactsToAdd) {
            toAdd.addContact(contact);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof AddEventCommand
                && toAdd.equals(((AddEventCommand) other).toAdd)
                && contactsToAdd.equals(((AddEventCommand) other).contactsToAdd));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .add("contactsToAdd", contactsToAdd)
                .toString();
    }
}
