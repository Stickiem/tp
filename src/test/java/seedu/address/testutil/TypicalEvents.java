package seedu.address.testutil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import seedu.address.model.event.Event;
import seedu.address.model.person.UniquePersonList;

/**
 * A utility class containing a list of {@code Event} objects to be used in tests.
 */
public class TypicalEvents {

    public static final Event BIRTHDAY = new Event(
            "Birthday Party",
            LocalDateTime.of(2024, 1, 1, 18, 0),
            "Marina Bay Sands",
            "Annual birthday celebration",
            new HashSet<>(),
            new UniquePersonList()
    );

    public static final Event MEETING = new Event(
            "Team Meeting",
            LocalDateTime.of(2024, 1, 2, 14, 0),
            "COM1-02-01",
            "Weekly team sync-up",
            new HashSet<>(),
            new UniquePersonList()
    );

    public static final Event CONFERENCE = new Event(
            "Tech Conference",
            LocalDateTime.of(2024, 1, 5, 9, 0),
            "Suntec Convention Centre",
            "Annual tech conference",
            new HashSet<>(),
            new UniquePersonList()
    );

    private TypicalEvents() {} // prevents instantiation

    public static List<Event> getTypicalEvents() {
        return new ArrayList<>(Arrays.asList(BIRTHDAY, MEETING, CONFERENCE));
    }
}
