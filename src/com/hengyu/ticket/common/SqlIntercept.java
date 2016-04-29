package com.hengyu.ticket.common;

import java.util.Properties;

import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;

import com.alibaba.fastjson.JSON;

/*
Executor
	(update, query, flushStatements, commit, rollback, getTransaction, close, isClosed)
ParameterHandler
	(getParameterObject, setParameters)
ResultSetHandler
	(handleResultSets, handleOutputParameters)
StatementHandler
	(prepare, parameterize, batch, update, query)

@Intercepts(
{@Signature
(
	type= Executor.class,
	args = {MappedStatement.class,Object.class}, 
	method = "query"
),
@Signature(type= StatementHandler.class,
args = {StatementHandler.class,Object.class}, method = "query")})
*/
public class SqlIntercept implements Interceptor {

    private boolean show_sql = true;
    private SQLFormat sf = new SQLFormat();

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        try {
            if (show_sql) {
                System.out.println(JSON.toJSONString(invocation.getArgs()));
            }
        } catch (Exception e) {
        }

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {

        if (show_sql) {
            try {
                if (target instanceof RoutingStatementHandler) {
                    RoutingStatementHandler rsh = (RoutingStatementHandler) target;
                    BoundSql boundSql = rsh.getBoundSql();
                    String sql = boundSql.getSql();
                    System.out.println("\n\n^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ sql ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ \n " + sf.format(sql));
                    System.out.println("\n^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ params ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^\n ");
                    System.out.println(JSON.toJSONString(boundSql.getParameterObject()));
                    System.out.println("\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return target;
    }

    @Override
    public void setProperties(Properties properties) {

    }

    public boolean isShow_sql() {
        return show_sql;
    }

    public void setShow_sql(boolean show_sql) {
        this.show_sql = show_sql;
    }

}
