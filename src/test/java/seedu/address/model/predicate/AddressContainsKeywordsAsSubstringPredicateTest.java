package seedu.address.model.predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class AddressContainsKeywordsAsSubstringPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("NUS");
        List<String> secondPredicateKeywordList = Arrays.asList("NUS", "Com1");

        AddressContainsKeywordsAsSubstringPredicate firstPredicate =
                new AddressContainsKeywordsAsSubstringPredicate(firstPredicateKeywordList);
        AddressContainsKeywordsAsSubstringPredicate secondPredicate =
                new AddressContainsKeywordsAsSubstringPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        AddressContainsKeywordsAsSubstringPredicate firstPredicateCopy =
                new AddressContainsKeywordsAsSubstringPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different predicate -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_addressContainsKeywords_returnsTrue() {
        // One keyword
        AddressContainsKeywordsAsSubstringPredicate predicate =
                new AddressContainsKeywordsAsSubstringPredicate(Collections.singletonList("NUS"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("NUS Main Lab").build()));

        // Multiple keywords
        predicate = new AddressContainsKeywordsAsSubstringPredicate(Arrays.asList("Com1", "Com2"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("Com1 Lab, Com2 Avenue").build()));
    }

    @Test
    public void test_addressDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        AddressContainsKeywordsAsSubstringPredicate predicate =
                new AddressContainsKeywordsAsSubstringPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withAddress("NUS Main Lab").build()));

        // Non-matching keyword
        predicate = new AddressContainsKeywordsAsSubstringPredicate(Arrays.asList("Com3"));
        assertFalse(predicate.test(new PersonBuilder().withAddress("Com1 Lab, Com2 Avenue").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("NUS", "Com1");
        AddressContainsKeywordsAsSubstringPredicate predicate =
                new AddressContainsKeywordsAsSubstringPredicate(keywords);

        String expected =
                AddressContainsKeywordsAsSubstringPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
