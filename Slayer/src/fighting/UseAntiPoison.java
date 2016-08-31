package fighting;

import org.tbot.methods.Players;
import org.tbot.methods.Time;
import org.tbot.methods.Widgets;
import org.tbot.methods.tabs.Inventory;
import util.Monster;
import util.Task;

/**
 * Created by Russell on 2/17/2016.
 */
public class UseAntiPoison extends Task {

    int[] antiPoisonDrink = {179,185,177,183,175,181,2446,2448};
    int[] antiPoisonBank = {2448,2446,181,175,183,177,185,179};

    @Override
    public boolean validate() {
        if(Monster.getCurrentMonster().isUsingAntiPoison() && Widgets.getWidget(160,8).isVisible() && Inventory.containsOneOf(antiPoisonDrink)){
            return true;
        }
        return false;
    }

    @Override
    public void execute() {
        if(Widgets.getWidget(160,8).isVisible() && Inventory.containsOneOf(antiPoisonBank)){
            Inventory.getFirst(antiPoisonDrink).interact("Drink");
            Time.sleep(800,1200);
        }
    }

    @Override
    public int priority() {
        return 22;
    }

    @Override
    public String getName() {
        return "Using antipoison";
    }
}
