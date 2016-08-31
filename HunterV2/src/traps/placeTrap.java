package traps;

import org.tbot.methods.*;
import org.tbot.methods.tabs.Inventory;
import org.tbot.methods.walking.Path;
import org.tbot.methods.walking.Walking;
import org.tbot.util.Condition;
import org.tbot.util.Filter;
import org.tbot.wrappers.GameObject;
import org.tbot.wrappers.Tile;
import org.tbot.wrappers.Widget;
import org.tbot.wrappers.WidgetChild;
import util.Task;
import util.TrapLocations;
import util.Var;

import java.util.ArrayList;

/**
 * Created by Russell on 2/11/2016.
 */
public class placeTrap extends Task {

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

    private static final Filter<GameObject> trippedTrap_filter = new Filter<GameObject>() {

        @Override
        public boolean accept(GameObject trippedTrap) {
            if (trippedTrap!=null && TrapLocations.getInstance().getTrapTiles().contains(trippedTrap.getLocation()) && trippedTrap.getID()== Var.getCurrentVar().getTrippedTrapID()){
                return true;
            }
            return false;
        }
    };

    private Tile getEmptyTile(){
        GameObject[] activeTraps = GameObjects.getLoaded(actvTrap_filter);
        ArrayList<Tile> listOfEmptyTiles = new ArrayList();
        Tile closestEmptyTile = new Tile(0,0,0);
        int closestDist = 100;
        for(int i = 0; i<TrapLocations.getInstance().getTrapTiles().size(); i++){
            boolean noActive = true;
            boolean noPlaced = true;
            boolean noTripped = true;
            if(activeTraps!=null) {
                for (int c = 0; c < activeTraps.length; c++) {
                    if(TrapLocations.getInstance().getTrapTiles().get(i).getLocation().equals(activeTraps[c].getLocation())){
                        noActive = false;
                        break;
                    }
                }
            }
            GameObject[] placedTraps = GameObjects.getLoaded(placedTrap_filter);
            if(noActive && placedTraps!=null) {
                for (int c = 0; c < placedTraps.length; c++) {
                    if(TrapLocations.getInstance().getTrapTiles().get(i).getLocation().equals(placedTraps[c].getLocation())){
                        noPlaced = false;
                        break;
                    }
                }
            }
            GameObject[] trippedTraps = GameObjects.getLoaded(trippedTrap_filter);
            if(noActive && noPlaced && trippedTraps!=null) {
                for (int c = 0; c < trippedTraps.length; c++) {
                    if(TrapLocations.getInstance().getTrapTiles().get(i).getLocation().equals(trippedTraps[c].getLocation())){
                        noTripped = false;
                        break;
                    }
                }
            }
            if(noActive && noPlaced && noTripped){
                listOfEmptyTiles.add(TrapLocations.getInstance().getTrapTiles().get(i));
            }
        }
        if(listOfEmptyTiles.size()>0) {
            for (int i = 0; i < listOfEmptyTiles.size(); i++) {
                if (listOfEmptyTiles.get(i).distance(Players.getLocal().getLocation()) < closestDist) {
                    closestEmptyTile = listOfEmptyTiles.get(i);
                    closestDist = listOfEmptyTiles.get(i).distance(Players.getLocal().getLocation());
                }
            }
            return closestEmptyTile;
        }
        return null;
    }

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

    public GameObject checkGameObjectArrayForID(GameObject[] g, int id){
        if(g!=null) {
            for (int i = 0; i < g.length; i++) {
                if (g[i]!=null && g[i].getID() == id) {
                    return g[i];
                }
            }
        }
        return null;
    }

    @Override
    public boolean validate(){
        return getEmptyTile()!=null;
    }

    @Override
    public void execute() {
        final Tile closest = getEmptyTile();
        if(closest!=null) {
            if (Players.getLocal().getLocation().equals(closest) && !Walking.isMoving()) {
                Inventory.getFirst(Var.getCurrentVar().getInvTrapID()).interact("Lay");
                if (cantPlaceTrap()) {
                    TrapLocations.getInstance().changeTrapLocation(closest);
                }
                Time.sleepUntil(new Condition() {
                    @Override
                    public boolean check() {
                        return checkGameObjectArrayForID(GameObjects.getAt(closest), Var.getCurrentVar().getPlacedTrapID()) != null;
                    }
                }, 2000);
            } else {
                Path toSpot = Walking.findPath(closest);
                if(closest.isOnScreen()){
                    closest.interact("Walk here");
                    Time.sleepUntil(new Condition() {
                        @Override
                        public boolean check() {
                            return Players.getLocal().getLocation().equals(closest);
                        }
                    }, Random.nextInt(1000, 1500));
                }
                else if(toSpot!=null){
                    toSpot.traverse();
                    Time.sleepUntil(new Condition() {
                        @Override
                        public boolean check() {
                            return Players.getLocal().getLocation().equals(closest);
                        }
                    }, Random.nextInt(1000, 1500));
                }
            }
        }
    }

    @Override
    public int priority() {
        return 3;
    }

    @Override
    public String getName() {
        return "Placing trap";
    }
}
