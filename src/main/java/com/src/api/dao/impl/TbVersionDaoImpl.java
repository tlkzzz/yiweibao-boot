/**    
* @{#} TbInformationCataDaoImpl.java Create on 2015��8��17�� ����3:33:49    
* Copyright (c) 2015.    
*/
package com.src.api.dao.impl;

import org.springframework.stereotype.Repository;

import com.src.common.base.dao.impl.BaseDaoMysqlImpl;
import com.src.api.dao.TbVersionDao;
import com.src.api.entity.TbVersion;

/**
 * @author <a href="mailto:liwei.flyf@gmail.com">author</a>
 * @version 1.0
 * @description
 */
@Repository("tbVersionDao")
public class TbVersionDaoImpl extends BaseDaoMysqlImpl<TbVersion, Integer>implements TbVersionDao {

	public TbVersionDaoImpl() {
		super(TbVersion.class);
	}
}
