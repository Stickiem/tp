package seedu.address.model.event;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import seedu.address.model.tag.Tag;

/**
 * Represents an Event in the address book.
 * Guarantees: details are present and not null, field values are validated, and the class is immutable.
 */
public class Event {

    // A counter to generate unique IDs for each event.
    private static final AtomicInteger COUNTER = new AtomicInteger(1);

    public static final String MESSAGE_CONSTRAINTS_NAME = "Event name cannot be blank.";
    public static final String MESSAGE_CONSTRAINTS_DATE = "Date must be in the format YYYY-MM-DD.";

    private final String id;
    private final String name;
    private final String date;
    private final String location;
    private final String description;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Constructs an {@code Event}.
     *
     * @param name A valid event name.
     * @param date A valid date in the format YYYY-MM-DD.
     * @param location The location of the event (can be empty).
     * @param description The event description (can be empty).
     * @param tags A set of tags associated with the event.
     */
    public Event(String name, String date, String location, String description, Set<Tag> tags) {
        requireNonNull(name, "Event name is required");
        requireNonNull(date, "Event date is required");
        if (name.trim().isEmpty()) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS_NAME);
        }
        // Optionally, add date format validation here.

        this.id = String.format("%08d", COUNTER.getAndIncrement());
        this.name = name;
        this.date = date;
        this.location = (location == null) ? "" : location;
        this.description = (description == null) ? "" : description;
        if (tags != null) {
            this.tags.addAll(tags);
        }
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Event)) {
            return false;
        }
        Event otherEvent = (Event) other;
        return id.equals(otherEvent.id)
                && name.equals(otherEvent.name)
                && date.equals(otherEvent.date)
                && location.equals(otherEvent.location)
                && description.equals(otherEvent.description)
                && tags.equals(otherEvent.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, date, location, description, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Event Name: ")
                .append(name)
                .append(" | Date: ")
                .append(date)
                .append(" | Location: ")
                .append(location)
                .append(" | Description: ")
                .append(description);
        if (!tags.isEmpty()) {
            builder.append(" | Tags: ");
            tags.forEach(tag -> builder.append(tag.toString()).append(" "));
        }
        builder.append(" | ID: ").append(id);
        return builder.toString();
    }
}
