package com.yitong.weixin.modules.wechat.web;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.util.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yitong.weixin.common.utils.DateUtils;
import com.yitong.weixin.common.utils.IdGen;
import com.yitong.weixin.common.utils.StreamUtils;
import com.yitong.weixin.common.web.BaseController;
import com.yitong.weixin.modules.wechat.entity.WeixinMenu;
import com.yitong.weixin.modules.wechat.model.MenuModel;
import com.yitong.weixin.modules.wechat.model.SubMenuModel;
import com.yitong.weixin.modules.wechat.service.WeixinMenuService;
import com.yitong.weixin.modules.wechat.utils.C;
import com.yitong.weixin.modules.wechat.utils.WeixinUtils;

/**
 * 内容管理Controller
 * 
 * @author ThinkGem
 * @version 2013-4-21
 */
@Controller
@RequestMapping(value = "${adminPath}/wechat/menu")
public class WeixinMenuController extends BaseController {

	@Autowired
	private WeixinMenuService weixinMenuService;

	//获取menu的菜单
	@RequestMapping(value = "index")
	public String menuList(HttpServletRequest request,Model model) {
//		List<WeixinMenu> weixinMenuList = weixinMenuService.getByIds(1001, 1002, 1003);
		List<WeixinMenu> weixinMenuList = weixinMenuService.getByPId();
		ArrayList<MenuModel> menuModelList = new ArrayList<MenuModel>();
		for (WeixinMenu weixinMenu : weixinMenuList) {
			MenuModel menuModel = new MenuModel();
			BeanUtils.copyProperties(weixinMenu, menuModel);
			ArrayList<SubMenuModel> subMenuModelList = new ArrayList<SubMenuModel>();
			List<WeixinMenu> subMenuList = weixinMenuService.getByPId_(weixinMenu.getId());//二级菜单集合
			for (WeixinMenu subMenu : subMenuList) {
				SubMenuModel subMenuModel = new SubMenuModel();
				BeanUtils.copyProperties(subMenu, subMenuModel);
				/*ArrayList<SubMenuModelList> list = new ArrayList<SubMenuModelList>();//三级菜单集合
				List<WeixinMenuList> thirdMenuList = weixinMenuListervice.getBySubCode(subMenu.getCode());
				for(WeixinMenuList weixinMenuList1:thirdMenuList){
					SubMenuModelList subMenuModelList2 = new SubMenuModelList();
					BeanUtils.copyProperties(weixinMenuList1, subMenuModelList2);
					list.add(subMenuModelList2);
				}*/
//				subMenuModel.setModules(list);
				subMenuModelList.add(subMenuModel);
			}
			menuModel.setSubMenu(subMenuModelList);
			menuModelList.add(menuModel);
		}
		String menuJson = JSON.toJSONString(menuModelList);
		model.addAttribute("menuJson", menuJson);
		logger.debug("menuJson--->"+menuJson);
		return "modules/wechat/menuList";
	}

//	保存更新的菜单
	@RequestMapping(value = "save")
	@ResponseBody
	public String updateMenu(HttpServletRequest request) throws IOException{

		String xml = StreamUtils.InputStreamTOString(request.getInputStream(), "utf-8");
		JSONObject jObject = JSON.parseObject(xml);
		
		String menuJson = jObject.getString("menuJson");
		String ids_ = jObject.getString("ids");
		
		List<String> ids = JSON.parseArray(ids_, String.class);
		
		if(ids!=null && ids.size()>0){
			for(int i=0;i<ids.size();i++){
				/*List<WeixinMenuList> list = weixinMenuListervice.getBySubCode(String.valueOf(ids[i]));
				if(list.size()>0){
					weixinMenuListervice.deleteList(list);//删除三级菜单
				}*/
				List<WeixinMenu> list2 = weixinMenuService.getByPId_(ids.get(i));
				if(list2.size()>0){
					weixinMenuService.deleteList(list2);//删除二级菜单
				}
				weixinMenuService.deleteById(ids.get(i));
			}
		}
		if(StringUtils.hasText(menuJson)){
			try {
				List<MenuModel> list = JSON.parseArray(menuJson, MenuModel.class);
/*				JavaType javaType = JsonMapper.getInstance().createCollectionType(ArrayList.class, MenuModel.class);
				List<MenuModel> list = JsonMapper.getInstance().fromJson(menuJson, javaType);
*/				
				for (int i=0; i< list.size(); i++) {
					MenuModel menuModel = list.get(i);
					WeixinMenu weixinMenu = new WeixinMenu();
					BeanUtils.copyProperties(menuModel, weixinMenu);
					if(weixinMenu.getId().equals("0"))
						weixinMenu.setId(null);
					weixinMenu.setPid(null);
					weixinMenu.setOrderN(String.valueOf(i));
					weixinMenuService.save(weixinMenu);//保存一级菜单
					List<SubMenuModel> subMenuList = menuModel.getSubMenu();//二级菜单
					for (int k=0; k<subMenuList.size(); k++) {
						SubMenuModel subMenuModel = subMenuList.get(k);
						WeixinMenu weixinMenu2 = new WeixinMenu();
						BeanUtils.copyProperties(subMenuModel, weixinMenu2);
						if(weixinMenu2.getId().equals("0"))
							weixinMenu2.setId(null);
						if(weixinMenu2.getCode()==null || weixinMenu2.getCode().equals("")){
							weixinMenu2.setCode(IdGen.uuid());
						}
						weixinMenu2.setPid(weixinMenu.getId().toString());
						weixinMenu2.setOrderN(String.valueOf(k));
						weixinMenuService.save(weixinMenu2);//更新保存二级菜单
						// 微信不支持三级菜单 // @author ZhangPeiZhong 修改 2014年4月18日 上午8:39:53
						/*List<SubMenuModelList> gongNengList = subMenuModel.getModules();
						for (SubMenuModelList subMenuModelList : gongNengList) {//更新保存三级菜单
							WeixinMenuList weixinMenuList = new WeixinMenuList();
							BeanUtils.copyProperties(subMenuModelList, weixinMenuList);
							if(weixinMenuList.getId()==0)
								weixinMenuList.setId(null);
							weixinMenuList.setSuperCode(weixinMenu2.getCode());
							weixinMenuList.setMessage("明细消息如下:");
							weixinMenuListervice.save(weixinMenuList);
						}*/
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
                return  "{\"result\":\"error\",\"message\":\"保存菜单出错\"}";
			}
		}else{
			logger.info("更新的json是null....");
		}
		return  "{\"result\":\"ok\",\"message\":\"success\"}";
	}
	
	@RequestMapping(value = "publish")
	@ResponseBody
	public String publishMenu(HttpServletRequest request){
		 try {
			 String url = C.publishUrl+WeixinUtils.getAccessToken();
			 System.out.println("url = [" + url + DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss") + "]");
				List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
				List<WeixinMenu> weixinMenuList = weixinMenuService.getByPId();
				for(WeixinMenu wm: weixinMenuList){
					Map<String,Object> map = new HashMap<String, Object>();
					map.put("name", wm.getName());
					if(Integer.valueOf(wm.getType()).intValue()==0){
						map.put("type", "click");
						map.put("key", String.valueOf(wm.getCode()));
					}else{
						List<WeixinMenu> subMenuList = weixinMenuService.getByPId_(wm.getId());//二级菜单集合
						List<Map<String,Object>> sublist = new ArrayList<Map<String,Object>>();
						for(WeixinMenu subwm:subMenuList){
							Map<String,Object> submap = new HashMap<String, Object>();
							submap.put("name", subwm.getName());
							if(Integer.valueOf(subwm.getType()).intValue()==0){
								submap.put("type", "click");
								submap.put("key", String.valueOf(subwm.getCode()));
							}else{
								submap.put("type", "view");
								submap.put("url", String.valueOf(subwm.getUrl()));
							}
							sublist.add(submap);
						}
						map.put("sub_button",sublist);
					}
					list.add(map);
				}
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("button", list);
				String content = JSON.toJSONString(map);
				return PostWeiXin(url,content);
		} catch (Exception e) {
			 return "{\"result\":\"error\",\"message\":\""+e.getMessage()+"\"}";
		}
	}
	
//	检验增加的子菜单的Code 是否合法
	@RequestMapping(value = "check")
	@ResponseBody
	public String checkMenu(HttpServletRequest request) throws Exception{
		String xml = StreamUtils.InputStreamTOString(request.getInputStream(), "utf-8");
		JSONObject jObject = JSON.parseObject(xml);
		String code = jObject.getString("code");
		
		boolean isRegular = true;
		/*List<WeixinMenuList> list00=(List)weixinMenuListervice.findAll();
		for(int z1=0;z1<list00.size();z1++){
			if((list00.get(z1)).getCode().equals(code)){
				isRegular = true;
			}
		}*/
		if(!isRegular){
			return "{\"result\":\"error\",\"message\":\"规则编码不在已有的编码内！\"}";
		}
		return "{\"result\":\"ok\",\"message\":\"ok\"}";
	}
	
	private String PostWeiXin(String urlParams,String content)throws Exception{
		System.out.println("rq_content = [" + content + "| cur_date = [" + DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss") + "]");
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
        out.write(content.getBytes("utf-8"));
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
            String rp_content = new String(outStream.toByteArray());
            System.out.println("rp_content = [" + rp_content + "| cur_date = [" + DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss") + "]");
            return rp_content;
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
	
}
