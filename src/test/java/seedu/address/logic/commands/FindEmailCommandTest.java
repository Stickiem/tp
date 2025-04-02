package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersonsTestSet2.ALICE;
import static seedu.address.testutil.TypicalPersonsTestSet2.BENSON;
import static seedu.address.testutil.TypicalPersonsTestSet2.CARL;
import static seedu.address.testutil.TypicalPersonsTestSet2.DANIEL;
import static seedu.address.testutil.TypicalPersonsTestSet2.ELLE;
import static seedu.address.testutil.TypicalPersonsTestSet2.FIONA;
import static seedu.address.testutil.TypicalPersonsTestSet2.GEORGE;
import static seedu.address.testutil.TypicalPersonsTestSet2.HOON;
import static seedu.address.testutil.TypicalPersonsTestSet2.IDA;
import static seedu.address.testutil.TypicalPersonsTestSet2.PHUNG_KHANH_LINH;
import static seedu.address.testutil.TypicalPersonsTestSet2.TRAN_KHOI_NGUYEN;
import static seedu.address.testutil.TypicalPersonsTestSet2.TRISTAN_NGUYEN;
import static seedu.address.testutil.TypicalPersonsTestSet2.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.predicate.EmailContainsKeywordsAsSubstringPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindEmailCommand}.
 */
public class FindEmailCommandTest {
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void equals() {
        EmailContainsKeywordsAsSubstringPredicate firstPredicate =
                new EmailContainsKeywordsAsSubstringPredicate(Collections.singletonList("first"));
        EmailContainsKeywordsAsSubstringPredicate secondPredicate =
                new EmailContainsKeywordsAsSubstringPredicate(Collections.singletonList("second"));

        FindEmailCommand findFirstCommand = new FindEmailCommand(firstPredicate);
        FindEmailCommand findSecondCommand = new FindEmailCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindEmailCommand findFirstCommandCopy = new FindEmailCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        EmailContainsKeywordsAsSubstringPredicate predicate = preparePredicate(" ");
        FindEmailCommand command = new FindEmailCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 12);
        EmailContainsKeywordsAsSubstringPredicate predicate = preparePredicate("gmail com");
        FindEmailCommand command = new FindEmailCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(TRAN_KHOI_NGUYEN, TRISTAN_NGUYEN, PHUNG_KHANH_LINH, ALICE, BENSON,
                CARL, DANIEL, ELLE, FIONA, GEORGE, HOON, IDA), model.getFilteredPersonList());
    }

    @Test
    public void execute_partialKeywords_partialPersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        EmailContainsKeywordsAsSubstringPredicate predicate = preparePredicate("nusmail");
        FindEmailCommand command = new FindEmailCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(PHUNG_KHANH_LINH, ELLE), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        EmailContainsKeywordsAsSubstringPredicate predicate =
                new EmailContainsKeywordsAsSubstringPredicate(Arrays.asList("keyword"));
        FindEmailCommand findCommand = new FindEmailCommand(predicate);
        String expected = FindEmailCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code EmailContainsKeywordsAsSubstringPredicate}.
     */
    private EmailContainsKeywordsAsSubstringPredicate preparePredicate(String userInput) {
        return new EmailContainsKeywordsAsSubstringPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}