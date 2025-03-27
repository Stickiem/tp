package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.*;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final Logger logger = LogsCenter.getLogger(AddressBookParser.class);

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        logger.fine("Command word: " + commandWord + "; Arguments: " + arguments);

        return switch (commandWord) {
            case AddCommand.COMMAND_WORD -> new AddCommandParser().parse(arguments);
            case EditCommand.COMMAND_WORD -> new EditCommandParser().parse(arguments);
            case DeleteCommand.COMMAND_WORD -> new DeleteCommandParser().parse(arguments);
            case ClearCommand.COMMAND_WORD -> new ClearCommand();
            case FindAddressCommand.COMMAND_WORD -> new FindAddressCommandParser().parse(arguments);
            case FindNameCommand.COMMAND_WORD -> new FindCommandParser().parse(arguments);
            case FindEmailCommand.COMMAND_WORD -> new FindEmailCommandParser().parse(arguments);
            case FindPhoneCommand.COMMAND_WORD -> new FindPhoneCommandParser().parse(arguments);
            case FindSocialCommand.COMMAND_WORD -> new FindSocialCommandParser().parse(arguments);
            case FindTagCommand.COMMAND_WORD -> new FindTagCommandParser().parse(arguments);
            case ListCommand.COMMAND_WORD -> new ListCommand();
            case ExitCommand.COMMAND_WORD -> new ExitCommand();
            case HelpCommand.COMMAND_WORD -> new HelpCommand();
            case AddRelationshipCommand.COMMAND_WORD -> new AddRelationshipCommandParser().parse(arguments);
            case DeleteRelationshipCommand.COMMAND_WORD -> new DeleteRelationshipCommandParser().parse(arguments);
            case AddEventCommand.COMMAND_WORD -> new AddEventCommandParser().parse(arguments);
            case DeleteEventCommand.COMMAND_WORD -> new DeleteEventCommandParser().parse(arguments);
            case AddContactToEventCommand.COMMAND_WORD -> new AddContactToEventCommandParser().parse(arguments);
            case DeleteContactFromEventCommand.COMMAND_WORD ->
                    new DeleteContactFromEventCommandParser().parse(arguments);
            case AddTagToEventCommand.COMMAND_WORD -> new AddTagToEventCommandParser().parse(arguments);
            case DeleteTagFromEventCommand.COMMAND_WORD -> new DeleteTagFromEventCommandParser().parse(arguments);
            case UpdateEventDescriptionCommand.COMMAND_WORD ->
                    new UpdateEventDescriptionCommandParser().parse(arguments);
            case AddTagToRelationshipCommand.COMMAND_WORD -> new AddTagToRelationshipCommandParser().parse(arguments);
            case DeleteTagFromRelationshipCommand.COMMAND_WORD ->
                    new DeleteTagFromRelationshipCommandParser().parse(arguments);
            default -> {
                logger.finer("This user input caused a ParseException: " + userInput);
                throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
            }
        };
    }
}
