package com.admin.nacosprovide.controller;

import com.admin.nacosprovide.model.LxhTest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO
 *
 * @author by lxh
 * @date 2021/3/22 15:37
 */
@RestController
@RequestMapping("/lxh-test")
public class TestController {


  @RequestMapping("/query")
  @ResponseBody
  public LxhTest query() {
    LxhTest lxhTest = new LxhTest();
    lxhTest.setId("1");
    lxhTest.setUsername("lixiaohong");
    lxhTest.setPassword("123456");
    return lxhTest;
  }
}
