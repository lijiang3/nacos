package com.admin.nacosconsumer.dao.base;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 继承自己的MyMapper
 *
 * @author liuzh
 * @since 2015-09-06 21:53
 */
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {
  //FIXME 特别注意，该接口不能被扫描到，否则会出错

  default int updateByPrimaryKeySelectiveWithVersion(T t) {
    int result = updateByPrimaryKeySelective(t);
    if (result == 0) {
      throw new RuntimeException("更新失败！");
    }
    return result;
  }

}
