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
public class trippedTrap extends Task {

    private static final Filter<GameObject> trippedTrap_filter = new Filter<GameObject>() {

        @Override
        public boolean accept(GameObject trippedTrap) {
            if (trippedTrap!=null && TrapLocations.getInstance().getTrapTiles().contains(trippedTrap.getLocation()) && trippedTrap.getID()== Var.getCurrentVar().getTrippedTrapID()){
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
        WidgetChild toCheck4 = chatText.getChild(4);
        if(toCheck.getText().equalsIgnoreCase("This isn't your trap.") && toCheck2.getText().equalsIgnoreCase("This isn't your trap.")){
            return true;
        }
        return false;
    }
    
    @Override
    public boolean validate() {
        return GameObjects.getNearest(trippedTrap_filter)!=null;
    }

    @Override
    public void execute() {
        final GameObject trippedTrap = GameObjects.getNearest(trippedTrap_filter);
        if(trippedTrap!=null) {
            if (trippedTrap.isOnScreen()) {
                trippedTrap.interact("Dismantle");
                if (notYourTrap()) {
                    TrapLocations.getInstance().changeTrapLocation(trippedTrap.getLocation());
                }
                Time.sleep(800, 1200);
            } else {
                Path toTrippedTrap = Walking.findPath(trippedTrap.getLocation());
                if (toTrippedTrap != null) {
                    toTrippedTrap.traverse();
                    Time.sleepUntil(new Condition() {
                        @Override
                        public boolean check() {
                            return Walking.getDestinationDistance() < 3 || trippedTrap.isOnScreen();
                        }
                    }, 3500);
                }
            }
        }
    }

    @Override
    public int priority() {
        return 5;
    }

    @Override
    public String getName() {
        return "Dismantling trap";
    }
}
