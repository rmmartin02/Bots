package banking.deposit;

import org.tbot.methods.Bank;
import org.tbot.methods.Time;
import org.tbot.methods.tabs.Inventory;
import org.tbot.wrappers.Area;
import org.tbot.wrappers.Tile;
import util.Monster;
import util.Task;
import walking.WebSingelton;
import java.util.Arrays;


/**
 * Created by Russell on 3/15/2016.
 */
public class DepositAxe extends Task {

    String[] names = {"Ankou", "Fire giant", "Greater demon"};
    int[] axes = {1351,1349,1353,1361,1355,1359,1359};

    @Override
    public boolean validate() {
        if(!Arrays.asList(names).contains(Monster.getCurrentMonster().getNames()[0]) && Inventory.containsOneOf(axes)){
            return true;
        }
        return false;
    }

    @Override
    public void execute() {
        if(Bank.isOpen()){
            Bank.depositAll(axes);
            Time.sleep(800,1200);
        }
        else{
            WebSingelton.getInstance().openBank();
        }
    }

    @Override
    public int priority() {
        return 0;
    }

    @Override
    public String getName() {
        return null;
    }
}
