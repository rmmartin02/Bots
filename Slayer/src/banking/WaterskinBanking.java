package banking;

import org.tbot.methods.Bank;
import org.tbot.methods.Time;
import org.tbot.methods.tabs.Inventory;
import util.Monster;
import util.Task;
import walking.WebSingelton;

/**
 * Created by Russell on 2/26/2016.
 */
public class WaterskinBanking extends Task {

    private int[] waterskinsIDs = {1823,1825,1827,1829,1831};

    @Override
    public boolean validate() {
        return Monster.getCurrentMonster().isUsingWaterSkins() && (!Inventory.contains(waterskinsIDs[0]) && !Inventory.contains(waterskinsIDs[1]) && !Inventory.contains(waterskinsIDs[2]) && !Inventory.contains(waterskinsIDs[3]) && !Inventory.contains(waterskinsIDs[4])
                || ((Inventory.getCount(waterskinsIDs[0]) + Inventory.getCount(waterskinsIDs[1]) + Inventory.getCount(waterskinsIDs[2]) + Inventory.getCount(waterskinsIDs[3]) + Inventory.getCount(waterskinsIDs[4])) < 4));
    }

    @Override
    public void execute() {
        int numberOfWaterskins = (Inventory.getCount(waterskinsIDs[0])+Inventory.getCount(waterskinsIDs[1])+Inventory.getCount(waterskinsIDs[2])+Inventory.getCount(waterskinsIDs[3])+Inventory.getCount(waterskinsIDs[4]));
        if(Bank.isOpen()){
            if(Inventory.isFull()){
                Bank.depositAllExcept("Enchanted gem");
                Time.sleep(600,1200);
            }
            else{
                if(Bank.contains(waterskinsIDs[0])){
                    Bank.withdraw(waterskinsIDs[0], 4-numberOfWaterskins);
                    Time.sleep(600,1200);
                }
                else if(Bank.contains(waterskinsIDs[1])){
                    Bank.withdraw(waterskinsIDs[1], 4-numberOfWaterskins);
                    Time.sleep(600,1200);
                }
                else if(Bank.contains(waterskinsIDs[2])){
                    Bank.withdraw(waterskinsIDs[2], 4-numberOfWaterskins);
                    Time.sleep(600,1200);
                }
                else if(Bank.contains(waterskinsIDs[3])){
                    Bank.withdraw(waterskinsIDs[3], 4-numberOfWaterskins);
                    Time.sleep(600,1200);
                }
                else if(Bank.contains(waterskinsIDs[4])){
                    Bank.withdraw(waterskinsIDs[4], 4-numberOfWaterskins);
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
        return 55;
    }

    @Override
    public String getName() {
        return "Waterskin Banking";
    }
}
