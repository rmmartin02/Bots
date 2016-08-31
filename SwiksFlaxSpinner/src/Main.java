
import org.tbot.graphics.MouseTrail;
import org.tbot.internal.AbstractScript;
import org.tbot.internal.Manifest;
import org.tbot.internal.ScriptCategory;
import org.tbot.internal.event.events.InventoryEvent;
import org.tbot.internal.event.events.MessageEvent;
import org.tbot.internal.event.listeners.InventoryListener;
import org.tbot.internal.event.listeners.MessageListener;
import org.tbot.internal.event.listeners.PaintListener;
import org.tbot.methods.*;
import org.tbot.methods.input.keyboard.*;
import org.tbot.methods.tabs.Inventory;
import org.tbot.methods.walking.LocalPath;
import org.tbot.methods.walking.Walking;
import org.tbot.methods.web.banks.WebBanks;
import org.tbot.util.Condition;
import org.tbot.util.Filter;
import org.tbot.wrappers.GameObject;
import org.tbot.wrappers.Tile;
import org.tbot.wrappers.WidgetChild;
import org.tbot.wrappers.Widget;
import util.Antiban;
import util.PriceLookup;
import util.Timer;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

@Manifest(name = "Swik's Flax Spinner", authors = "Swikoka", version = 1.05 , description = "Spins Flax at either Lumbridge or Seers", category = ScriptCategory.CRAFTING)
public class Main extends AbstractScript implements PaintListener, MessageListener, InventoryListener {

    private boolean Lumby;
    private Tile LUMBYTARGET = new Tile(3209,3213,1);
    private Tile SEERTARGET1 = new Tile(2715, 3471,0);
    private Tile SEERTARGET2 = new Tile(2711, 3471, 1);
    private String state;
    private int bowStringID = 1777;
    private int flaxID = 1779;
    private int expPer = 15;
    private int price;
    private int startXP;
    private int startLvl;
    Object[] SpinOptions = {"Lumbridge", "Seers"};
    private Timer timer;
    private MouseTrail mouseTrail = new MouseTrail(100);
    private Antiban antiban = new Antiban(2,1,2);
    private int runEnergy = Random.nextInt(30, 60);

    private final Filter<GameObject> Ladder_filter = new Filter<GameObject>() {

        @Override
        public boolean accept(GameObject aLad) {
            if (aLad != null && aLad.getLocation().equals(new Tile(2715, 3470, 0)) && aLad.getName().equalsIgnoreCase("Ladder")){
                return true;
            }
            return false;
        }
    };
    private final Filter<GameObject> LumbyStairs_filter = new Filter<GameObject>() {

        @Override
        public boolean accept(GameObject aLad) {
            if (aLad != null && aLad.getLocation().getY()<3213 && aLad.getName().equalsIgnoreCase("Staircase")){
                return true;
            }
            return false;
        }
    };

    private boolean isSpinning(){
        for(int i = 0; i<20; i++){
            if(Players.getLocal().getAnimation()==894){
                return true;
            }
            Time.sleep(50);
        }
        return false;
    }

    public String formatTime(double h){
        String s;
        s = "" + (int) h + ":" + (int)((h%1)*60) + ":" + (int)((((h%1)*60)%1)*60);
        return s;
    }

    //Main Loop
    @Override
    public int loop() {
        //to stop script, return -1
        if (!Game.isLoggedIn()) {
            return 200;
        }
        if ((!Walking.isRunEnabled()) && (Walking.getRunEnergy() >= runEnergy)) {
            if (Walking.setRun(true)) {
                Time.sleepUntil(new Condition() {
                    public boolean check() {
                        return Walking.isRunEnabled();
                    }
                }, Random.nextInt(800, 1200));
                runEnergy = Random.nextInt(30, 60);
            }
        }
        if(!Inventory.isOpen()){
            Inventory.openTab();
        }
        if(Inventory.hasItemSelected()){
            Inventory.deselectItem();
        }
        if(Lumby){
            if(Inventory.contains("Flax")){
                if(isSpinning()){
                    int which = -1;
                    for(int i = 0; i<antiban.getDoTimes().length; i++){
                        if (timer.getTimeElapsed()>antiban.getDoTimes()[i]){
                            which = i;
                        }
                    }
                    if(which == 0){
                        state = "Random mouse";
                        antiban.randomMouse();
                    }
                    if(which == 1){
                        state = "Hover skill";
                        antiban.hoverSkill();
                    }
                    if(which==2){
                        state = "Move camera";
                        antiban.moveCameraRandomly();
                    }
                    state = "Spinning";
                    Time.sleepUntil(new Condition() {
                        @Override
                        public boolean check() {
                            return !isSpinning();
                        }
                    }, 1000);
                }
                else {
                    Widget spinMenu = Widgets .getWidget(459);
                    WidgetChild makeX = Widgets.getWidget(162).getChild(33);
                    if (makeX.isVisible()) {
                        state = "Entering Number";
                        org.tbot.methods.input.keyboard.Keyboard.sendText(Integer.toString(Random.nextInt(28, 999)), true);
                        Time.sleep(1000);
                    } else {
                        if (spinMenu.getChild(91).isVisible()) {
                            state = "Clicking Make X";
                            spinMenu.getChild(91).interact("Make x");
                        } else {
                            if (Players.getLocal().getLocation().getPlane() == 1) {
                                GameObject closedDoor = GameObjects.getNearest(7143);
                                if(closedDoor!=null && closedDoor.getLocation().equals(new Tile(3207,3214,1))){
                                    closedDoor.interact("Open");
                                    Time.sleep(650,900);
                                }
                                else {
                                    GameObject wheel = GameObjects.getNearest("Spinning wheel");
                                    if (wheel != null && wheel.isOnScreen()) {
                                        state = "Clicking Wheel";
                                        wheel.interact("Spin");
                                        Time.sleep(800, 1300);
                                    } else {
                                        state = "Walking to Wheel";
                                        LocalPath toWheel = Walking.findLocalPath(LUMBYTARGET);
                                        if (toWheel != null) {
                                            toWheel.traverse();
                                            Time.sleep(800, 1300);
                                        }
                                    }
                                }
                            } else if(Players.getLocal().getLocation().getPlane() == 2){
                                GameObject stairs = GameObjects.getNearest(LumbyStairs_filter);
                                if(stairs!=null && stairs.isOnScreen()) {
                                    state = "Clicking Stairs";
                                    stairs.interact("Climb-down");
                                    Time.sleep(800,1300);
                                } else {
                                    state = "Walking to Stairs";
                                    LocalPath toStairs = Walking.findLocalPath(new Tile(3205, 3209, 2));
                                    if (toStairs != null) {
                                        toStairs.traverse();
                                        Time.sleep(800, 1300);
                                    }
                                }
                            }
                            else{
                                state = "Climbing stairs";
                                Bank.openBank(WebBanks.LUMBRIDGE_CASTLE_BANK);
                                Time.sleep(800, 1300);
                            }
                            //3204,3207,1 to 3216,
                        }
                    }
                }
            }
            else{
                if(Bank.isOpen()){
                    if(Inventory.contains("Bow string")){
                        state = "Depositing";
                        Bank.depositAll();
                        Time.sleepUntil(new Condition() {
                            @Override
                            public boolean check() {
                                return !Inventory.contains("Bow string");
                            }
                        }, 1000);
                    }
                    else {
                        state = "Withdrawing";
                        Bank.withdrawAll("Flax");
                        Time.sleepUntil(new Condition() {
                            @Override
                            public boolean check() {
                                return Inventory.contains("Flax");
                            }
                        }, 1000);
                    }
                }
                else{
                    if(Players.getLocal().getPlane()==1){
                        state = "Walking to stairs";
                        GameObject closedDoor = GameObjects.getNearest(7143);
                        if(closedDoor!=null && closedDoor.getLocation().equals(new Tile(3207,3214,1))){
                            closedDoor.interact("Open");
                            Time.sleep(650,900);
                        }
                        else {
                            GameObject stairs = GameObjects.getNearest(LumbyStairs_filter);
                            if (stairs != null && stairs.isOnScreen()) {
                                stairs.interact("Climb-up");
                                Time.sleep(800, 1300);
                            } else {
                                LocalPath toStairs = Walking.findLocalPath(new Tile(3205, 3209, 1));
                                if (toStairs != null) {
                                    toStairs.traverse();
                                    Time.sleep(800, 1300);
                                }
                            }
                        }
                    }
                    else {
                        GameObject booth = GameObjects.getNearest("Bank booth");
                        if(booth!=null && booth.isOnScreen()) {
                            state = "Opening Bank";
                            Bank.openBank(WebBanks.LUMBRIDGE_CASTLE_BANK);
                            Time.sleep(800, 1300);
                        }
                        else{
                            LocalPath toTile = Walking.findLocalPath(new Tile(3206,3217,2));
                            if(toTile!=null){
                                toTile.traverse();
                                Time.sleep(800, 1200);
                            }
                        }
                    }
                }
            }
        }
        else{
            if(Inventory.contains("Flax")){
                if(isSpinning()){
                    int which = -1;
                    for(int i = 0; i<antiban.getDoTimes().length; i++){
                        if (timer.getTimeElapsed()>antiban.getDoTimes()[i]){
                            which = i;
                        }
                    }
                    if(which == 0){
                        state = "Random mouse";
                        antiban.randomMouse();
                    }
                    if(which == 1){
                        state = "Hover skill";
                        antiban.hoverSkill();
                    }
                    if(which==2){
                        state = "Move camera";
                        antiban.moveCameraRandomly();
                    }
                    state = "Spinning";
                    Time.sleepUntil(new Condition() {
                        @Override
                        public boolean check() {
                            return !isSpinning();
                        }
                    }, 1000);
                    state = "Spinning";
                    Time.sleepUntil(new Condition() {
                        @Override
                        public boolean check() {
                            return !isSpinning();
                        }
                    });
                }
                else {
                    Widget spinMenu = Widgets.getWidget(459);
                    WidgetChild makeX = Widgets.getWidget(162).getChild(33);
                    if (makeX.isVisible()) {
                        state = "Entering Number";
                        org.tbot.methods.input.keyboard.Keyboard.sendText(Integer.toString(Random.nextInt(28, 999)), true);
                        Time.sleep(1000);
                    } else {
                        if (spinMenu.getChild(91).isVisible()) {
                            state = "Clicking Make X";
                            spinMenu.getChild(91).interact("Make x");
                        } else {
                            if (Players.getLocal().getLocation().getPlane() == 1) {
                                GameObject wheel = GameObjects.getNearest("Spinning wheel");
                                if (wheel != null && wheel.isOnScreen()) {
                                    state = "Clicking Wheel";
                                    wheel.interact("Spin");
                                    Time.sleep(800,1300);
                                } else {
                                    state = "Walking to Wheel";
                                    LocalPath toWheel = Walking.findLocalPath(SEERTARGET2);
                                    if (toWheel != null) {
                                        toWheel.traverse();
                                        Time.sleep(800,1300);
                                    }
                                }
                            } else {
                                GameObject stairs = GameObjects.getNearest(Ladder_filter);
                                if (stairs != null && stairs.isOnScreen()) {
                                    state = "Climbing Ladder";
                                    stairs.interact("Climb-up");
                                    Time.sleep(800,1300);
                                } else {
                                    state = "Walking to Ladder";
                                    LocalPath toLadder = Walking.findLocalPath(SEERTARGET1);
                                    if (toLadder != null) {
                                        toLadder.traverse();
                                        Time.sleep(800,1300);
                                    }
                                }
                            }
                            //3204,3207,1 to 3216,
                        }
                    }
                }
            }

            else{
                if(Bank.isOpen()){
                    if(Inventory.contains("Bow string")){
                        state = "Depositing";
                        Bank.depositAll();
                        Time.sleepUntil(new Condition() {
                            @Override
                            public boolean check() {
                                return !Inventory.contains("Bow string");
                            }
                        }, 1000);
                    }
                    else {
                        state = "Withdrawing";
                        Bank.withdrawAll("Flax");
                        Time.sleepUntil(new Condition() {
                            @Override
                            public boolean check() {
                                return Inventory.contains("Flax");
                            }
                        }, 1000);
                    }
                }
                else{
                    state = "Walking to Bank";
                    if(Players.getLocal().getPlane()==1){
                        GameObjects.getNearest("Ladder").interact("Climb-down");
                        Time.sleep(800,1300);
                    }
                    else {
                        Bank.openBank(WebBanks.SEERS_BANK);
                        Time.sleep(800, 1300);
                    }
                }
            }
        }
        return 100;
    }

    @Override
    public boolean onStart(){
        Object selectedHuntType = JOptionPane.showInputDialog(null, "Choose one", "Location", JOptionPane.INFORMATION_MESSAGE, null, SpinOptions, SpinOptions[0]);
        if (null != selectedHuntType.toString()) {
            switch (selectedHuntType.toString()) {
                case "Lumbridge":
                    Lumby = true;
                    break;
                case "Seers":
                    Lumby = false;
                    break;
            }
        }
        startXP = Skills.getExperience(Skills.Skill.CRAFTING);
        startLvl = Skills.getCurrentLevel(Skills.Skill.CRAFTING);
        price = PriceLookup.getPrice(bowStringID);
        timer = new Timer();
        return true;
    }

    //Inventory Listener
    @Override
    public void itemsRemoved(InventoryEvent inventoryEvent) {

    }

    @Override
    public void itemsAdded(InventoryEvent inventoryEvent) {

    }

    //Message Listener
    @Override
    public void messageReceived(MessageEvent messageEvent) {

    }

    //Paint Listener
    @Override
    public void onRepaint(Graphics g) {
        g.setColor(Color.WHITE);
        if(timer != null) {
            double numMade = ((Skills.getExperience(Skills.Skill.CRAFTING)-startXP)/expPer);
            double currentXP = Skills.getExperience(Skills.Skill.CRAFTING);
            double xpToLvl = ((Skills.experienceAtLevel(Skills.getCurrentLevel(Skills.Skill.CRAFTING)+1))-currentXP);
            g.drawString("Swik's Flaxer v1.04", 10 , 15 + 220);
            g.drawString("Time ran: " + timer.getFormattedTime(), 10, 29 + 220);
            g.drawString("Made (P/h): " + (int)numMade +" (" + (int)timer.calculatePerHour(numMade) + ")" , 10, 43+220);
            g.drawString("Lvl: " + Skills.getCurrentLevel(Skills.Skill.CRAFTING)+" ("+ (Skills.getCurrentLevel(Skills.Skill.CRAFTING)-startLvl)+")",10,57+220);
            g.drawString("XP Gained (P/h): " + (int)(currentXP-startXP)  +" (" + (int)timer.calculatePerHour(currentXP-startXP) + ")", 10, 71 + 220);
            g.drawString("XP to Lvl (Time): " + (int)xpToLvl + " (" + formatTime(xpToLvl / timer.calculatePerHour(currentXP - startXP)) + ")", 10, 85+220);
            g.drawString("Gold (P/h): " + (int) (price * numMade) + " (" + (int) timer.calculatePerHour(price * numMade) + ")", 10, 99 + 220);
            g.drawString("State: " + state, 10, 113 + 220);
            g.setColor(new Color(0, 80, 0, 85));
            Polygon p = new Polygon(new int[]{4,190,190,4},new int[]{222,222,336,336},4);
            g.fillPolygon(p);
            g.setColor(new Color(0, 0, 0, 255));
            g.drawPolygon(p);
            g.setColor(Color.RED);
            mouseTrail.draw(g);
            g.drawOval(Mouse.getX(), Mouse.getY(), 10, 10);
        }
    }

}
