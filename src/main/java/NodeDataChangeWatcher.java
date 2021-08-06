import org.apache.zookeeper.*;

import java.io.IOException;

public class NodeDataChangeWatcher implements Watcher, Runnable {

    private static final String zooDataPath = "/MyConfig";
    byte[] zooData = null;
    ZooKeeper zk = null;

    public NodeDataChangeWatcher(String hostPort) throws IOException, KeeperException, InterruptedException {
        hostPort = (hostPort != null) ? hostPort : "localhost:2181";
        zk = new ZooKeeper(hostPort, 2000, this);
        if (zk.exists(zooDataPath, this) == null)
            zk.create(zooDataPath, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    public void printData() throws KeeperException, InterruptedException {
        zooData = zk.getData(zooDataPath, this, null);
        String zooDataString = new String(zooData);
        System.out.println("Current data @ zkPath " + zooDataPath + ": " + zooDataString);
    }

    @Override
    public void run() {
        try {
            synchronized (this) {
                while (true) {
                    wait();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println("Event received: " + watchedEvent.toString());
        if (watchedEvent.getType() == Event.EventType.NodeDataChanged) {
            try {
                printData();
            } catch (InterruptedException | KeeperException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException, IOException, KeeperException {
        NodeDataChangeWatcher nodeDataChangeWatcher = new NodeDataChangeWatcher(null);
        nodeDataChangeWatcher.printData();
        nodeDataChangeWatcher.run();
    }
}
