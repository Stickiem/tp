package seedu.address.model.person;

/**
 * Represents a Person's social details in the address book.
 */
public class Social {
    public final String value;

    public Social(String social) {
        this.value = social;
    }

    @Override
    public String toString() {
        return value;
    }

}
