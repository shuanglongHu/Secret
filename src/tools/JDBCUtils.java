package tools;
import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;

public class JDBCUtils {
    /**
     * 通过连接池预先同数据库建立一些连接，放在内存中，应用程序需要
     * 建立数据库连接时直接到连接池中申请一个就行，用完后再放回去。
     */
    private static BasicDataSource dataSource = new BasicDataSource();

    /**
     * 静态代码块，项目初始化时实现必要参数设置
     */
    static {
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/secret");
        dataSource.setUsername("root");
        dataSource.setPassword("long");
        dataSource.setMaxActive(10);
        dataSource.setMaxIdle(5);
        dataSource.setMinIdle(2);
        dataSource.setInitialSize(10);
    }

    public static DataSource getDataSource() {
        return dataSource;
    }
}
