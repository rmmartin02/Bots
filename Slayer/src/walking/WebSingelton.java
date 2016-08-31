package walking;

import org.tbot.methods.*;
import org.tbot.methods.tabs.Inventory;
import org.tbot.methods.walking.Path;
import org.tbot.methods.walking.Walking;
import org.tbot.methods.web.Web;
import org.tbot.methods.web.banks.WebBanks;
import org.tbot.util.Condition;
import org.tbot.wrappers.GameObject;
import org.tbot.wrappers.Tile;
import util.GUI;
import util.Monster;

/**
 * Created by Russell on 2/21/2016.
 */
public class WebSingelton {

    private Web web;
    private static WebSingelton ourInstance = new WebSingelton();

    public static WebSingelton getInstance() {
        if(ourInstance==null){
            ourInstance = new WebSingelton();
        }
        return ourInstance;
    }

    private WebSingelton() {
        web = new Web();
        web.getData().add(new LumbridgeSwampCaveWebArea(), new StrongholdCaveWebArea(), new DesertWebArea(),
                new ZanarisWebArea(), new FremSlayerCaveWebArea(), new SlayerTowerWebArea());
    }

    public Web getWeb(){
        return web;
    }

    public void openBank(){
        if(Players.getLocal().getLocation().getY()<3116 && WebBanks.getNearest().equals(WebBanks.SHANTAY_PASS_BANK)){
            travel(WebBanks.SHANTAY_PASS_BANK.getLocation());
        }
        else {
            web.openNearestBank();
            Time.sleep(800,1200);
            /*Time.sleepUntil(new Condition() {
                @Override
                public boolean check() {
                    return Walking.getDestinationDistance() <= Random.nextInt(2, 5) || Bank.isOpen();
                }
            }, 3000);*/
        }
    }

    public void travel(Tile tile){
        Path toSpot = web.findPath(tile);
        if(Bank.isOpen()){
            Bank.close();
            Time.sleep(600,1200);
        }
        if((Players.getLocal().getLocation().getY()<3116 && tile.getY()>3116) || (Players.getLocal().getLocation().getY()>3116 && tile.getY()<3116)){
            GameObject pass = GameObjects.getNearest("Shantay pass");
            if(pass!=null && pass.isOnScreen()){
                pass.interact("Go-through");
                Time.sleep(800,1200);
            }
            else if (Players.getLocal().getLocation().getY()>3116 && tile.getY()<3116){
                travel(new Tile(3304,3117,0));
            }
            else if(Players.getLocal().getLocation().getY()<3116 && tile.getY()>3116){
                travel(new Tile(3304,3115,0));
            }
        }
        else if(toSpot!=null){
            toSpot.traverse();
            Time.sleepUntil(new Condition() {
                @Override
                public boolean check() {
                    return Walking.getDestinationDistance()<=Random.nextInt(2,5);
                }
            }, 3000);
        }
    }

}
