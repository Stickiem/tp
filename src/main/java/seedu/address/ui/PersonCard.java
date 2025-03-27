package seedu.address.ui;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.relationship.Relationship;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;
    private final AddressBook addressBook;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label socials;
    @FXML
    private Label contactId;
    @FXML
    private FlowPane tags;
    @FXML
    private VBox relationshipsPane;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex, List<Relationship> relationships, AddressBook addressBook) {
        super(FXML);
        this.person = person;
        this.addressBook = addressBook;
        String socialsDisplay = person.getSocials().stream().map(s -> s.toString()).collect(Collectors.joining(","));
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);
        socials.setText(socialsDisplay);
        contactId.setText("ID: " + person.getId());
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));

        // Display relationships for this person
        displayRelationships(relationships);
    }

    /**
     * Displays the relationships associated with this person
     */
    private void displayRelationships(List<Relationship> relationships) {
        if (relationships == null || relationships.isEmpty()) {
            Label noRelationshipsLabel = new Label("No relationships");
            noRelationshipsLabel.getStyleClass().add("relationship-label");
            relationshipsPane.getChildren().add(noRelationshipsLabel);
            return;
        }

        relationshipsPane.getChildren().add(new Label("Relationships:"));
        for (Relationship relationship : relationships) {
            HBox relationshipBox = new HBox();
            relationshipBox.setSpacing(5);

            Label relationshipLabel = new Label(relationship.getName());
            relationshipLabel.getStyleClass().add("relationship-name");

            Label withLabel = new Label("with");
            withLabel.getStyleClass().add("relationship-with");

            // Determine the other person in the relationship
            String otherId = person.getId().equals(relationship.getUser1Id())
                    ? relationship.getUser2Id() : relationship.getUser1Id();

            // Fetch the other person and display their name
            String otherPersonName = getOtherPersonName(otherId);
            Label otherPersonLabel = new Label(otherPersonName + " (" + otherId + ")");
            otherPersonLabel.getStyleClass().add("relationship-person");

            relationshipBox.getChildren().addAll(relationshipLabel, withLabel, otherPersonLabel);
            relationshipsPane.getChildren().add(relationshipBox);

            // Add tags for this relationship if any
            if (!relationship.getTags().isEmpty()) {
                FlowPane relationshipTags = new FlowPane();
                relationshipTags.setHgap(5);
                relationshipTags.setVgap(3);
                relationship.getTags().forEach(tag -> {
                    Label tagLabel = new Label(tag.tagName);
                    tagLabel.getStyleClass().add("relationship-tag");
                    relationshipTags.getChildren().add(tagLabel);
                });
                relationshipsPane.getChildren().add(relationshipTags);
            }
        }
    }

    /**
     * Helper method to fetch the name of the other person given their ID.
     *
     * @param otherId The ID of the other person in the relationship.
     * @return The name of the other person, or "Unknown" if the person is not found.
     */
    private String getOtherPersonName(String otherId) {
        // Iterate through the person list to find the person with the given ID
        return addressBook.getPersonList().stream()
                .filter(p -> p.getId().equals(otherId))
                .findFirst()
                .map(p -> p.getName().fullName)
                .orElse("Unknown");
    }
}
