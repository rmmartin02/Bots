package falconry;

import org.tbot.methods.*;
import org.tbot.methods.input.keyboard.*;
import org.tbot.methods.walking.LocalPath;
import org.tbot.methods.walking.Walking;
import org.tbot.wrappers.*;
import util.Task;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Created by Russell on 2/11/2016.
 */
public class noFalcon extends Task {
    @Override
    public boolean validate() {
        if(Players.getLocal().getAppearanceEquipment()[3]!=10024 && Projectiles.getNearest(922)==null){
            return true;
        }
        return false;
    }

    @Override
    public void execute() {
        Time.sleep(1000);
        while(Players.getLocal().getAppearanceEquipment()[3]!=10024) {
            final NPC mat = Npcs.getNearest("Matthias");
            if(NPCChat.isChatOpen()) {
                if (NPCChat.canContinue()) {
                    NPCChat.clickContinue();
                    Time.sleep(Random.nextInt(500, 1000));
                }
                else {
                    if (Arrays.asList(NPCChat.getOptions()).contains("Yes, please.")) {
                        NPCChat.selectOption("Yes, please.");
                        Time.sleep(Random.nextInt(500, 1000));
                    }
                    if (Arrays.asList(NPCChat.getOptions()).contains("Could I have a go with your bird?")) {
                        NPCChat.selectOption("Could I have a go with your bird?");
                        Time.sleep(Random.nextInt(500, 1000));
                    }
                    if (Arrays.asList(NPCChat.getOptions()).contains("Ok, that seems reasonable.")) {
                        NPCChat.selectOption("Ok, that seems reasonable.");
                        Time.sleep(Random.nextInt(500, 1000));
                    }
                }
            }
            else if (mat == null && !NPCChat.isChatOpen()) {
                LocalPath toMatDefault = Walking.findLocalPath(new Tile(2374, 3605, 0));
                if (toMatDefault != null) {
                    toMatDefault.traverse();
                    Time.sleep(Random.nextInt(500, 1000));
                }
            } else if (!mat.isOnScreen() && !NPCChat.isChatOpen()) {
                LocalPath toMat = Walking.findLocalPath(mat.getLocation());
                if (toMat != null) {
                    toMat.traverse();
                    Time.sleep(Random.nextInt(500, 1000));
                }
            } else if (mat.isOnScreen() && !NPCChat.isChatOpen()) {
                    mat.interact("Talk-to");
                    Time.sleep(Random.nextInt(500, 1000));
                }
            }
        }

    @Override
    public int priority() {
        return 2;
    }

    @Override
    public String getName() {
        return "Getting another falcon";
    }
}
