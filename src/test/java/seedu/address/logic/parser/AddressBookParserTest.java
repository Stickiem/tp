package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddRelationshipCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteRelationshipCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindAddressCommand;
import seedu.address.logic.commands.FindEmailCommand;
import seedu.address.logic.commands.FindNameCommand;
import seedu.address.logic.commands.FindPhoneCommand;
import seedu.address.logic.commands.FindSocialCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.RedoListCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Person;
import seedu.address.model.predicate.AddressContainsKeywordsAsSubstringPredicate;
import seedu.address.model.predicate.EmailContainsKeywordsAsSubstringPredicate;
import seedu.address.model.predicate.NameContainsKeywordsAsSubstringPredicate;
import seedu.address.model.predicate.PhoneContainsKeywordsAsSubstringPredicate;
import seedu.address.model.predicate.SocialContainsKeywordsAsSubstringPredicate;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;

public class AddressBookParserTest {
    private final AddressBookParser parser = new AddressBookParser();
    private final Model model = new ModelManager();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person), model);
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD, model) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3", model) instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased(), model);
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " "
                + PersonUtil.getEditPersonDescriptorDetails(descriptor), model);
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD, model) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3", model) instanceof ExitCommand);
    }

    @Test
    public void parseCommand_findName() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindNameCommand command = (FindNameCommand) parser.parseCommand(
                FindNameCommand.COMMAND_WORD + " " + String.join(" ", keywords), model);
        assertEquals(new FindNameCommand(new NameContainsKeywordsAsSubstringPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_findAddress() throws Exception {
        List<String> keywords = Arrays.asList("clementi", "bedok");
        FindAddressCommand command = (FindAddressCommand) parser.parseCommand(
                FindAddressCommand.COMMAND_WORD + " " + String.join(" ", keywords), model);
        assertEquals(new FindAddressCommand(new AddressContainsKeywordsAsSubstringPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_findEmail() throws Exception {
        List<String> keywords = Arrays.asList("test@example.com", "user@domain.com");
        FindEmailCommand command = (FindEmailCommand) parser.parseCommand(
                FindEmailCommand.COMMAND_WORD + " " + String.join(" ", keywords), model);
        assertEquals(new FindEmailCommand(new EmailContainsKeywordsAsSubstringPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_findPhone() throws Exception {
        List<String> keywords = Arrays.asList("12345", "67890");
        FindPhoneCommand command = (FindPhoneCommand) parser.parseCommand(
                FindPhoneCommand.COMMAND_WORD + " " + String.join(" ", keywords), model);
        assertEquals(new FindPhoneCommand(new PhoneContainsKeywordsAsSubstringPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_findSocial() throws Exception {
        List<String> keywords = Arrays.asList("twitter", "facebook");
        FindSocialCommand command = (FindSocialCommand) parser.parseCommand(
                FindSocialCommand.COMMAND_WORD + " " + String.join(" ", keywords), model);
        assertEquals(new FindSocialCommand(new SocialContainsKeywordsAsSubstringPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD, model) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3", model) instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD, model) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3", model) instanceof ListCommand);
    }

    @Test
    public void parseCommand_sort() throws Exception {
        String sortCommand = SortCommand.COMMAND_WORD + " name phone";
        Command command = parser.parseCommand(sortCommand, model);
        assertTrue(command instanceof SortCommand);
        assertEquals("SortCommand{isReverse=false, fields=[name, phone]}",
                command.toString());
    }

    @Test
    public void parseCommand_redo() throws Exception {
        String redoCommand = RedoCommand.COMMAND_WORD + " 1";
        Command command = parser.parseCommand(redoCommand, model);
        assertTrue(command instanceof RedoCommand);
        assertEquals("RedoCommand{commandIndex=1}", command.toString());
    }

    @Test
    public void parseCommand_redoList() throws Exception {
        assertTrue(parser.parseCommand(RedoListCommand.COMMAND_WORD, model) instanceof RedoListCommand);
    }

    @Test
    public void parseCommand_relationshipCommands() throws Exception {
        // Test AddRelationshipCommand
        assertTrue(parser.parseCommand(AddRelationshipCommand.COMMAND_WORD + " u/1 u/2 fn/Friend rn/Friend", model)
                instanceof AddRelationshipCommand);

        // Test DeleteRelationshipCommand
        assertTrue(parser.parseCommand(DeleteRelationshipCommand.COMMAND_WORD + " u/1 u/2 n/Friend", model)
                instanceof DeleteRelationshipCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                HelpCommand.MESSAGE_USAGE), () -> parser.parseCommand("", model));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand", model));
    }
}
