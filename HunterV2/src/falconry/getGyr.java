package falconry;

import org.tbot.methods.*;
import org.tbot.methods.tabs.Inventory;
import org.tbot.methods.walking.LocalPath;
import org.tbot.methods.walking.Walking;
import org.tbot.util.Condition;
import org.tbot.util.Filter;
import org.tbot.wrappers.NPC;
import util.Task;

/**
 * Created by Russell on 2/11/2016.
 */
public class getGyr extends Task {

    private final Filter<NPC> gyr_filter = new Filter<NPC>() {

        @Override
        public boolean accept(NPC gy) {
            if(gy!=null && Game.getHintArrow()!=null){
                if(gy.getName().equalsIgnoreCase("Gyr falcon") && gy.getLocation().equals(Game.getHintArrow().getLocation())){
                    return true;
                }
            }
            return false;
        }
    };

    @Override
    public boolean validate() {
        return Npcs.getNearest(gyr_filter)!=null;
    }

    @Override
    public void execute() {
        final NPC gyr = Npcs.getNearest(gyr_filter);
        if(gyr!=null) {
            if (gyr.isOnScreen()) {
                final int invCount = Inventory.getCount();
                gyr.interact("Retrieve");
                Time.sleepUntil(new Condition() {
                    @Override
                    public boolean check() {
                        return Inventory.getCount() > invCount;
                    }
                }, Random.nextInt(1200, 2000));
            } else {
                    LocalPath toGyr = Walking.findLocalPath(gyr.getLocation());
                    if (toGyr != null) {
                        toGyr.traverse();
                        Time.sleepUntil(new Condition() {
                            @Override
                            public boolean check() {
                                return Walking.getDestinationDistance() < 4 || gyr.isOnScreen();
                            }
                        }, 3500);
                    }
            }
        }
    }

    @Override
    public int priority() {
        return 12;
    }

    @Override
    public String getName() {
        return "Capturing gyr";
    }
}
