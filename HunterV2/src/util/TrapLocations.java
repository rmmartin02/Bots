package util;

import org.tbot.methods.GameObjects;
import org.tbot.methods.GroundItems;
import org.tbot.methods.Players;
import org.tbot.methods.Time;
import org.tbot.methods.input.keyboard.Keyboard;
import org.tbot.util.Filter;
import org.tbot.wrappers.GameObject;
import org.tbot.wrappers.GroundItem;
import org.tbot.wrappers.Locatable;
import org.tbot.wrappers.Tile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TrapLocations {

    private final Filter<GameObject> trapTrees_filter = new Filter<GameObject>() {

        @Override
        public boolean accept(GameObject tree) {
            if (tree != null && tree.getID()==Var.getCurrentVar().getTrippedTrapID() && !trapTiles.contains(tree.getLocation())){
                return true;
            }
            return false;
        }
    };

    private List<Tile> trapTiles;
    private static TrapLocations instance;
    private static Tile defaultTile;

    public static TrapLocations getInstance() {
        if(instance == null) {
            instance = new TrapLocations();
        }
        return instance;
    }

    private TrapLocations() {
        defaultTile = Players.getLocal().getLocation();
        trapTiles = new ArrayList();
    }

    public List<Tile> getTrapTiles() {
        return trapTiles;
    }

    public void addTrapTile(Tile tile) {
        trapTiles.add(tile);
    }

    public void changeDefaultTile(Tile tile){
        defaultTile = tile;
    }

    public void changeTrapLocation(Tile currentTile){
        Tile closestTrapTile = trapTiles.get(0);
        int smallestDist = 100;
        for(int i = 0; i<trapTiles.size(); i++){
            if(!trapTiles.get(i).equals(currentTile) && trapTiles.get(i).distance(currentTile)<smallestDist){
                closestTrapTile = trapTiles.get(i);
            }
        }
        Tile[] targetTiles = {new Tile(currentTile.getX()+1, currentTile.getY(), 0), new Tile(currentTile.getX()-1, currentTile.getY(), 0), new Tile(currentTile.getX(), currentTile.getY()+1, 0), new Tile(currentTile.getX(), currentTile.getY()-1, 0), new Tile(currentTile.getX()-1, currentTile.getY()-1, 0), new Tile(currentTile.getX()+1, currentTile.getY()+1, 0), new Tile(currentTile.getX()+1, currentTile.getY()-1, 0), new Tile(currentTile.getX()-1, currentTile.getY()+1, 0)};
        Tile targetTile = targetTiles[0];
        smallestDist = 100;
        for(int i = 0; i<targetTiles.length; i++){
            if(!trapTiles.contains(targetTiles[i]) && targetTiles[i].distance(closestTrapTile)<smallestDist){
                targetTile = targetTiles[i];
            }
        }
        for(int i = 0; i<trapTiles.size();i++){
            if(currentTile.equals(trapTiles.get(i))){
                trapTiles.add(i, targetTile);
            }
        }
        Keyboard.sendText(" ", true);
    }

    private static final Filter<GameObject> actvTrap_filter = new Filter<GameObject>() {

        @Override
        public boolean accept(GameObject actvTrap) {
            if (actvTrap!=null && TrapLocations.getInstance().getTrapTiles().contains(actvTrap.getLocation()) && actvTrap.getID()== Var.getCurrentVar().getActvTrapID()){
                return true;
            }
            return false;
        }
    };

    private static final Filter<GameObject> placedTrap_filter = new Filter<GameObject>() {

        @Override
        public boolean accept(GameObject placedTrap) {
            if (placedTrap!=null && TrapLocations.getInstance().getTrapTiles().contains(placedTrap.getLocation()) && placedTrap.getID()== Var.getCurrentVar().getPlacedTrapID()){
                return true;
            }
            return false;
        }
    };

    private static final Filter<GameObject> trippedTrap_filter = new Filter<GameObject>() {

        @Override
        public boolean accept(GameObject trippedTrap) {
            if (trippedTrap!=null && TrapLocations.getInstance().getTrapTiles().contains(trippedTrap.getLocation()) && trippedTrap.getID()== Var.getCurrentVar().getTrippedTrapID()){
                return true;
            }
            return false;
        }
    };

    public void pickUpTraps(){

        final Filter<GroundItem> trapOnGround_filter = new Filter<GroundItem>() {

            @Override
            public boolean accept(GroundItem trap) {
                if (trap!=null && TrapLocations.getInstance().getTrapTiles().contains(trap.getLocation()) && trap.getID()== Var.getCurrentVar().getInvTrapID()){
                    return true;
                }
                return false;
            }
        };

        GameObject actvTrap = GameObjects.getNearest(actvTrap_filter);
        GameObject trippedTrap = GameObjects.getNearest(trippedTrap_filter);
        GameObject placedTrap = GameObjects.getNearest(placedTrap_filter);
        GroundItem trapOnGround = GroundItems.getNearest(trapOnGround_filter);
        while(actvTrap!=null && trippedTrap!=null && placedTrap!=null && trapOnGround!=null){
            if(actvTrap!=null){
                actvTrap.interact("Check");
                Time.sleep(500, 800);
            }
            else if(trippedTrap!=null){
                trippedTrap.interact("Dismantle");
                Time.sleep(500, 800);
            }
            else if(placedTrap!=null){
                placedTrap.interact("Dismantle");
                Time.sleep(500, 800);
            }
            else if(trapOnGround!=null){
                trapOnGround.pickUp();
                Time.sleep(500, 800);
            }
            pickUpTraps();
        }
    }

    public void pickUpSalTraps(){

        final Filter<GroundItem> ropeOnGround_filter = new Filter<GroundItem>() {

            @Override
            public boolean accept(GroundItem rOnG) {
                if (rOnG != null && locatableIsWithinOfTrapTile(rOnG, 2) && rOnG.getName().equalsIgnoreCase("Rope")){
                    return true;
                }
                return false;
            }
        };
        final Filter<GroundItem> netOnGround_filter = new Filter<GroundItem>() {

            @Override
            public boolean accept(GroundItem nOnG) {
                if (nOnG != null && locatableIsWithinOfTrapTile(nOnG, 2) && nOnG.getName().equalsIgnoreCase("Small fishing net")){
                    return true;
                }
                return false;
            }
        };

        GameObject actvTrap = GameObjects.getNearest(actvTrap_filter);
        GameObject trippedTrap = GameObjects.getNearest(trippedTrap_filter);
        GameObject placedTrap = GameObjects.getNearest(placedTrap_filter);
        GroundItem rope = GroundItems.getNearest(ropeOnGround_filter);
        GroundItem net = GroundItems.getNearest(netOnGround_filter);
        while(actvTrap!=null && rope!=null && placedTrap!=null && net!=null){
            if(actvTrap!=null){
                actvTrap.interact("Check");
                Time.sleep(500, 800);
            }
            else if(placedTrap!=null){
                placedTrap.interact("Dismantle");
                Time.sleep(500, 800);
            }
            else if(rope!=null){
                rope.pickUp();
                Time.sleep(500, 800);
            }
            else if(net!=null){
                net.pickUp();
                Time.sleep(500, 800);
            }
            pickUpSalTraps();
        }
    }

    public void setSalTrapLocations(){
        trapTiles.clear();
        for(int i = 0; i<Var.numTraps(); i++){
            trapTiles.add(i, GameObjects.getNearest(trapTrees_filter).getLocation());
        }
    }
    
    public void setTrapLocations(int type){
        trapTiles.clear();
        if(Var.numTraps()==1) {
            trapTiles.add(new Tile(defaultTile.getX(), defaultTile.getY(), 0));
        }
        else if(Var.numTraps()==2) {
            trapTiles.add(new Tile(defaultTile.getX() + 1, defaultTile.getY(), 0));
            trapTiles.add(new Tile(defaultTile.getX() - 1, defaultTile.getY(), 0));
        }
        else if(Var.numTraps()==3) {
            if (type == 0) {
                trapTiles.add(new Tile(defaultTile.getX(), defaultTile.getY() + 1, 0));
                trapTiles.add(new Tile(defaultTile.getX() + 1, defaultTile.getY(), 0));
                trapTiles.add(new Tile(defaultTile.getX() - 1, defaultTile.getY(), 0));
            } else if (type == 1) {
                trapTiles.add(new Tile(defaultTile.getX(), defaultTile.getY(), 0));
                trapTiles.add(new Tile(defaultTile.getX(), defaultTile.getY() + 1, 0));
                trapTiles.add(new Tile(defaultTile.getX(), defaultTile.getY() - 1, 0));
            } else if (type == 2) {
                trapTiles.add(new Tile(defaultTile.getX(), defaultTile.getY(), 0));
                trapTiles.add(new Tile(defaultTile.getX() + 1, defaultTile.getY(), 0));
                trapTiles.add(new Tile(defaultTile.getX() - 1, defaultTile.getY(), 0));
            } else if (type == 3) {
                trapTiles.add(new Tile(defaultTile.getX(), defaultTile.getY(), 0));
                trapTiles.add(new Tile(defaultTile.getX() + 1, defaultTile.getY() + 1, 0));
                trapTiles.add(new Tile(defaultTile.getX() - 1, defaultTile.getY() - 1, 0));
            }
        }
        else if(Var.numTraps()==4){
            if(type==0) {
                    trapTiles.add(new Tile(defaultTile.getX() + 1, defaultTile.getY() + 1, 0));
                    trapTiles.add(new Tile(defaultTile.getX() - 1, defaultTile.getY() - 1, 0));
                    trapTiles.add(new Tile(defaultTile.getX() + 1, defaultTile.getY() - 1, 0));
                    trapTiles.add(new Tile(defaultTile.getX() - 1, defaultTile.getY() + 1, 0));
                }
            else if(type==1) {
                    trapTiles.add(new Tile(defaultTile.getX(), defaultTile.getY() + 1, 0));
                    trapTiles.add(new Tile(defaultTile.getX(), defaultTile.getY() - 1, 0));
                    trapTiles.add(new Tile(defaultTile.getX() - 1, defaultTile.getY(), 0));
                    trapTiles.add(new Tile(defaultTile.getX() + 1, defaultTile.getY(), 0));
                }
            else if(type==2) {
                    trapTiles.add(new Tile(defaultTile.getX(), defaultTile.getY(), 0));
                    trapTiles.add(new Tile(defaultTile.getX(), defaultTile.getY() + 1, 0));
                    trapTiles.add(new Tile(defaultTile.getX(), defaultTile.getY() - 1, 0));
                    trapTiles.add(new Tile(defaultTile.getX(), defaultTile.getY() + 2, 0));
                }
            else if(type==3) {
                    trapTiles.add(new Tile(defaultTile.getX() + 1, defaultTile.getY(), 0));
                    trapTiles.add(new Tile(defaultTile.getX(), defaultTile.getY(), 0));
                    trapTiles.add(new Tile(defaultTile.getX() - 1, defaultTile.getY(), 0));
                    trapTiles.add(new Tile(defaultTile.getX() + 2, defaultTile.getY(), 0));
                }
            else if(type==4) {
                    trapTiles.add(new Tile(defaultTile.getX(), defaultTile.getY(), 0));
                    trapTiles.add(new Tile(defaultTile.getX() + 1, defaultTile.getY() + 1, 0));
                    trapTiles.add(new Tile(defaultTile.getX() + 2, defaultTile.getY() + 2, 0));
                    trapTiles.add(new Tile(defaultTile.getX() - 1, defaultTile.getY() - 1, 0));
                }
        }
        else if(Var.numTraps()==5){
            if(type==0) {
                    trapTiles.add(new Tile(defaultTile.getX() + 1, defaultTile.getY() + 1, 0));
                    trapTiles.add(new Tile(defaultTile.getX() - 1, defaultTile.getY() - 1, 0));
                    trapTiles.add(new Tile(defaultTile.getX() + 1, defaultTile.getY() - 1, 0));
                    trapTiles.add(new Tile(defaultTile.getX() - 1, defaultTile.getY() + 1, 0));
                    trapTiles.add(new Tile(defaultTile.getX(), defaultTile.getY(), 0));
                }
            }
            else if(type==1) {
                    trapTiles.add(new Tile(defaultTile.getX(), defaultTile.getY(), 0));
                    trapTiles.add(new Tile(defaultTile.getX(), defaultTile.getY() + 1, 0));
                    trapTiles.add(new Tile(defaultTile.getX(), defaultTile.getY() - 1, 0));
                    trapTiles.add(new Tile(defaultTile.getX(), defaultTile.getY() + 2, 0));
                    trapTiles.add(new Tile(defaultTile.getX() - 1, defaultTile.getY() - 2, 0));
                }
                else if(type==2) {
                    trapTiles.add(new Tile(defaultTile.getX() + 1, defaultTile.getY(), 0));
                    trapTiles.add(new Tile(defaultTile.getX(), defaultTile.getY(), 0));
                    trapTiles.add(new Tile(defaultTile.getX() - 1, defaultTile.getY(), 0));
                    trapTiles.add(new Tile(defaultTile.getX() + 2, defaultTile.getY(), 0));
                    trapTiles.add(new Tile(defaultTile.getX() - 2, defaultTile.getY(), 0));
                }
            else if(type==3) {
                    trapTiles.add(new Tile(defaultTile.getX(), defaultTile.getY(), 0));
                    trapTiles.add(new Tile(defaultTile.getX() + 1, defaultTile.getY() + 1, 0));
                    trapTiles.add(new Tile(defaultTile.getX() + 2, defaultTile.getY() + 2, 0));
                    trapTiles.add(new Tile(defaultTile.getX() - 1, defaultTile.getY() - 1, 0));
                    trapTiles.add(new Tile(defaultTile.getX() - 2, defaultTile.getY() - 2, 0));
                }
        }

        private static boolean locatableIsWithinOfTrapTile(Locatable loc, int dist){
            for(int i = 0; i<TrapLocations.getInstance().getTrapTiles().size(); i++){
                if(loc.distance(TrapLocations.getInstance().getTrapTiles().get(i))<dist){
                    return true;
                }
            }
            return false;
        }

    }