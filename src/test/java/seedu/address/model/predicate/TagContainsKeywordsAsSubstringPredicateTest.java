package seedu.address.model.predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class TagContainsKeywordsAsSubstringPredicateTest {

    @Test
    public void equals() {
        Set<Tag> firstPredicateKeywordSet = Collections.singleton(new Tag("Partner"));
        Set<Tag> secondPredicateKeywordSet = new HashSet<>(Arrays.asList(new Tag("Partner"), new Tag("Business")));

        TagContainsKeywordsAsSubstringPredicate firstPredicate =
                new TagContainsKeywordsAsSubstringPredicate(firstPredicateKeywordSet);
        TagContainsKeywordsAsSubstringPredicate secondPredicate =
                new TagContainsKeywordsAsSubstringPredicate(secondPredicateKeywordSet);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TagContainsKeywordsAsSubstringPredicate firstPredicateCopy =
                new TagContainsKeywordsAsSubstringPredicate(firstPredicateKeywordSet);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different predicate -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_tagContainsKeywords_returnsTrue() {
        // One keyword exactly matching
        TagContainsKeywordsAsSubstringPredicate predicate =
                new TagContainsKeywordsAsSubstringPredicate(Collections.singleton(new Tag("Partner")));
        assertTrue(predicate.test(new PersonBuilder().withTags("Partner").build()));

        // Multiple keywords, one matches
        predicate = new TagContainsKeywordsAsSubstringPredicate(
                new HashSet<>(Arrays.asList(new Tag("Partner"), new Tag("Friend"))));
        assertTrue(predicate.test(new PersonBuilder().withTags("Partner", "Business").build()));

        // Case insensitive matching
        predicate = new TagContainsKeywordsAsSubstringPredicate(Collections.singleton(new Tag("partner")));
        assertTrue(predicate.test(new PersonBuilder().withTags("Partner").build()));

        predicate = new TagContainsKeywordsAsSubstringPredicate(Collections.singleton(new Tag("PARTNER")));
        assertTrue(predicate.test(new PersonBuilder().withTags("Partner").build()));
    }

    @Test
    public void test_personHasMultipleTags_returnsTrue() {
        // Person has multiple tags, one matches the predicate
        TagContainsKeywordsAsSubstringPredicate predicate =
                new TagContainsKeywordsAsSubstringPredicate(Collections.singleton(new Tag("Business")));
        assertTrue(predicate.test(new PersonBuilder().withTags("Partner", "Business", "Borrower").build()));
    }

    @Test
    public void test_tagDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        TagContainsKeywordsAsSubstringPredicate predicate =
                new TagContainsKeywordsAsSubstringPredicate(Collections.emptySet());
        assertFalse(predicate.test(new PersonBuilder().withTags("Partner").build()));

        // Non-matching keyword
        predicate = new TagContainsKeywordsAsSubstringPredicate(
                Collections.singleton(new Tag("Friend")));
        assertFalse(predicate.test(new PersonBuilder().withTags("Partner", "Business").build()));
    }

    @Test
    public void test_containsSubstringIgnoreCase() {
        TagContainsKeywordsAsSubstringPredicate predicate =
                new TagContainsKeywordsAsSubstringPredicate(Collections.singleton(new Tag("Part")));

        assertFalse(predicate.test(new PersonBuilder().withTags("Partner").build()));

        assertTrue(predicate.test(new PersonBuilder().withTags("Part").build()));
    }

    @Test
    public void toStringMethod() {
        Set<Tag> keywords = new HashSet<>(Arrays.asList(new Tag("Partner"), new Tag("Business")));
        TagContainsKeywordsAsSubstringPredicate predicate =
                new TagContainsKeywordsAsSubstringPredicate(keywords);

        String expected =
                TagContainsKeywordsAsSubstringPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}

