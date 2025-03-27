package seedu.address.logic.commands;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

import java.util.Comparator;
import java.util.List;


/**
 * Sorts the address book by one or more fields.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts the address book by one or more fields.\n"
            + "Parameters: [-r] [FIELD]...\n"
            + "  -r: Reverse the order\n"
            + "  FIELD: one or more fields to sort by (e.g., name, phone, email, address, tags)\n"
            + "Example: " + COMMAND_WORD + " -r name phone";

    private final boolean isReverse;
    private final List<String> fields;

    /**
     * Creates a SortCommand to sort the address book by the specified fields.
     * @param isReverse Whether to reverse the sorting order
     * @param fields The fields by which to sort
     */
    public SortCommand(boolean isReverse, List<String> fields) {
        this.isReverse = isReverse;
        this.fields = fields;
    }

    @Override
    public CommandResult execute(Model model) {
        Comparator<Person> comparator = createComparator(fields);

        if (isReverse) {
            comparator = comparator.reversed();
        }

        model.updateSortedPersonList(comparator);
        System.out.println("hello I insert comparator");
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    /**
     * Creates a comparator based on the fields provided.
     * @param fields The fields to sort by
     * @return A comparator for sorting
     */
    private Comparator<Person> createComparator(List<String> fields) {
        Comparator<Person> comparator;

        if (fields.isEmpty()) {
            throw new IllegalArgumentException("At least one field must be specified for sorting.");
        }

        String firstField = fields.get(0).toLowerCase();
        switch (firstField) {
            case "name":
                comparator = (p1, p2) -> p1.getName().toString().compareToIgnoreCase(p2.getName().toString());
                break;
            case "phone":
                comparator = (p1, p2) -> p1.getPhone().toString().compareToIgnoreCase(p2.getPhone().toString());
                break;
            case "email":
                comparator = (p1, p2) -> p1.getEmail().toString().compareToIgnoreCase(p2.getEmail().toString());
                break;
            case "address":
                comparator = (p1, p2) -> p1.getAddress().toString().compareToIgnoreCase(p2.getAddress().toString());
                break;
            case "tags":
                comparator = Comparator.comparingInt(p -> p.getTags().size());
                break;
            default:
                throw new IllegalArgumentException("Unknown field: " + firstField);
        }

        for (int i = 1; i < fields.size(); i++) {
            String field = fields.get(i).toLowerCase();
            switch (field) {
                case "name":
                    comparator = comparator.thenComparing((p1, p2) -> p1.getName().toString().compareToIgnoreCase(p2.getName().toString()));
                    break;
                case "phone":
                    comparator = comparator.thenComparing((p1, p2) -> p1.getPhone().toString().compareToIgnoreCase(p2.getPhone().toString()));
                    break;
                case "email":
                    comparator = comparator.thenComparing((p1, p2) -> p1.getEmail().toString().compareToIgnoreCase(p2.getEmail().toString()));
                    break;
                case "address":
                    comparator = comparator.thenComparing((p1, p2) -> p1.getAddress().toString().compareToIgnoreCase(p2.getAddress().toString()));
                    break;
                case "tags":
                    comparator = comparator.thenComparingInt(p -> p.getTags().size());
                    break;
                default:
                    throw new IllegalArgumentException("Unknown field: " + field);
            }
        }

        return comparator;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof SortCommand)) {
            return false;
        }

        SortCommand otherSortCommand = (SortCommand) other;
        return isReverse == otherSortCommand.isReverse && fields.equals(otherSortCommand.fields);
    }

    @Override
    public String toString() {
        return "SortCommand{" +
                "isReverse=" + isReverse +
                ", fields=" + fields +
                '}';
    }
}
