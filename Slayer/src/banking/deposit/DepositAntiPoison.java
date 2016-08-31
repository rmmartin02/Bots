package banking.deposit;

import org.tbot.methods.Bank;
import org.tbot.methods.Time;
import org.tbot.methods.tabs.Inventory;
import util.Monster;
import util.Task;
import walking.WebSingelton;

/**
 * Created by Russell on 3/14/2016.
 */
public class DepositAntiPoison extends Task {

    private int[] antiPoisonBank = {2448,2446,181,175,183,177,185,179};

    @Override
    public boolean validate() {
        if(Inventory.containsOneOf(antiPoisonBank) && !Monster.getCurrentMonster().isUsingAntiPoison()){
            return true;
        }
        return false;
    }

    @Override
    public void execute() {
        if(Bank.isOpen()){
            Bank.depositAll(antiPoisonBank);
            Time.sleep(800,1200);
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
