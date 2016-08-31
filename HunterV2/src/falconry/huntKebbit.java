package falconry;

import org.tbot.methods.Npcs;
import org.tbot.methods.Players;
import org.tbot.methods.Time;
import org.tbot.methods.walking.LocalPath;
import org.tbot.methods.walking.Walking;
import org.tbot.util.Condition;
import org.tbot.util.Filter;
import org.tbot.wrappers.Locatable;
import org.tbot.wrappers.NPC;
import org.tbot.wrappers.Player;
import util.Task;
import util.Var;

/**
 * Created by Russell on 2/11/2016.
 */
public class huntKebbit extends Task {

    private final Filter<Player> player_filter = new Filter<Player>() {

        @Override
        public boolean accept(Player p) {
            if (p!=null && !p.equals(Players.getLocal())){
                return true;
            }
            return false;
        }
    };

    private final Filter<NPC> kebbit_filter = new Filter<NPC>() {

        @Override
        public boolean accept(NPC ke) {
            if (ke != null && ke.getName().equalsIgnoreCase(Var.getCurrentVar().getHunting()) && Walking.canReach(ke.getLocation()) && otherPlayerWithin(ke)>8){
                return true;
            }
            return false;
        }
    };

    @Override
    public boolean validate() {
        return Npcs.getNearest(kebbit_filter)!=null;
    }

    @Override
    public void execute() {
        final NPC kebbit = Npcs.getNearest(kebbit_filter);
        if(kebbit!=null && Players.getLocal().getAppearanceEquipment()[3]==10024) {
            if (kebbit.isOnScreen()) {
                kebbit.interact("Catch");
                Time.sleep(400, 750);
            } else {
                    LocalPath toKebbit = Walking.findLocalPath(kebbit.getLocation());
                    if (toKebbit != null) {
                        toKebbit.traverse();
                        Time.sleepUntil(new Condition() {
                            @Override
                            public boolean check() {
                                return Walking.getDestinationDistance() < 3 || kebbit.isOnScreen();
                            }
                        }, 3500);
                    }
            }
        }
    }

    @Override
    public int priority() {
        return 6;
    }

    @Override
    public String getName() {
        return "Hunting Kebbits";
    }

    private int otherPlayerWithin(Locatable loc){
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
