import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class NodeDataUpdater implements Watcher {
    String zooDataPath = "/MyConfig";
    ZooKeeper zk = null;

    public NodeDataUpdater(String hostPort) throws IOException {
        hostPort = (hostPort != null) ? hostPort : "localhost:2181";
        zk = new ZooKeeper(hostPort, 2000, this);
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println("Event received: " + watchedEvent.toString());
    }

    public void run() throws InterruptedException, KeeperException {
        while (true) {
            String uuid = UUID.randomUUID().toString();
            byte[] uuidBytes = uuid.getBytes(StandardCharsets.UTF_8);
            zk.setData(zooDataPath, uuidBytes, -1);
            Thread.sleep(5000);
        }
    }

    public static void main(String[] args) throws KeeperException, InterruptedException, IOException {
        NodeDataUpdater nodeDataUpdater = new NodeDataUpdater(null);
        nodeDataUpdater.run();
    }
}
