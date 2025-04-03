package seedu.address.ui;


import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.event.Event;

/**
 * Panel containing the list of events.
 */
public class EventListPanel extends UiPart<Region> {
    private static final String FXML = "EventListPanel.fxml";

    @FXML
    private ListView<Event> eventListView;

    private final ReadOnlyAddressBook addressBook;

    /**
     * Creates an {@code EventListPanel} with the given {@code ObservableList} and {@code ReadOnlyAddressBook}.
     */
    public EventListPanel(ObservableList<Event> eventList, ReadOnlyAddressBook addressBook) {
        super(FXML);
        this.addressBook = addressBook;
        eventListView.setItems(eventList);
        eventListView.setCellFactory(listView -> new EventListViewCell());

        // Add a listener to refresh the list when items change
        eventList.addListener((ListChangeListener<Event>) c -> {
            while (c.next()) {
                if (c.wasAdded() || c.wasRemoved()) {
                    eventListView.refresh();
                }
            }
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of an {@code Event} using an {@code EventCard}.
     */
    class EventListViewCell extends ListCell<Event> {
        @Override
        protected void updateItem(Event event, boolean empty) {
            super.updateItem(event, empty);

            if (empty || event == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new EventCard(event, getIndex() + 1,
                        (seedu.address.model.AddressBook) addressBook).getRoot());
            }
        }
    }
}
