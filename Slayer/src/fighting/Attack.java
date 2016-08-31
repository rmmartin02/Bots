package fighting;

import org.tbot.methods.Npcs;
import org.tbot.methods.Players;
import org.tbot.methods.Time;
import org.tbot.methods.tabs.Inventory;
import org.tbot.methods.walking.Walking;
import org.tbot.util.Condition;
import org.tbot.util.Filter;
import org.tbot.wrappers.Locatable;
import org.tbot.wrappers.NPC;
import org.tbot.wrappers.Player;
import util.Monster;
import util.Task;
import walking.WebSingelton;

import java.util.Arrays;

/**
 * Created by Russell on 2/17/2016.
 */
public class Attack extends Task {

    private static final Filter<NPC> monster_filter = new Filter<NPC>() {

        @Override
        public boolean accept(NPC npc) {

            if (npc == null) {
                return false;
            }

            if (!Arrays.asList(Monster.getCurrentMonster().getNames()).contains(npc.getName())) {
                return false;
            }

            if (npc.getInteractingEntity() != null && !npc.getInteractingEntity().equals(Players.getLocal())) {
                return false;
            }
            if(npc.isHealthBarVisible()){
                return false;
            }
            if(otherPlayerWithin(npc)<=4){
                return false;
            }
            return true;
        }

    };

    @Override
    public boolean validate() {
        NPC mon = Npcs.getNearest(monster_filter);
        return !Players.getLocal().isHealthBarVisible() && mon!=null;
    }

    @Override
    public void execute() {
//fight
        NPC monster = Npcs.getNearest(monster_filter);
        if(monster!=null){
            if(monster.isOnScreen()){
                monster.interact("Attack");
                Time.sleepUntil(new Condition() {
                    @Override
                    public boolean check() {
                        return Players.getLocal().isHealthBarVisible();
                    }
                }, 3500);
            }
            else{
                WebSingelton.getInstance().travel(monster.getLocation());
            }
        }
    }

    @Override
    public int priority() {
        return 5;
    }

    @Override
    public String getName() {
        return "Attack";
    }

    private static final Filter<Player> player_filter = new Filter<Player>() {

        @Override
        public boolean accept(Player p) {
            if (p!=null && !p.equals(Players.getLocal())){
                return true;
            }
            return false;
        }
    };

    private static int otherPlayerWithin(Locatable loc){
        Player[] players =  Players.getLoaded(player_filter);
        int closest = 1000;
        for(int i = 0; i<players.length; i++){
            if(players[i].distance(loc)<closest){
                closest=players[i].distance(loc);
            }
        }
        return closest;
    }
}
