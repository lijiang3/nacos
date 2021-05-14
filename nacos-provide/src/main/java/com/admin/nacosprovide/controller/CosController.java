package com.admin.nacosprovide.controller;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.Bucket;
import com.qcloud.cos.model.COSObject;
import com.qcloud.cos.model.COSObjectInputStream;
import com.qcloud.cos.model.COSObjectSummary;
import com.qcloud.cos.model.GetObjectRequest;
import com.qcloud.cos.model.ListObjectsRequest;
import com.qcloud.cos.model.ObjectListing;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.model.UploadResult;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.transfer.TransferManager;
import com.qcloud.cos.transfer.Upload;
import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO
 *
 * @author by lxh
 * @date 2021/3/22 15:37
 */
@RestController
@RequestMapping("/cos")
public class CosController {


  // 1 初始化用户身份信息(secretId, secretKey，可在腾讯云后台中的API密钥管理中查看！
  private static COSCredentials cred = new BasicCOSCredentials(
      "AKIDFE2uXvzkxtL5qL8YGuIPM3TcUXIn40kj",
      "0jxwgARpWyDMFv7ShsnlAJCqDkDTUKuF");

  // 2 设置bucket的区域, COS地域的简称请参照 https://cloud.tencent.com/document/product/436/6224，根据自己创建的存储桶选择地区
  private static ClientConfig clientConfig = new ClientConfig(new Region("ap-beijing"));

  // bucket的命名规则为{name}-{appid} ，此处填写的存储桶名称必须为此格式，这个为存储桶名称
  private static String bucketName = "lxh-1300036073";


  @RequestMapping("/listBuckets")
  public void listBuckets() {
    COSClient cosClient = new COSClient(cred, clientConfig);

    List<Bucket> buckets = cosClient.listBuckets();
    for (Bucket bucketElement : buckets) {
      String bucketName = bucketElement.getName();
      String bucketLocation = bucketElement.getLocation();
      System.out.println(bucketName + "/" + bucketLocation);
    }
  }


  @RequestMapping("/putObject")
  public void putObject() {

    // 生成cos客户端
    COSClient cosclient = new COSClient(cred, clientConfig);
    try {
// 指定要上传的文件
      File localFile = new File("C:\\Users\\lxh\\Desktop\\111.xls");

// 指定文件上传到 COS 上的路径，即对象键。例如对象键为folder/picture.jpg，则表示将文件 picture.jpg 上传到 folder 路径下
      String key = "org" + "/" + System.currentTimeMillis() + "/" + "111.xls";
      PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
      PutObjectResult putObjectResult = cosclient.putObject(putObjectRequest);
      System.out.println(putObjectResult.toString());

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      // 关闭客户端(关闭后台线程)
      cosclient.shutdown();
    }

  }


  /**
   * 上传文件, 根据文件大小自动选择简单上传或者分块上传。
   */
  @RequestMapping("/upload ")
  public void upload() {
// 3 生成cos客户端
    COSClient cosclient = new COSClient(cred, clientConfig);
// 指定要上传的文件
    File localFile = new File("C:\\Users\\lxh\\Desktop\\111.xls");
    ExecutorService threadPool = Executors.newFixedThreadPool(32);

    // 传入一个threadpool, 若不传入线程池, 默认TransferManager中会生成一个单线程的线程池。
    TransferManager transferManager = new TransferManager(cosclient, threadPool);

    String key = "org" + "/" + System.currentTimeMillis() + "/" + "111.xls";
    PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
    try {
      // 返回一个异步结果Upload, 可同步的调用waitForUploadResult等待upload结束, 成功返回UploadResult, 失败抛出异常.
      long startTime = System.currentTimeMillis();
      Upload upload = transferManager.upload(putObjectRequest);
      UploadResult uploadResult = upload.waitForUploadResult();
      long endTime = System.currentTimeMillis();
      System.out.println("used time: " + (endTime - startTime) / 1000);
      System.out.println(uploadResult.getETag());
    } catch (CosServiceException e) {
      e.printStackTrace();
    } catch (CosClientException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    transferManager.shutdownNow();
    cosclient.shutdown();

  }

  /**
   * 删除文件
   */
  @RequestMapping("/deleteObject")
  public void deletefile(String key) throws CosClientException, CosServiceException {

    // 生成cos客户端
    COSClient cosclient = new COSClient(cred, clientConfig);

    // 指定要删除的 bucket 和路径
    cosclient.deleteObject(bucketName, key);

    // 关闭客户端(关闭后台线程)
    cosclient.shutdown();

  }

  @RequestMapping("/listObjects")
  public void listObjects() {
    // 生成cos客户端
    COSClient cosclient = new COSClient(cred, clientConfig);
// Bucket的命名格式为 BucketName-APPID ，此处填写的存储桶名称必须为此格式
    String bucketName = "lxh-1300036073";
    ListObjectsRequest listObjectsRequest = new ListObjectsRequest();
// 设置bucket名称
    listObjectsRequest.setBucketName(bucketName);
// prefix表示列出的object的key以prefix开始
    listObjectsRequest.setPrefix("org/");
// deliter表示分隔符, 设置为/表示列出当前目录下的object, 设置为空表示列出所有的object
    //listObjectsRequest.setDelimiter("/");
// 设置最大遍历出多少个对象, 一次listobject最大支持1000
    listObjectsRequest.setMaxKeys(1000);
    ObjectListing objectListing = null;
    do {
      try {
        objectListing = cosclient.listObjects(listObjectsRequest);
      } catch (CosServiceException e) {
        e.printStackTrace();
        return;
      } catch (CosClientException e) {
        e.printStackTrace();
        return;
      }
      // common prefix表示表示被delimiter截断的路径, 如delimter设置为/, common prefix则表示所有子目录的路径
      List<String> commonPrefixs = objectListing.getCommonPrefixes();
      // object summary表示所有列出的object列表
      List<COSObjectSummary> cosObjectSummaries = objectListing.getObjectSummaries();
      for (COSObjectSummary cosObjectSummary : cosObjectSummaries) {
        // 文件的路径key
        String key = cosObjectSummary.getKey();
        // 文件的etag
        String etag = cosObjectSummary.getETag();
        // 文件的长度
        long fileSize = cosObjectSummary.getSize();
        // 文件的存储类型
        String storageClasses = cosObjectSummary.getStorageClass();
      }
      String nextMarker = objectListing.getNextMarker();
      listObjectsRequest.setMarker(nextMarker);
    } while (objectListing.isTruncated());
  }

  @RequestMapping("/getObject")
  public void getObject() {
    try {
      // 生成cos客户端
      COSClient cosclient = new COSClient(cred, clientConfig);
      // Bucket的命名格式为 BucketName-APPID ，此处填写的存储桶名称必须为此格式
      String bucketName = "lxh-1300036073";
// 指定文件在 COS 上的路径，即对象键。例如对象键为folder/picture.jpg，则表示下载的文件 picture.jpg 在 folder 路径下
      String key = "org/1620889773398/111.xls";
// 方法1 获取下载输入流
      GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, key);
      COSObject cosObject = cosclient.getObject(getObjectRequest);
      COSObjectInputStream cosObjectInput = cosObject.getObjectContent();
// 下载对象的 CRC64
      String crc64Ecma = cosObject.getObjectMetadata().getCrc64Ecma();
// 关闭输入流
      cosObjectInput.close();
//// 方法2 下载文件到本地的路径，例如 D 盘的某个目录
//      String outputFilePath = "D:\\E\\workspace各个项目资料文件\\刘峰交接";
//      File downFile = new File(outputFilePath);
//      GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, key);
//      ObjectMetadata downObjectMeta = cosClient.getObject(getObjectRequest, downFile);
//      System.out.println(downObjectMeta.toString());
    } catch (Exception io) {
      io.printStackTrace();
    }

  }
}
