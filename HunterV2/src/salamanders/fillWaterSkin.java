package salamanders;

import org.tbot.methods.GameObjects;
import org.tbot.methods.Players;
import org.tbot.methods.Random;
import org.tbot.methods.Time;
import org.tbot.methods.tabs.Inventory;
import org.tbot.util.Condition;
import org.tbot.util.Filter;
import org.tbot.wrappers.GameObject;
import org.tbot.wrappers.Tile;
import util.Task;
import util.TrapLocations;
import util.Var;
import util.WebSingelton;

import java.util.Arrays;

/**
 * Created by Russell on 2/18/2016.
 */
public class fillWaterSkin extends Task {

    private int shantyPass = 1854;
    private int knife = 946;
    private int[] waterskins = {1823,1825,1827,1829};
    private int emptySkin = 1831;
    private int cactusID = 2670;

    private static final Filter<GameObject> cactus_filter = new Filter<GameObject>() {

        @Override
        public boolean accept(GameObject cactus) {
            if (cactus!=null && cactus.getID()==2670){
                return true;
            }
            return false;
        }
    };

    @Override
    public boolean validate() {
        return Var.getCurrentVar().equals(Var.ORANGE_SAL) && (!Inventory.contains(1823) && !Inventory.contains(1825) && !Inventory.contains(1827) && !Inventory.contains(1829)) && Players.getLocal().getAnimation()==-1;
    }

    @Override
    public void execute() {
        if(Inventory.contains(946)) {
            GameObject cactus = GameObjects.getNearest(2670);
            if (cactus != null) {
                if (cactus.isOnScreen()) {
                    cactus.interact("Cut");
                    Time.sleep(600, 1200);
                } else {
                    WebSingelton.getInstance().travel(cactus.getLocation());
                }
            }
        }
        else{
            TrapLocations.getInstance().pickUpSalTraps();
            GameObject fountain = GameObjects.getNearest("Fountain");
            if(fountain!=null && fountain.isOnScreen()){
                if(!Inventory.hasItemSelected()){
                    Inventory.getFirst(waterskins).interact("Use");
                    Time.sleep(800,1200);
                }
                if(Inventory.hasItemSelected()) {
                    fountain.interact("Use");
                    Time.sleepUntil(new Condition() {
                        @Override
                        public boolean check() {
                            return !Inventory.containsOneOf(1831,1829,1827,1825);
                        }
                    }, Random.nextInt(3000,5000));
                }
            }
            else{
                WebSingelton.getInstance().travel(new Tile(3366,2971,0));
            }
        }
    }

    @Override
    public int priority() {
        return 11;
    }

    @Override
    public String getName() {
        return "Filling waterskin";
    }
}
