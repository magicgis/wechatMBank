/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.yitong.weixin.front.info.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yitong.weixin.common.service.CrudService;
import com.yitong.weixin.front.info.dao.ArticleDataFDao;
import com.yitong.weixin.front.info.entity.ArticleDataF;

/**
 * 站点Service
 * @author ThinkGem
 * @version 2013-01-15
 */
@Service
@Transactional(readOnly = true)
public class ArticleDataFService extends CrudService<ArticleDataFDao, ArticleDataF> {

}
