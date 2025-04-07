package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;
    private final String id;

    // Data fields
    private final Address address;
    private final Set<Social> socials = new HashSet<>();
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null, except for ID.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Social> socials, Set<Tag> tags) {
        this(name, phone, email, address, socials, tags, null);
    }

    /**
     * Creates a Person with the given details. Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Social> socials, Set<Tag> tags, String id) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.socials.addAll(socials);
        this.tags.addAll(tags);
        this.id = id != null ? id : generateId(name, phone); // Use provided ID or generate one
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public Set<Social> getSocials() {
        return Collections.unmodifiableSet(socials);
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null && otherPerson.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person otherPerson)) {
            return false;
        }

        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && tags.equals(otherPerson.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("socials", socials)
                .add("tags", tags)
                .toString();
    }

    /**
     * Generates an ID for this person based on their name and phone number.
     *
     * @param name The name of the person.
     * @param phone The phone number of the person.
     * @return The generated ID.
     */
    private String generateId(Name name, Phone phone) {
        return (name.toString() + phone.toString()).hashCode() + "";
    }

    /**
     * Returns the ID of this person.
     *
     * @return The ID of this person.
     */
    public String getId() {
        return id;
    }
}
