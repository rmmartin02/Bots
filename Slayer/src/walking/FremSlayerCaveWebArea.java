package walking;

import org.tbot.methods.web.Web;
import org.tbot.methods.web.actions.ObjectAction;
import org.tbot.methods.web.areas.WebArea;
import org.tbot.methods.web.nodes.WebNode;
import org.tbot.methods.web.nodes.connections.ActionConnection;
import org.tbot.wrappers.Tile;

/**
 * Created by Russell on 2/29/2016.
 */
public class FremSlayerCaveWebArea extends WebArea {
    @Override
    public void addTo(Web web) {
        WebNode startNode = web.getNearestWebNode(new Tile(2766,3615,0));
        WebNode node0 = web.getNode(new Tile(2778,3613,0));
        WebNode node1 = web.getNode(new Tile(2788,3614,0));
        WebNode node2 = web.getNode(new Tile(2796,3615,0));
        WebNode node3 = web.getNode(new Tile(2808,10002,0));
        WebNode node4 = web.getNode(new Tile(2796,9997,0));
        WebNode node5 = web.getNode(new Tile(2783,9998,0));
        WebNode node6 = web.getNode(new Tile(2778,10012,0));
        WebNode node7 = web.getNode(new Tile(2792,10018,0));
        WebNode node8 = web.getNode(new Tile(2803,10022,0));
        WebNode node9 = web.getNode(new Tile(2795,10033,0));
        WebNode node10 = web.getNode(new Tile(2781,10035,0));
        WebNode node11 = web.getNode(new Tile(2768,10034,0));
        WebNode node12 = web.getNode(new Tile(2757,10029,0));
        WebNode node13 = web.getNode(new Tile(2765,10019,0));
        WebNode node14 = web.getNode(new Tile(2761,10008,0));
        WebNode node15 = web.getNode(new Tile(2759,9996,0));
        WebNode node16 = web.getNode(new Tile(2746,9998,0));
        WebNode node17 = web.getNode(new Tile(2742,10010,0));
        WebNode node18 = web.getNode(new Tile(2745,10025,0));
        WebNode node19 = web.getNode(new Tile(2733,10029,0));
        WebNode node20 = web.getNode(new Tile(2722,10024,0));
        WebNode node21 = web.getNode(new Tile(2711,10027,0));
        WebNode node22 = web.getNode(new Tile(2704,10017,0));
        WebNode node23 = web.getNode(new Tile(2715,10011,0));
        WebNode node24 = web.getNode(new Tile(2722,10001,0));
        WebNode node25 = web.getNode(new Tile(2718,9990,0));
        WebNode node26 = web.getNode(new Tile(2706,9993,0));
        WebNode node27 = web.getNode(new Tile(2696,10001,0));

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

        web.addWalkConnection(node5, node6);
        web.addWalkConnection(node6, node5);

        web.addWalkConnection(node6, node7);
        web.addWalkConnection(node7, node6);

        web.addWalkConnection(node7, node8);
        web.addWalkConnection(node8, node7);

        web.addWalkConnection(node8, node9);
        web.addWalkConnection(node9, node8);

        web.addWalkConnection(node9, node10);
        web.addWalkConnection(node10, node9);

        web.addWalkConnection(node10, node11);
        web.addWalkConnection(node11, node10);

        web.addWalkConnection(node11, node12);
        web.addWalkConnection(node12, node11);

        web.addWalkConnection(node12, node13);
        web.addWalkConnection(node13, node12);

        web.addWalkConnection(node13, node14);
        web.addWalkConnection(node14, node13);

        web.addWalkConnection(node14, node15);
        web.addWalkConnection(node15, node14);

        web.addWalkConnection(node15, node16);
        web.addWalkConnection(node16, node15);

        web.addWalkConnection(node16, node17);
        web.addWalkConnection(node17, node16);

        web.addWalkConnection(node17, node18);
        web.addWalkConnection(node18, node17);

        web.addWalkConnection(node18, node19);
        web.addWalkConnection(node19, node18);

        web.addWalkConnection(node19, node20);
        web.addWalkConnection(node20, node19);

        web.addWalkConnection(node20, node21);
        web.addWalkConnection(node21, node20);

        web.addWalkConnection(node21, node22);
        web.addWalkConnection(node22, node21);

        web.addWalkConnection(node22, node23);
        web.addWalkConnection(node23, node22);

        web.addWalkConnection(node23, node24);
        web.addWalkConnection(node24, node23);

        web.addWalkConnection(node24, node25);
        web.addWalkConnection(node25, node24);

        web.addWalkConnection(node25, node26);
        web.addWalkConnection(node26, node25);

        web.addWalkConnection(node26, node27);
        web.addWalkConnection(node27, node26);

        ObjectAction enterCave = new ObjectAction(new Tile(2797,3614,0), "Cave Entrance", "Enter");
        ObjectAction exitCave = new ObjectAction(new Tile(2809,10001,0), "Tunnel", "Enter");

        ActionConnection enterConnection = new ActionConnection(node2, node3, enterCave);
        ActionConnection exitConnection = new ActionConnection(node3, node2, exitCave);

        node2.addConnection(enterConnection);
        node3.addConnection(exitConnection);
    }
}
