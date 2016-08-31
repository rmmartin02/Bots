package salamanders;

import org.tbot.methods.GameObjects;
import org.tbot.methods.Players;
import org.tbot.methods.Time;
import org.tbot.methods.tabs.Inventory;
import org.tbot.methods.walking.LocalPath;
import org.tbot.methods.walking.Walking;
import org.tbot.util.Condition;
import org.tbot.util.Filter;
import org.tbot.wrappers.GameObject;
import org.tbot.wrappers.Tile;
import util.Task;
import util.TrapLocations;
import util.Var;

/**
 * Created by Russell on 2/11/2016.
 */
public class checkActivatedTree extends Task{

    private final Filter<GameObject> actvtrap_filter = new Filter<GameObject>() {

        @Override
        public boolean accept(GameObject aTrap) {
            if (aTrap != null && TrapLocations.getInstance().getTrapTiles().contains(aTrap.getLocation()) && aTrap.getID()== Var.getCurrentVar().getActvTrapID()){
                return true;
            }
            return false;
        }
    };

    @Override
    public boolean validate() {
        return GameObjects.getNearest(actvtrap_filter)!=null && Players.getLocal().getAnimation()==-1;
    }

    @Override
    public void execute() {
        final int invCount = Inventory.getCount();
        final GameObject activatedTrap = GameObjects.getNearest(actvtrap_filter);
        if(activatedTrap!=null){
            if(activatedTrap.isOnScreen()){
                activatedTrap.interact("Check");
                Time.sleepUntil(new Condition() {
                    @Override
                    public boolean check() {
                        return Inventory.getCount()>invCount;
                    }
                }, 3500);
            }
            else {
                LocalPath toactivatedTrap = Walking.findLocalPath(new Tile(activatedTrap.getLocation().getX() - 1, activatedTrap.getLocation().getY()));
                if (toactivatedTrap != null) {
                    toactivatedTrap.traverse();
                    Time.sleepUntil(new Condition() {
                        @Override
                        public boolean check() {
                            return Walking.getDestinationDistance() < 3 || activatedTrap.isOnScreen();
                        }
                    }, 3500);
                } else {
                    LocalPath toactivatedTrap1 = Walking.findLocalPath(new Tile(activatedTrap.getLocation().getX() + 1, activatedTrap.getLocation().getY()));
                    if (toactivatedTrap1 != null) {
                        toactivatedTrap1.traverse();
                        Time.sleepUntil(new Condition() {
                            @Override
                            public boolean check() {
                                return Walking.getDestinationDistance() < 3 || activatedTrap.isOnScreen();
                            }
                        }, 3500);
                    } else {
                        LocalPath toactivatedTrap2 = Walking.findLocalPath(new Tile(activatedTrap.getLocation().getX(), activatedTrap.getLocation().getY() + 1));
                        if (toactivatedTrap2 != null) {
                            toactivatedTrap2.traverse();
                            Time.sleepUntil(new Condition() {
                                @Override
                                public boolean check() {
                                    return Walking.getDestinationDistance() < 3 || activatedTrap.isOnScreen();
                                }
                            }, 3500);
                        } else {
                            LocalPath toactivatedTrap3 = Walking.findLocalPath(new Tile(activatedTrap.getLocation().getX(), activatedTrap.getLocation().getY() - 1));
                            if (toactivatedTrap3 != null) {
                                toactivatedTrap3.traverse();
                                Time.sleepUntil(new Condition() {
                                    @Override
                                    public boolean check() {
                                        return Walking.getDestinationDistance() < 3 || activatedTrap.isOnScreen();
                                    }
                                }, 3500);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public int priority() {
        if(GameObjects.getNearest(actvtrap_filter)!=null){
             if(GameObjects.getNearest(actvtrap_filter).distance()<3) {
                 return 8;
             }
        }
        return 4;
    }

    @Override
    public String getName() {
        return "Checking tree";
    }
}
