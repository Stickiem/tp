package seedu.address.model.predicate;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;
/**
 * Tests that a {@code Person}'s {@code Address} matches any of the keywords given.
 */
public class AddressContainsKeywordsAsSubstringPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public AddressContainsKeywordsAsSubstringPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> containsSubstringIgnoreCase(person.getAddress().value, keyword));
    }

    private boolean containsSubstringIgnoreCase(String address, String keyword) {
        return address.toLowerCase().contains(keyword.toLowerCase());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddressContainsKeywordsAsSubstringPredicate)) {
            return false;
        }

        AddressContainsKeywordsAsSubstringPredicate otherAddressContainsKeywordsAsSubstringPredicate =
                (AddressContainsKeywordsAsSubstringPredicate) other;
        return keywords.equals(otherAddressContainsKeywordsAsSubstringPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
