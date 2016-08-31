package falconry;

import org.tbot.methods.Players;
import org.tbot.methods.Time;
import org.tbot.methods.walking.LocalPath;
import org.tbot.methods.walking.Walking;
import org.tbot.util.Condition;
import org.tbot.wrappers.Tile;
import util.Task;

/**
 * Created by Russell on 2/11/2016.
 */
public class nothing extends Task {
    @Override
    public boolean validate() {
        return true;
    }

    @Override
    public void execute() {
        Tile defaultSpot = new Tile(2378, 3584, 0);
        LocalPath toDefaultSpot = Walking.findLocalPath(defaultSpot);
        if (toDefaultSpot != null) {
            toDefaultSpot.traverse();
            Time.sleepUntil(new Condition() {
                @Override
                public boolean check() {
                    return Walking.getDestinationDistance()<3;
                }
            }, 3500);
        }
    }

    @Override
    public int priority() {
        return 1;
    }

    @Override
    public String getName() {
        return "Going to default spot";
    }
}
