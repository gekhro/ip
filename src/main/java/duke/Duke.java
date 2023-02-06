package duke;
import duke.commands.Command;

public class Duke{
    private Storage storage;
    private TaskList tasks;
    private Ui ui;
    
    // Loads up the previous tasks if there are any.
    public Duke(String filepath) {
        this.ui = new Ui();
        this.storage = new Storage(filepath);
        try {
            tasks = storage.load();
        } catch (DukeException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    /**
     * The function runs a loop that reads a command from the user, parses it, executes it, and then
     * checks if the command is an exit command
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = this.ui.readCommand();
                ui.showLine(); // show the divider line ("_______")
                Command c = Parser.parse(fullCommand);
                c.execute(tasks, ui, storage);
                isExit = c.isExit();
            } catch (DukeException e) {
                ui.showError(e.getMessage());
            } finally {
                ui.showLine();
            }
        }
    }


    // public static void main(String[] args) {
    //     new Duke("data/tasks.txt").run();
    // }
    
    public static void main(String[] args) {
        new Duke("tasks.txt").run();
    }
}
