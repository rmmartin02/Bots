package walking;

import org.tbot.methods.web.Web;
import org.tbot.methods.web.actions.ObjectAction;
import org.tbot.methods.web.areas.WebArea;
import org.tbot.methods.web.nodes.WebNode;
import org.tbot.methods.web.nodes.connections.ActionConnection;
import org.tbot.wrappers.Tile;

/**
 * Created by Russell on 3/7/2016.
 */
public class SlayerTowerWebArea extends WebArea {
    @Override
    public void addTo(Web web) {
        WebNode startNode = web.getNearestWebNode(new Tile(3438,3537,0));

        WebNode node0 = web.getNode(new Tile(3433,3537,1));
        WebNode node1 = web.getNode(new Tile(3447,3540,1));
        WebNode node2 = web.getNode(new Tile(3447,3555,1));
        WebNode node3 = web.getNode(new Tile(3439,3562,1));
        WebNode node4 = web.getNode(new Tile(3431,3572,1));
        WebNode node5 = web.getNode(new Tile(3415,3572,1));
        WebNode node6 = web.getNode(new Tile(3419,3561,1));
        WebNode node7 = web.getNode(new Tile(3426,3556,1));

        WebNode node8 = web.getNode(new Tile(3426,3555,1));
        WebNode node9 = web.getNode(new Tile(3437,3548,1));
        WebNode node10 = web.getNode(new Tile(3415,3547,1));
        WebNode node11 = web.getNode(new Tile(3412,3540,1));

        WebNode node12 = web.getNode(new Tile(3417,3540,2));
        WebNode node13 = web.getNode(new Tile(3431,3539,2));
        WebNode node14 = web.getNode(new Tile(3441,3545,2));
        WebNode node15 = web.getNode(new Tile(3445,3554,2));

        WebNode node16 = web.getNode(new Tile(3445,3555,2));
        WebNode node17 = web.getNode(new Tile(3438,3568,2));
        WebNode node18 = web.getNode(new Tile(3433,3557,2));
        WebNode node19 = web.getNode(new Tile(3423,3551,2));
        WebNode node20 = web.getNode(new Tile(3415,3561,2));
        WebNode node21 = web.getNode(new Tile(3423,3571,2));

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

        web.addWalkConnection(node8, node10);
        web.addWalkConnection(node10, node8);

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

        ObjectAction upStair1 = new ObjectAction(new Tile(3434,3537,0), "Staircase", "Climb-up");
        ObjectAction downStair1 = new ObjectAction(new Tile(3415,3540,2), "Staircase", "Climb-down");

        ActionConnection upStair1Connection = new ActionConnection(startNode, node0, upStair1);
        ActionConnection downStair1Connection = new ActionConnection(node0, startNode, downStair1);

        startNode.addConnection(upStair1Connection);
        node0.addConnection(downStair1Connection);

        //7 - 8
        ObjectAction openDoor78 = new ObjectAction(new Tile(3426,3555,1), "Door", "Open");

        ActionConnection openDoor7_8 = new ActionConnection(node7, node9, openDoor78);
        ActionConnection openDoor8_7 = new ActionConnection(node8, node7, openDoor78);

        node7.addConnection(openDoor7_8);
        node8.addConnection(openDoor8_7);

        //11-12
        ObjectAction upStair2 = new ObjectAction(new Tile(3413,3540,1), "Staircase", "Climb-up");
        ObjectAction downStair2 = new ObjectAction(new Tile(3415,3540,2), "Staircase", "Climb-down");

        ActionConnection upStair2Connection = new ActionConnection(node11, node12, upStair2);
        ActionConnection downStair2Connection = new ActionConnection(node12, node11, downStair2);

        startNode.addConnection(upStair2Connection);
        node0.addConnection(downStair2Connection);

        //15-16
        ObjectAction openDoor1516 = new ObjectAction(new Tile(3445,3554,2), "Door", "Open");

        ActionConnection openDoor15_16 = new ActionConnection(node15, node16, openDoor78);
        ActionConnection openDoor16_15 = new ActionConnection(node16, node15, openDoor78);

        node7.addConnection(openDoor15_16);
        node8.addConnection(openDoor16_15);
    }
}
