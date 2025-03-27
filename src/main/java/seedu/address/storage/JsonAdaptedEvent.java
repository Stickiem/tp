package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Event}.
 */
class JsonAdaptedEvent {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Event's %s field is missing!";

    private final String id;
    private final String name;
    private final String date;
    private final String location;
    private final String description;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();
    private final List<JsonAdaptedPerson> contacts = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedEvent} with the given event details.
     */
    @JsonCreator
    public JsonAdaptedEvent(
            @JsonProperty("id") String id,
            @JsonProperty("name") String name,
            @JsonProperty("date") String date,
            @JsonProperty("location") String location,
            @JsonProperty("description") String description,
            @JsonProperty("tags") List<JsonAdaptedTag> tags,
            @JsonProperty("contacts") List<JsonAdaptedPerson> contacts) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.location = location;
        this.description = description;
        if (tags != null) {
            this.tags.addAll(tags);
        }
        if (contacts != null) {
            this.contacts.addAll(contacts);
        }
    }

    /**
     * Converts a given {@code Event} into this class for Jackson use.
     */
    public JsonAdaptedEvent(Event source) {
        this.id = source.getId();
        this.name = source.getName();
        this.date = source.getDate();
        this.location = source.getLocation();
        this.description = source.getDescription();
        this.tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .toList());
        this.contacts.addAll(source.getContacts().stream()
                .map(JsonAdaptedPerson::new)
                .toList());
    }

    /**
     * Converts this Jackson-friendly adapted event object into the model's {@code Event} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted event.
     */
    public Event toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Name"));
        }
        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Date"));
        }
        // Optionally, add further validation for name and date if required.

        final List<Tag> eventTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            eventTags.add(tag.toModelType());
        }
        final Set<Tag> modelTags = new HashSet<>(eventTags);

        final List<Person> modelContacts = new ArrayList<>();
        for (JsonAdaptedPerson jsonContact : contacts) {
            modelContacts.add(jsonContact.toModelType());
        }
        UniquePersonList contactsList = new UniquePersonList();
        for (Person p : modelContacts) {
            contactsList.add(p);
        }

        return new Event(name, date, location, description, modelTags, contactsList);
    }
}
