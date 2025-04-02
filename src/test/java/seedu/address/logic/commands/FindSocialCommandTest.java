package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersonsTestSet2.ALICE;
import static seedu.address.testutil.TypicalPersonsTestSet2.BENSON;
import static seedu.address.testutil.TypicalPersonsTestSet2.CARL;
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
import seedu.address.model.predicate.SocialContainsKeywordsAsSubstringPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindSocialCommand}.
 */
public class FindSocialCommandTest {
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void equals() {
        SocialContainsKeywordsAsSubstringPredicate firstPredicate =
                new SocialContainsKeywordsAsSubstringPredicate(Collections.singletonList("first"));
        SocialContainsKeywordsAsSubstringPredicate secondPredicate =
                new SocialContainsKeywordsAsSubstringPredicate(Collections.singletonList("second"));

        FindSocialCommand findFirstCommand = new FindSocialCommand(firstPredicate);
        FindSocialCommand findSecondCommand = new FindSocialCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindSocialCommand findFirstCommandCopy = new FindSocialCommand(firstPredicate);
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
        SocialContainsKeywordsAsSubstringPredicate predicate = preparePredicate(" ");
        FindSocialCommand command = new FindSocialCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        SocialContainsKeywordsAsSubstringPredicate predicate = preparePredicate("alice_pauline benson_meier carl_kurz");
        FindSocialCommand command = new FindSocialCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, CARL), model.getFilteredPersonList());
    }

    @Test
    public void execute_partialKeywords_partialPersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        SocialContainsKeywordsAsSubstringPredicate predicate = preparePredicate("nguyen");
        FindSocialCommand command = new FindSocialCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(TRAN_KHOI_NGUYEN, TRISTAN_NGUYEN), model.getFilteredPersonList());
    }

    @Test
    public void execute_atSymbolSearch_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        SocialContainsKeywordsAsSubstringPredicate predicate = preparePredicate("@phung @khoi");
        FindSocialCommand command = new FindSocialCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(TRAN_KHOI_NGUYEN, PHUNG_KHANH_LINH), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        SocialContainsKeywordsAsSubstringPredicate predicate =
                new SocialContainsKeywordsAsSubstringPredicate(Arrays.asList("facebook"));
        FindSocialCommand findCommand = new FindSocialCommand(predicate);
        String expected = FindSocialCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code SocialContainsKeywordsAsSubstringPredicate}.
     */
    private SocialContainsKeywordsAsSubstringPredicate preparePredicate(String userInput) {
        return new SocialContainsKeywordsAsSubstringPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
