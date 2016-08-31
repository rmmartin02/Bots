package banking;

import org.tbot.methods.Bank;
import org.tbot.methods.tabs.Inventory;
import util.GUI;
import util.Monster;
import util.Task;
import walking.WebSingelton;

/**
 * Created by Russell on 2/26/2016.
 */
public class ItemOnDeathBanking extends Task {
    @Override
    public boolean validate() {
        return Monster.getCurrentMonster().getItemUseOnDeath()!=-1 && !Inventory.contains(Monster.getCurrentMonster().getItemUseOnDeath());
    }

    @Override
    public void execute() {
        if(Bank.isOpen()){
            if(Inventory.isFull()){
                Bank.depositAllExcept("Enchanted gem");
            }
            else{
                if(Monster.getCurrentMonster().getItemUseOnDeath()!=-1){
                    if(Bank.contains(Monster.getCurrentMonster().getItemUseOnDeath())){
                        Bank.withdrawAll(Monster.getCurrentMonster().getItemUseOnDeath());
                    }
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
        return "Item Banking";
    }
}
