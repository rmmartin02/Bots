package banking.deposit;

import org.tbot.methods.Bank;
import org.tbot.methods.tabs.Inventory;
import util.Monster;
import util.Task;
import walking.WebSingelton;

import java.util.Arrays;

/**
 * Created by Russell on 3/14/2016.
 */
public class DepositItemOnDeath extends Task {

    int[] allItems = {4166,4164,4156,4158,4161,4162,4168,1718,7051,7159,6720,4170,8923,10952,6664,6696,4551,11902};

    @Override
    public boolean validate() {
        if(getUnecessaryItems()!=-1){
            return true;
        }
        return false;
    }

    @Override
    public void execute() {
        int itemID = getUnecessaryItems();
        if(Bank.isOpen()){
            if(Inventory.contains(itemID))
                Bank.depositAll(itemID);
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

    private int getUnecessaryItems(){
        for(int i =0; i<allItems.length; i++){
            if(Inventory.contains(allItems[i])){
                if(Monster.getCurrentMonster().getItemUseOnDeath()!=allItems[i]){
                    return allItems[i];
                }
            }
        }
        return -1;
    }
}
