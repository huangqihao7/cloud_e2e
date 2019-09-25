package com.qihao.config;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author huangqihao
 * @version 1.0
 * @date 2019/9/25 10:01
 */
@Configuration
public class ZookeeperConfiguration {

    //@Value("${zk.url}")
    private String zkUrl;

    @Bean
    public CuratorFramework getCuratorFramework(){
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000,3);
        CuratorFramework client = CuratorFrameworkFactory.newClient("192.168.1.156:2181,192.168.1.157:2181,192.168.1.159:2181",retryPolicy);
        client.start();
        return client;
    }
}
