package com.qihao.controller;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ZookeeperController {

    @Autowired
    private CuratorFramework zkClient;

    @RequestMapping(value = "/watchPath", method = RequestMethod.GET)
    public String watchPath() throws Exception {


        PathChildrenCache childrenNode = new PathChildrenCache(zkClient, "/ZookeeperDemo", true);
        PathChildrenCacheListener childrenListener = (curatorFramework, event) -> {
            System.out.println(event.getType() + " -> " + new String(event.getData().getData()));

        };
        childrenNode.getListenable().addListener(childrenListener);
        childrenNode.start(PathChildrenCache.StartMode.BUILD_INITIAL_CACHE);
        Thread.sleep(100000000);
        return "success";
    }
}
