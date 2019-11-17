package com.robby.app.commons.utils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.spring.stat.DruidStatInterceptor;
import com.google.common.base.Splitter;
import com.robby.app.commons.properties.db.DataSourceProperties;
import com.robby.app.commons.properties.db.DruidProperties;
import com.robby.app.commons.properties.db.StateViewProperties;
import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Stream;

/**
 * 数据库相关函数
 * Created @ 2019/11/17
 * @author liuwei
 */
public class DataBaseUtil {
    /**
     * 数据源参数
     */
    @Getter
    final DataSourceProperties dataSourceProperties;
    /**
     * Druid连接池参数
     */
    @Getter
    final DruidProperties druidProperties;
    /**
     * Druid监控参数
     */
    @Getter
    final StateViewProperties statViewProperties;

    public DataBaseUtil(DataSourceProperties dataSourceProperties, DruidProperties druidProperties, StateViewProperties statViewProperties) {
        this.dataSourceProperties = dataSourceProperties;
        this.druidProperties = druidProperties;
        this.statViewProperties = statViewProperties;
    }

    /**
     * 自动创建数据库，在SpringBoot中创建DataSource前调用此方法可自动创建项目所需的空数据库
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public boolean initDataBase() throws ClassNotFoundException, SQLException {
        Splitter sp = Splitter.on("/").trimResults();
        String uri = dataSourceProperties.getUrl();
        String base = uri.substring(0, uri.lastIndexOf("?"));
        List<String> uriProp = sp.splitToList(uri);
        String database = uriProp.get(uriProp.size() - 2);
        String name = database.substring(0, database.indexOf("?"));
        String mysqlUri = String.format("%s/mysql?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf8", base.substring(0, base.lastIndexOf("/")));
        // 初始化Driver，然后连接到mysql库
        Class.forName(dataSourceProperties.getDriverClassName());
        Connection connection = DriverManager.getConnection(mysqlUri, dataSourceProperties.getUsername(), dataSourceProperties.getPassword());
        Statement statement = connection.createStatement();
        // 生成创建database的SQL
        String createSQL = String.format(
                "create database if not exists `%s` default character set utf8mb4 collate utf8mb4_general_ci",
                name
        );
        // 执行SQL创建database
        boolean flag = statement.executeUpdate(createSQL) > 0;
        // 关闭连接
        statement.close();
        connection.close();
        return flag;
    }

    /**
     * 创建datasource
     * @return
     */
    public DruidDataSource createDruidSource() throws SQLException {
        DruidDataSource source = new DruidDataSource();
        // 设置datasource配置参数
        // 基本连接参数
        source.setUrl(dataSourceProperties.getUrl());
        source.setDriverClassName(dataSourceProperties.getDriverClassName());
        source.setUsername(dataSourceProperties.getUsername());
        source.setPassword(dataSourceProperties.getPassword());
        // 连接池参数
        source.setInitialSize(druidProperties.getInitialSize());
        source.setMaxActive(druidProperties.getMaxActive());
        source.setMinIdle(druidProperties.getMinIdle());
        source.setMaxWait(druidProperties.getMaxWait());
        source.setTimeBetweenConnectErrorMillis(druidProperties.getTimeBetweenEvictionRunsMillis());
        source.setMinEvictableIdleTimeMillis(druidProperties.getMinEvictableIdleTImeMillis());
        source.setValidationQuery(druidProperties.getValidationQuery());
        source.setTestOnBorrow(druidProperties.getTestOnBorrow());
        source.setTestOnReturn(druidProperties.getTestOnReturn());
        source.setTestWhileIdle(druidProperties.getTestWhileIdle());
        source.setPoolPreparedStatements(druidProperties.getPoolPreparedStatements());
        source.setMaxPoolPreparedStatementPerConnectionSize(druidProperties.getMaxIdle());
        // 监控拦截器
        source.setFilters(druidProperties.getFilter());
        // 长连接和慢查询参数
        Optional<String> connProp = Optional.ofNullable(druidProperties.getConnectionProperties());
        if(connProp.isPresent()) {
            Properties connectProperties = new Properties();
            Splitter sp = Splitter.on(";").trimResults();
            sp.splitToList(connProp.get()).stream().forEach(m -> {
                String[] attr = m.split("=");
                connectProperties.setProperty(attr[0], attr[1]);
            });
            source.setConnectProperties(connectProperties);
        }
        return source;
    }

    /**
     * druid的状态拦截器
     * @return
     */
    public DruidStatInterceptor createStatInterceptor() {
        DruidStatInterceptor interceptor = new DruidStatInterceptor();
        return interceptor;
    }

}
