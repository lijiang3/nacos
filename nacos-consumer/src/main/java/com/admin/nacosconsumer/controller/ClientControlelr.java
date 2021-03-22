package com.admin.nacosconsumer.controller;

import com.admin.nacosconsumer.model.LxhTest;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class ClientControlelr {

  @Autowired
  private DiscoveryClient discoveryClient;

  @RequestMapping("/queryService")
  @ResponseBody
  public LxhTest query() {
    List<ServiceInstance> instances = discoveryClient.getInstances("nacos-provide");
    StringBuilder urls = new StringBuilder();
    for (ServiceInstance instance : instances) {
      urls.append(instance.getHost() + ":" + instance.getPort()).append(",");
    }
    LxhTest vo = new LxhTest();
    vo.setUsername("service-prodvider1");
    vo.setDescription(urls.toString());
    return vo;
  }
}