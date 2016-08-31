package salamanders;

import org.tbot.methods.Players;
import org.tbot.methods.Time;
import org.tbot.methods.tabs.Inventory;
import org.tbot.wrappers.Item;
import util.Task;
import util.Var;

/**
 * Created by Russell on 2/11/2016.
 */
public class release extends Task {
    @Override
    public boolean validate() {
        return Inventory.contains(Var.getCurrentVar().getThingID()) || Inventory.contains(464) && Players.getLocal().getAnimation()==-1;
    }

    @Override
    public void execute() {
        while(Inventory.contains(Var.getCurrentVar().getThingID()) || Inventory.contains(464)){
            if(Inventory.contains(464)){
                Inventory.getFirst(464).interact("Drop");
            }
            final Item toBeReleased = Inventory.getFirst(Var.getCurrentVar().getThingID());
            toBeReleased.interact("Release");
            Time.sleep(300,500);
        }
    }

    @Override
    public int priority() {
        if(Inventory.getCount()>25){
            return 10;
        }
        return 0;
    }

    @Override
    public String getName() {
        return "Be Free!";
    }
}
