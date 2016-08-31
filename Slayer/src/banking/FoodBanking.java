package banking;

import org.tbot.methods.Bank;
import org.tbot.methods.Time;
import org.tbot.methods.tabs.Inventory;
import util.GUI;
import util.Task;
import walking.WebSingelton;

/**
 * Created by Russell on 2/26/2016.
 */
public class FoodBanking extends Task {
    @Override
    public boolean validate() {
        if(!Inventory.contains(GUI.getFood())){
            return true;
        }
        if(Bank.isOpen() && Inventory.getCount(GUI.getFood())<GUI.getFoodAmount() && !Inventory.isFull()){
            return true;
        }
        return false;
    }

    @Override
    public void execute() {
        if(Bank.isOpen()){
            if(Inventory.isFull()){
                Bank.depositAllExcept("Enchanted gem");
            }
            else{
                if(Bank.contains(GUI.getFood())) {
                    Bank.withdrawAll(GUI.getFood());
                    Time.sleep(600,1200);
                }
            }
        }
        else{
            WebSingelton.getInstance().openBank();
        }
    }

    @Override
    public int priority() {
        return 50;
    }

    @Override
    public String getName() {
        return "Food Banking";
    }
}
