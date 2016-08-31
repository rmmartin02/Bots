package banking.deposit;

import org.tbot.methods.Bank;
import org.tbot.methods.tabs.Inventory;
import util.Monster;
import util.Task;
import walking.WebSingelton;

/**
 * Created by Russell on 3/14/2016.
 */
public class DepositWaterskin extends Task {

    private int[] waterskinsIDs = {1823,1825,1827,1829,1831};

    @Override
    public boolean validate() {
        if(!Monster.getCurrentMonster().isUsingWaterSkins()){
            if(Inventory.containsOneOf(waterskinsIDs)){
                return true;
            }
        }
        return false;
    }

    @Override
    public void execute() {
        if(Bank.isOpen()){
            if(Inventory.containsOneOf(waterskinsIDs)){
                Bank.depositAll(waterskinsIDs);
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
