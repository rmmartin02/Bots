package util;

/**
 * Created by Russell on 2/10/2016.
 */

import org.tbot.methods.Skills;
import org.tbot.wrappers.Tile;

/**
 * Created by Russell on 2/10/2016.
 */
public enum Var{

    //Birds
    CRIMSON_SWIFT(0,"Crimson swift",10006,9345,9344,9373,34,10088, new Tile(2608,2923,0)),
    COPPER_LONGTAIL (0, "Copper longtail",10006,9345,9344,9379,61,10091, new Tile(2354,3585,0)),
    CERULEAN_TWITCH (0, "Cerulean twitch",10006,9345,9344,9375,64,10089, new Tile(0,0,0)),
    TROPICAL_WAGTAIL (0, "Tropical wagtail",10006,9345,9344,9348,95,10087, new Tile(2526,2937,0)),

    //chins
    GREY_CHINS (0,"Grey chinchompa",10008,9380,9385,9382,198.25,10033, new Tile(0,0,0)),
    RED_CHINS (0,"Red chinchompa",10008,9380,9385,9383,265,10034, new Tile(0,0,0)),
    // public static final Var BLACK_CHINS (0,"Black chinchompa",0,0,0,0,0,0, new Tile(0,0,0)),

    //salamanders
    GREEN_SAL (2, "Green salamanders", 0 , 9257, 9341, 9004, 152, 10149, new Tile(3537,3448,0)),
    ORANGE_SAL (2, "Orange salamanders", 0 , 8731, 8732, 8734, 224, 10146, new Tile(3411,3076,0)),
    RED_SAL (2, "Red salamanders", 0 , 8989, 8990, 8986, 272, 10147, new Tile(0,0,0)),
    BLACK_SAL (2, "Black salamanders", 0 , 8999, 9000, 8996, 319.2, 10148, new Tile(0,0,0)),

    //falconry
    SPOTTED_KEBBIT (1, "Spotted kebbit", 0,0,0,1342,104,10125, new Tile(0,0,0)),
    DARK_KEBBIT (1, "Dark kebbit", 0,0,0,1344,132,10115, new Tile(0,0,0)),
    DASHING_KEBBIT (1, "Dashing kebbit", 0,0,0,1342,156,10127, new Tile(0,0,0));

    Var(int huntType, String hunting, int invTrapID,int placedTrapID, int trippedTrapID, int actvTrapID, double expPer, int thingID, Tile location){
        this.huntType = huntType;
        this.hunting = hunting;
        this.invTrapID = invTrapID;
        this.placedTrapID = placedTrapID;
        this.trippedTrapID = trippedTrapID;
        this.actvTrapID = actvTrapID;
        this.expPer = expPer;
        this.thingID = thingID;
        this.location = location;
    }
    
    private int huntType;
    private String hunting;
    private int invTrapID;
    private int placedTrapID;
    private  int trippedTrapID;
    private  int actvTrapID;
    private  double expPer;
    private  int thingID;
    private Tile location;
    private static Var currentVar;

    public int getHuntType(){
        return huntType;
    }

    public String getHunting(){
        return hunting;
    }

    public int getInvTrapID(){
        return invTrapID;
    }

    public int getPlacedTrapID(){
        return placedTrapID;
    }

    public int getTrippedTrapID(){
        return trippedTrapID;
    }

    public int getActvTrapID(){
        return actvTrapID;
    }

    public double getExpPer(){
        return expPer;
    }

    public int getThingID(){
        return thingID;
    }

    public Tile getLocation() {return location;}

    public static void setCurrentVar(Var v){
        currentVar = v;
    }

    public static Var getCurrentVar(){
        return currentVar;
    }

    public static int numTraps(){
        int hntlvl = Skills.getCurrentLevel(Skills.Skill.HUNTER);
        if (hntlvl >= 80) {
            return 5;
        }
        if (hntlvl >= 60 && hntlvl < 80) {
            return 4;
        }
        if (hntlvl >= 40 && hntlvl < 60) {
            return 3;
        }
        if (hntlvl < 40 && hntlvl >= 20) {
            return 2;
        }
        return 1;
    }

}

