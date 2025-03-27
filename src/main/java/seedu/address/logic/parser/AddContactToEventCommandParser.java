package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddContactToEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;

/**
 * Parses input arguments and creates a new AddContactToEventCommand object.
 */
public class AddContactToEventCommandParser implements Parser<AddContactToEventCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddContactToEventCommand
     * and returns an AddContactToEventCommand object for execution.
     *
     * @param userInput full user input string.
     * @return an AddContactToEventCommand object containing the parsed data.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    @Override
    public AddContactToEventCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(userInput, PREFIX_CONTACT);

        // The preamble should contain the event index.
        String indexString = argMultimap.getPreamble().trim();
        if (indexString.isEmpty()) {
            throw new ParseException("Event index is required.");
        }
        Index eventIndex = ParserUtil.parseIndex(indexString);

        if (!argMultimap.getValue(PREFIX_CONTACT).isPresent()) {
            throw new ParseException("Contact information is required.");
        }
        // Here we assume only one contact is provided per command.
        String contactString = argMultimap.getValue(PREFIX_CONTACT).get();
        Person contact = ParserUtil.parseContact(contactString);

        return new AddContactToEventCommand(eventIndex, contact);
    }
}
