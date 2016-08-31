package fighting;

import org.tbot.methods.GameObjects;
import org.tbot.methods.Random;
import org.tbot.methods.Time;
import org.tbot.methods.tabs.Inventory;
import org.tbot.util.Condition;
import org.tbot.wrappers.GameObject;
import org.tbot.wrappers.Tile;
import util.Monster;
import util.Task;
import walking.WebSingelton;

/**
 * Created by Russell on 2/18/2016.
 */
public class FillWaterSkin extends Task {

    private int shantyPass = 1854;
    private int knife = 946;
    private int[] waterskins = {1823,1825,1827,1829,1831};
    private int emptySkin = 1831;
    private int cactusID = 2670;

    @Override
    public boolean validate() {
        return Monster.getCurrentMonster().isUsingWaterSkins() && (!Inventory.contains(1823) && !Inventory.contains(1825) && !Inventory.contains(1827) && Inventory.getCount(1829)<3);
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
        return 20;
    }

    @Override
    public String getName() {
        return "Filling waterskin";
    }
}
