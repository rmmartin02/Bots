package walking;

import org.tbot.methods.Bank;
import org.tbot.methods.Npcs;
import org.tbot.methods.Players;
import org.tbot.methods.Time;
import org.tbot.methods.walking.Walking;
import org.tbot.methods.web.Web;
import org.tbot.util.Filter;
import org.tbot.wrappers.NPC;
import util.Monster;
import util.Task;

import java.util.Arrays;

/**
 * Created by Russell on 2/17/2016.
 */
public class WalkTo extends Task {

    private static final Filter<NPC> monster_filter = new Filter<NPC>() {

        @Override
        public boolean accept(NPC mon) {
            if (Arrays.asList(Monster.getCurrentMonster().getNames()).contains(mon.getName()) && Walking.canReach(mon.getLocation())){
                return true;
            }
            return false;
        }
    };
    Web web = new Web();

    @Override
    public boolean validate() {
        if(Npcs.getNearest(monster_filter)==null){
            return true;
        }
        if(Players.getLocal().getInteractingEntity()!=null){
            if(!Arrays.asList(Monster.getCurrentMonster().getNames()).contains(Players.getLocal().getInteractingEntity().getName())){
                return true;
            }
        }
        return false;
    }

    @Override
    public void execute() {
        WebSingelton.getInstance().travel(Monster.getCurrentMonster().getLocation());
    }

    @Override
    public int priority() {
        return 10;
    }

    @Override
    public String getName() {
        return "Walking there";
    }
}
