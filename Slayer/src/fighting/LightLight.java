package fighting;

import org.tbot.methods.Time;
import org.tbot.methods.tabs.Inventory;
import org.tbot.wrappers.Item;
import util.Monster;
import util.Task;

/**
 * Created by Russell on 3/8/2016.
 */
public class LightLight extends Task {

    int[] litSources = {32,33,34,594,4524,4531,4534,4539,4550,4702};
    int[] unlitSources = {36,37,38,595,596,597,4522,4523,4525,4526,4529,4528,4529,4530,4532,4533,4535,4565,4537,4538,4544,4545,4546,4547,4548,4549,4700,4701};

    @Override
    public boolean validate() {
        if(Monster.getCurrentMonster().isUsingLight() && !Inventory.containsOneOf(litSources)){
            return true;
        }
        return false;
    }

    @Override
    public void execute() {
        Item toLight = Inventory.getFirst(unlitSources);
        if(toLight!=null) {
            toLight.interact("Light");
            Time.sleep(800, 1200);
        }
    }

    @Override
    public int priority() {
        return 21;
    }

    @Override
    public String getName() {
        return "Lighting light";
    }

}
