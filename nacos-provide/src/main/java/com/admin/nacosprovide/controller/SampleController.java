package com.admin.nacosprovide.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class SampleController {

  @Value("${user.name}")
  String userName;

  @Value("${user.age:25}")
  int age;

  @RequestMapping("/user")
  public String simple() {
    return "Hello Nacos Config!" + "Hello " + userName + " " + age + "!";
  }
}
