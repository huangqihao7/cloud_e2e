package com.qihao.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author huangqihao
 * @version 1.0
 * @date 2019/9/24 17:45
 */

@RestController
@RequestMapping
public class ZookeeperContrller {

    private static String CONNECTION_STR = "192.168.1.156:2181,192.168.1.157:2181,192.168.1.159:2181";

    @RequestMapping(value="/watchPath",method= RequestMethod.GET)
    public String watchPath(){

        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder().
                connectString(CONNECTION_STR).sessionTimeoutMs(5000).
                retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();
        PathChildrenCache nodeCache=new PathChildrenCache(curatorFramework,"/ZookeeperDemo",true);
        PathChildrenCacheListener nodeCacheListener= (curatorFramework1, pathChildrenCacheEvent) -> {
            System.out.println(pathChildrenCacheEvent.getType()+"->"+new String(pathChildrenCacheEvent.getData().getData()));
        };
        nodeCache.getListenable().addListener(nodeCacheListener);
        try {
            nodeCache.start(PathChildrenCache.StartMode.NORMAL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }
}
