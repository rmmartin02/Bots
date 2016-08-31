package util;

import org.tbot.methods.Players;
import org.tbot.methods.Widgets;
import org.tbot.wrappers.Tile;

/**
 * Created by Russell on 2/27/2016.
 */
public enum SlayerMaster {

    TURAEL ("Turael", 0, "", new Tile(2930, 3536,0)),
    MAZCHNA ("Mazchna", 20, "Priest in Peril", new Tile(3511,3509,0)),
    VANNAKA ("Vannaka", 40, "", new Tile(3142,9915,0)),
    CHAELDAR ("Chaeldar", 70, "Lost City", new Tile(2445,4433, 0));

   SlayerMaster(String nme, int lvlRq, String qstReq, Tile loc){
        this.name = nme;
        this.lvlReq = lvlRq;
        this.questReq = qstReq;
        this.location = loc;
    }

    private String name;
    private int lvlReq;
    private String questReq;
    private Tile location;

    public String getName(){
        return name;
    }

    public Tile getLocation(){
        return location;
    }

    private static int getCombatLvl(){
        String txt = Widgets.getWidget(593,2).getText();
        return Integer.parseInt(txt.substring(txt.indexOf(":")+2));
    }

    private static boolean isQuestComplete(String quest){
        return Widgets.getWidget(274).getChildWithText(quest).getTextColor()==65280;
    }


    public static SlayerMaster getSlayerMaster() {
        int combatLvl = Players.getLocal().getCombatLevel();
        if (combatLvl < 20) {
            return SlayerMaster.TURAEL;
        }
        if (combatLvl < 40 && Widgets.getWidget(274, 101).getTextColor() == 65280) {
            return SlayerMaster.MAZCHNA;
        }
        if (combatLvl < 70) {
            return SlayerMaster.VANNAKA;
        }
        if (combatLvl > 70 && Widgets.getWidget(274, 85).getTextColor() == 65280) {
            return SlayerMaster.CHAELDAR;
        }
        return null;
    }

}
