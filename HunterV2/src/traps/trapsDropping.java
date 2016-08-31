package traps;

import org.tbot.methods.Time;
import org.tbot.methods.tabs.Inventory;
import util.Task;

/**
 * Created by Russell on 2/9/2016.
 */
public class trapsDropping extends Task{

    @Override
    public boolean validate() {
        return Inventory.contains(526) || Inventory.contains(9978) || Inventory.contains(464);
    }

    @Override
    public void execute() {
        while(Inventory.contains(526) || Inventory.contains(9978) || Inventory.contains(464)){
            Inventory.getFirst(526,9978,464).interact("Drop");
            Time.sleep(400,600);
        }
    }

    @Override
    public int priority() {
        if(Inventory.getCount()>25){
            return 8;
        }
        return 1;
    }

    @Override
    public String getName() {
        return "Dropping";
    }


}
