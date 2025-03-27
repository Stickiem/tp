package seedu.address.ui;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.collections.ListChangeListener;
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

        // Listener for relationship changes
        relationships.addListener((ListChangeListener<Relationship>) change -> {
            while (change.next()) {
                if (change.wasAdded() || change.wasRemoved()) {
                    refreshAffectedPersonCards(change.getAddedSubList(), change.getRemoved());
                }
            }
        });
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

    /**
     * Refreshes the person cards that are affected by relationship changes.
     * @param added List of added relationships.
     * @param removed List of removed relationships.
     */
    private void refreshAffectedPersonCards(List<? extends Relationship> added, List<? extends Relationship> removed) {
        Set<String> affectedPersonIds = new HashSet<>();

        // Collect IDs of persons affected by added relationships
        added.forEach(relationship -> {
            affectedPersonIds.add(relationship.getUser1Id());
            affectedPersonIds.add(relationship.getUser2Id());
        });

        // Collect IDs of persons affected by removed relationships
        removed.forEach(relationship -> {
            affectedPersonIds.add(relationship.getUser1Id());
            affectedPersonIds.add(relationship.getUser2Id());
        });

        // Refresh the affected person cards
        for (int i = 0; i < personListView.getItems().size(); i++) {
            Person person = personListView.getItems().get(i);
            if (affectedPersonIds.contains(person.getId())) {
                personListView.refresh();
                break; // Exit after refreshing the list once
            }
        }
    }
}
