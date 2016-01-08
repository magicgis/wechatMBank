/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.yitong.weixin.common.persistence;

import com.yitong.weixin.common.utils.IdGen;

/**
 * 数据Entity类
 * @author Fwy
 * @version 2015-12-29
 */
public abstract class MyEntity<T> extends BaseEntity<T> {

	private static final long serialVersionUID = 1L;
	
	public MyEntity() {
		super();
	}
	
	public MyEntity(String id) {
		super(id);
	}
	
	/**
	 * 插入之前执行方法，需要手动调用
	 */
	@Override
	public void preInsert(){
		// 不限制ID为UUID，调用setIsNewRecord()使用自定义ID
		if (!this.isNewRecord){
			setId(IdGen.uuid());
		}
	}
	
	/**
	 * 更新之前执行方法，需要手动调用
	 */
	@Override
	public void preUpdate(){
		
	}
}
