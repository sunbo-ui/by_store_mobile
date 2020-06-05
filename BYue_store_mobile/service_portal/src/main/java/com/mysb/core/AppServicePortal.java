package com.mysb.core;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.mysb.core.dao.*")
public class AppServicePortal {
    public static void main(String[] args) {
        try{
            SpringApplication.run(AppServicePortal.class,args);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
