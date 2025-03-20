package seedu.address.ui;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.relationship.Relationship;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private ListView<Person> personListView;

    private final ObservableList<Relationship> relationships;
    private final ReadOnlyAddressBook addressBook;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public PersonListPanel(ObservableList<Person> personList, ObservableList<Relationship> relationships,
                           ReadOnlyAddressBook addressBook) {
        super(FXML);
        this.relationships = relationships;
        this.addressBook = addressBook;

        personListView.setItems(personList);
        personListView.setCellFactory(listView -> new PersonListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<Person> {
        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                // Get relationships for this person
                List<Relationship> personRelationships = relationships.stream()
                        .filter(r -> r.involvesUser(person.getId()))
                        .collect(Collectors.toList());

                setGraphic(new PersonCard(person, getIndex() + 1, personRelationships,
                        (seedu.address.model.AddressBook) addressBook).getRoot());
            }
        }
    }
}
