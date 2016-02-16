/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.yitong.weixin.front.info.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yitong.weixin.common.service.BaseService;
import com.yitong.weixin.common.service.CrudService;
import com.yitong.weixin.front.info.dao.BankObjectDao;
import com.yitong.weixin.front.info.dao.WeiXinSessionParaDao;
import com.yitong.weixin.front.info.entity.BankObject;
import com.yitong.weixin.front.info.entity.WeiXinSessionPara;
import com.yitong.weixin.front.receive.func.LonLat;


/**
 * 文章Service
 * @author he.huang
 * @version 2013-05-15
 */
@Service
@Transactional(readOnly = true)
public class BankObjectService extends CrudService<BankObjectDao, BankObject> {
	
	public List<Map> findByLocation(String title, String loc_x, String loc_y) {
		List<Map> list = new ArrayList<Map>();
		Map map = null;
		double x = Double.parseDouble(loc_x);
		double y = Double.parseDouble(loc_y);
		double add = x+y;
			try {
				System.out.println("findByLocation-------------------------------");
				List<BankObject> listb = dao.findByLocation(title, add);
				for (BankObject bankObject : listb) {
					map = new HashMap();
					String name = bankObject.getBankName();
					String address = bankObject.getAddress();
					double posx = Double.parseDouble(bankObject.getPosX());
					double posy = Double.parseDouble(bankObject.getPosY());
					double r = LonLat.getDistance(x, posx, y, posy);
					DecimalFormat df   = new DecimalFormat("######0.00");   
					String len = df.format(r/1000); 
					map.put("name", name);
					map.put("address", address);
					map.put("len", len);
					map.put("posx", posx);
					map.put("posy", posy);
					list.add(map);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		return list;
	}
}
