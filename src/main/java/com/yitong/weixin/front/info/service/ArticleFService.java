/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.yitong.weixin.front.info.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.yitong.weixin.common.persistence.Page;
import com.yitong.weixin.common.service.CrudService;
import com.yitong.weixin.common.utils.StringUtils;
import com.yitong.weixin.front.info.dao.ArticleDataFDao;
import com.yitong.weixin.front.info.dao.ArticleFDao;
import com.yitong.weixin.front.info.entity.ArticleDataF;
import com.yitong.weixin.front.info.entity.ArticleF;

/**
 * 文章Service
 * @author ThinkGem
 * @version 2013-05-15
 */
@Service
@Transactional(readOnly = true)
public class ArticleFService extends CrudService<ArticleFDao, ArticleF> {

	@Autowired
	private ArticleDataFDao articleDataFDao;
//	@Autowired
//	private CategoryFDao categoryFDao;
	
	/*@Transactional(readOnly = false)
	public Page<ArticleF> findPage(Page<ArticleF> page, ArticleF article, boolean isDataScopeFilter) {
//		DetachedCriteria dc = dao.createDetachedCriteria();
//		dc.createAlias("category", "category");
//		dc.createAlias("category.site", "category.site");
		if (article.getCategory()!=null && StringUtils.isNotBlank(article.getCategory().getId()) && !CategoryF.isRoot(article.getCategory().getId())){
			CategoryF category = categoryFDao.get(article.getCategory().getId());
			if (category==null){
				category = new CategoryF();
			}
			category.setParentIds(category.getId());
			category.setSite(category.getSite());
			article.setCategory(category);
		}
		else{
			article.setCategory(new CategoryF());
		}
//		if (StringUtils.isBlank(page.getOrderBy())){
//			page.setOrderBy("a.weight,a.update_date desc");
//		}
//		return dao.find(page, dc);
	//	article.getSqlMap().put("dsf", dataScopeFilter(article.getCurrentUser(), "o", "u"));
		return super.findPage(page, article);
		
	}*/
	
//	@Transactional(readOnly = false)
//	public Page<ArticleF> findPageByMsgType(Page<ArticleF> page, ArticleF article, boolean isDataScopeFilter) {
//		if (article.getCategory()!=null && StringUtils.isNotBlank(article.getCategory().getId()) && !CategoryF.isRoot(article.getCategory().getId())){
//			CategoryF category = categoryFDao.get(article.getCategory().getId());
//			if (category==null){
//				category = new CategoryF();
//			}
//			category.setParentIds(category.getId());
//			category.setSite(category.getSite());
//			article.setCategory(category);
//		}
//		else{
//			article.setCategory(new CategoryF());
//		}
//		article.setPage(page);
//		page.setList(dao.findListByMsgType(article));
//		return page;
//	}

	@Transactional(readOnly = false)
	public void save(ArticleF article) {
		if (article.getArticleData().getContent()!=null){
			article.getArticleData().setContent(StringEscapeUtils.unescapeHtml4(
					article.getArticleData().getContent()));
		}
		article.setDelFlag(ArticleF.DEL_FLAG_NORMAL);
//		article.setUpdateBy(UserUtils.getUser());
		article.setUpdateDate(new Date());
		
        ArticleDataF articleData = new ArticleDataF();;
		if (StringUtils.isBlank(article.getId())){
			article.preInsert();
			articleData = article.getArticleData();
			articleData.setId(article.getId());
			dao.insert(article);
			articleDataFDao.insert(articleData);
		}else{
			article.preUpdate();
			articleData = article.getArticleData();
			articleData.setId(article.getId());
			dao.update(article);
			articleDataFDao.update(article.getArticleData());
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(ArticleF article, Boolean isRe) {
//		dao.updateDelFlag(id, isRe!=null&&isRe?Article.DEL_FLAG_NORMAL:Article.DEL_FLAG_DELETE);
		// 使用下面方法，以便更新索引。
		//Article article = dao.get(id);
		//article.setDelFlag(isRe!=null&&isRe?Article.DEL_FLAG_NORMAL:Article.DEL_FLAG_DELETE);
		//dao.insert(article);
		super.delete(article);
	}
	
	/**
	 * 通过编号获取内容标题
	 * @return new Object[]{栏目Id,文章Id,文章标题}
	 */
//	public List<Object[]> findByIds(String ids) {
//		if(ids == null){
//			return new ArrayList<Object[]>();
//		}
//		List<Object[]> list = Lists.newArrayList();
//		String[] idss = StringUtils.split(ids,",");
//		ArticleF e = null;
//		for(int i=0;(idss.length-i)>0;i++){
//			e = dao.get(idss[i]);
//			list.add(new Object[]{e.getCategory().getId(),e.getId(),StringUtils.abbr(e.getTitle(),50)});
//		}
//		return list;
//	}
	
	/**
	 * 点击数加一
	 */
	@Transactional(readOnly = false)
	public void updateHitsAddOne(String id) {
		dao.updateHitsAddOne(id);
	}
	
	public List<ArticleF> findByDescription(String content){
		return dao.findByDescription(content);
	}
	
	public ArticleF findByTitle(String title){
		List<ArticleF> list = dao.findByTitle(title);
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
}
