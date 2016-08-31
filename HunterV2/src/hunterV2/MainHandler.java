package hunterV2;

import org.tbot.methods.*;
import org.tbot.wrappers.WidgetChild;
import salamanders.*;
import traps.*;
import falconry.*;
import org.tbot.graphics.MouseTrail;
import org.tbot.internal.AbstractScript;
import org.tbot.internal.Manifest;
import org.tbot.internal.ScriptCategory;
import org.tbot.internal.event.events.InventoryEvent;
import org.tbot.internal.event.events.MessageEvent;
import org.tbot.internal.event.listeners.InventoryListener;
import org.tbot.internal.event.listeners.MessageListener;
import org.tbot.internal.event.listeners.PaintListener;
import org.tbot.methods.tabs.Inventory;
import org.tbot.methods.walking.Walking;
import org.tbot.util.Condition;
import org.tbot.wrappers.GameObject;
import org.tbot.wrappers.GroundItem;
import util.*;
import util.Timer;

import java.awt.*;

/**
 * Created by Russell on 2/9/2016.
 */
@Manifest(name = "Swik's Hunter", authors = "Swikoka", version = 2.12, description = "*UPDATED*  Birds, Falconry, Salamanders (Orange!), and Chinchompas",category = ScriptCategory.HUNTER)
public class MainHandler extends AbstractScript implements PaintListener, MessageListener, InventoryListener {

    private String state;
    private int startXP;
    private int startLvl;
    private Timer timer;
    private boolean hasStarted = false;
    private int nextTrapLvl=-1;
    private boolean[] hasHit = {Skills.getCurrentLevel(Skills.Skill.HUNTER)>=20, Skills.getCurrentLevel(Skills.Skill.HUNTER)>=40,Skills.getCurrentLevel(Skills.Skill.HUNTER)>=60,Skills.getCurrentLevel(Skills.Skill.HUNTER)>=80};
    private Controller traps = new Controller(new trapsDropping(), new trippedTrap(), new trapOnGround(), new placeTrap(), new pickUpActvTrap(), new waiting(), new Antiban(1,1,1));
    private Controller falconry = new Controller(new dropping(), new getGyr(), new huntKebbit(), new noFalcon(), new nothing(), new Antiban(1,1,1));
    private Controller salamanders = new Controller(new fillWaterSkin(), new checkActivatedTree(), new pickingUpStuff(), new release(), new setTrapTree(), new Antiban(1,1,1), new waiting());
    private int runEnergy = Random.nextInt(30, 60);
    private MouseTrail mouseTrail = new MouseTrail(100);
    //private int initialThings = 0;
    GUI gui = new GUI();

    @Override
    public boolean onStart() {
        return true;
    }

    @Override
    public int loop() {
        if(!hasStarted){
            gui.setVisible(true);
            while (gui.isVisible()) {
                Time.sleep(200);
            }
            gui.setVariables();
            gui.dispose();
            startLvl = Skills.getCurrentLevel(Skills.Skill.HUNTER);
            startXP = Skills.getExperience(Skills.Skill.HUNTER);
            //if(Inventory.contains(initialThings)){
            //    initialThings = Inventory.getCount(Var.getCurrentVar().getThingID());
            //}
            timer = new Timer();
            hasStarted = true;
        }
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
        if(startLvl<20 && Skills.getCurrentLevel(Skills.Skill.HUNTER)==20 && !hasHit[0]){
            if(Var.getCurrentVar().getHuntType()==1) {
                TrapLocations.getInstance().pickUpSalTraps();
                TrapLocations.getInstance().setSalTrapLocations();
            }
            if(Var.getCurrentVar().getHuntType()==0) {
                TrapLocations.getInstance().pickUpTraps();
                TrapLocations.getInstance().setTrapLocations(0);
            }
            hasHit[0] = true;
        }
        if(startLvl<40 && Skills.getCurrentLevel(Skills.Skill.HUNTER)==40 && !hasHit[1]){
            if(Var.getCurrentVar().getHuntType()==2) {
                TrapLocations.getInstance().pickUpSalTraps();
                TrapLocations.getInstance().setSalTrapLocations();
            }
            if(Var.getCurrentVar().getHuntType()==0) {
                TrapLocations.getInstance().pickUpTraps();
                TrapLocations.getInstance().setTrapLocations(0);
            }
            hasHit[1] = true;
        }
        if(startLvl<60 && Skills.getCurrentLevel(Skills.Skill.HUNTER)==60 && !hasHit[2]){
            if(Var.getCurrentVar().getHuntType()==2) {
                TrapLocations.getInstance().pickUpSalTraps();
                TrapLocations.getInstance().setSalTrapLocations();
            }
            if(Var.getCurrentVar().getHuntType()==0) {
                TrapLocations.getInstance().pickUpTraps();
                TrapLocations.getInstance().setTrapLocations(0);
            }
            hasHit[2] = true;
        }
        if(startLvl<80 && Skills.getCurrentLevel(Skills.Skill.HUNTER)==80 && !hasHit[3]){
            if(Var.getCurrentVar().getHuntType()==2) {
                TrapLocations.getInstance().pickUpSalTraps();
                TrapLocations.getInstance().setSalTrapLocations();
            }
            if(Var.getCurrentVar().getHuntType()==0) {
                TrapLocations.getInstance().pickUpTraps();
                TrapLocations.getInstance().setTrapLocations(0);
            }
            hasHit[3] = true;
        }
        if(Var.getCurrentVar().getHuntType() == 0){
            Task t = traps.getCurrentTask();
            if(t!=null){
                state = t.getName();
                t.execute();
            }
            return 100;
        }
        if(Var.getCurrentVar().getHuntType() == 1){
            Task t = falconry.getCurrentTask();
            if(t!=null){
                state = t.getName();
                t.execute();
            }
            return 100;
        }
        if(Var.getCurrentVar().getHuntType() == 2){
            Task t = salamanders.getCurrentTask();
            if(t!=null){
                state = t.getName();
                t.execute();
            }
            return 100;
        }
        return 100;
    }

    //Paint Listener
    @Override
    public void onRepaint(Graphics g){
        if(timer != null) {
           // int price = PriceLookup.getPrice(Var.getCurrentVar().getThingID());
            double numCaught = ((Skills.getExperience(Skills.Skill.HUNTER)-startXP)/Var.getCurrentVar().getExpPer());
            double currentXP = Skills.getExperience(Skills.Skill.HUNTER);
            double xpBetweenLvls = (Skills.experienceAtLevel(Skills.getCurrentLevel(Skills.Skill.HUNTER)+1))-(Skills.experienceAtLevel(Skills.getCurrentLevel(Skills.Skill.HUNTER)));
            double xpToLvl = ((Skills.experienceAtLevel(Skills.getCurrentLevel(Skills.Skill.HUNTER)+1))-currentXP);
            g.setColor(new Color(0, 80, 0, 85));
            Polygon p = new Polygon(new int[]{4,190,190,4},new int[]{222,222,336,336},4);
            g.fillPolygon(p);
            g.setColor(Color.RED);
            mouseTrail.draw(g);
            g.drawOval(Mouse.getX(), Mouse.getY(), 10, 10);
            g.setColor(Color.WHITE);
            g.drawString("Swik's Hunter v2.12", 10, 15 + 220);
            g.drawString("Time ran: " + timer.getFormattedTime(), 10, 29 + 220);
            g.drawString("Caught (P/h): " + (int)numCaught +" (" + (int)timer.calculatePerHour((long)numCaught) + ")" , 10, 43+220);
            g.drawString("Lvl: " + Skills.getCurrentLevel(Skills.Skill.HUNTER)+" ("+ (Skills.getCurrentLevel(Skills.Skill.HUNTER)-startLvl)+")",10,57+220);
            g.drawString("XP Gained (P/h): " + (int) (currentXP - startXP) + " (" + (int) timer.calculatePerHour((long)(currentXP - startXP)) + ")", 10, 71 + 220);
            g.drawString("% XP to Lvl (Time): " + (int)(100-((xpToLvl / xpBetweenLvls) * 100)) + "% (" + formatTime(xpToLvl / timer.calculatePerHour((long)(currentXP - startXP))) + ")", 10, 85 + 220);
            //if(Var.getCurrentVar().getHuntType()==0 && Inventory.contains(Var.getCurrentVar().getThingID())){
              //  g.drawString("Gold (P/h): " + (int) (price * (Inventory.getCount(Var.getCurrentVar().getThingID())-initialThings)) + " (" + (int) timer.calculatePerHour(price * (Inventory.getCount(Var.getCurrentVar().getThingID()) - initialThings)) + ")", 10, 99 + 220);
            //}
            g.drawString("Hunting: " + Var.getCurrentVar().getHunting(), 10, 99 + 220);
            g.drawString("State: " + state, 10, 113 + 220);
            if(Var.getCurrentVar().getHuntType()==0 || Var.getCurrentVar().getHuntType()==3){
                for(int i = 0; i<TrapLocations.getInstance().getTrapTiles().size(); i++) {
                    if(checkGameObjectArrayForID(GameObjects.getAt(TrapLocations.getInstance().getTrapTiles().get(i)), Var.getCurrentVar().getActvTrapID())!=null){
                        g.setColor(Color.GREEN);
                        TrapLocations.getInstance().getTrapTiles().get(i).draw(g);
                    }
                    else if(checkGameObjectArrayForID(GameObjects.getAt(TrapLocations.getInstance().getTrapTiles().get(i)), Var.getCurrentVar().getPlacedTrapID())!=null){
                        g.setColor(Color.YELLOW);
                        TrapLocations.getInstance().getTrapTiles().get(i).draw(g);
                    }
                    else{
                        g.setColor(Color.RED);
                        TrapLocations.getInstance().getTrapTiles().get(i).draw(g);
                    }
                }
            }
            if(Var.getCurrentVar().getHuntType()==2){
                for(int i = 0; i<TrapLocations.getInstance().getTrapTiles().size(); i++) {
                    if(checkGameObjectArrayForID(GameObjects.getAt(TrapLocations.getInstance().getTrapTiles().get(i)), Var.getCurrentVar().getActvTrapID())!=null){
                        g.setColor(Color.GREEN);
                        TrapLocations.getInstance().getTrapTiles().get(i).draw(g);
                    }
                    else if(checkGameObjectArrayForID(GameObjects.getAt(TrapLocations.getInstance().getTrapTiles().get(i)), Var.getCurrentVar().getPlacedTrapID())!=null){
                        g.setColor(Color.YELLOW);
                        TrapLocations.getInstance().getTrapTiles().get(i).draw(g);
                    }
                    else{
                        g.setColor(Color.RED);
                        TrapLocations.getInstance().getTrapTiles().get(i).draw(g);
                    }
                }
            }
        }
    }


//
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

    private GameObject checkGameObjectArrayForID(GameObject[] g, int id){
        if(g!=null) {
            for (int i = 0; i < g.length; i++) {
                if (g[i]!=null && g[i].getID() == id) {
                    return g[i];
                }
            }
        }
        return null;
    }

    private GroundItem checkGroundItemArrayForID(GroundItem[] g, int id){
        if(g!=null) {
            for (int i = 0; i < g.length; i++) {
                if (g[i]!=null && g[i].getID() == id) {
                    return g[i];
                }
            }
        }
        return null;
    }
    
}
