package traps;

import util.Task;

/**
 * Created by Russell on 2/15/2016.
 */
public class waiting extends Task {

    @Override
    public boolean validate() {
        return true;
    }

    @Override
    public void execute() {

    }

    @Override
    public int priority() {
        return 0;
    }

    @Override
    public String getName() {
        return "Waiting";
    }
}
