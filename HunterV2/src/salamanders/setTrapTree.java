package salamanders;

import org.tbot.methods.*;
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

import java.util.Arrays;

/**
 * Created by Russell on 2/11/2016.
 */
public class setTrapTree extends Task {

    private static final Filter<GameObject> trippedTrap_filter = new Filter<GameObject>() {

        @Override
        public boolean accept(GameObject trippedTrap) {
            if (trippedTrap!=null && TrapLocations.getInstance().getTrapTiles().contains(trippedTrap.getLocation()) && trippedTrap.getID()== Var.getCurrentVar().getTrippedTrapID() && Arrays.asList(trippedTrap.getActions()).contains("Set-trap")){
                return true;
            }
            return false;
        }
    };

    @Override
    public boolean validate() {
        return GameObjects.getNearest(trippedTrap_filter)!=null && Inventory.contains("Rope") && Inventory.contains("Small fishing net") && Players.getLocal().getAnimation()==-1;
    }

    @Override
    public void execute() {
        final int invCount = Inventory.getCount();
        final GameObject tree = GameObjects.getNearest(trippedTrap_filter);
        if(tree!=null){
            if (tree.isOnScreen()) {
                tree.interact("Set-trap");
                Time.sleepUntil(new Condition() {
                    @Override
                    public boolean check() {
                        return Inventory.getCount()<invCount;
                    }
                }, Random.nextInt(800,2000));
            } else {
                Camera.turnTo(tree);
                LocalPath toTree = Walking.findLocalPath(new Tile(tree.getLocation().getX() - 1, tree.getLocation().getY()));
                if (toTree != null) {
                    toTree.traverse();
                    Time.sleepUntil(new Condition() {
                        @Override
                        public boolean check() {
                            return Walking.getDestinationDistance() < 3 || tree.isOnScreen();
                        }
                    }, 3500);
                } else {
                    LocalPath toTree1 = Walking.findLocalPath(new Tile(tree.getLocation().getX() + 1, tree.getLocation().getY()));
                    if (toTree1 != null) {
                        toTree1.traverse();
                        Time.sleepUntil(new Condition() {
                            @Override
                            public boolean check() {
                                return Walking.getDestinationDistance() < 3 || tree.isOnScreen();
                            }
                        }, 3500);
                    } else {
                        LocalPath toTree2 = Walking.findLocalPath(new Tile(tree.getLocation().getX(), tree.getLocation().getY() + 1));
                        if (toTree2 != null) {
                            toTree2.traverse();
                            Time.sleepUntil(new Condition() {
                                @Override
                                public boolean check() {
                                    return Walking.getDestinationDistance() < 3 || tree.isOnScreen();
                                }
                            }, 3500);
                        } else {
                            LocalPath toTree3 = Walking.findLocalPath(new Tile(tree.getLocation().getX(), tree.getLocation().getY() - 1));
                            if (toTree3 != null) {
                                toTree3.traverse();
                                Time.sleepUntil(new Condition() {
                                    @Override
                                    public boolean check() {
                                        return Walking.getDestinationDistance() < 3 || tree.isOnScreen();
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
        GameObject tripped = GameObjects.getNearest(trippedTrap_filter);
        if(tripped!=null){
            if(tripped.distance()<3) {
                return 7;
            }
        }
        return 2;
    }

    @Override
    public String getName() {
        return "Setting up tree";
    }
}
