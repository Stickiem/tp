package seedu.address.model.predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class SocialContainsKeywordsAsSubstringPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("facebook");
        List<String> secondPredicateKeywordList = Arrays.asList("facebook", "whatsapp");

        SocialContainsKeywordsAsSubstringPredicate firstPredicate =
                new SocialContainsKeywordsAsSubstringPredicate(firstPredicateKeywordList);
        SocialContainsKeywordsAsSubstringPredicate secondPredicate =
                new SocialContainsKeywordsAsSubstringPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        SocialContainsKeywordsAsSubstringPredicate firstPredicateCopy =
                new SocialContainsKeywordsAsSubstringPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different predicate -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_socialContainsKeywords_returnsTrue() {
        // One keyword
        SocialContainsKeywordsAsSubstringPredicate predicate =
                new SocialContainsKeywordsAsSubstringPredicate(Collections.singletonList("facebook"));
        assertTrue(predicate.test(new PersonBuilder().withSocial("facebook.com/user").build()));

        // Multiple keywords
        predicate = new SocialContainsKeywordsAsSubstringPredicate(Arrays.asList("facebook", "whatsapp"));
        assertTrue(predicate.test(new PersonBuilder().withSocial("whatsapp.com/user").build()));
    }

    @Test
    public void test_socialDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        SocialContainsKeywordsAsSubstringPredicate predicate =
                new SocialContainsKeywordsAsSubstringPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withSocial("facebook.com/user").build()));

        // Non-matching keyword
        predicate = new SocialContainsKeywordsAsSubstringPredicate(Arrays.asList("instagram"));
        assertFalse(predicate.test(new PersonBuilder().withSocial("facebook.com/user").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("facebook", "whatsapp");
        SocialContainsKeywordsAsSubstringPredicate predicate =
                new SocialContainsKeywordsAsSubstringPredicate(keywords);

        String expected =
                SocialContainsKeywordsAsSubstringPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
