package com.qihao;

import com.qihao.controller.ZookeeperController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableDiscoveryClient
public class ZookeeperApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ZookeeperApplication.class, args);
        ZookeeperController bean = context.getBean(ZookeeperController.class);
        try {
           // bean.watchPath();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
