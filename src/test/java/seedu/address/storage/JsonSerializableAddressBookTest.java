package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.AddressBook;
import seedu.address.testutil.TypicalEvents;
import seedu.address.testutil.TypicalPersons;
import seedu.address.testutil.TypicalRelationships;

public class JsonSerializableAddressBookTest {

    private static final String TEST_DATA_FOLDER = "src/test/data/JsonSerializableAddressBookTest/";

    @Test
    public void toModelType_nullRelationshipsAndEvents_success() throws Exception {
        // Create a list of JsonAdaptedPerson
        List<JsonAdaptedPerson> persons = TypicalPersons.getTypicalPersons().stream()
                .map(JsonAdaptedPerson::new)
                .collect(Collectors.toList());

        // Create JsonSerializableAddressBook with null relationships and events
        JsonSerializableAddressBook addressBook = new JsonSerializableAddressBook(persons, null, null);
        AddressBook modelAddressBook = addressBook.toModelType();

        // Verify that persons were added but relationships and events are empty
        assertEquals(TypicalPersons.getTypicalPersons().size(), modelAddressBook.getPersonList().size());
        assertEquals(0, modelAddressBook.getRelationshipList().size());
        assertEquals(0, modelAddressBook.getEventList().size());
    }

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        Optional<JsonSerializableAddressBook> optionalData = JsonUtil.readJsonFile(
                Path.of(TEST_DATA_FOLDER + "typicalPersonsAddressBook.json"),
                JsonSerializableAddressBook.class);

        // Ensure data is present
        assertTrue(optionalData.isPresent(), "Expected file to contain valid data");

        AddressBook addressBookFromFile = optionalData.get().toModelType();
        AddressBook typicalPersonsAddressBook = TypicalPersons.getTypicalAddressBook();
        assertEquals(addressBookFromFile, typicalPersonsAddressBook);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        Optional<JsonSerializableAddressBook> optionalData = JsonUtil.readJsonFile(
                Path.of(TEST_DATA_FOLDER + "invalidPersonAddressBook.json"),
                JsonSerializableAddressBook.class);

        // Ensure data is present
        assertTrue(optionalData.isPresent(), "Expected file to contain data");

        JsonSerializableAddressBook dataFromFile = optionalData.get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        Optional<JsonSerializableAddressBook> optionalData = JsonUtil.readJsonFile(
                Path.of(TEST_DATA_FOLDER + "duplicatePersonAddressBook.json"),
                JsonSerializableAddressBook.class);

        // Ensure data is present
        assertTrue(optionalData.isPresent(), "Expected file to contain data");

        JsonSerializableAddressBook dataFromFile = optionalData.get();
        assertThrows(IllegalValueException.class,
                JsonSerializableAddressBook.MESSAGE_DUPLICATE_PERSON, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateRelationships_throwsIllegalValueException() {
        // Create lists with a duplicate relationship
        List<JsonAdaptedPerson> persons = new ArrayList<>();
        List<JsonAdaptedRelationship> relationships = new ArrayList<>();
        relationships.add(new JsonAdaptedRelationship(TypicalRelationships.ALICE_BEFRIENDS_BENSON));
        relationships.add(new JsonAdaptedRelationship(TypicalRelationships.ALICE_BEFRIENDS_BENSON));

        JsonSerializableAddressBook addressBook = new JsonSerializableAddressBook(persons, relationships, null);
        assertThrows(IllegalValueException.class,
                JsonSerializableAddressBook.MESSAGE_DUPLICATE_RELATIONSHIP, addressBook::toModelType);
    }

    @Test
    public void toModelType_duplicateEvents_throwsIllegalValueException() {
        // Create lists with a duplicate event
        List<JsonAdaptedPerson> persons = new ArrayList<>();
        List<JsonAdaptedRelationship> relationships = new ArrayList<>();
        List<JsonAdaptedEvent> events = new ArrayList<>();
        events.add(new JsonAdaptedEvent(TypicalEvents.BIRTHDAY));
        events.add(new JsonAdaptedEvent(TypicalEvents.BIRTHDAY));

        JsonSerializableAddressBook addressBook = new JsonSerializableAddressBook(persons, relationships, events);
        assertThrows(IllegalValueException.class,
                JsonSerializableAddressBook.MESSAGE_DUPLICATE_EVENT, addressBook::toModelType);
    }
}
