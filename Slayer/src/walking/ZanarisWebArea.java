package walking;

import org.tbot.methods.web.Web;
import org.tbot.methods.web.actions.ObjectAction;
import org.tbot.methods.web.areas.WebArea;
import org.tbot.methods.web.nodes.WebNode;
import org.tbot.methods.web.nodes.connections.ActionConnection;
import org.tbot.wrappers.Tile;

/**
 * Created by Russell on 3/2/2016.
 */
public class ZanarisWebArea extends WebArea {
    @Override
    public void addTo(Web web) {
        WebNode startNode = web.getNearestWebNode(new Tile(3201,3169,0));
        WebNode node0 = web.getNode(new Tile(2452,4473,0));
        WebNode node1 = web.getNode(new Tile(2452,4462,0));
        WebNode node2 = web.getNode(new Tile(2452,4452,0));
        WebNode node3 = web.getNode(new Tile(2450,4442,0));
        WebNode node4 = web.getNode(new Tile(2446,4433,0));

        web.addWalkConnection(startNode, node0);
        web.addWalkConnection(node0, startNode);

        web.addWalkConnection(node0, node1);
        web.addWalkConnection(node1, node0);

        web.addWalkConnection(node1, node2);
        web.addWalkConnection(node2, node1);

        web.addWalkConnection(node2, node3);
        web.addWalkConnection(node3, node2);

        web.addWalkConnection(node3, node4);
        web.addWalkConnection(node4, node3);

        ObjectAction enterZanaris = new ObjectAction(new Tile(3202,3169,0),"Door","Open");
        ObjectAction exitZanaris = new ObjectAction(new Tile(2452,4473,0),"Fairy ring","Use");

        ActionConnection enterConnection = new ActionConnection(startNode, node0, enterZanaris);
        ActionConnection exitConnection = new ActionConnection(node0, startNode, exitZanaris);

        startNode.addConnection(enterConnection);
        node0.addConnection(exitConnection);
    }
}
