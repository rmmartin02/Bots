package util;

import org.tbot.methods.web.Web;
import org.tbot.methods.web.actions.ObjectAction;
import org.tbot.methods.web.areas.WebArea;
import org.tbot.methods.web.nodes.WebNode;
import org.tbot.methods.web.nodes.connections.ActionConnection;
import org.tbot.wrappers.Tile;

/**
 * Created by Russell on 1/29/2016.
 */
public class DesertWebArea extends WebArea {
    public void addTo(Web web){

        //fountain 3366,2971
        //well 3359,2972
        //general store 3360,2988

        WebNode startNode1 = web.getNearestWebNode(new Tile(3304,3122,0));
        WebNode startNode0 = web.getNode(new Tile(3304,3117,0));
        WebNode startNode = web.getNode(new Tile(3304,3115,0));
        WebNode node0 = web.getNode(new Tile(3312,3104,0));
        WebNode node1 = web.getNode(new Tile(3324,3097,0));
        WebNode node2 = web.getNode(new Tile(3337,3089,0));
        WebNode node3 = web.getNode(new Tile(3350,3083,0));
        WebNode node4 = web.getNode(new Tile(3365,3082,0));
        WebNode node5 = web.getNode(new Tile(3381,3082,0));
        WebNode node6 = web.getNode(new Tile(3396,3085,0));
        WebNode node7 = web.getNode(new Tile(3407,3085,0));
        WebNode node8 = web.getNode(new Tile(3402,3100,0));
        WebNode node9 = web.getNode(new Tile(3400,3116,0));
        WebNode node10 = web.getNode(new Tile(3413,3073,0));
        WebNode node11 = web.getNode(new Tile(3418,3061,0));
        WebNode node12 = web.getNode(new Tile(3421,3045,0));
        WebNode node13 = web.getNode(new Tile(3398,3073,0));
        WebNode node14 = web.getNode(new Tile(3400,3058,0));
        WebNode node15 = web.getNode(new Tile(3400,3045,0));
        WebNode node16 = web.getNode(new Tile(3389,3053,0));
        WebNode node17 = web.getNode(new Tile(3381,3064,0));
        WebNode node18 = web.getNode(new Tile(3427,3072,0));
        WebNode node19 = web.getNode(new Tile(3380,3094,0));
        WebNode node20 = web.getNode(new Tile(3371,3104,0));
        WebNode node21 = web.getNode(new Tile(3371,3116,0));
        WebNode node22 = web.getNode(new Tile(3376,3105,0));
        WebNode node23 = web.getNode(new Tile(3389,3105,0));
        WebNode node31 = web.getNode(new Tile(3359,2963,0));
        WebNode node32 = web.getNode(new Tile(3411,3045,0));
        WebNode node33 = web.getNode(new Tile(3408,3031,0));
        WebNode node34 = web.getNode(new Tile(3422,3031,0));
        WebNode node35 = web.getNode(new Tile(3423,3017,0));
        WebNode node36 = web.getNode(new Tile(3408,3018,0));
        WebNode node37 = web.getNode(new Tile(3394,3019,0));
        WebNode node38 = web.getNode(new Tile(3384,3013,0));
        WebNode node39 = web.getNode(new Tile(3394,3005,0));
        WebNode node40 = web.getNode(new Tile(3423,3004,0));
        WebNode node41 = web.getNode(new Tile(3437,3004,0));
        WebNode node42 = web.getNode(new Tile(3448,3015,0));
        WebNode node43 = web.getNode(new Tile(3443,2994,0));
        WebNode node44 = web.getNode(new Tile(3408,3005,0));

        WebNode node24 = web.getNode(new Tile(3351,3068,0));
        WebNode node25 = web.getNode(new Tile(3352,3044,0));
        WebNode node26 = web.getNode(new Tile(3352,3031,0));
        WebNode node27 = web.getNode(new Tile(3352,3016,0));
        WebNode node28 = web.getNode(new Tile(3353,3003,0));
        WebNode node29 = web.getNode(new Tile(3357,2988,0));
        WebNode node30 = web.getNode(new Tile(3358,2975,0));

        //to lizards
        web.addWalkConnection(startNode1, startNode0);
        web.addWalkConnection(startNode0, startNode1);

        web.addWalkConnection(startNode0, startNode);
        web.addWalkConnection(startNode, startNode0);


        web.addWalkConnection(node0, startNode);
        web.addWalkConnection(startNode, node0);

        web.addWalkConnection(node1, node0);
        web.addWalkConnection(node0, node1);

        web.addWalkConnection(node2, node1);
        web.addWalkConnection(node1, node2);

        web.addWalkConnection(node2, node3);
        web.addWalkConnection(node3, node2);

        web.addWalkConnection(node3, node4);
        web.addWalkConnection(node4, node3);

        web.addWalkConnection(node4, node5);
        web.addWalkConnection(node5, node4);

        web.addWalkConnection(node5, node6);
        web.addWalkConnection(node6, node5);
        web.addWalkConnection(node5, node13);
        web.addWalkConnection(node13, node5);
        web.addWalkConnection(node5, node17);
        web.addWalkConnection(node17, node5);
        web.addWalkConnection(node5, node19);
        web.addWalkConnection(node19, node5);

        web.addWalkConnection(node6, node7);
        web.addWalkConnection(node7, node6);
        web.addWalkConnection(node6, node13);
        web.addWalkConnection(node13, node6);

        web.addWalkConnection(node7, node8);
        web.addWalkConnection(node8, node7);
        web.addWalkConnection(node7, node10);
        web.addWalkConnection(node10, node7);
        web.addWalkConnection(node7, node13);
        web.addWalkConnection(node13, node7);

        web.addWalkConnection(node8, node9);
        web.addWalkConnection(node9, node8);
        web.addWalkConnection(node8, node23);
        web.addWalkConnection(node23, node8);

        web.addWalkConnection(node9, node23);
        web.addWalkConnection(node23, node9);

        web.addWalkConnection(node10, node11);
        web.addWalkConnection(node11, node10);
        web.addWalkConnection(node10, node18);
        web.addWalkConnection(node18, node10);
        web.addWalkConnection(node10, node13);
        web.addWalkConnection(node13, node10);

        web.addWalkConnection(node11, node12);
        web.addWalkConnection(node12, node11);

        web.addWalkConnection(node12, node32);
        web.addWalkConnection(node32, node12);
        web.addWalkConnection(node12, node34);
        web.addWalkConnection(node34, node12);

        web.addWalkConnection(node13, node14);
        web.addWalkConnection(node14, node13);
        web.addWalkConnection(node13, node17);
        web.addWalkConnection(node17, node13);

        web.addWalkConnection(node14, node15);
        web.addWalkConnection(node15, node14);
        web.addWalkConnection(node14, node16);
        web.addWalkConnection(node16, node14);

        web.addWalkConnection(node15, node16);
        web.addWalkConnection(node16, node15);
        web.addWalkConnection(node15, node32);
        web.addWalkConnection(node32, node15);
        web.addWalkConnection(node15, node33);
        web.addWalkConnection(node33, node15);

        web.addWalkConnection(node19, node20);
        web.addWalkConnection(node20, node19);
        web.addWalkConnection(node19, node22);
        web.addWalkConnection(node22, node19);

        web.addWalkConnection(node20, node21);
        web.addWalkConnection(node21, node20);
        web.addWalkConnection(node20, node22);
        web.addWalkConnection(node22, node20);

        web.addWalkConnection(node21, node22);
        web.addWalkConnection(node22, node21);

        web.addWalkConnection(node22, node23);
        web.addWalkConnection(node23, node22);

        //part 2
        web.addWalkConnection(node32, node12);
        web.addWalkConnection(node12, node32);
        web.addWalkConnection(node32, node33);
        web.addWalkConnection(node33, node32);
        web.addWalkConnection(node32, node34);
        web.addWalkConnection(node34, node32);

        web.addWalkConnection(node33, node34);
        web.addWalkConnection(node34, node33);
        web.addWalkConnection(node33, node36);
        web.addWalkConnection(node36, node33);

        web.addWalkConnection(node34, node35);
        web.addWalkConnection(node35, node34);

        web.addWalkConnection(node35, node36);
        web.addWalkConnection(node36, node35);
        web.addWalkConnection(node35, node40);
        web.addWalkConnection(node40, node35);

        web.addWalkConnection(node36, node37);
        web.addWalkConnection(node37, node36);
        web.addWalkConnection(node36, node44);
        web.addWalkConnection(node44, node36);

        web.addWalkConnection(node37, node38);
        web.addWalkConnection(node38, node37);
        web.addWalkConnection(node37, node39);
        web.addWalkConnection(node39, node37);

        web.addWalkConnection(node38, node39);
        web.addWalkConnection(node39, node38);

        web.addWalkConnection(node39, node44);
        web.addWalkConnection(node44, node39);

        web.addWalkConnection(node40, node42);
        web.addWalkConnection(node42, node40);
        web.addWalkConnection(node40, node44);
        web.addWalkConnection(node44, node40);

        web.addWalkConnection(node41, node42);
        web.addWalkConnection(node42, node41);
        web.addWalkConnection(node41, node43);
        web.addWalkConnection(node43, node41);

        //to pollinvench
        web.addWalkConnection(node3, node24);
        web.addWalkConnection(node24, node3);

        web.addWalkConnection(node24, node25);
        web.addWalkConnection(node25, node24);

        web.addWalkConnection(node25, node26);
        web.addWalkConnection(node26, node25);

        web.addWalkConnection(node26, node27);
        web.addWalkConnection(node27, node26);

        web.addWalkConnection(node27, node28);
        web.addWalkConnection(node28, node27);

        web.addWalkConnection(node28, node29);
        web.addWalkConnection(node29, node28);

        web.addWalkConnection(node29, node30);
        web.addWalkConnection(node30, node29);

        web.addWalkConnection(node30, node31);
        web.addWalkConnection(node31, node30);


        ObjectAction shantayPassAction = new ObjectAction(new Tile(3304,3116,0), "Shantay pass", "Go-through");
        ActionConnection enter = new ActionConnection(startNode0, startNode, shantayPassAction);
        ActionConnection exit = new ActionConnection(startNode, startNode0, shantayPassAction);
        startNode0.addConnection(enter);
        startNode.addConnection(exit);
    }
}

