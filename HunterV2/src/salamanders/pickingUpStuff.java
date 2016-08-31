package salamanders;

import org.tbot.methods.GroundItems;
import org.tbot.methods.Players;
import org.tbot.methods.Time;
import org.tbot.methods.tabs.Inventory;
import org.tbot.methods.walking.LocalPath;
import org.tbot.methods.walking.Walking;
import org.tbot.util.Filter;
import org.tbot.wrappers.GroundItem;
import org.tbot.wrappers.Locatable;
import util.Task;
import util.TrapLocations;

/**
 * Created by Russell on 2/11/2016.
 */
public class pickingUpStuff extends Task{

    private static final Filter<GroundItem> ropeOnGround_filter = new Filter<GroundItem>() {

        @Override
        public boolean accept(GroundItem rOnG) {
            if (rOnG != null && locatableIsWithinOfTrapTile(rOnG, 2) && rOnG.getName().equalsIgnoreCase("Rope")){
                return true;
            }
            return false;
        }
    };
    private static final Filter<GroundItem> netOnGround_filter = new Filter<GroundItem>() {

        @Override
        public boolean accept(GroundItem nOnG) {
            if (nOnG != null && locatableIsWithinOfTrapTile(nOnG, 2) && nOnG.getName().equalsIgnoreCase("Small fishing net")){
                return true;
            }
            return false;
        }
    };

    private static boolean locatableIsWithinOfTrapTile(Locatable loc, int dist){
        for(int i = 0; i<TrapLocations.getInstance().getTrapTiles().size(); i++){
            if(loc.distance(TrapLocations.getInstance().getTrapTiles().get(i))<dist){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean validate() {
        return GroundItems.getNearest(ropeOnGround_filter)!=null || GroundItems.getNearest(netOnGround_filter)!=null && Players.getLocal().getAnimation()==-1;
    }

    @Override
    public void execute() {
        final int invCount = Inventory.getCount();
        final  GroundItem rope = GroundItems.getNearest(ropeOnGround_filter);
        final GroundItem net = GroundItems.getNearest(netOnGround_filter);
        if(rope!=null && net!=null){
            if(rope.distance()<net.distance()){
                if(rope.isOnScreen()){
                    rope.pickUp();
                    Time.sleep(800,1200);
                }
                else{
                    LocalPath toRope = Walking.findLocalPath(rope);
                    if(toRope!=null){
                        toRope.traverse();
                        Time.sleep(800,1200);
                    }
                }
            }
            else{
                if(net.isOnScreen()){
                    net.pickUp();
                    Time.sleep(800,1200);
                }
                else{
                    LocalPath toNet = Walking.findLocalPath(net);
                    if(toNet!=null){
                        toNet.traverse();
                        Time.sleep(800,1200);
                    }
                }
            }
        }
        else if(rope!=null && net==null){
            if(rope.isOnScreen()){
                rope.pickUp();
                Time.sleep(800,1200);
            }
            else{
                LocalPath toRope = Walking.findLocalPath(rope);
                if(toRope!=null){
                    toRope.traverse();
                    Time.sleep(800,1200);
                }
            }
        }
        else if(rope==null && net!=null){
            if(net.isOnScreen()){
                net.pickUp();
                Time.sleep(800,1200);
            }
            else{
                LocalPath toNet = Walking.findLocalPath(net);
                if(toNet!=null){
                    toNet.traverse();
                    Time.sleep(800,1200);
                }
            }
        }
    }


    @Override
    public int priority() {
        if(GroundItems.getNearest(ropeOnGround_filter)!=null) {
            if (GroundItems.getNearest(ropeOnGround_filter).distance() < 3) {
                return 6;
            }
        }
        if(GroundItems.getNearest(netOnGround_filter)!=null) {
            if (GroundItems.getNearest(netOnGround_filter).distance() < 3) {
                return 6;
            }
        }
        return 1;
        }

    @Override
    public String getName() {
        return "Picking up net/rope";
    }
}
