package com.admin.nacosprovide.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO
 *
 * @author by lxh
 * @date 2021/3/22 15:37
 */
@RestController
@RequestMapping("/")
public class TestController {


  @GetMapping("/helloNacos")
  public String helloNacos() {
    return "你好，nacos！";
  }

}
