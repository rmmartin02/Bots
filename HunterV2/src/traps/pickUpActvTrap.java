package traps;

import org.tbot.methods.GameObjects;
import org.tbot.methods.Time;
import org.tbot.methods.Widgets;
import org.tbot.methods.walking.Path;
import org.tbot.methods.walking.Walking;
import org.tbot.util.Condition;
import org.tbot.util.Filter;
import org.tbot.wrappers.GameObject;
import org.tbot.wrappers.Widget;
import org.tbot.wrappers.WidgetChild;
import util.Task;
import util.TrapLocations;
import util.Var;

/**
 * Created by Russell on 2/11/2016.
 */
public class pickUpActvTrap extends Task {

    private static final Filter<GameObject> actvTrap_filter = new Filter<GameObject>() {

        @Override
        public boolean accept(GameObject actvTrap) {
            if (actvTrap!=null && TrapLocations.getInstance().getTrapTiles().contains(actvTrap.getLocation()) && actvTrap.getID()== Var.getCurrentVar().getActvTrapID()){
                return true;
            }
            return false;
        }
    };

    private boolean notYourTrap(){
        Widget chatBox = Widgets.getWidget(162);
        WidgetChild chatText= chatBox.getChild(43);
        WidgetChild toCheck = chatText.getChild(0);
        WidgetChild toCheck2 = chatText.getChild(2);
        if(toCheck.getText().equalsIgnoreCase("This isn't your trap.") && toCheck2.getText().equalsIgnoreCase("This isn't your trap.")){
            return true;
        }
        return false;
    }

    @Override
    public boolean validate() {
        return GameObjects.getNearest(actvTrap_filter)!=null;
    }

    @Override
    public void execute() {
        final GameObject actvTrap = GameObjects.getNearest(actvTrap_filter);
        if(actvTrap!=null) {
            if (actvTrap.isOnScreen()) {
                actvTrap.interact("Check");
                if (notYourTrap()) {
                    TrapLocations.getInstance().changeTrapLocation(actvTrap.getLocation());
                }
                Time.sleep(800, 1200);
            } else {
                Path toTrap = Walking.findPath(actvTrap);
                if (toTrap != null) {
                    toTrap.traverse();
                    Time.sleepUntil(new Condition() {
                        @Override
                        public boolean check() {
                            return Walking.getDestinationDistance() < 3 || actvTrap.isOnScreen();
                        }
                    }, 3500);
                }
            }
        }
    }

    @Override
    public int priority() {
        return 6;
    }

    @Override
    public String getName() {
        return "Picking up trap";
    }
}
