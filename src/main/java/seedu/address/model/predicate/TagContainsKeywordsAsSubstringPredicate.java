package seedu.address.model.predicate;

import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code Person}'s tags contain any of the keywords given.
 */
public class TagContainsKeywordsAsSubstringPredicate implements Predicate<Person> {
    private final Set<Tag> keywords;

    public TagContainsKeywordsAsSubstringPredicate(Set<Tag> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return person.getTags().stream()
                .anyMatch(tag -> keywords.stream()
                        .anyMatch(keyword -> tag.tagName.toLowerCase()
                                .contains(keyword.tagName.toLowerCase())));
    }

    private boolean containsSubstringIgnoreCase(String tagName, String keyword) {
        return tagName.toLowerCase().contains(keyword.toLowerCase());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof TagContainsKeywordsAsSubstringPredicate)) {
            return false;
        }

        TagContainsKeywordsAsSubstringPredicate otherTagContainsKeywordsAsSubstringPredicate =
                (TagContainsKeywordsAsSubstringPredicate) other;
        return keywords.equals(otherTagContainsKeywordsAsSubstringPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
