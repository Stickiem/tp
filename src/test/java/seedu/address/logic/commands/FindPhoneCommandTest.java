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
import seedu.address.model.predicate.PhoneContainsKeywordsAsSubstringPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindPhoneCommand}.
 */
public class FindPhoneCommandTest {
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void equals() {
        PhoneContainsKeywordsAsSubstringPredicate firstPredicate =
                new PhoneContainsKeywordsAsSubstringPredicate(Collections.singletonList("first"));
        PhoneContainsKeywordsAsSubstringPredicate secondPredicate =
                new PhoneContainsKeywordsAsSubstringPredicate(Collections.singletonList("second"));

        FindPhoneCommand findFirstCommand = new FindPhoneCommand(firstPredicate);
        FindPhoneCommand findSecondCommand = new FindPhoneCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindPhoneCommand findFirstCommandCopy = new FindPhoneCommand(firstPredicate);
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
        PhoneContainsKeywordsAsSubstringPredicate predicate = preparePredicate(" ");
        FindPhoneCommand command = new FindPhoneCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 5);
        PhoneContainsKeywordsAsSubstringPredicate predicate = preparePredicate("9435 8765");
        FindPhoneCommand command = new FindPhoneCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(TRAN_KHOI_NGUYEN, TRISTAN_NGUYEN, ALICE, BENSON, DANIEL),
                model.getFilteredPersonList());
    }

    @Test
    public void execute_partialKeywords_partialPersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        PhoneContainsKeywordsAsSubstringPredicate predicate = preparePredicate("9535");
        FindPhoneCommand command = new FindPhoneCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(PHUNG_KHANH_LINH, CARL), model.getFilteredPersonList());
    }

    @Test
    public void execute_specificAreaCode_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 8);
        PhoneContainsKeywordsAsSubstringPredicate predicate = preparePredicate("94 87");
        FindPhoneCommand command = new FindPhoneCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(TRAN_KHOI_NGUYEN, TRISTAN_NGUYEN, ALICE, BENSON, DANIEL, ELLE, FIONA, GEORGE),
                model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        PhoneContainsKeywordsAsSubstringPredicate predicate =
                new PhoneContainsKeywordsAsSubstringPredicate(Arrays.asList("keyword"));
        FindPhoneCommand findCommand = new FindPhoneCommand(predicate);
        String expected = FindPhoneCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code PhoneContainsKeywordsAsSubstringPredicate}.
     */
    private PhoneContainsKeywordsAsSubstringPredicate preparePredicate(String userInput) {
        return new PhoneContainsKeywordsAsSubstringPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
