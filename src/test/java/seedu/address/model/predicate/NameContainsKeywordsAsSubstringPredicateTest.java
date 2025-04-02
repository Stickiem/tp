package seedu.address.model.predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class NameContainsKeywordsAsSubstringPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("Ng");
        List<String> secondPredicateKeywordList = Arrays.asList("Ng", "Tr");

        NameContainsKeywordsAsSubstringPredicate firstPredicate =
                new NameContainsKeywordsAsSubstringPredicate(firstPredicateKeywordList);
        NameContainsKeywordsAsSubstringPredicate secondPredicate =
                new NameContainsKeywordsAsSubstringPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        NameContainsKeywordsAsSubstringPredicate firstPredicateCopy =
                new NameContainsKeywordsAsSubstringPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different predicate -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywordsAsSubstring_returnsTrue() {
        // One keyword matching full name
        NameContainsKeywordsAsSubstringPredicate predicate =
                new NameContainsKeywordsAsSubstringPredicate(Collections.singletonList("Tran Khoi Nguyen"));
        assertTrue(predicate.test(new PersonBuilder().withName("Tran Khoi Nguyen").build()));

        // One keyword matching part of the name
        predicate = new NameContainsKeywordsAsSubstringPredicate(Collections.singletonList("Khoi"));
        assertTrue(predicate.test(new PersonBuilder().withName("Tran Khoi Nguyen").build()));

        // Multiple keywords, one matches
        predicate = new NameContainsKeywordsAsSubstringPredicate(Arrays.asList("Tran", "Linh"));
        assertTrue(predicate.test(new PersonBuilder().withName("Tran Khoi Nguyen").build()));

        // Case insensitive
        predicate = new NameContainsKeywordsAsSubstringPredicate(Collections.singletonList("khoi"));
        assertTrue(predicate.test(new PersonBuilder().withName("Tran Khoi Nguyen").build()));
    }

    @Test
    public void test_nameContainsSubstrings_returnsTrue() {
        // Substring "Ng" matches "Nguyen"
        NameContainsKeywordsAsSubstringPredicate predicate =
                new NameContainsKeywordsAsSubstringPredicate(Collections.singletonList("Ng"));
        assertTrue(predicate.test(new PersonBuilder().withName("Tran Khoi Nguyen").build()));
        assertTrue(predicate.test(new PersonBuilder().withName("Tristan Nguyen").build()));
        assertTrue(predicate.test(new PersonBuilder().withName("Nguyen Dep Trai").build()));

        // Substring "Tr" matches "Tristan" and "Tran"
        predicate = new NameContainsKeywordsAsSubstringPredicate(Collections.singletonList("Tr"));
        assertTrue(predicate.test(new PersonBuilder().withName("Tran Khoi Nguyen").build()));
        assertTrue(predicate.test(new PersonBuilder().withName("Tristan Nguyen").build()));
        assertFalse(predicate.test(new PersonBuilder().withName("Phung Khanh Linh").build()));

        // Substring "uy" matches "Nguyen"
        predicate = new NameContainsKeywordsAsSubstringPredicate(Collections.singletonList("uy"));
        assertTrue(predicate.test(new PersonBuilder().withName("Tristan Nguyen").build()));

        // Substring "Li" matches "Linh"
        predicate = new NameContainsKeywordsAsSubstringPredicate(Collections.singletonList("Li"));
        assertTrue(predicate.test(new PersonBuilder().withName("Phung Khanh Linh").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        NameContainsKeywordsAsSubstringPredicate predicate =
                new NameContainsKeywordsAsSubstringPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withName("Tran Khoi Nguyen").build()));

        // Non-matching keyword
        predicate = new NameContainsKeywordsAsSubstringPredicate(Arrays.asList("Wong"));
        assertFalse(predicate.test(new PersonBuilder().withName("Tran Khoi Nguyen").build()));

        // Non-matching substring
        predicate = new NameContainsKeywordsAsSubstringPredicate(Arrays.asList("Zo"));
        assertFalse(predicate.test(new PersonBuilder().withName("Tran Khoi Nguyen").build()));
    }

    @Test
    public void test_containsSubstringIgnoreCase() {
        // Create a predicate with any keyword to access the private method through test()
        NameContainsKeywordsAsSubstringPredicate predicate =
                new NameContainsKeywordsAsSubstringPredicate(Collections.singletonList("Ng"));

        // Test with matching exact case
        assertTrue(predicate.test(new PersonBuilder().withName("Nguyen").build()));

        // Test with different case
        assertTrue(predicate.test(new PersonBuilder().withName("nguyen").build()));

        // Test with mixed case
        assertTrue(predicate.test(new PersonBuilder().withName("NgUyEn").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("Ng", "Tr");
        NameContainsKeywordsAsSubstringPredicate predicate =
                new NameContainsKeywordsAsSubstringPredicate(keywords);

        String expected =
                NameContainsKeywordsAsSubstringPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
