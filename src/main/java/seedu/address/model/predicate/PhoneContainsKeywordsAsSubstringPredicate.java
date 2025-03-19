package seedu.address.model.predicate;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

public class PhoneContainsKeywordsAsSubstringPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public PhoneContainsKeywordsAsSubstringPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> containsSubstringIgnoreCase(person.getPhone().value, keyword));
    }

    private boolean containsSubstringIgnoreCase(String phone, String keyword) {
        return phone.toLowerCase().contains(keyword.toLowerCase());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof PhoneContainsKeywordsAsSubstringPredicate)) {
            return false;
        }

        PhoneContainsKeywordsAsSubstringPredicate otherPhoneContainsKeywordsAsSubstringPredicate
                = (PhoneContainsKeywordsAsSubstringPredicate) other;
        return keywords.equals(otherPhoneContainsKeywordsAsSubstringPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
