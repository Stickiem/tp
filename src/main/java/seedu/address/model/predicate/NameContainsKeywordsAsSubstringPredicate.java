package seedu.address.model.predicate;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;
/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsKeywordsAsSubstringPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public NameContainsKeywordsAsSubstringPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> containsSubstringIgnoreCase(person.getName().fullName, keyword));
    }

    private boolean containsSubstringIgnoreCase(String name, String keyword) {
        return name.toLowerCase().contains(keyword.toLowerCase());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NameContainsKeywordsAsSubstringPredicate)) {
            return false;
        }

        NameContainsKeywordsAsSubstringPredicate otherNameContainsKeywordsAsSubstringPredicate =
                (NameContainsKeywordsAsSubstringPredicate) other;
        return keywords.equals(otherNameContainsKeywordsAsSubstringPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
