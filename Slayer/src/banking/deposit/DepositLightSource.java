package banking.deposit;

import org.tbot.methods.Bank;
import org.tbot.methods.tabs.Inventory;
import util.Monster;
import util.Task;
import walking.WebSingelton;

/**
 * Created by Russell on 3/14/2016.
 */
public class DepositLightSource extends Task {

    int[] litSources = {32,33,34,594,4524,4531,4534,4539,4550,4702};
    int[] unlitSources = {36,37,38,595,596,597,4522,4523,4525,4526,4529,4528,4529,4530,4532,4533,4535,4565,4537,4538,4544,4545,4546,4547,4548,4549,4700,4701};
    int tinderbox = 590;


    @Override
    public boolean validate() {
        if(!Monster.getCurrentMonster().isUsingLight()) {
            if (Inventory.containsOneOf(litSources) || Inventory.containsOneOf(unlitSources) || Inventory.contains(tinderbox)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void execute() {
        if(Bank.isOpen()){
            if(Inventory.contains(tinderbox)){
                Bank.depositAll(tinderbox);
            }
            else if(Inventory.containsOneOf(litSources)){
                Bank.depositAll(litSources);
            }
            else if(Inventory.containsOneOf(unlitSources)){
                Bank.depositAll(unlitSources);
            }
        }
        else{
            WebSingelton.getInstance().openBank();
        }
    }

    @Override
    public int priority() {
        return 65;
    }

    @Override
    public String getName() {
        return null;
    }
}
