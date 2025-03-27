package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteContactFromEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;

/**
 * Parses input arguments and creates a new DeleteContactFromEventCommand object.
 */
public class DeleteContactFromEventCommandParser implements Parser<DeleteContactFromEventCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteContactFromEventCommand
     * and returns a DeleteContactFromEventCommand object for execution.
     *
     * @param userInput full user input string.
     * @return a DeleteContactFromEventCommand object containing the parsed data.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    @Override
    public DeleteContactFromEventCommand parse(String userInput) throws ParseException {
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
        String contactString = argMultimap.getValue(PREFIX_CONTACT).get();
        Person contact = ParserUtil.parseContact(contactString);

        return new DeleteContactFromEventCommand(eventIndex, contact);
    }
}
