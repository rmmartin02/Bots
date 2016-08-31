package falconry;

import org.tbot.methods.Time;
import org.tbot.methods.tabs.Inventory;
import util.Task;
import util.Var;

/**
 * Created by Russell on 2/11/2016.
 */
public class dropping extends Task {
    @Override
    public boolean validate() {
        return Inventory.getCount()>25;
    }

    @Override
    public void execute() {
        //9978
        while(Inventory.contains(526) || Inventory.contains(Var.getCurrentVar().getThingID())){
            Inventory.getFirst(526,Var.getCurrentVar().getThingID()).interact("Drop");
            Time.sleep(500,650);
        }
    }

    @Override
    public int priority() {
        return 20;
    }

    @Override
    public String getName() {
        return "Dropping";
    }
}
