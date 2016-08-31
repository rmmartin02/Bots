package banking;

import org.tbot.methods.Bank;
import org.tbot.methods.Time;
import org.tbot.methods.tabs.Inventory;
import util.GUI;
import util.Monster;
import util.Task;
import walking.WebSingelton;

/**
 * Created by Russell on 2/26/2016.
 */
public class AntipoisonBanking extends Task {

    int[] antiPoisonDrink = {179,185,177,183,175,181,2446,2448};
    int[] antiPoisonBank = {2448,2446,181,175,183,177,185,179};

    @Override
    public boolean validate() {
        if(Monster.getCurrentMonster().isUsingAntiPoison()) {
            if (!Inventory.containsOneOf(antiPoisonBank)) {
                return true;
            }
            if (Bank.isOpen() && countAntipoison(antiPoisonBank) < 2) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void execute() {
        if(Inventory.contains(229)){
            Inventory.dropAll(229);
        }
        else if(Bank.isOpen()){
            if(Inventory.getCount()>26){
                Bank.depositAllExcept("Enchanted gem");
                Time.sleep(600,1200);
            }
            else{
                if(countAntipoison(antiPoisonBank)<2){
                    if(Bank.containsOneOf(antiPoisonBank)) {
                        Bank.withdraw(firstAvailableBank(antiPoisonBank,2), 2);
                        Time.sleep(600,1200);
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
        return "Antipoison Banking";
    }

    private int firstAvailableBank(int [] list, int amount){
        for(int i = 0; i<list.length; i++){
            if(Bank.containsOneOf(list[i])){
                if(Bank.getCount(list[i])>=amount){
                    return list[i];
                }
            }
        }
        return -1;
    }

    private int countAntipoison(int [] list){
        int amount = 0;
        for(int i = 0; i<list.length; i++){
            if(Inventory.containsOneOf(list[i])){
                amount+= Inventory.getCount(list[i]);
            }
        }
        return amount;
    }
}
