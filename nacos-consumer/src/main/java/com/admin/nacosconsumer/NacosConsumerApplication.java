package com.admin.nacosconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class NacosConsumerApplication {

  public static void main(String[] args) {
    SpringApplication.run(NacosConsumerApplication.class, args);
  }

}
