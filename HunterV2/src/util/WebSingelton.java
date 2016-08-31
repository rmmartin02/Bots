package util;

import org.tbot.methods.*;
import org.tbot.methods.walking.Path;
import org.tbot.methods.walking.Walking;
import org.tbot.methods.web.Web;
import org.tbot.util.Condition;
import org.tbot.wrappers.Tile;

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
        web.getData().add(new DesertWebArea());
    }

    public Web getWeb(){
        return web;
    }

    public void travel(Tile tile){
        Path toSpot = WebSingelton.getInstance().getWeb().findPath(tile);
        if(tile.isOnScreen()){
            tile.interact("Walk here");
            Time.sleepUntil(new Condition() {
                @Override
                public boolean check() {
                    return Players.getLocal().getLocation().equals(tile);
                }
            }, 3000);
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
