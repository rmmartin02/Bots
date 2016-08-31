/*To Do
Add potion support
Add cosmic support
Add ring of dueling support
 */
import java.awt.*;

import javax.swing.JOptionPane;

import org.tbot.graphics.SkillPaint;
import org.tbot.internal.AbstractScript;
import org.tbot.internal.Manifest;
import org.tbot.internal.ScriptCategory;
import org.tbot.internal.event.events.InventoryEvent;
import org.tbot.internal.event.events.MessageEvent;
import org.tbot.internal.event.listeners.InventoryListener;
import org.tbot.internal.event.listeners.MessageListener;
import org.tbot.internal.event.listeners.PaintListener;
import org.tbot.methods.Bank;
import org.tbot.methods.Camera;
import org.tbot.methods.GameObjects;
import org.tbot.methods.Players;
import org.tbot.methods.Random;
import org.tbot.methods.Skills;
import org.tbot.methods.Time;
import org.tbot.methods.combat.magic.Rune;
import org.tbot.methods.io.PriceLookup;
import org.tbot.methods.tabs.Inventory;
import org.tbot.methods.walking.Path;
import org.tbot.methods.walking.Walking;
import org.tbot.methods.web.Web;
import org.tbot.methods.web.banks.WebBanks;
import org.tbot.util.Condition;
import org.tbot.wrappers.Area;
import org.tbot.wrappers.GameObject;
import org.tbot.wrappers.Tile;

import util.Altar;
import util.Timer;

@Manifest(name = "Swik's F2p Runecrafter", authors = "Swikoka", version = 1.0, description = "Crafts F2p Runes",category = ScriptCategory.RUNECRAFTING)
public class RuneCraft extends AbstractScript implements PaintListener, MessageListener, InventoryListener {

    private SkillPaint sp = null;
    private Timer runTime;
    private Tile duelArenaChest = new Tile(3382,3266,0);
    private String state;
    private int runesCreated;
    private int runesCreate;
    private int drinkAt;
    private Altar targetAltar;
    private int price;
    private int essPrice;
    private int startXP;
    private int startLvl;
    private double expPer;
    Object[] RuneOptions = {"Air", "Earth", "Water", "Fire", "Body", "Mind"};
    private Altar AIRALTAR = new Altar(Rune.AIR_RUNE, new Tile(2986,3294,0), new Tile(2841,4829,0), new Tile(2843,4832,0), new Area(new Tile(2839,4826,0), new Tile(2849,4840,0)));
    private Altar EARTHALTAR = new Altar(Rune.EARTH_RUNE, new Tile(3305,3472,0), new Tile(2655,4830,0), new Tile(2658,4839,0), new Area(new Tile(2640,4816,0), new Tile(2672,4853,0)));
    private Altar FIREALTAR =  new Altar(Rune.FIRE_RUNE, new Tile(3311,3253,0), new Tile(2574,4849,0), new Tile(2584,4840,0), new Area(new Tile(2570,4825,0), new Tile(2596,4854,0)));
    private Altar BODYALTAR =  new Altar(Rune.BODY_RUNE, new Tile(3055,3444,0), new Tile(2521,4834,0), new Tile(2522,4842,0), new Area(new Tile[]  {new Tile(2522,4847,0),new Tile(2517,4847,0),new Tile(2511,4842,0),new Tile(2512,4837,0),new Tile(2514,4835,0),new Tile(2518,4832,0),new Tile(2521,4831,0),new Tile(2528,4833,0),new Tile(2530,4836,0),new Tile(2533,4844,0),new Tile(2531,4849,0)}));
    private Altar WATERALTAR = new Altar(Rune.WATER_RUNE, new Tile(3183,3165,0), new Tile(2726,4832,0), new Tile(2718,4835,0),new Area(new Tile[]  {new Tile(2730,4844,0),new Tile(2732,4835,0),new Tile(2728,4829,0),new Tile(2725,4825,0),new Tile(2718,4822,0),new Tile(2710,4829,0),new Tile(2707,4836,0),new Tile(2706,4842,0),new Tile(2708,4843,0),new Tile(2718,4845,0)}));
    private Altar MINDALTAR = new Altar(Rune.MIND_RUNE, new Tile(2980,3514,0), new Tile(2793,4828,0), new Tile(2788,4840,0),new Area(new Tile[]  {new Tile(2795,4820,0),new Tile(2788,4819,0),new Tile(2782,4824,0),new Tile(2773,4831,0),new Tile(2763,4836,0),new Tile(2764,4845,0),new Tile(2774,4853,0),new Tile(2783,4854,0),new Tile(2795,4850,0),new Tile(2800,4846,0),new Tile(2801,4842,0),new Tile(2801,4838,0),new Tile(2802,4834,0),new Tile(2799,4826,0),new Tile(2798,4822,0)}));
    private Web web = new Web();
  
    /*
     * To Add Altar:
     * 
     * private Altar NewAltar =  new Altar(Rune rune, Tile ruinTile, Tile portalTile, Tile altarTile, Area altarArea);
     * 
     * private Altar AIRALTAR =  new Altar(Rune rune, Tile ruinTile, Tile portalTile, Tile altarTile, Area altarArea);
     * private Altar EARTHALTAR = new Altar(Rune.EARTH_RUNE, new Tile(3305,3472,0), new Tile(2655,4830,0), new Tile(2658,4839,0), new Area(new Tile(2640,4816,0), new Tile(2672,4853,0)));
     * private Altar FIREALTAR =  new Altar(Rune rune, Tile ruinTile, Tile portalTile, Tile altarTile, Area altarArea);
     * private Altar BODYALTAR =  new Altar(Rune rune, Tile ruinTile, Tile portalTile, Tile altarTile, Area altarArea);
     */
    
    @Override
    public boolean onStart() {
    	Object selectedRuneType = JOptionPane.showInputDialog(null, "Choose one", "Select Rune Type", JOptionPane.INFORMATION_MESSAGE, null, RuneOptions, RuneOptions[0]);
        if (null != selectedRuneType.toString()) {
            switch (selectedRuneType.toString()) {
                case "Air":
                    expPer = 5;
                    targetAltar = AIRALTAR;
                    break;
                case "Water":
                    expPer = 6;
                    targetAltar = WATERALTAR;
                    break;
                case "Earth":
                    expPer = 6.5;
                    targetAltar = EARTHALTAR;
                    break;
                case "Fire":
                    expPer = 7;
                    targetAltar = FIREALTAR;
                    break;
                case "Body":
                    expPer = 7.5;
                    targetAltar = BODYALTAR;
                    break;
                case "Mind":
                    expPer = 5.5;
                    targetAltar = MINDALTAR;
                    break;
            }
        }
        essPrice = (int)PriceLookup.getPrice(7936);
    	price = (int) PriceLookup.getPrice(targetAltar.getRune().getId());
    	sp = new SkillPaint();
		runTime = new Timer();
    	web.getData().add(targetAltar);
        startXP = Skills.getExperience(Skills.Skill.Runecraft);
        startLvl = Skills.getCurrentLevel(Skills.Skill.Runecraft);
        return true;
    }

    static enum Action {
        CRAFTING, BANKING, ENTER, EXIT; // Declare the actions you want your script to do, can call this anything
    }

    public Action getAction() {
        try {
        	if(playerIsInAltar()){
        		if(Inventory.contains("Pure essence") || Inventory.contains("Rune essence")){
        			return Action.CRAFTING;
        		}
        		else{
        			return Action.EXIT;
        		}
        	}
        	else{
        		if(Inventory.isFull() && !Inventory.contains(targetAltar.getRune().getId())){
        			return Action.ENTER;
        		}
        		else{
        			return Action.BANKING;
        		}
        	}

        } catch(final Exception e) {

        }
        return null;
    }

    private boolean playerIsInAltar(){
        return targetAltar.getAltarArea().contains(Players.getLocal().getLocation());
    }
   
    private long calculatePerHour(int xp){       
		return ((xp * 3600000) / runTime.getTimeElapsed());    
	}
    
    private long moneyMade(){
    	return ((price * runesCreated) - (essPrice * runesCreated));
    }

    
    //Main Loop
    @Override
    public int loop() {
    	int r = Random.nextInt(1, 10);
    	if(!Inventory.isOpen()){
    		Inventory.openTab();
    	}
    	if(r == 5){
    		Camera.rotateRandomly();
    		Camera.setPitch(Random.nextInt(60, 70));
    	}
		if(!Walking.isRunEnabled()){
        		Walking.setRun(Walking.getRunEnergy()>Random.nextInt(25,50));
		}
        try {
            switch(getAction()) {
                case CRAFTING: craftRunes();
                    break;
                case BANKING: bankStuff();
                    break;
                case EXIT: exitAltar();
                	break;
                case ENTER: enterAltar();
            	break;
                default:
                    break;
            }
        } catch(final Exception e) {
            return 100;
        }
        
        return Random.nextInt(500, 2000);
    }

    private void craftRunes(){
    	state = "Crafting";
        GameObject altar = GameObjects.getNearest("Altar");
		if(altar!=null) {
			if (altar.isOnScreen()) {
				altar.interact("Craft-rune");
                Time.sleepUntil(new Condition() {
                    @Override
                    public boolean check() {
                        return Inventory.contains(targetAltar.getRune().getId());
                    }
                }, Random.nextInt(600,1500));
			}
			else {
				Path toAltar = web.findPath(targetAltar.getAltarTile());
				if(toAltar!=null){
					toAltar.traverse();
                    Time.sleepUntil(new Condition() {
                        @Override
                        public boolean check() {
                            return altar.isOnScreen() || Walking.getDestinationDistance()<6 || Walking.getDestination()==null;
                        }
                    }, Random.nextInt(1000,1500));
				}
			}
		}
    }
        

    private void bankStuff() {
		state = "Banking";
		if (Bank.isOpen()) {
            if (Inventory.contains(targetAltar.getRune().getId())) {
                Bank.depositAll(targetAltar.getRune().getId());
                Time.sleepUntil(new Condition() {
                    @Override
                    public boolean check() {
                        return !Inventory.contains(targetAltar.getRune().getId());
                    }
                }, Random.nextInt(600, 1500));
            }
            if (!Inventory.isFull()) {
                if (Bank.contains("Pure essence")) {
                    Bank.withdrawAll("Pure essence");
                    Time.sleepUntil(new Condition() {
                        @Override
                        public boolean check() {
                            return Inventory.contains("Pure essence");
                        }
                    }, Random.nextInt(600, 1500));
                } else if (Bank.contains("Rune essence")) {
                    Bank.withdrawAll("Rune essence");
                    Time.sleepUntil(new Condition() {
                        @Override
                        public boolean check() {
                            return Inventory.contains("Rune essence");
                        }
                    }, Random.nextInt(600, 1500));
                }
            }
        }
        else {
            if(targetAltar.getRune().equals(Rune.MIND_RUNE)){
                web.openBank(WebBanks.FALADOR_WEST_BANK);
                Time.sleepUntil(new Condition() {
                    @Override
                    public boolean check() {
                        return Bank.isOpen() || Walking.getDestinationDistance() < 5 || Walking.getDestination() == null;
                    }
                });
            }
            else {
                web.openNearestBank();
                Time.sleepUntil(new Condition() {
                    @Override
                    public boolean check() {
                        return Bank.isOpen() || Walking.getDestinationDistance() < 5 || Walking.getDestination() == null;
                    }
                });
            }
        }
	}
    
    private void enterAltar(){
    	state = "Entering Altar";
		GameObject ruins = GameObjects.getNearest("Mysterious Ruins");
    	if(ruins!=null && ruins.isOnScreen()){
			ruins.interact("Enter");
            Time.sleepUntil(new Condition() {
                @Override
                public boolean check() {
                    return ruins==null || !ruins.isOnScreen();
                }
            }, Random.nextInt(600,1500));
		}
		else{
			Path toRuins = web.findPath(targetAltar.getRuinTile());
			if(toRuins!=null){
				toRuins.traverse();
                Time.sleepUntil(new Condition() {
                    @Override
                    public boolean check() {
                        return ruins.isOnScreen() || Walking.getDestinationDistance()<6 || Walking.getDestination()==null;
                    }
                });
			}
		}
    }

    private void exitAltar(){
    	state = "Exiting Altar";
		GameObject portal = GameObjects.getNearest("Portal");
		if(portal!=null && portal.isOnScreen()){
			portal.interact("Use");
            Time.sleepUntil(new Condition() {
                @Override
                public boolean check() {
                    return portal==null || !portal.isOnScreen();
                }
            }, 1000);
		}
		else{
			Path toPortal = web.findPath(targetAltar.getPortalTile());
			if(toPortal!=null){
				toPortal.traverse();
                Time.sleepUntil(new Condition() {
                    @Override
                    public boolean check() {
                        return portal.isOnScreen() || Walking.getDestinationDistance()<6 || Walking.getDestination()==null;
                    }
                }, Random.nextInt(600,1500));
			}
		}
    }

    //Paint Listener
    @Override
    public void onRepaint(Graphics g){
        g.setColor(Color.WHITE);
        if(runTime != null) {
            double numMade = ((Skills.getExperience(Skills.Skill.Runecraft)-startXP)/expPer);
            double currentXP = Skills.getExperience(Skills.Skill.Runecraft);
            double xpToLvl = ((Skills.experienceAtLevel(Skills.getCurrentLevel(Skills.Skill.Runecraft)+1))-currentXP);
            g.drawString("Swik's F2p RCer v1.0", 10 , 15 + 220);
            g.drawString("Time ran: " + runTime.getFormattedTime(), 10, 29 + 220);
            g.drawString("Made (P/h): " + (int)numMade +" (" + (int)runTime.calculatePerHour((long)numMade) + ")" , 10, 43+220);
            g.drawString("Lvl: " + Skills.getCurrentLevel(Skills.Skill.Runecraft)+" ("+ (Skills.getCurrentLevel(Skills.Skill.Runecraft)-startLvl)+")",10,57+220);
            g.drawString("XP Gained (P/h): " + (int)(currentXP-startXP)  +" (" + (int)runTime.calculatePerHour((long)(currentXP-startXP)) + ")", 10, 71 + 220);
            g.drawString("XP to Lvl (Time): " + (int)xpToLvl + " (" + formatTime(xpToLvl / runTime.calculatePerHour((long)(currentXP - startXP))) + ")", 10, 85+220);
            g.drawString("Gold (P/h): " + (int) (price * numMade) + " (" + (int) runTime.calculatePerHour((long)(price * numMade)) + ")", 10, 99 + 220);
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

}