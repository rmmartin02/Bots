package banking;

import org.tbot.methods.Bank;
import org.tbot.methods.Time;
import org.tbot.methods.tabs.Inventory;
import util.Monster;
import util.Task;
import walking.WebSingelton;

import java.util.Arrays;

/**
 * Created by Russell on 3/15/2016.
 */
public class AxeBanking extends Task {

    String[] names = {"Ankou", "Fire giant", "Greater demon"};
    int[] axes = {1351,1349,1353,1361,1355,1359,1359};

    @Override
    public boolean validate() {
        if(Arrays.asList(names).contains(Monster.getCurrentMonster().getNames()[0]) && !Inventory.containsOneOf(axes)){
            return true;
        }
        return false;
    }
    @Override
    public void execute() {
        if(Bank.isOpen()){
            if(Bank.containsOneOf(axes)){
                Bank.withdraw(returnFirst(axes), 1);
                Time.sleep(800,1200);
            }
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

    private int returnFirst(int[] list){
        for(int i = 0; i<list.length; i++){
            if(Bank.containsOneOf(list[i])){
                return list[i];
            }
        }
        return -1;
    }
}
