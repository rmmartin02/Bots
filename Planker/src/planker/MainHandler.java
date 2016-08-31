package planker;

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
import org.tbot.methods.walking.Path;
import org.tbot.methods.walking.Walking;
import org.tbot.methods.web.Web;
import org.tbot.util.Condition;
import org.tbot.wrappers.*;

import java.awt.*;

/**
 * Created by Russell on 1/19/2016.
 */
@Manifest(name = "Swik's Planker", authors = "Swikoka", version = 1.0, description = "Makes Planks",category = ScriptCategory.MONEY_MAKING)
public class MainHandler extends AbstractScript implements PaintListener, MessageListener, InventoryListener {

    WidgetChild wood = Widgets.getWidget(403,89);
    WidgetChild oak = Widgets.getWidget(403,90);
    WidgetChild teak = Widgets.getWidget(403,91);
    WidgetChild mahogany = Widgets.getWidget(403,92);
    int startGold;
    int currentGold;
    int logPrice = 48;
    int plankPrice = 346;
    int dosesDrunk = 0;
    Boolean usingPotion = true;
    String state;
    Tile sawmill = new Tile(3302,3491,0);
    Web web = new Web();
    Timer timer;

    //Main Loop
    @Override
    public int loop() {
        if(!Inventory.isOpen()){
            Inventory.openTab();
        }
        if(Inventory.contains("Vial")){
            Inventory.getFirst("Vial").interact("Drop");
            Time.sleepUntil(new Condition() {
                @Override
                public boolean check() {
                    return !Inventory.contains("Vial");
                }
            }, Random.nextInt(1000, 1500));
        }
        if(usingPotion && (Inventory.contains("Stamina potion(4)") || Inventory.contains("Stamina potion(3)") || Inventory.contains("Stamina potion(2)") || Inventory.contains("Stamina potion(1)")) && Walking.getRunEnergy()<20){
            if(Inventory.contains("Stamina potion(1)")){
                Inventory.getFirst("Stamina potion(1)").interact("Drink");
                Time.sleepUntil(new Condition() {
                    @Override
                    public boolean check() {
                        return Walking.getRunEnergy()>20;
                    }
                }, Random.nextInt(1000, 1500));
            }
            else if(Inventory.contains("Stamina potion(2)")){
                Inventory.getFirst("Stamina potion(2)").interact("Drink");
                Time.sleepUntil(new Condition() {
                    @Override
                    public boolean check() {
                        return Walking.getRunEnergy()>20;
                    }
                }, Random.nextInt(1000, 1500));
            }
            else if(Inventory.contains("Stamina potion(3)")){
                Inventory.getFirst("Stamina potion(3)").interact("Drink");
                Time.sleepUntil(new Condition() {
                    @Override
                    public boolean check() {
                        return Walking.getRunEnergy()>20;
                    }
                }, Random.nextInt(1000, 1500));
            }
            else if(Inventory.contains("Stamina potion(4)")){
                Inventory.getFirst("Stamina potion(4)").interact("Drink");
                Time.sleepUntil(new Condition() {
                    @Override
                    public boolean check() {
                        return Walking.getRunEnergy()>20;
                    }
                }, Random.nextInt(1000, 1500));
            }
            dosesDrunk++;
        }
        if(!Walking.isRunEnabled()){
            Walking.setRun(Walking.getRunEnergy()>20);
        }
        //Making
        if(Inventory.contains("Logs") && !Inventory.contains("Plank")){
            NPC operator = Npcs.getNearest("Sawmill operator");
            if(operator!=null){
                if(operator.isOnScreen()){
                    if(wood.isVisible()){
                        state = "Buy-all";
                        wood.interact("Buy All");
                        Time.sleepUntil(new Condition() {
                            @Override
                            public boolean check() {
                                return !wood.isVisible();
                            }
                        }, Random.nextInt(1000, 1500));
                    }
                    else{
                        state = "Buy-plank";
                        operator.interact("Buy-plank");
                        Time.sleepUntil(new Condition() {
                            @Override
                            public boolean check() {
                                return wood.isVisible();
                            }
                        }, Random.nextInt(1000, 1500));
                    }
                }
                else{
                    Path toSawmill = Walking.findPath(sawmill);
                    if(toSawmill!=null){
                        state = "Going to operator";
                        toSawmill.traverse();
                        Time.sleepUntil(new Condition() {
                            @Override
                            public boolean check() {
                                return operator.isOnScreen() || Walking.getDestinationDistance()<Random.nextInt(4,8);
                            }
                        }, Random.nextInt(2000, 2500));
                    }
                }
            }
            else{

                Path toSawmill = Walking.findPath(sawmill);
                if(toSawmill!=null){
                    state = "Going to sawmill";
                    toSawmill.traverse();
                    Time.sleepUntil(new Condition() {
                        @Override
                        public boolean check() {
                            return  Walking.getDestinationDistance()<Random.nextInt(4,8);
                        }
                    }, Random.nextInt(2000, 2500));
                }
            }
        }
        //banking
        else{
            if(Bank.isOpen()){
                if(Inventory.contains("Plank")){
                    state = "Deposit Plank";
                    Bank.depositAll("Plank");
                    Time.sleepUntil(new Condition() {
                        @Override
                        public boolean check() {
                            return !Inventory.contains("Plank");
                        }
                    }, Random.nextInt(1000, 1500));
                }
                else if(usingPotion && (Bank.contains("Stamina Potion(4)") || Bank.contains("Stamina Potion(3)") || Bank.contains("Stamina Potion(2)") || Bank.contains("Stamina Potion(1)")) && (Inventory.contains("Vial") || !(Inventory.contains("Stamina potion(4)") || Inventory.contains("Stamina potion(3)") || Inventory.contains("Stamina potion(2)") || Inventory.contains("Stamina potion(1)")))){
                    state = "Potion Banking";
                    if(Inventory.contains("Vial")){
                        state = "Deposting Vial";
                        Bank.depositAll("Vial");
                        Time.sleepUntil(new Condition() {
                            @Override
                            public boolean check() {
                                return !Inventory.contains("Vial");
                            }
                        }, Random.nextInt(1000, 1500));
                    }
                    else if(!(Inventory.contains("Stamina potion(4)") || Inventory.contains("Stamina potion(3)") || Inventory.contains("Stamina potion(2)") || Inventory.contains("Stamina potion(1)"))){
                       state = "Withdrawing potion";
                        if(Bank.contains("Stamina potion(4)")){
                            Bank.withdraw("Stamina potion(4)", 1);
                            Time.sleepUntil(new Condition() {
                                @Override
                                public boolean check() {
                                    return Inventory.contains("Stamina potion(4)");
                                }
                            }, Random.nextInt(1000, 1500));
                        }
                        else if(Bank.contains("Stamina potion(3)")){
                            Bank.withdraw("Stamina potion(3)", 1);
                            Time.sleepUntil(new Condition() {
                                @Override
                                public boolean check() {
                                    return Inventory.contains("Stamina potion(3)");
                                }
                            }, Random.nextInt(1000, 1500));
                        }
                        else if(Bank.contains("Stamina potion(2)")){
                            Bank.withdraw("Stamina potion(2)", 1);
                            Time.sleepUntil(new Condition() {
                                @Override
                                public boolean check() {
                                    return Inventory.contains("Stamina potion(2)");
                                }
                            }, Random.nextInt(1000, 1500));
                        }
                        else if(Bank.contains("Stamina potion(1)")){
                            Bank.withdraw("Stamina potion(1)", 1);
                            Time.sleepUntil(new Condition() {
                                @Override
                                public boolean check() {
                                    return Inventory.contains("Stamina potion(1)");
                                }
                            }, Random.nextInt(1000, 1500));
                        }
                    }
                }
                else if(!Inventory.isFull()){
                    state = "Withdraw Logs";
                    Bank.withdrawAll("Logs");
                    Time.sleepUntil(new Condition() {
                        @Override
                        public boolean check() {
                            return Inventory.isFull();
                        }
                    }, Random.nextInt(1000, 1500));
                }
            }
            else{
                state = "Opening Bank";
                web.openNearestBank();
                Time.sleepUntil(new Condition() {
                    @Override
                    public boolean check() {
                        return Bank.isOpen() || Walking.getDestinationDistance()<Random.nextInt(4,8);
                    }
                }, Random.nextInt(2000, 2500));
            }
        }
        //to stop script, return -1
        return 100;
    }

    @Override
    public boolean onStart() {
        startGold = Inventory.getCount("Coins");
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
            currentGold = Inventory.getCount("Coins");
            double numMade = (startGold-currentGold)/100;
            g.drawString("Swik's Planker v1.0", 10 , 15 + 220);
            g.drawString("Time ran: " + timer.getFormattedTime(), 10, 29 + 220);
            g.drawString("Made (P/h): " + (int)numMade +" (" + (int)timer.calculatePerHour((long)numMade) + ")" , 10, 43+220);
            g.drawString("Gold (P/h): " + (int) ((plankPrice - (logPrice+100+(dosesDrunk*2000))) * numMade) + " (" + (int) timer.calculatePerHour((long)((plankPrice - (logPrice+100+(dosesDrunk*2000))) * numMade)) + ")", 10, 99 + 220);
            g.drawString("State: " + state, 10, 113 + 220);
            g.setColor(new Color(0, 80, 0, 85));
            Polygon p = new Polygon(new int[]{4,190,190,4},new int[]{222,222,336,336},4);
            g.fillPolygon(p);
            g.setColor(new Color(0, 0, 0, 255));
            g.drawPolygon(p);
        }
    }
}
