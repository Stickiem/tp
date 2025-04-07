package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.DateParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.Event;
import seedu.address.model.person.UniquePersonList;
import seedu.address.testutil.ModelStub;

class AddEventCommandTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddEventCommand(null, null));
    }

    @Test
    public void execute_eventAcceptedByModel_addSuccessful() throws CommandException, ParseException {
        ModelStubAcceptingEventAdded modelStub = new ModelStubAcceptingEventAdded();
        Event validEvent = new Event(
                "A valid event name.",
                DateParserUtil.parseDate("2025-01-01"),
                null,
                null,
                null,
                new UniquePersonList()
        );

        CommandResult commandResult = new AddEventCommand(validEvent, List.of()).execute(modelStub);

        assertEquals(
                String.format(AddEventCommand.MESSAGE_SUCCESS, validEvent),
                commandResult.getFeedbackToUser());
        assertEquals(List.of(validEvent), modelStub.events);
    }

    private class ModelStubAcceptingEventAdded extends ModelStub {
        private final java.util.ArrayList<Event> events = new java.util.ArrayList<>();

        @Override
        public boolean hasEvent(Event event) {
            return false;
        }

        @Override
        public void addEvent(Event event) {
            requireNonNull(events);
            events.add(event);
        }
    }

}
