package banking;

import org.tbot.methods.Bank;
import org.tbot.methods.Players;
import org.tbot.methods.Time;
import org.tbot.methods.tabs.Inventory;
import util.GUI;
import util.Monster;
import util.Task;
import walking.WebSingelton;

/**
 * Created by Russell on 3/10/2016.
 */
public class ShantayPassBanking extends Task {
    @Override
    public boolean validate() {
        if(Monster.getCurrentMonster().isUsingWaterSkins() && Players.getLocal().getLocation().getY()>3116 && !Inventory.contains(1854)){
            return true;
        }
        return false;
    }

    @Override
    public void execute() {
        if(!Inventory.contains(1854)){
            if(Bank.isOpen()){
                if(Inventory.isFull()){
                    Bank.deposit(GUI.getFood(), 1);
                    Time.sleep(600,1200);
                }
                else{
                    Bank.withdraw(1854,1);
                    Time.sleep(600,1200);
                }
            }
            else{
                WebSingelton.getInstance().openBank();
            }
        }
    }

    @Override
    public int priority() {
        return 55;
    }

    @Override
    public String getName() {
        return "Banking for shantay pass";
    }
}
