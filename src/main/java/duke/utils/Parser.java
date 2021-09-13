package duke.utils;

import duke.commands.Command;
import duke.commands.DeadlineCommand;
import duke.commands.DeleteCommand;
import duke.commands.DoneCommand;
import duke.commands.EventCommand;
import duke.commands.ExitCommand;
import duke.commands.FindCommand;
import duke.commands.ListAllCommand;
import duke.commands.SortDeadlinesCommand;
import duke.commands.ToDoCommand;
import duke.exceptions.EmptyTaskDescriptionException;
import duke.exceptions.UnknownInputException;

/**
 * The Parser to parse and make sense of the user commands.
 */
public class Parser {

    /**
     * Returns a Command object containing the necessary information to be executed.
     *
     * @param fullCommand the full command input by the user.
     * @return a Command object containing the necessary information to be executed.
     * @throws UnknownInputException if the full command given is invalid.
     * @throws EmptyTaskDescriptionException if a valid command was given but there was no task description.
     */
    public static Command parse(String fullCommand) throws UnknownInputException, EmptyTaskDescriptionException {
        int taskNum;
        if (true == fullCommand.startsWith("bye")) {
            return new ExitCommand();
        } else if (true == fullCommand.startsWith("list")) {
            return new ListAllCommand();
        } else if (true == fullCommand.startsWith("done")) {
            taskNum = Integer.parseInt(fullCommand.substring(5)) - 1;
            return new DoneCommand(taskNum);
        } else if (true == fullCommand.startsWith("todo")) {
            try {
                String description = fullCommand.substring(5);
                return new ToDoCommand(description);
            } catch (StringIndexOutOfBoundsException strE) {
                throw new EmptyTaskDescriptionException("todo");
            }
        } else if (true == fullCommand.startsWith("deadline")) {
            try {
                int sep = fullCommand.indexOf('/', 9);
                String descPart = fullCommand.substring(9, sep - 1);
                String byPart = fullCommand.substring(sep + 4);
                return new DeadlineCommand(descPart, byPart);
            } catch (StringIndexOutOfBoundsException strE) {
                throw new EmptyTaskDescriptionException("deadline");
            }
        } else if (true == fullCommand.startsWith("event")) {
            try {
                int sep = fullCommand.indexOf('/', 6);
                String descPart = fullCommand.substring(6, sep - 1);
                String atPart = fullCommand.substring(sep + 1);
                return new EventCommand(descPart, atPart);
            } catch (StringIndexOutOfBoundsException strE) {
                throw new EmptyTaskDescriptionException("event");
            }
        } else if (true == fullCommand.startsWith("delete")) {
            taskNum = Integer.parseInt(fullCommand.substring(7)) - 1;
            return new DeleteCommand(taskNum);
        } else if (true == fullCommand.startsWith("find")) {
            String keyword = fullCommand.substring(5);
            return new FindCommand(keyword);
        } else if (true == fullCommand.startsWith("chrono deadlines")) {
            return new SortDeadlinesCommand();
        } else {
            UnknownInputException e = new UnknownInputException();
            throw e;
        }
    }
}
