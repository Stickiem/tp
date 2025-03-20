package seedu.address.model.predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class EmailContainsKeywordsAsSubstringPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("trankhoinguyen@gmail.com");
        List<String> secondPredicateKeywordList = Arrays.asList("trankhoinguyen@gmail.com", "e2222222@u.nus.edu");

        EmailContainsKeywordsAsSubstringPredicate firstPredicate =
                new EmailContainsKeywordsAsSubstringPredicate(firstPredicateKeywordList);
        EmailContainsKeywordsAsSubstringPredicate secondPredicate =
                new EmailContainsKeywordsAsSubstringPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        EmailContainsKeywordsAsSubstringPredicate firstPredicateCopy =
                new EmailContainsKeywordsAsSubstringPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different predicate -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_emailContainsKeywords_returnsTrue() {
        // One keyword
        EmailContainsKeywordsAsSubstringPredicate predicate =
                new EmailContainsKeywordsAsSubstringPredicate(Collections.singletonList("trankhoinguyen@gmail.com"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("trankhoinguyen@gmail.com").build()));

        // Multiple keywords
        predicate = new EmailContainsKeywordsAsSubstringPredicate(
                Arrays.asList("trankhoinguyen@gmail.com", "e2222222@u.nus.edu")
        );
        assertTrue(predicate.test(new PersonBuilder().withEmail("e2222222@u.nus.edu").build()));
    }

    @Test
    public void test_emailDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        EmailContainsKeywordsAsSubstringPredicate predicate =
                new EmailContainsKeywordsAsSubstringPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withEmail("trankhoinguyen@gmail.com").build()));

        // Non-matching keyword
        predicate = new EmailContainsKeywordsAsSubstringPredicate(Arrays.asList("e33333333@u.nus.edu"));
        assertFalse(predicate.test(new PersonBuilder().withEmail("trankhoinguyen@gmail.com").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("trankhoinguyen@gmail.com", "e2222222@u.nus.edu");
        EmailContainsKeywordsAsSubstringPredicate predicate =
                new EmailContainsKeywordsAsSubstringPredicate(keywords);

        String expected =
                EmailContainsKeywordsAsSubstringPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
