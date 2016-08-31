package walking;

import org.tbot.methods.web.Web;
import org.tbot.methods.web.actions.ObjectAction;
import org.tbot.methods.web.areas.WebArea;
import org.tbot.methods.web.nodes.WebNode;
import org.tbot.methods.web.nodes.connections.ActionConnection;
import org.tbot.wrappers.Tile;

/**
 * Created by Russell on 3/8/2016.
 */
public class LumbridgeSwampCaveWebArea extends WebArea {
    @Override
    public void addTo(Web web) {
        WebNode startNode = web.getNearestWebNode(new Tile(3169,3173,0));

        WebNode node0 = web.getNode(new Tile(3169,9573,0));
        WebNode node1 = web.getNode(new Tile(3155,9574,0));
        WebNode node2 = web.getNode(new Tile(3160,9588,0));
        WebNode node3 = web.getNode(new Tile(3177,9585,0));
        WebNode node4 = web.getNode(new Tile(3187,9580,0));
        WebNode node5 = web.getNode(new Tile(3191,9572,0));

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

        web.addWalkConnection(node4, node5);
        web.addWalkConnection(node5, node4);

        ObjectAction ObjectAction0 = new ObjectAction(new Tile(3169,3172,0), "Dark hole", "Climb-down");
        ActionConnection nodeConnector0 = new ActionConnection(startNode, node0, ObjectAction0);
        startNode.addConnection(nodeConnector0);
        ObjectAction ObjectAction1 = new ObjectAction(new Tile(3169,9572,0), "Climbing rope", "Climb");
        ActionConnection nodeConnector1 = new ActionConnection(node0, startNode, ObjectAction1);
        node0.addConnection(nodeConnector1);;
    }
}

