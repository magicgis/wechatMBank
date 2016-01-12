/**  
* @Title: WeixinUtils.java
* @Package com.yitong.weixin.utils
* @Description: TODO
* @author zpz
* @date 2014年3月31日 下午5:40:09
* @version V1.0  
*/ 
package com.yitong.weixin.modules.wechat.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.Date;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yitong.weixin.common.utils.DateUtils;

/**
 * @ClassName: WeixinUtils
 * @Description: 微信工具类
 * 验证签名、获致access_token
 * 
 * @author zpz
 * @date 2014年3月31日 下午5:40:09
 *
 */

public class WeixinUtils {
	
	// 保存访问token
	private static AccessToken accessToken = new AccessToken();
//	private static WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
//	private static AccessToken accessToken = (AccessToken)webApplicationContext.getBean("accessToken");
	
	/**
	 * 获取access_token;
	 * @throws WeixinException 
    **/
	public static String getAccessToken() throws Exception{
		final String accessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token";
		System.out.println("cur_token = [" + accessToken.getToken() + "| isExpired = " + accessToken.isExpired() + "]");
		//if(accessToken.isExpired())
		{
			String acctokenUrl = accessTokenUrl+"?grant_type=client_credential" + "&appid="+AcctUtils.getAcct().getAppId()+"&secret="+AcctUtils.getAcct().getAppSercet();
			String result = getWeiXin(acctokenUrl);
			System.out.println("result------------------->"+result + " cur_date = "+ DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
			JSONObject jsonStr = JSONObject.parseObject(result);
			accessToken.setToken(jsonStr.getString("access_token"), jsonStr.getString("expires_in"));
		}
		
		return accessToken.getToken();
	}
	/**
	 * 与微信交互，get请求
    **/
	public static String getWeiXin(String urlNameString) throws Exception{
        String result = "";
        BufferedReader in = null;
        try {
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(),"UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
            throw new Exception("发送GET请求出现异常！");
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
	
	/**
	 * 
	* @Title: postWeiXin
	* @Description: 与微信交互，post请求
	* @param urlParams
	* @param content
	* @return
	* @throws WeixinException
	* @return String
	 */
	public static String postWeiXin(String urlParams,String content)throws Exception{
		
		DataOutputStream out = null;
		InputStream is = null;
		try {
			SSLContext sc = SSLContext.getInstance("SSL");
	        sc.init(null, new TrustManager[] { new TrustAnyTrustManager() },
	                new java.security.SecureRandom());
	 
	        URL console = new URL(urlParams);
	        HttpsURLConnection conn = (HttpsURLConnection) console.openConnection();
	        conn.setSSLSocketFactory(sc.getSocketFactory());
	        conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
	        conn.setDoInput(true);
	        conn.setDoOutput(true);
	        conn.connect();
	        out = new DataOutputStream(conn.getOutputStream());
	        out.write(content.getBytes("utf-8"));
	        // 刷新、关闭
	        out.flush();
//	        out.close();
	        is = conn.getInputStream();
	        if (is != null) {
	            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
	            byte[] buffer = new byte[1024];
	            int len = 0;
	            while ((len = is.read(buffer)) != -1) {
	                outStream.write(buffer, 0, len);
	            }
//	            is.close();
	            return new String(outStream.toByteArray(),"utf-8");
	        }
//			return "{\"result\":\"error\",\"message\":\"更新微信菜单错误\"}";
	        return null;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new Exception("post请求失败");
		}
		finally{
			if(null != out)
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if(null != is)
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
	}
	private static class TrustAnyTrustManager implements X509TrustManager {
		 
        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }
 
        public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }
 
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[] {};
        }
	}
	private static class TrustAnyHostnameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
	}
	
	/**
	 * 获取用户具体信息
	 * @throws WeixinException 
	 * */
	public static String getUserDetailInfo(String openId) throws Exception{
		final String getUserInfoUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=%s&openid=%s";
		
		String url = String.format(getUserInfoUrl, getAccessToken(),openId);
		return getWeiXin(url);
	}
	
	public static boolean parseWeixinResult(String result){
		JSONObject jsonObject = JSON.parseObject(result);
		String str = jsonObject.getString("errcode");
		if("0".equals(str)){
			return true;
		}
		return false;
	}
}

class AccessToken {
	
	private String access_token;	// 有效token  
    private Date expires_in;  		// 有效期,获取token时间+过期时间
	
    /**
    * @Title: setToken
    * @Description: 获取新的token，重新设置
    * @param token
    * @param expires_in
    * @return void
     */
    public void setToken(String token, String expires_in)
    {
    	this.access_token = token;
    	Calendar cal = Calendar.getInstance();
    	cal.add(Calendar.SECOND, Integer.parseInt(expires_in)-10);
    	this.expires_in = cal.getTime();
    }
    /**
     * 
    * @Title: isExpired
    * @Description: 是否过期
    * @return
    * @return boolean
     */
    public boolean isExpired()
    {
    	System.out.println("expires_in = [" + expires_in + "]");
    	// 第一次使用，过期
    	if(null == expires_in)
    		return true;
    	// 现在时间在过期时间之后，认为过期
    	Calendar cal=Calendar.getInstance();
    	cal.setTime(expires_in);
    	if(Calendar.getInstance().after(cal))
    		return true;
    	return false;
    }
    
    public String getToken()
    {
    	return this.access_token;
    }
    
}