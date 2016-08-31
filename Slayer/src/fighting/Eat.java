package fighting;

import org.tbot.methods.Players;
import org.tbot.methods.Widgets;
import org.tbot.methods.tabs.Inventory;
import org.tbot.wrappers.WidgetChild;
import util.GUI;
import util.Task;

/**
 * Created by Russell on 2/17/2016.
 */
public class Eat extends Task {
    @Override
    public boolean validate() {
        return getHealth()<= GUI.getEatAt() && Inventory.contains(GUI.getFood());
    }

    @Override
    public void execute() {
        if(Inventory.contains(GUI.getFood())){
            Inventory.getFirst(GUI.getFood()).interact("Eat");
        }
    }

    @Override
    public int priority() {
        return 25;
    }

    @Override
    public String getName() {
        return "Eating";
    }

    private int getHealth(){
        WidgetChild healthWidget = Widgets.getWidget(160,5);
        return Integer.parseInt(healthWidget.getText());
    }
}
