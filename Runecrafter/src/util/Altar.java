package util;
import org.tbot.methods.combat.magic.Rune;
import org.tbot.methods.web.Web;
import org.tbot.methods.web.actions.ObjectAction;
import org.tbot.methods.web.areas.WebArea;
import org.tbot.methods.web.nodes.WebNode;
import org.tbot.methods.web.nodes.connections.ActionConnection;
import org.tbot.methods.web.nodes.connections.WalkConnection;
import org.tbot.methods.web.nodes.connections.WebNodeConnection;
import org.tbot.wrappers.Area;
import org.tbot.wrappers.Tile;
 
public class Altar extends WebArea {

 
public Rune rune;
public Tile ruinTile;
public Tile portalTile;
public Tile altarTile;
public Area altarArea;
 
public Altar(Rune rune, Tile ruinTile, Tile portalTile, Tile altarTile, Area altarArea) {
	this.rune = rune;
	this.portalTile = portalTile;
	this.ruinTile = ruinTile;
	this.altarTile = altarTile;
	this.altarArea = altarArea;
}
 
public Rune getRune() {
  return rune;
}
 
public Tile getPortalTile() {
  return portalTile;
}
 
public Tile getRuinTile() {
  return ruinTile;
}
 
public Tile getAltarTile() {
  return altarTile;
}

public Area getAltarArea(){
	return altarArea;
}
 //3314 3255//
@Override
public void addTo(Web web) {

	WebNode startNode = web.getNearestWebNode(new Tile(3309,3235,0));
	WebNode node0 = web.getNode(new Tile(3309,3241,0));
	WebNode node1 = web.getNode(new Tile(3309,3247,0));
	WebNode node2 = web.getNode(new Tile(3312,3253,0));
	web.addWalkConnection(startNode, node0);
	web.addWalkConnection(node0, node1);
	web.addWalkConnection(node1, node2);
	web.addWalkConnection(node2, node1);
	web.addWalkConnection(node1, node0);
	web.addWalkConnection(node0, startNode);

	web.addWalkConnection(web.getNode(getPortalTile()),web.getNode(getAltarTile()));
	web.addWalkConnection(web.getNode(getAltarTile()),web.getNode(getPortalTile()));
	 
	ObjectAction enterRuins = new ObjectAction("Mysterious ruins", "Enter");
	ObjectAction exitRuins = new ObjectAction("Portal", "Use");	 
	 
	WebNode outside = web.getNearestWebNode(getRuinTile()); 
	WebNode inside = web.getNearestWebNode(getPortalTile());
	 
	ActionConnection enterConnection = new ActionConnection(outside,inside,enterRuins);
	ActionConnection exitConnection = new ActionConnection(inside, outside,exitRuins); 
	
	outside.addConnection(enterConnection);
	inside.addConnection(exitConnection);
	
}
 
@Override
  public void reset(Web web) {
   //Do nothing
  }
}