package barbarianfisher;

        import org.tbot.methods.web.Web;
        import org.tbot.methods.web.actions.ObjectAction;
        import org.tbot.methods.web.areas.WebArea;
        import org.tbot.methods.web.nodes.WebNode;
        import org.tbot.methods.web.nodes.connections.ActionConnection;
        import org.tbot.wrappers.Tile;

/**
 * Created by Russell on 1/29/2016.
 */
public class FishWebArea extends WebArea {
    public void addTo(Web web){
        WebNode startNode = web.getNearestWebNode(new Tile(2504,3517,0));
        WebNode node0 = web.getNode(new Tile(2500,3515,0));
        web.addWalkConnection(startNode, node0);
        web.addWalkConnection(node0, startNode);
        WebNode node1 = web.getNode(new Tile(2499,3511,0));
        web.addWalkConnection(node0, node1);
        web.addWalkConnection(node1, node0);
        WebNode node2 = web.getNode(new Tile(2499,3506,0));
        web.addWalkConnection(node1, node2);
        web.addWalkConnection(node2, node1);
        WebNode node3 = web.getNode(new Tile(2500,3502,0));
        web.addWalkConnection(node2, node3);
        web.addWalkConnection(node3, node2);
        WebNode node4 = web.getNode(new Tile(2502,3499,0));
        web.addWalkConnection(node4, node3);
        web.addWalkConnection(node3, node4);
        WebNode node5 = web.getNode(new Tile(2504,3495,0));
        web.addWalkConnection(node5, node4);
        web.addWalkConnection(node4, node5);
    }
}

