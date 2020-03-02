package com.dj.pms.utils;

import java.io.IOException;
import java.util.UUID;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.springframework.web.multipart.MultipartFile;

/**
 * 七牛云 上传图片 文件 工具类
 */
public class QiNiuYunUtil {

    /**
     * accessKey 七牛云密钥  相当于账号
     */
    private static String AK = "4nvAQY6RFLWECbKoqPf6JljGrMMCWCSFQujtEBbO";
    /**
     * secretKey 七牛云密钥   相当于密码
     */
    private static final String SK ="MvrZGwX3GscdGyYRTGq8cV8nTKvFy_dUgAVzQzu7";
    /**
     * 储存空间名
     */
    private static final String BUCKET = "qza";
    /**
     * 外链URL前缀
     */
    public static final String  FRONTURL = "http://q6joiglti.bkt.clouddn.com/";
    
    
   
    
    /**
     * Auth 认证账号和密码<br />
     * create 创建  <br />
     * 密钥配置 账号和密码  <br />
     */
    private static final Auth AUTH = Auth.create(AK, SK);

    /**
     * 创建上传对象
     */
    private static final UploadManager UPLOADMANAGER = new UploadManager();
    
    
    
    
    /**
     * 完成验证
     * 简单上传，使用默认策略，只需要设置上传的空间名就可以了
     * @return
     */
    public static String getUpToken() {
    	
    	// 上传域名
		return AUTH.uploadToken(BUCKET);
    	
    }


    /**
     * 上传文件 图片
     * @param file 文件 图片
     * @return  1 失败  其他成功
     * @throws IOException
     */
    public static String upload(MultipartFile file)throws IOException {
    	try {
            // 生成令牌 避免 重复  被覆盖
            String token = UUID.randomUUID().toString().replace("-", "");

            // 将file 转换成 byte 数组类型
            byte[] bytes = file.getBytes();

    		// 调用put方法上传
    		Response put = UPLOADMANAGER.put(bytes, token, getUpToken());
    		
    		// 打印返回的信息
    		System.out.println(put.isOK());
    		
    		System.out.println(put.bodyString());
            return FRONTURL + token;
		} catch (QiniuException e) {
			Response r = e.response;
			// 请求失败时 打印的异常信息
			System.out.println(r.toString());
			
			// 响应的文本信息
			System.out.println(r.bodyString());

		}
    	return "1";
    }
    
    
    /**
     * 普通删除
     */
    public static void del(String key) throws IOException {
    	try {
			// 实例化一个BucketManager对象        将密钥配置 放入
    		BucketManager bucketManager = new BucketManager(AUTH);
    		// 此处的33是去掉 ： http://ongsua0j7.bkt.clouddn.com/,剩下的key就是图片在七牛云的名称
    		key = key.substring(33);
    		bucketManager.delete(BUCKET, key);
    		System.out.println(key);
		} catch (QiniuException e) {
			e.printStackTrace();
		}
    }
       
    
    
}
