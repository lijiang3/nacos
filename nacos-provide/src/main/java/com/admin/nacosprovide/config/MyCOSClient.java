package com.admin.nacosprovide.config;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.region.Region;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * TODO
 *
 * @author by lxh
 * @date 2021/5/13 14:57
 */
//@Component
public class MyCOSClient {

  @Bean
  private COSClient init() {
    // 1 初始化用户身份信息（secretId, secretKey）。
    String secretId = "AKIDFE2uXvzkxtL5qL8YGuIPM3TcUXIn40kj";
    String secretKey = "0jxwgARpWyDMFv7ShsnlAJCqDkDTUKuF";
    COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
// 2 设置 bucket 的地域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
// clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
    Region region = new Region("ap-beijing");
    ClientConfig clientConfig = new ClientConfig(region);
// 这里建议设置使用 https 协议
    clientConfig.setHttpProtocol(HttpProtocol.https);
// 3 生成 cos 客户端。
    COSClient cosClient = new COSClient(cred, clientConfig);

    return cosClient;
  }
}
