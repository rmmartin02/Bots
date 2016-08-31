package banking;

import org.tbot.methods.Bank;
import org.tbot.methods.Time;
import org.tbot.methods.tabs.Inventory;
import util.Monster;
import util.Task;
import walking.WebSingelton;

/**
 * Created by Russell on 3/8/2016.
 */
public class LightSourceBanking extends Task {

    int[] litSources = {32,33,34,594,4524,4531,4534,4539,4550,4702};
    int[] unlitSources = {36,37,38,595,596,597,4522,4523,4525,4526,4529,4528,4529,4530,4532,4533,4535,4565,4537,4538,4544,4545,4546,4547,4548,4549,4700,4701};


    @Override
    public boolean validate() {
        if(Monster.getCurrentMonster().isUsingLight() && (!Inventory.contains(590) || (!Inventory.containsOneOf(litSources) && !Inventory.containsOneOf(unlitSources)))){
            return true;
        }
        return false;
    }

    @Override
    public void execute() {
        if(Bank.isOpen()){
            if(!Inventory.contains(590)){
                if(Bank.contains(590)){
                    Bank.withdraw(590,1);
                }
            }
            if(!Inventory.containsOneOf(litSources) && !Inventory.containsOneOf(unlitSources)) {
                if (Bank.containsOneOf(litSources)) {
                    Bank.withdraw(returnHighest(litSources), 1);
                    Time.sleep(800, 1200);
                } else if (Bank.containsOneOf(unlitSources)) {
                    Bank.withdraw(returnHighest(unlitSources), 1);
                    Time.sleep(800, 1200);
                }
            }
        }
        else{
            WebSingelton.getInstance().openBank();
        }
    }

    @Override
    public int priority() {
        return 55;
    }

    @Override
    public String getName() {
        return "Getting light source";
    }

    private int returnHighest(int[] list){
        int highest = -1;
        for(int i = 0; i<list.length; i++){
            if(Bank.containsOneOf(list[i]) && list[i]>highest){
                highest = list[i];
            }
        }
        return highest;
    }
}
