package com.qihao;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

/**
 * @author huangqihao
 * @version 1.0
 * @date 2019/9/23 17:36
 */
public class ZookeeperDemo {

    private static String CONNECTION_STR = "192.168.1.156:2181,192.168.1.157:2181,192.168.1.159:2181";


    public static void main(String[] args) throws Exception {
//        CuratorFramework curatorFramework= CuratorFrameworkFactory.newClient("")

        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder().
                connectString(CONNECTION_STR).sessionTimeoutMs(5000).
                retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();
        //ExponentialBackoffRetry
        //RetryOneTime  仅仅只重试一次
        //RetryUntilElapsed
        //RetryNTimes

        curatorFramework.start(); //启动
        //createData(curatorFramework);
        //updateData(curatorFramework);
        //deleteData(curatorFramework);
        addListenerWithChild(curatorFramework);

    }

    private static void createData(CuratorFramework curatorFramework) throws Exception {
        curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).
                forPath("/ZookeeperDemo/program", "test".getBytes());

    }

    private static void updateData(CuratorFramework curatorFramework) throws Exception {
        curatorFramework.setData().forPath("/ZookeeperDemo/program", "up".getBytes());

    }

    private static void deleteData(CuratorFramework curatorFramework) throws Exception {
        Stat stat = new Stat();
        String value = new String(curatorFramework.getData().storingStatIn(stat).forPath("/ZookeeperDemo/program"));
        curatorFramework.delete().withVersion(stat.getVersion()).forPath("/ZookeeperDemo/program");
    }

    //实现服务注册中心的时候，可以针对服务做动态感知
    private static void addListenerWithChild(CuratorFramework curatorFramework) throws Exception {
        PathChildrenCache nodeCache = new PathChildrenCache(curatorFramework, "/services/cloud_e2e", true);
        PathChildrenCacheListener nodeCacheListener = (curatorFramework1, pathChildrenCacheEvent) -> {
            System.out.println(pathChildrenCacheEvent.getType() + "->" + new String(pathChildrenCacheEvent.getData().getData()));
        };
        nodeCache.getListenable().addListener(nodeCacheListener);
        nodeCache.start(PathChildrenCache.StartMode.BUILD_INITIAL_CACHE);

        System.in.read();
    }


}
