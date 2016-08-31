package barbarianfisher;

import org.tbot.internal.AbstractScript;
import org.tbot.internal.Manifest;
import org.tbot.internal.ScriptCategory;
import org.tbot.internal.event.events.InventoryEvent;
import org.tbot.internal.event.events.MessageEvent;
import org.tbot.internal.event.listeners.InventoryListener;
import org.tbot.internal.event.listeners.MessageListener;
import org.tbot.internal.event.listeners.PaintListener;
import org.tbot.methods.*;
import org.tbot.methods.tabs.Inventory;
import org.tbot.methods.walking.LocalPath;
import org.tbot.methods.walking.Path;
import org.tbot.methods.walking.Walking;
import org.tbot.methods.web.Web;
import org.tbot.util.Condition;
import org.tbot.util.Filter;
import org.tbot.wrappers.Area;
import org.tbot.wrappers.GameObject;
import org.tbot.wrappers.NPC;
import org.tbot.wrappers.Tile;

import java.awt.*;

/**
 * Created by Russell on 1/29/2016.
 */
@Manifest(name = "Swik's Barbarian Fisher", authors = "Swikoka", version = 1.0, description = "Does barbarian fishing",category = ScriptCategory.FISHING)
public class MainHandler extends AbstractScript implements PaintListener, MessageListener, InventoryListener {

    private Timer runTime;
    private int antibanNumber;
    private int startFishXP;
    private int startStrXP;
    private int startAgilXP;
    private int startFishLvl;
    private int startStrLvl;
    private int startAgilLvl;
    private int intFeather;
    private String state;
    private int[] pauses = {300,1000,1500,2000,5000,8000,10000};
    Web web = new Web();
    Antiban antiban = new Antiban(2,1,1);

    public boolean onStart() {
        web.getData().add(new FishWebArea());
        startFishXP = Skills.getExperience(Skills.Skill.FISHING);
        startFishLvl = Skills.getCurrentLevel(Skills.Skill.FISHING);
        startStrXP = Skills.getExperience(Skills.Skill.STRENGTH);
        startStrLvl = Skills.getCurrentLevel(Skills.Skill.STRENGTH);
        startAgilXP = Skills.getExperience(Skills.Skill.AGILITY);
        startAgilLvl = Skills.getCurrentLevel(Skills.Skill.AGILITY);
        intFeather = Inventory.getCount("Feather");
        runTime = new Timer();
        return true;
    }

    //Main Loop
    @Override
    public int loop() {
        antibanNumber = chechAntiban();
        if(!Inventory.isOpen()){
            Inventory.openTab();
        }
        if(!Walking.isRunEnabled()){
            Walking.setRun(Walking.getRunEnergy()>Random.nextInt(25,50));
        }
        if(Inventory.hasItemSelected()){
            Inventory.deselectItem();
        }
        if(antibanNumber!=-1){
            if(antibanNumber==0){
                state = "Random Mouse";
                antiban.randomMouse();
            }
            if(antibanNumber==1){
                state = "Hover Skill";
                antiban.hoverSkill();
            }
            if(antibanNumber==2){
                state = "Examine Object";
                antiban.examineGameObject();
            }
        }
        if(Inventory.isFull()){
            state = "Dropping";
            drop();
        }
        else if(Players.getLocal().getAnimation()!=-1){
            state = "Waiting";
            antiban.mouseExit(Random.nextInt(0,5));
            Time.sleepUntil(new Condition() {
                @Override
                public boolean check() {
                    return Players.getLocal().getAnimation()==-1;
                }
            });
            Time.sleep(0,500+pauses[Random.nextInt(0,pauses.length-1)]);
        }
        else{
           NPC spot = Npcs.getNearest("Fishing spot");
            if(spot!=null){
                state = "Click on spot";
                if(spot.isOnScreen()){
                    spot.interact("Use-rod");
                    Time.sleepUntil(new Condition() {
                        @Override
                        public boolean check() {
                            return Players.getLocal().getAnimation()!=-1;
                        }
                    }, 5000);
                }
                else{
                    state = "Walk to spot";
                    LocalPath toTile = Walking.findLocalPath(spot.getLocation());
                    if(toTile!=null){
                        toTile.traverse();
                        Time.sleepUntil(new Condition() {
                            @Override
                            public boolean check() {
                                return Walking.getDestinationDistance()<3;
                            }
                        }, 2500);
                    }
                    else{
                        Path toEndTile = web.findPath(spot.getLocation());
                        if(toEndTile!=null){
                            toEndTile.traverse();
                            Time.sleepUntil(new Condition() {
                                @Override
                                public boolean check() {
                                    return Walking.getDestinationDistance()<3;
                                }
                            }, 2500);
                        }
                    }
                }
            }
        }
        return 100;
    }

    @Override
    public void onRepaint(Graphics g){
        g.setColor(Color.WHITE);
        if(runTime != null) {
            int curFeather = Inventory.getCount("Feather");
            double numMade = (intFeather-curFeather);
            double currentFishXP = Skills.getExperience(Skills.Skill.FISHING);
            double currentStrXP = Skills.getExperience(Skills.Skill.STRENGTH);
            double currentAgilXP = Skills.getExperience(Skills.Skill.AGILITY);
            double xpToFishLvl = ((Skills.experienceAtLevel(Skills.getCurrentLevel(Skills.Skill.FISHING)+1))-currentFishXP);
            double xpToStrLvl = ((Skills.experienceAtLevel(Skills.getCurrentLevel(Skills.Skill.STRENGTH)+1))-currentStrXP);
            double xpToAgilLvl = ((Skills.experienceAtLevel(Skills.getCurrentLevel(Skills.Skill.AGILITY)+1))-currentAgilXP);
            g.drawString("Swik's Barbarian Fisher v1.0", 10 , 15 + 220);
            g.drawString("Time ran: " + runTime.getFormattedTime(), 10, 29 + 220);
            g.drawString("Caught (P/h): " + (int)numMade +" (" + (int)runTime.calculatePerHour((long)numMade) + ")" , 10, 43+220);
            g.drawString("Lvl: " + Skills.getCurrentLevel(Skills.Skill.FISHING)+" ("+ (Skills.getCurrentLevel(Skills.Skill.FISHING)-startFishLvl)+")" +" Str: " + Skills.getCurrentLevel(Skills.Skill.STRENGTH)+" ("+ (Skills.getCurrentLevel(Skills.Skill.STRENGTH)-startStrLvl)+")" + " Agil: " + Skills.getCurrentLevel(Skills.Skill.AGILITY)+" ("+ (Skills.getCurrentLevel(Skills.Skill.AGILITY)-startAgilLvl)+")",10,57+220);
            g.drawString("XP (P/h): Fish:" + (int)(currentFishXP-startFishXP)  +" (" + (int)runTime.calculatePerHour((long)(currentFishXP-startFishXP)) +") Str/Agil: " + (int)(currentStrXP-startStrXP)  +" (" + (int)runTime.calculatePerHour((long)(currentStrXP-startStrXP))+ ")", 10, 71 + 220);
            g.drawString("Time to Lvl: Fish: " + formatTime(xpToFishLvl / runTime.calculatePerHour((long)(currentFishXP - startFishXP))), 10, 85+220);
            g.drawString("Str: " + formatTime(xpToStrLvl / runTime.calculatePerHour((long)(currentStrXP - startStrXP))) + " Agil: " + formatTime(xpToAgilLvl / runTime.calculatePerHour((long)(currentAgilXP - startAgilXP))),10,99+220);
            g.drawString("State: " + state, 10, 113 + 220);
            g.setColor(new Color(0, 80, 0, 85));
            Polygon p = new Polygon(new int[]{4,190,190,4},new int[]{222,222,336,336},4);
            g.fillPolygon(p);
            g.setColor(new Color(0, 0, 0, 255));
            g.drawPolygon(p);
        }
    }


    @Override
    public void itemsAdded(InventoryEvent ie) {
        // TODO Auto-generated method stub

    }

    @Override
    public void itemsRemoved(InventoryEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void messageReceived(MessageEvent arg0) {
        // TODO Auto-generated method stub

    }

    public String formatTime(double h){
        String s;
        s = "" + (int) h + ":" + (int)((h%1)*60) + ":" + (int)((((h%1)*60)%1)*60);
        return s;
    }

    private boolean isFISHING(){
        for(int i = 0; i<20; i++){
            if(Players.getLocal().getAnimation()==6756){
                return true;
            }
            Time.sleep(50);
        }
        return false;
    }

    private boolean contains(Tile[] tiles, Tile tile){
        for(int i = 0; i<tiles.length; i++){
            if(tiles[i].equals(tile)){
                return true;
            }
        }
        return false;
    }

    private void travel(Tile endTile){
        LocalPath toTile = Walking.findLocalPath(endTile);
        if(toTile!=null){
            toTile.traverse();
        }
        else{
            Path toEndTile = web.findPath(endTile);
            if(toEndTile!=null){
                toEndTile.traverse();
            }
        }
    }

    private void drop(){
        while(Inventory.contains("Leaping trout") || Inventory.contains("Leaping salmon") || Inventory.contains("Leaping sturgeon")){
            Inventory.getFirst("Leaping trout","Leaping salmon").interact("Drop");
            Time.sleep(400,800);
        }
    }

    public int chechAntiban(){
        for(int i = 0; i<antiban.getDoTimes().length;i++){
            if(antiban.getDoTimes()[i]<runTime.getTimeElapsed()){
                return i;
            }
        }
        return -1;
    }
}