package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.AddressBook;
import seedu.address.model.event.Event;

/**
 * An UI component that displays information of an {@code Event}.
 */
public class EventCard extends UiPart<Region> {

    private static final String FXML = "EventCard.fxml";

    public final Event event;
    private final AddressBook addressBook;

    @FXML
    private HBox cardPane;
    @FXML
    private VBox eventDetails;
    @FXML
    private Label id;
    @FXML
    private Label name;
    @FXML
    private Label date;
    @FXML
    private Label locationLabel;
    @FXML
    private Label description;
    @FXML
    private Label eventId;
    @FXML
    private FlowPane tags;
    @FXML
    private VBox contactsPane;

    /**
     * Creates a {@code EventCard} with the given {@code Event}, displayed index, and AddressBook.
     */
    public EventCard(Event event, int displayedIndex, AddressBook addressBook) {
        super(FXML);
        this.event = event;
        this.addressBook = addressBook;

        id.setText(displayedIndex + ". ");
        name.setText(event.getName());
        date.setText("Date: " + event.getDate());
        locationLabel.setText("Location: " + event.getLocation());
        description.setText("Description: " + event.getDescription());
        eventId.setText("Event ID: " + event.getId());

        // Populate tags
        event.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));

        // Populate contacts
        populateContacts();
    }

    /**
     * Populates the contacts pane with names of persons involved in the event.
     */
    private void populateContacts() {
        if (event.getContacts().isEmpty()) {
            Label noContacts = new Label("No contacts");
            contactsPane.getChildren().add(noContacts);
            return;
        }

        contactsPane.getChildren().add(new Label("Contacts:"));
        event.getContacts().forEach(person -> {
            String name = resolvePersonName(person.getId());
            Label contactLabel = new Label(name + " (ID: " + person.getId() + ")");
            contactsPane.getChildren().add(contactLabel);
        });
    }

    /**
     * Helper to resolve person's full name from AddressBook based on ID.
     */
    private String resolvePersonName(String personId) {
        return addressBook.getPersonList().stream()
                .filter(p -> p.getId().equals(personId))
                .map(p -> p.getName().fullName)
                .findFirst()
                .orElse("Unknown");
    }
}
