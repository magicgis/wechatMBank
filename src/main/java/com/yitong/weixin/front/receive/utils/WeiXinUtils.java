package com.yitong.weixin.front.receive.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.yitong.weixin.common.utils.SpringContextHolder;
import com.yitong.weixin.front.info.dao.WeixinAccountFDao;
import com.yitong.weixin.front.info.entity.WeixinAccountF;
import com.yitong.weixin.modules.wechat.dao.WeixinAccountDao;

public class WeiXinUtils {
	private static WeixinAccountFDao weixinAccountFDao = SpringContextHolder.getBean(WeixinAccountFDao.class);

	public static String readStreamParameter(ServletInputStream in) {
		StringBuilder buffer = new StringBuilder();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(in));
			String line = null;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != reader) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return buffer.toString();
	}
	
	//向请求端发送返回数据  
    public static void print(HttpServletResponse response,String content){  
        try{  
        	response.getWriter().print(content);  
        	response.getWriter().flush();  
        	response.getWriter().close();  
        }catch(Exception e){  
              
        }  
    } 

	// 微信接口验证
	public static boolean checkSignature(HttpServletRequest request, String openId) {
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		WeixinAccountF weixinAccountF = getAcct(openId);
		String token = weixinAccountF.getToken();
		String[] tmpArr = { token, timestamp, nonce };
		Arrays.sort(tmpArr);
		String tmpStr = ArrayToString(tmpArr);
		tmpStr = SHA1Encode(tmpStr);
		if (tmpStr.equalsIgnoreCase(signature)) {
			return true;
		} else {
			return false;
		}
	}
	
	// 微信接口验证
	public static boolean checkSignature(String signature,String timestamp,String nonce) {
		String token = CF.Token;
		String[] tmpArr = { token, timestamp, nonce };
		Arrays.sort(tmpArr);
		String tmpStr = ArrayToString(tmpArr);
		tmpStr = SHA1Encode(tmpStr);
		if (tmpStr.equalsIgnoreCase(signature)) {
			return true;
		} else {
			return false;
		}
	}

	// 数组转字符串
	public static String ArrayToString(String[] arr) {
		StringBuffer bf = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			bf.append(arr[i]);
		}
		return bf.toString();
	}

	// sha1加密
	public static String SHA1Encode(String sourceString) {
		String resultString = null;
		try {
			resultString = new String(sourceString);
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			resultString = byte2hexString(md.digest(resultString.getBytes()));
		} catch (Exception ex) {
		}
		return resultString;
	}

	public static String byte2hexString(byte[] bytes) {
		StringBuffer buf = new StringBuffer(bytes.length * 2);
		for (int i = 0; i < bytes.length; i++) {
			if (((int) bytes[i] & 0xff) < 0x10) {
				buf.append("0");
			}
			buf.append(Long.toString((int) bytes[i] & 0xff, 16));
		}
		return buf.toString().toUpperCase();
	}
	/**
	 * 获取access_token;
    **/
	public static String getAccessToken(String accOpenId){
		if(CF.AccessToken.equals("")){
			WeixinAccountF weixinAccount = getAcct(accOpenId);
			
//			#appId = wx9be53499a9491800
//			#appsecret = 123e6ebe3571da02195c0d983f540662
//			#token = dN3o59EeYo3Dey8i1tnQIi3Qi9L99e8q
			String acctokenUrl = CF.accessTokenUrl+"?grant_type=client_credential" +
					"&appid="+weixinAccount.getAppId()+"&secret="+weixinAccount.getAppSercet();
//			String acctokenUrl = CF.accessTokenUrl+"?grant_type=client_credential" +
//					"&appid="+"wx9be53499a9491800"+"&secret="+"123e6ebe3571da02195c0d983f540662";
			String result = getWeiXin(acctokenUrl);
			System.out.println("result------------------->"+result);
			JSONObject jsonStr = JSONObject.parseObject(result);
			return jsonStr.getString("access_token");
		}else{
			return CF.AccessToken;
		}
	}
	
	/**
	 * 获取用户具体信息
	 * */
	public static String getUserDetailInfo(String openId, String accOpenId){
		String url = String.format(CF.getUserInfoUrl, getAccessToken(accOpenId),openId);
		return getWeiXin(url);
	}
	
	public static String getWeiXin(String urlNameString) {
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
                    connection.getInputStream(),"utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
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
	 * 判断字符串是不是数字
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str){
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if( !isNum.matches() ){
			return false;
		}
		return true;
	} 

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
	        out.write(content.getBytes());
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
	                System.out.println("test==="+new String(outStream.toByteArray()));
	            }
//	            is.close();
	            return new String(outStream.toByteArray());
	        }
//			return "{\"result\":\"error\",\"message\":\"更新微信菜单错误\"}";
	        return null;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
	        return null;
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
	public static String PostWeiXin(String urlParams,String content)throws Exception{
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
        DataOutputStream out = new DataOutputStream(conn.getOutputStream());
        out.write(content.getBytes());
        // 刷新、关闭
        out.flush();
        out.close();
        InputStream is = conn.getInputStream();
        if (is != null) {
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = is.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
            is.close();
            System.out.println("test======="+new String(outStream.toByteArray()));
            return new String(outStream.toByteArray());
        }
		return "{\"result\":\"error\",\"message\":\"更新微信菜单错误\"}";
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
	 * 通过token让页面打开一次后失效
	 */
	public static String getToken(String openId){
		/*String url = C.lanIp+"/sendToken.do?OPENID="+openId;
		System.out.println("url==================="+url);
		String result = WeiXinUtils.getWeiXin(url);
		JSONObject jsonStr = JSONObject.parseObject(result);
		String token = jsonStr.getString("TOKEN");
		String params = "TOKEN="+token;
		*/
		String params = "TOKEN=111111";
		return params;
	}
	public static String getToken(String openId, int levelId){
		/*String url = C.lanIp+"/sendToken.do?OPENID="+openId+"&LEVELID="+levelId;
		System.out.println("url==================="+url);
		String result = WeiXinUtils.getWeiXin(url);
		JSONObject jsonStr = JSONObject.parseObject(result);
		String token = jsonStr.getString("TOKEN");
		String params = "TOKEN="+token;*/
		
		String params = "TOKEN=111111";
		return params;
	}
	
	public static WeixinAccountF getAcct(String accOpenId){
		return weixinAccountFDao.findByAcctId(accOpenId);
	}
	
}
