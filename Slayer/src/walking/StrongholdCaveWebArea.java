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
public class StrongholdCaveWebArea extends WebArea {

    //smoke devil area after node10
    /*
    WebNode startNode = web.getNearestWebNode(new Tile(2365,9781,0));
    WebNode node0 = web.getNode(new Tile(2358,9778,0));
    ObjectAction ObjectAction0 = new ObjectAction(new Tile(2357,9778,0), "Root", "Step-over");
    WebNode node1 = web.getNode(new Tile(2356,9778,0));
    ActionConnection nodeConnector0 = new ActionConnection(node0, node1, ObjectAction0);
    node0.addConnection(nodeConnector0);
    ActionConnection nodeConnector1 = new ActionConnection(node1, node0, ObjectAction0);
    node1.addConnection(nodeConnector1);
    web.addWalkConnection(startNode, node0);
    web.addWalkConnection(node0, node1);
    WebNode node2 = web.getNode(new Tile(2356,9782,0));
    web.addWalkConnection(node1, node2);
    ObjectAction ObjectAction1 = new ObjectAction(new Tile(2356,9783,0), "Crevice", "Use");
    WebNode node3 = web.getNode(new Tile(3748,5761,0));
    ObjectAction ObjectAction2 = new ObjectAction(new Tile(3748,5760,0), "Crevice", "Use");
    WebNode node4 = web.getNode(new Tile(3749,5777,0));
    WebNode node5 = web.getNode(new Tile(3765,5772,0));
    WebNode node6 = web.getNode(new Tile(3737,5784,0));
    WebNode node7 = web.getNode(new Tile(3746,5796,0));
    web.addWalkConnection(node3, node4);
    web.addWalkConnection(node4, node5);
    web.addWalkConnection(node4, node6);
    web.addWalkConnection(node6, node7);*/

    //after 15
    /*
    WebNode startNode = web.getNearestWebNode(new Tile(2380,9738,0));
    WebNode node0 = web.getNode(new Tile(2387,9740,0));
    ObjectAction ObjectAction0 = new ObjectAction(new Tile(2388,9740,0), "Root", "Step-over");
    WebNode node1 = web.getNode(new Tile(2389,9740,0));
    WebNode node2 = web.getNode(new Tile(2404,9741,0));
    WebNode node3 = web.getNode(new Tile(2409,9755,0));
    WebNode node4 = web.getNode(new Tile(2420,9746,0));
    WebNode node5 = web.getNode(new Tile(2420,9749,0));
    ObjectAction ObjectAction0 = new ObjectAction(new Tile(2420,9750,0), "Root", "Step-over");
    WebNode node6 = web.getNode(new Tile(2420,9751,0));
    WebNode node7 = web.getNode(new Tile(2424,9762,0));*/


    @Override
    public void addTo(Web web) {
        WebNode startNode = web.getNearestWebNode(new Tile(2430,3424,0));
        ObjectAction ObjectActionEnter = new ObjectAction(new Tile(2429,3424,0), "Cave", "Enter");
        WebNode node0 = web.getNode(new Tile(2444,9825,0));
        ObjectAction ObjectActionExit = new ObjectAction(new Tile(2445,9825,0), "Crevice", "Use");
        ActionConnection nodeConnectorEnter = new ActionConnection(startNode, node0, ObjectActionEnter);
        startNode.addConnection(nodeConnectorEnter);
        ActionConnection nodeConnectorExit = new ActionConnection(node0, startNode, ObjectActionExit);
        node0.addConnection(nodeConnectorExit);

        WebNode node1 = web.getNode(new Tile(2439,9812,0));
        WebNode node2 = web.getNode(new Tile(2439,9797,0));
        WebNode node3 = web.getNode(new Tile(2430,9788,0));
        WebNode node4 = web.getNode(new Tile(2416,9785,0));
        WebNode node5 = web.getNode(new Tile(2405,9786,0));
        WebNode node6 = web.getNode(new Tile(2414,9777,0));

        WebNode node7 = web.getNode(new Tile(2393,9788,0));
        ObjectAction ObjectActionChop = new ObjectAction(new Tile(2392,9788,0), "Roots", "Chop");
        WebNode node8 = web.getNode(new Tile(2391,9788,0));
        ActionConnection chopConnector0 = new ActionConnection(node7, node8, ObjectActionChop);
        node7.addConnection(chopConnector0);
        ActionConnection chopConnector1 = new ActionConnection(node8, node7, ObjectActionChop);
        node8.addConnection(chopConnector1);

        WebNode node9 = web.getNode(new Tile(2377,9785,0));
        WebNode node10 = web.getNode(new Tile(2365,9781,0));
        WebNode node11 = web.getNode(new Tile(2364,9767,0));
        WebNode node12 = web.getNode(new Tile(2370,9757,0));
        WebNode node13 = web.getNode(new Tile(2374,9744,0));

        WebNode node14 = web.getNode(new Tile(2378,9738,0));
        ObjectAction ObjectActionOver = new ObjectAction(new Tile(2379,9738,0), "Root", "Step-over");
        WebNode node15 = web.getNode(new Tile(2380,9738,0));
        ActionConnection overConnector0 = new ActionConnection(node14, node15, ObjectActionOver);
        node14.addConnection(overConnector0);
        ActionConnection overConnector1 = new ActionConnection(node15, node14, ObjectActionOver);
        node15.addConnection(overConnector1);

        WebNode node16 = web.getNode(new Tile(2390,9749,0));

        web.addWalkConnection(startNode, node0);
        web.addWalkConnection(node0, node1);
        web.addWalkConnection(node1, node2);
        web.addWalkConnection(node2, node3);
        web.addWalkConnection(node3, node4);
        web.addWalkConnection(node4, node5);
        web.addWalkConnection(node5, node6);
        web.addWalkConnection(node6, node7);
        web.addWalkConnection(node7, node8);
        web.addWalkConnection(node8, node9);
        web.addWalkConnection(node9, node10);
        web.addWalkConnection(node10, node11);
        web.addWalkConnection(node11, node12);
        web.addWalkConnection(node12, node13);
        web.addWalkConnection(node13, node14);
        web.addWalkConnection(node14, node15);
        web.addWalkConnection(node15, node16);

        //fire giant after node10
        WebNode firenode0 = web.getNode(new Tile(2358,9778,0));
        ObjectAction ObjectActionFire = new ObjectAction(new Tile(2357,9778,0), "Root", "Step-over");
        WebNode firenode1 = web.getNode(new Tile(2356,9778,0));
        WebNode firenode2 = web.getNode(new Tile(2351,9771,0));
        ActionConnection firenodeConnector0 = new ActionConnection(firenode0, firenode1, ObjectActionFire);
        firenode0.addConnection(firenodeConnector0);
        ActionConnection firenodeConnector1 = new ActionConnection(firenode1, firenode0, ObjectActionFire);
        firenode1.addConnection(firenodeConnector1);
        web.addWalkConnection(node10, firenode0);
        web.addWalkConnection(firenode0, firenode1);
        web.addWalkConnection(firenode1, firenode2);

        //kalphite area after node 0
        WebNode kalStartNode = web.getNearestWebNode(new Tile(2436,9824,0));
        ObjectAction ObjectActionkal0 = new ObjectAction(new Tile(2435,9824,0), "Crevice", "Use");
        ObjectAction ObjectActionkal1 = new ObjectAction(new Tile(3749,5849,0), "Crevice", "Use");
        WebNode kalnode0 = web.getNode(new Tile(3748,5849,0));
        WebNode kalnode1 = web.getNode(new Tile(3763,5853,0));
        ActionConnection kalnodeConnector0 = new ActionConnection(kalStartNode, kalnode0, ObjectActionkal0);
        kalStartNode.addConnection(kalnodeConnector0);
        ActionConnection kalnodeConnector1 = new ActionConnection(kalnode0, kalStartNode, ObjectActionkal1);
        kalnode0.addConnection(kalnodeConnector1);
        web.addWalkConnection(node0, kalStartNode);
        web.addWalkConnection(kalStartNode, kalnode0);
        web.addWalkConnection(kalnode0, kalnode1);
    }
}
