package util;

/**
 * Created by Russell on 2/9/2016.
 */
import java.util.ArrayList;
import java.util.List;

public class Controller {
    private List<Task> TaskList;

    public Controller(Task... Tasks) {
        this.TaskList = new ArrayList<Task>();
        for (Task n : Tasks) {
            TaskList.add(n);
        }
    }

    public void addTasks(Task... Tasks) {
        for (Task n : Tasks) {
            TaskList.add(n);
        }
    }

    public void clearTasks() {
        TaskList.clear();
    }

    public void removeTasks(Task... Tasks) {
        for (Task n : Tasks) {
            TaskList.remove(n);
        }
    }

    public List<Task> getTasks() {
        return TaskList;
    }

    public Task getCurrentTask() {
        List<Task> exeucuteableTasks = new ArrayList<Task>();
        for (Task n : TaskList) {
            if (n.validate()) {
                exeucuteableTasks.add(n);
            }
        }
        for (Task n : exeucuteableTasks) {
            if (getHighest(exeucuteableTasks) == n.priority()) {
                return n;
            }
        }
        return null;
    }

    private int getHighest(List<Task> exeucuteableTasks) {
        int highest = exeucuteableTasks.get(0).priority();
        for (Task n : exeucuteableTasks) {
            if (n.priority() > highest) {
                highest = n.priority();
            }
        }
        return highest;
    }

}
