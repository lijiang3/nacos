package com.admin.nacosconsumer.controller;

import com.admin.nacosconsumer.dao.mybatis.LxhTestMapper;
import com.admin.nacosconsumer.model.LxhTest;
import org.springframework.beans.factory.annotation.Autowired;
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

  @Autowired
  private LxhTestMapper lxhTestMapper;

  @RequestMapping("/query")
  @ResponseBody
  public LxhTest query() {
    LxhTest lxhTest = new LxhTest();
    lxhTest.setUserName("lixiaohong" + System.currentTimeMillis());
    lxhTest.setPassword("123456");
    lxhTestMapper.insertSelective(lxhTest);
    return lxhTest;
  }

  @RequestMapping("/query2")
  @ResponseBody
  public LxhTest query2(Long id) {
    LxhTest lxhTest = lxhTestMapper.selectByPrimaryKey(id);
    lxhTest.setUserName(
        lxhTest.getUserName() + System.currentTimeMillis() + System.currentTimeMillis() + System.currentTimeMillis() + System.currentTimeMillis()
            + System.currentTimeMillis() + System.currentTimeMillis());
    lxhTestMapper.updateByPrimaryKeySelectiveWithVersion(lxhTest);
    return lxhTest;
  }

  public static void main(String[] args) {

  }
}
