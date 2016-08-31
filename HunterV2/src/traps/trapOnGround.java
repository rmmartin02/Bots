package traps;

import org.tbot.methods.GameObjects;
import org.tbot.methods.GroundItems;
import org.tbot.methods.Time;
import org.tbot.methods.Widgets;
import org.tbot.methods.walking.Path;
import org.tbot.methods.walking.Walking;
import org.tbot.util.Condition;
import org.tbot.util.Filter;
import org.tbot.wrappers.*;
import util.Task;
import util.TrapLocations;
import util.Var;

import java.util.Arrays;

/**
 * Created by Russell on 2/9/2016.
 */
public class trapOnGround extends Task{

    private static final Filter<GameObject> actvTrap_filter = new Filter<GameObject>() {

        @Override
        public boolean accept(GameObject actvTrap) {
            if (actvTrap!=null && TrapLocations.getInstance().getTrapTiles().contains(actvTrap.getLocation()) && actvTrap.getID()== Var.getCurrentVar().getActvTrapID()){
                return true;
            }
            return false;
        }
    };

    private static final Filter<GameObject> placedTrap_filter = new Filter<GameObject>() {

        @Override
        public boolean accept(GameObject placedTrap) {
            if (placedTrap!=null && TrapLocations.getInstance().getTrapTiles().contains(placedTrap.getLocation()) && placedTrap.getID()== Var.getCurrentVar().getPlacedTrapID()){
                return true;
            }
            return false;
        }
    };

    private static final Filter<GroundItem> trapOnGround_filter = new Filter<GroundItem>() {

        @Override
        public boolean accept(GroundItem trap) {
            if (trap!=null && TrapLocations.getInstance().getTrapTiles().contains(trap.getLocation()) && trap.getID()== Var.getCurrentVar().getInvTrapID()){
                return true;
            }
            return false;
        }
    };

    private boolean cantPlaceTrap(){
        Widget chatBox = Widgets.getWidget(162);
        WidgetChild chatText= chatBox.getChild(43);
        WidgetChild toCheck = chatText.getChild(0);
        WidgetChild toCheck2 = chatText.getChild(2);
        WidgetChild toCheck4 = chatText.getChild(4);
        if(toCheck.getText().equalsIgnoreCase("You can't lay a trap here.") && toCheck2.getText().equalsIgnoreCase("You can't lay a trap here.") && toCheck4.getText().equalsIgnoreCase("You can't lay a trap here.")){
            return true;
        }
        return false;
    }

    @Override
    public boolean validate() {
        return  GroundItems.getNearest(trapOnGround_filter)!=null;
    }

    @Override
    public void execute() {
        final GroundItem tOnG = GroundItems.getNearest(trapOnGround_filter);
        if(tOnG!=null) {
            final Tile tOnGTile = tOnG.getLocation();
            if (tOnG.isOnScreen()) {
                if (checkTile(tOnGTile)) {
                    tOnG.interact("Lay");
                    if (cantPlaceTrap()) {
                        TrapLocations.getInstance().changeTrapLocation(tOnG.getLocation());
                    }
                    Time.sleepUntil(new Condition() {
                        @Override
                        public boolean check() {
                            return !Arrays.asList(GroundItems.getAt(tOnGTile)).contains(tOnG);
                        }
                    }, 3500);
                } else {
                    tOnG.pickUp();
                    Time.sleep(800, 1200);
                }
            } else {
                Path toTOnG = Walking.findLocalPath(tOnG);
                if (toTOnG != null) {
                    toTOnG.traverse();
                    Time.sleepUntil(new Condition() {
                        @Override
                        public boolean check() {
                            return Walking.getDestinationDistance() < 3 || tOnG.isOnScreen();
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
        return "Reseting trap";
    }

    private boolean checkTile(Tile tile){
        GameObject[] actvTraps = GameObjects.getLoaded(actvTrap_filter);
        GameObject[] placedTraps = GameObjects.getLoaded(placedTrap_filter);
        for(int i = 0; i<actvTraps.length; i++){
            if(actvTraps[i].getLocation().equals(tile)){
                return false;
            }
        }
        for(int i = 0; i<placedTraps.length; i++){
            if(placedTraps[i].getLocation().equals(tile)){
                return false;
            }
        }
        return true;
    }
}
