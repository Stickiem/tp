package seedu.address.model.predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PhoneContainsKeywordsAsSubstringPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("1111111");
        List<String> secondPredicateKeywordList = Arrays.asList("1111111", "777777");

        PhoneContainsKeywordsAsSubstringPredicate firstPredicate = new PhoneContainsKeywordsAsSubstringPredicate(firstPredicateKeywordList);
        PhoneContainsKeywordsAsSubstringPredicate secondPredicate = new PhoneContainsKeywordsAsSubstringPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PhoneContainsKeywordsAsSubstringPredicate firstPredicateCopy = new PhoneContainsKeywordsAsSubstringPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different predicate -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_phoneContainsKeywords_returnsTrue() {
        // One keyword
        PhoneContainsKeywordsAsSubstringPredicate predicate = new PhoneContainsKeywordsAsSubstringPredicate(Collections.singletonList("1111111"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("1111111").build()));

        // Multiple keywords
        predicate = new PhoneContainsKeywordsAsSubstringPredicate(Arrays.asList("1111111", "777777"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("1111111").build()));
    }

    @Test
    public void test_phoneDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        PhoneContainsKeywordsAsSubstringPredicate predicate = new PhoneContainsKeywordsAsSubstringPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withPhone("1111111").build()));

        // Non-matching keyword
        predicate = new PhoneContainsKeywordsAsSubstringPredicate(Arrays.asList("123456789"));
        assertFalse(predicate.test(new PersonBuilder().withPhone("1111111").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("1111111", "777777");
        PhoneContainsKeywordsAsSubstringPredicate predicate = new PhoneContainsKeywordsAsSubstringPredicate(keywords);

        String expected = PhoneContainsKeywordsAsSubstringPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
