package com.admin.nacosprovide.config;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * TODO
 *
 * @author by lxh
 * @date 2021/5/27 10:38
 */
public class ThreadPoolExecutorUtils {


  public static ThreadPoolExecutor executor = new ThreadPoolExecutor(
      9, 1000,
      5, TimeUnit.MINUTES,
      new LinkedBlockingQueue<>(1000),
      Executors.defaultThreadFactory(),
      new ThreadPoolExecutor.AbortPolicy()
  );

  public static Future submit(Callable task) {
    return executor.submit(task);
  }

}
