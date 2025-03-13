package seedu.address.model.person;

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
