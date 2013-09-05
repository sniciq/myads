package com.ku6ads.dao.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.ibatis.sqlmap.engine.impl.SqlMapClientImpl;
import com.ibatis.sqlmap.engine.mapping.parameter.ParameterMap;
import com.ibatis.sqlmap.engine.mapping.sql.Sql;
import com.ibatis.sqlmap.engine.mapping.sql.dynamic.DynamicSql;
import com.ibatis.sqlmap.engine.mapping.sql.simple.SimpleDynamicSql;
import com.ibatis.sqlmap.engine.mapping.statement.MappedStatement;
import com.ibatis.sqlmap.engine.scope.SessionScope;
import com.ibatis.sqlmap.engine.scope.StatementScope;

/**
 * DAO层基础抽象类
 * @author yangHanguang
 *
 */
public abstract class BaseAbstractDao implements BaseDao {
	
	private SqlMapClientTemplate sqlMapClientTemplate;

	protected IbatisMethodMapping mapping;
	
	public Connection getConnection() throws Exception {
		return getSqlMapClientTemplate().getSqlMapClient().getDataSource().getConnection();
	}
	
	public String getSql(String sqlMapId, Object parameterObject) {
		SqlMapClientImpl sqlmap = (SqlMapClientImpl) getSqlMapClientTemplate().getSqlMapClient();
		MappedStatement stmt = sqlmap.getMappedStatement(sqlMapId);
		SessionScope sessionScope = new SessionScope();
		sessionScope.incrementRequestStackDepth();
		sessionScope.setSqlMapClient(sqlmap);
		sessionScope.setInBatch(true);
		StatementScope stmtScope = new StatementScope(sessionScope);
		stmt.initRequest(stmtScope);
		stmtScope.setStatement(stmt);
		
		Sql sql = stmt.getSql();
		ParameterMap parameterMap = sql.getParameterMap(stmtScope, parameterObject);
		stmtScope.setParameterMap(parameterMap);
		String sqlString = sql.getSql(stmtScope, parameterObject);
		
		Object[] parameterArr = parameterMap.getParameterObjectValues(stmtScope, parameterObject);  
		
		return sqlString;
	}
	
	public Integer insert(Object po) {
		return (Integer) getSqlMapClientTemplate().insert(getMapping().getInsertMethod(), po);
	}
	
	public Integer updateById(Object po) {
		return getSqlMapClientTemplate().update(getMapping().getUpdateByIdMethod(), po);
	}
	
	public void deleteById(Integer id) {
		getSqlMapClientTemplate().delete(getMapping().getDeleteByIdMethod(), id);
	}
	
	public Object selectById(Integer id) {
		return getSqlMapClientTemplate().queryForObject(getMapping().getSelectByIdMethod(), id);
	}
	
	@SuppressWarnings("unchecked")
	public List selectByEntity(Object po) {
		return getSqlMapClientTemplate().queryForList(getMapping().getSelectByEntityMethod(), po);
	}
	
	public List selectByLimit(Object po) {
		return getSqlMapClientTemplate().queryForList(getMapping().getSelectByLimitMethod(), po);
	}
	
	public Integer selectLimitCount(Object po) {
		return (Integer) getSqlMapClientTemplate().queryForObject(getMapping().getSelectLimitCountMethod(), po);
	}
	

	public IbatisMethodMapping getMapping() {
		return mapping;
	}

	public void setMapping(IbatisMethodMapping mapping) {
		this.mapping = mapping;
	}

	public SqlMapClientTemplate getSqlMapClientTemplate() {
		return sqlMapClientTemplate;
	}

	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}
}
