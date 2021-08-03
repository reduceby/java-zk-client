import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ZnodesChildren {
    public static void main(String[] args) throws IOException {
        String hostPort = "localhost:2181";
        String zPath = "/";
        List<String> zooChildren = new ArrayList<String>();
        ZooKeeper zk = new ZooKeeper(hostPort, 2000, null);

        try {
            zooChildren = zk.getChildren(zPath, false);
            for (String child :
                    zooChildren) {
                System.out.println(child);
            }
        } catch (InterruptedException | KeeperException e) {
            e.printStackTrace();
        }
    }
}
