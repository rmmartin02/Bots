package util;

import banking.*;
import fighting.Attack;
import fighting.Eat;
import fighting.FillWaterSkin;
import fighting.UseItemOnDeath;
import org.tbot.wrappers.Tile;
import walking.WalkTo;

/**
 * Created by Russell on 2/19/2016.
 */
public enum Monster {

    //Monster(String tskNme, String nme, Tile loc, int itmUseOnDth, boolean useWtrSkn,boolean useAntiPsn, int[] eqpmntIDs,  Controller c)

    //Turael
    //bats
    //kalphites 3763,5853 X
    //monkeys

    //Mazchna
    //public static final Monster SHADE = new Monster("Shade", new Tile(0,0,0), new int[]{}, new int[]{}, new Controller(new Attack(), new Eat(), new WalkTo(), new FoodBanking()));
    //wall beast 3162,9573,0
    //hobgoblin
    //ice warrior
    //ghoul
    //hill giant
    //vampire
//
    //Vannaka
    //abyssal demon 3417,3565,2 or stronghold 2404,9741,0 X
    //ankou 2374,9744,0 X
    //gargoyle 3440,3540,2
    //nechryael 3441,3565,2
    //public static final Monster DUST_DEVIL = new Monster("Dust devil", new Tile(0,0,0), new int[]{}, new int[]{facemask}, new Controller(new Attack(), new Eat(), new WalkTo(), new FoodBanking()));
    //public static final Monster HARPIE_BUG_SWARM = new Monster("Harpie bug swarm", new Tile(0,0,0), new int[]{}, new int[]{litBugLantern}, new Controller(new Attack(), new Eat(), new WalkTo(), new FoodBanking()));
    //public static final Monster JUNGLE_HORROR = new Monster("Jungle horror", new Tile(0,0,0), new int[]{}, new int[]{}, new Controller(new Attack(), new Eat(), new WalkTo(), new FoodBanking()));
    //public static final Monster OTHERWORLDY_BEING = new Monster("Otherwordly being", new Tile(0,0,0), new int[]{}, new int[]{}, new Controller(new Attack(), new Eat(), new WalkTo(), new FoodBanking()));
    //public static final Monster SHADOW_WARRIOR = new Monster("Shadow warrior", new Tile(0,0,0), new int[]{}, new int[]{}, new Controller(new Attack(), new Eat(), new WalkTo(), new FoodBanking()));
    //kurask 2706,9993,0
    //hellhound 2405,9786 X
    //fire giant 2351,9771,0 X
    //ghoul
    //dagannoth
    //ice warrior
    //ice giant

    //smoke devil 3765,5772,0
    //greater demon 2364,9767,0 X

    //enums

    ABERRANT_SPECTRE ("aberrant spectres", new String[] {"Aberrant spectre"}, new Tile(3423,3551,1), -1, false, false, false, new int[] {4168}),
    ABYSSAL_DEMON("abyssal demons", new String[] {"Abyssal demon"}, new Tile(3417,3565,2), -1, false, false, false, new int[] {}),
    ANKOU ("ankou",new String[] {"Ankou"},new Tile(2374,9744,0), -1,false, false, false, new int[] {}),
    BASILISK ("basilisks", new String[] {"Basilisk"}, new Tile(2722,10024,0), -1, false, false, false, new int[] {4156}),
    BANSHEE ("banshees", new String[] {"Banshee"}, new Tile(3438,3561,0), -1, false, false, false, new int[]{4166}),
    BEAR ("bears", new String[] {"Black bear"}, new Tile(2972,3477,0),-1,false,false,false,new int[]{}),
    BIRDS ("birds", new String[] {"Chicken"}, new Tile(3230,3297,0),-1,false,false,false,new int[]{}),
    BLOODVELD ("bloodvelds", new String[] {"Bloodveld"}, new Tile(3421,3573,1), -1, false, false, false, new int[]{}),
    CATABLEPON ("cataplepons", new String[]{"Catablepon"}, new Tile(2163,5299,0), -1, false, false, false, new int[] {}),
    COCKATRICE ("cockatrices", new String[]{"Cockatrice"}, new Tile(2795,10033,0), -1, false, false, false, new int[] {4156}),
    DWARF ("dwarves", new String[] {"Dwarf"}, new Tile(3018,3432,0),-1,false,false,false,new int[]{}),
    ROCKSLUG ("rockslugs", new String[]{"Rockslug"}, new Tile(2792,10018,0), 4161, false, true, false, new int[] {}),
    CAVE_BUG ("cave bugs", new String[]{"Cave bug"}, new Tile(3150,9574,0), -1, false, false, true, new int[] {}),
    CAVE_CRAWLER ("cave crawlers", new String[] {"Cave crawler"}, new Tile(3190,9576,0), -1, false, true, true, new int[] {}),
    CAVE_SLIME ("cave slimes", new String[] {"Cave slime"}, new Tile(3164,9588,0), -1, false, true, true, new int[] {}),
    COW ("cows", new String[] {"Cow","Cow calf"}, new Tile(3259,3267,0), -1, false, false, false, new int[] {}),
    CRAWLING_HAND ("crawling hands", new String[] {"Crawling Hand"}, new Tile(3419,3545,0), -1, false, false, false, new int[]{}),
    DESERT_LIZARD ("desert lizards", new String[]{"Lizard","Desert Lizard", "Small Lizard"}, new Tile(3386,3073,0), 6696, true, false, false, new int[]{}),
    DOG ("dogs", new String[]{"Jackal"}, new Tile(3423,3017,0), -1,true,false, false, new int[]{}),
    FIRE_GIANT("fire giants", new String[] {"Fire giant"}, new Tile(2351,9771,0), -1, false, false, false, new int[]{}),
    GHOST ("ghosts", new String[] {"Ghost"}, new Tile(3241,9910,0),-1,false,false,false,new int[]{}),
    GOBLIN ("goblins", new String[]{"Goblin"}, new Tile(3251,3237,0), -1,false,false, false, new int[]{}),
    GREATER_DEMON ("greater demons", new String[] {"Greater demon"},  new Tile(2364,9767,0), -1, false, false, false, new int[]{}),
    HELLHOUND ("hellhounds", new String[]{"Hellhound"}, new Tile(2405,9786,0), -1, false, false, false, new int[]{}),
    ICEFIEND ("icefiends", new String[] {"Icefiend"}, new Tile(3008,3478,0),-1,false,false,false,new int[]{}),
    INFERNAL_MAGE ("infernal mages", new String[] {"Infernal Mage"}, new Tile(3440,3562,1), -1, false, false, false, new int[]{}),
    JELLY ("jellys", new String[] {"Jelly"}, new Tile(2711,10027,0), -1, false, false, false, new int[]{}),
    KALPHITE ("kalphites", new String[]{"Kalphite worker"}, new Tile(3763,5853,0), -1, false, false, false, new int[]{}),
    MINOTAUR ("minotaurs", new String[] {"Minotaur"}, new Tile(1871,5232,0),-1,false,false,false,new int[]{}),
    MOSS_GIANT ("moss giants", new String[] {"Moss giant"}, new Tile(3165,9889,0),-1,false,false,false,new int[]{}),
    PYREFIEND ("pyrefiends",new String[]{"Pyrefiend"}, new Tile(2761,10008,0), -1, false, false, false, new int[] {}),
    RAT("rats", new String[] {"Rat, Gaint rat"}, new Tile(3239,9868,0),-1,false,false,false,new int[]{}),
    SCORPION ("scorpions", new String[] {"Scorpion"}, new Tile(3300,33304,0),-1,false,false,false,new int[]{}),
    SPIDER ("spiders", new String[] {"Gaint spider"}, new Tile(3211,3205,0),-1,false,false,false,new int[]{}),
    SKELETON ("skeletons", new String[] {"Skeleton"}, new Tile(3275,9911,0),-1,false,false,false,new int[]{}),
    TUROTH ("turoths", new String[] {"Turoth"}, new Tile(2715,10011,0), -1, false, false, false, new int[] {4158}),
    WOLF ("wolves", new String[] {"Wolf"}, new Tile(1871,5232,0),-1,false,false,false,new int[]{}),
    ZOMBIE ("zombies", new String[] {"Zombie"}, new Tile(3253,9893,0),-1,false,false,false,new int[]{});

    //constructor
    Monster(String taskName, String[] names, Tile location, int itemUseOnDeath, boolean useWaterskins,boolean useAntiPoison,boolean useLight, int[] equipmentIds){
       this.taskName = taskName;
        this.names = names;
        this.location = location;
        this.itemUseOnDeath = itemUseOnDeath;
        this.useWaterskins = useWaterskins;
        this.useAntiPoison = useAntiPoison;
        this.useLight = useLight;
        this.equipmentIds = equipmentIds.clone();
    }

    //variables
    private String[] names;
    private String taskName;
    private Tile location;
    private int itemUseOnDeath;
    private boolean useWaterskins;
    private boolean useAntiPoison;
    private boolean useLight;
    private int[] equipmentIds;

    int earmuffs = 4166;
    int facemask = 4164;
    int mirrorShield = 4156;
    int leafBladedSpear = 4158;
    int bagOfSalt = 4161;
    int rockHammer = 4162;
    int nosePeg = 4168;
    int holySymbol = 1718;
    int bugLantern = 7051;
    int insulatedBoots = 7159;
    int slayerGloves = 6720;
    int slayerStaff = 4170;
    int witchWoodIcon = 8923;
    int slayerBell = 10952;
    int fishingExplosive = 6664;
    int iceCooler = 6696;
    int spinyHelmet = 4551;
    int leafBladedSword = 11902;
    int[] allItems = {4166,4164,4156,4158,4161,4162,4168,1718,7051,7159,6720,4170,8923,10952,6664,6696,4551,11902};

    private static Monster currentMonster;


    //methods

    public String[] getNames(){
        return names;
    }

    public Tile getLocation(){
        return location;
    }

    public int getItemUseOnDeath(){return itemUseOnDeath;}

    public boolean isUsingWaterSkins(){return useWaterskins;}

    public boolean isUsingAntiPoison(){return useAntiPoison;}

    public boolean isUsingLight(){
        return useLight;
    }

    public int[] getEquipmentIds(){
        return equipmentIds;
    }

    public String getTaskName(){ return taskName; }

    public static void setCurrentMonster(Monster m){
        currentMonster = m;
    }

    public static Monster getCurrentMonster(){
        return currentMonster;
    }

    public static Monster[] getMonsterList(){
        Monster[] monList = values();
        return monList;
    }

}
