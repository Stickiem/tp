package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.event.Event;

/**
 * An UI component that displays information of an {@code Event}.
 */
public class EventCard extends UiPart<Region> {

    private static final String FXML = "EventCard.fxml";

    public final Event event;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label date;
    @FXML
    private Label location;
    @FXML
    private Label description;

    /**
     * Creates an {@code EventCard} with the given {@code Event} and index to display.
     */
    public EventCard(Event event, int displayedIndex) {
        super(FXML);
        this.event = event;
        name.setText(event.getName());
        date.setText(event.getDate());
        location.setText(event.getLocation());
        description.setText(event.getDescription());
    }
}
