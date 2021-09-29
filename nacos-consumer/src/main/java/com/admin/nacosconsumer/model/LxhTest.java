package com.admin.nacosconsumer.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;
import tk.mybatis.mapper.annotation.Version;

/**
 * TODO
 *
 * @author by lxh
 * @date 2021/3/22 15:39
 */
@Data
public class LxhTest implements Serializable {

  private String userName;

  private String password;

  @Id
  @Column(
      name = "Id"
  )
  @GeneratedValue(
      strategy = GenerationType.IDENTITY
  )
  private Long id;
  private String createUser;
  private Date createTime;
  private String updateUser;
  private Date updateTime;
  @Version
  private Integer version;
  private Integer status;
  private String remark;
}
