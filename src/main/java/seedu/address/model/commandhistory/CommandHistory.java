package seedu.address.model.commandhistory;

import java.util.Deque;
import java.util.LinkedList;

/**
 * A class to manage the history of commands.
 */
public class CommandHistory {
    /**
     * Deque to store the last 5 user inputs.
     */
    private static final Deque<String> lastCommands = new LinkedList<>();

    /**
     * Adds the given command input to the history of last 5 commands.
     * If the list exceeds 5, removes the oldest command.
     *
     * @param userInput The command to add to the history.
     */
    public static void addCommandToHistory(String userInput) {
        if (lastCommands.size() == 5) {
            lastCommands.poll();
        }
        lastCommands.offer(userInput);
    }

    /**
     * Retrieves the command at the specified index from the lastCommands deque.
     *
     * @param index The index of the command in the deque (1-based).
     * @return The command string at the specified index.
     * @throws IndexOutOfBoundsException if the index is out of bounds.
     */
    public static String getCommandFromHistory(int index) {
        int dequeIndex = 5 - index;

        if (lastCommands.size() <= dequeIndex) {
            throw new IndexOutOfBoundsException("Not enough commands in history to redo.");
        }

        return (String) lastCommands.toArray()[dequeIndex];
    }

    /**
     * Returns the last 5 commands (user inputs).
     *
     * @return list of last 5 commands.
     */
    public static Deque<String> getLastCommands() {
        return lastCommands;
    }
}
