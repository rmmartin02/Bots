package fighting;

import org.tbot.methods.Npcs;
import org.tbot.methods.Random;
import org.tbot.methods.Time;
import org.tbot.methods.tabs.Inventory;
import org.tbot.util.Condition;
import org.tbot.util.Filter;
import org.tbot.wrappers.NPC;
import util.Monster;
import util.Task;

import java.util.Arrays;

/**
 * Created by Russell on 2/18/2016.
 */
public class UseItemOnDeath extends Task {

    private static final Filter<NPC> monster_filter = new Filter<NPC>() {

        @Override
        public boolean accept(NPC mon) {
            if (Arrays.asList(Monster.getCurrentMonster().getNames()).contains(mon.getName()) && mon.isInteractingWithLocalPlayer() && mon.isHealthBarVisible() && mon.getCurrentHealth() <= 4){
                return true;
            }
            return false;
        }
    };

    @Override
    public boolean validate() {
        if(Npcs.getNearest(monster_filter)!=null) {
            return Monster.getCurrentMonster().getItemUseOnDeath() != -1;
        }
        return false;
    }

    @Override
    public void execute() {
        final NPC monster = Npcs.getNearest(monster_filter);
        if(!Inventory.hasItemSelected()){
            Inventory.getFirst(Monster.getCurrentMonster().getItemUseOnDeath()).interact("Use");
            Time.sleepUntil(new Condition() {
                @Override
                public boolean check() {
                    return Inventory.hasItemSelected();
                }
            }, 1000);
        }
        if(Inventory.hasItemSelected()) {
            monster.interact("Use");
            Time.sleepUntil(new Condition() {
                @Override
                public boolean check() {
                    return monster==null;
                }
            }, Random.nextInt(1200,2000));
        }
    }

    @Override
    public int priority() {
        return 24;
    }

    @Override
    public String getName() {
        return "Kill with item";
    }
}