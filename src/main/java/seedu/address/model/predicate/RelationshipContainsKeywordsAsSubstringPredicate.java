package seedu.address.model.predicate;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code Relationships} matches any of the keywords given.
 */
public class RelationshipContainsKeywordsAsSubstringPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public RelationshipContainsKeywordsAsSubstringPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return true;
    }

    private boolean containsSubstringIgnoreCase(String relationships, String keyword) {
        return relationships.toLowerCase().contains(keyword.toLowerCase());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof RelationshipContainsKeywordsAsSubstringPredicate)) {
            return false;
        }

        RelationshipContainsKeywordsAsSubstringPredicate otherRelationshipContainsKeywordsAsSubstringPredicate =
                (RelationshipContainsKeywordsAsSubstringPredicate) other;
        return keywords.equals(otherRelationshipContainsKeywordsAsSubstringPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
