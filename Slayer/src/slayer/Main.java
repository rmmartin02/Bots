package slayer;

import banking.*;
import banking.deposit.DepositAntiPoison;
import banking.deposit.DepositItemOnDeath;
import banking.deposit.DepositLightSource;
import banking.deposit.DepositWaterskin;
import fighting.*;
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
import org.tbot.methods.tabs.Equipment;
import org.tbot.methods.tabs.Inventory;
import org.tbot.methods.walking.Walking;
import org.tbot.methods.web.Web;
import org.tbot.util.Condition;
import org.tbot.wrappers.Item;
import org.tbot.wrappers.NPC;
import org.tbot.wrappers.WidgetChild;
import util.*;
import util.Timer;
import walking.WalkTo;
import walking.WebSingelton;

import java.awt.*;
import java.util.Arrays;

/**
 * Created by Russell on 2/17/2016.
 */
@Manifest(name = "Swik's Slayer BETA", authors = "Swikoka", version = .10, description = "Supports 33 tasks from first 4 masters",category = ScriptCategory.SLAYER)
public class Main extends AbstractScript implements PaintListener, MessageListener, InventoryListener {

    private String state;
    private int startXP;
    private int startLvl;
    private int curXP;
    private Timer timer;
    private boolean hasStarted = false;
    private boolean needNewTask = false;
    private int runEnergy = Random.nextInt(30, 60);
    private MouseTrail mouseTrail = new MouseTrail(100);
    private int toKill = -1;
    private Web web = new Web();
    private static Item startWeapon;
    private static Item startShield;
    GUI gui = new GUI();
    private Controller controller = new Controller(new LightSourceBanking(), new AntipoisonBanking(), new ItemOnDeathBanking(),
            new ShantayPassBanking(), new WaterskinBanking(), new EquipmentBanking(), new Attack(), new Eat(),
            new WalkTo(), new FoodBanking(), new UseItemOnDeath(), new FillWaterSkin(), new LightLight(), new UseAntiPoison(),
            new DepositAntiPoison(), new DepositItemOnDeath(), new DepositLightSource(), new DepositWaterskin());

    @Override
    public int loop() {
        if(!hasStarted){
            gui.setVisible(true);
            while (gui.isVisible()) {
                Time.sleep(200);
            }
            gui.dispose();
            startLvl = Skills.getCurrentLevel(Skills.Skill.SLAYER);
            curXP = startXP;
            startXP = Skills.getExperience(Skills.Skill.SLAYER);
            startWeapon = Equipment.getItemInSlot(Equipment.SLOTS_WEAPON);
            startShield = Equipment.getItemInSlot(Equipment.SLOTS_SHIELD);
            timer = new Timer();
            hasStarted = true;
        }
        if(Skills.getExperience(Skills.Skill.SLAYER)>curXP){
            toKill -= 1;
            curXP = Skills.getExperience(Skills.Skill.SLAYER);
        }
        if (!Game.isLoggedIn()) {
            return 200;
        }
        if ((!Walking.isRunEnabled()) && (Walking.getRunEnergy() >= runEnergy)) {
            state = "Setting Run";
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
            state = "Opening Inventory";
            Inventory.openTab();
        }
        if(Inventory.hasItemSelected()){
            state = "Deselecting Item";
            Inventory.deselectItem();
        }
        if(needNewTask){
            SlayerMaster currentMaster = SlayerMaster.getSlayerMaster();
            NPC master = Npcs.getNearest(currentMaster.getName());
            if(master!=null && master.isOnScreen() && Walking.canReach(master.getLocation())){
                state = "Getting assignment";
                master.interact("Assignment");
                Time.sleep(600,1200);
                needNewTask = false;
            }
            else if(currentMaster.getName().equals("Chaeldar")){
                int dramenStaff = 772;
                if(Equipment.getItemInSlot(Equipment.SLOTS_WEAPON).getID()!=dramenStaff){
                    if(Inventory.contains(dramenStaff)){
                        if(Inventory.isFull()){
                            Inventory.getFirst(GUI.getFood()).interact("Drop");
                            Time.sleep(800,1200);
                        }
                        else {
                            Inventory.getFirst(dramenStaff).interact("Wield");
                            Time.sleep(800,1200);
                        }
                    }
                    else{
                        if(Bank.isOpen()){
                            if(Bank.contains(dramenStaff)){
                                Bank.withdraw(dramenStaff,1);
                                Time.sleep(800,1200);
                            }
                        }
                        else{
                            WebSingelton.getInstance().getWeb().openNearestBank();
                        }
                    }
                }
            }
            else{
                state = "Walking to " + currentMaster.getName();
                WebSingelton.getInstance().travel(currentMaster.getLocation());
                Time.sleep(600,1200);
            }
        }
        if((toKill<=0 || Monster.getCurrentMonster()==null) && !needNewTask){
            if(NPCChat.isChatOpen()){
                String npcTxt = Widgets.getWidget(231,3).getText();
                if(npcTxt.contains("<br>")){
                    npcTxt = npcTxt.replace("<br>"," ");
                }
                if(NPCChat.canContinue()){
                    if(npcTxt.contains("You need something new to hunt")){
                        needNewTask = true;
                        NPCChat.clickContinue();
                        Time.sleep(600,1200);
                    }
                    else if(npcTxt.contains("You're currently assigned")){
                        state = "Getting assigned Monster";
                        String nameOfTask = npcTxt.substring(npcTxt.indexOf("kill ")+5,npcTxt.indexOf("; o"));
                        for (int i = 0; i < Monster.getMonsterList().length; i++) {
                            //Anyone know why this is returning a null error for me? if(correctString.equalsIgnoreCase(ListObject.getList[i].getString()))
                            if (nameOfTask.equalsIgnoreCase(Monster.getMonsterList()[i].getTaskName())) {
                                Monster.setCurrentMonster(Monster.getMonsterList()[i]);
                                break;
                            }
                        }
                        String numberOfKills = npcTxt.substring(npcTxt.indexOf("only ") + 5, npcTxt.indexOf(" more"));
                        toKill = Integer.parseInt(numberOfKills);
                        Time.sleep(600,1200);
                        if(Arrays.asList(NPCChat.getOptions()).contains("That's all thanks.")) {
                            NPCChat.selectOption("That's all thanks.");
                            Time.sleep(600, 1200);
                        }
                    }
                    else {
                        NPCChat.clickContinue();
                        Time.sleep(600,1200);
                    }
                }
                else if(Arrays.asList(NPCChat.getOptions()).contains("How am I doing so far?")){
                    NPCChat.selectOption("How am I doing so far?");
                    Time.sleep(600,1200);
                }
                else if(Arrays.asList(NPCChat.getOptions()).contains("That's all thanks.")){
                    NPCChat.selectOption("That's all thanks.");
                    Time.sleep(600,1200);
                }
            }
            else if(Inventory.contains("Enchanted gem")){
                if(Bank.isOpen()){
                    Bank.close();
                    Time.sleep(800,1200);
                }
                else {
                    state = "Checking Gem";
                    Inventory.getFirst("Enchanted gem").interact("Activate");
                    Time.sleep(600, 1200);
                }
            }
            else{
                if(Bank.isOpen()){
                    state = "Withdrawing Gem";
                    Bank.withdraw("Enchanted gem",1);
                    Time.sleep(600,1200);
                    Bank.close();
                }
                else{
                    state = "Going to bank for gem";
                    WebSingelton.getInstance().getWeb().openNearestBank();
                    Time.sleep(600,1200);
                }
            }
        }
        if(hasStarted && toKill>0 && Monster.getCurrentMonster()!=null && !needNewTask){
            Task t = controller.getCurrentTask();
            if(t!=null){
                state = t.getName();
                t.execute();
            }
            return 100;
        }
        return 100;
    }

    @Override
    public void itemsAdded(InventoryEvent inventoryEvent) {

    }

    @Override
    public void itemsRemoved(InventoryEvent inventoryEvent) {

    }

    @Override
    public void messageReceived(MessageEvent messageEvent) {

    }

    @Override
    public void onRepaint(Graphics g) {
        g.setColor(new Color(0, 80, 0, 85));
        Polygon p = new Polygon(new int[]{4,190,190,4},new int[]{222,222,336,336},4);
        g.fillPolygon(p);
        g.setColor(Color.RED);
        mouseTrail.draw(g);
        g.drawOval(Mouse.getX(), Mouse.getY(), 10, 10);
        g.setColor(Color.WHITE);
        g.drawString("Swik's Slayer v1.0", 10, 15 + 220);
        g.drawString("State: " + state, 10, 113 + 220);
        g.drawString("To Kill: " + toKill, 10, 99 + 220);
        g.drawString("Time ran: " + timer.getFormattedTime(), 10, 29 + 220);
        g.drawString("XP(hour): " + (Skills.getExperience(Skills.Skill.SLAYER)-startXP)+"("+((Skills.getExperience(Skills.Skill.SLAYER)-startXP)/timer.getHoursElapsed())+")", 10,71+220);
        g.drawString("Task: " + Monster.getCurrentMonster().getTaskName(), 10, 85 + 220);

    }

    public static Item getStartWeapon(){
        return startWeapon;
    }

    public static Item getStartShield(){
        return startShield;
    }
}