package seedu.address.model.predicate;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code Email} matches any of the keywords given.
 */
public class EmailContainsKeywordsAsSubstringPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public EmailContainsKeywordsAsSubstringPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> containsSubstringIgnoreCase(person.getEmail().value, keyword));
    }

    private boolean containsSubstringIgnoreCase(String email, String keyword) {
        return email.toLowerCase().contains(keyword.toLowerCase());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof EmailContainsKeywordsAsSubstringPredicate otherPredicate)) {
            return false;
        }

        return keywords.equals(otherPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
