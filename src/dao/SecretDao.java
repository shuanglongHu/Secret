package dao;
import domain.Secret;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import tools.DESCoder;
import tools.JDBCUtils;

import java.io.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 实现对数据库的操作（增删改查）
 * dbutils工具类完成，类成员创建QueryRunner对象，指定数据源
 */
public class SecretDao {
    private QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());

    /**
     * 给定需加密的文件及加密和保存的文件，进行文件加密
     * @param inputFile
     * @param outputFile
     * @return
     */
    public int desEncryptFile(String username, File inputFile, File outputFile) {
        String key = null;
        int row = 0;
        try {
            key = DESCoder.initKey();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("初始化密钥失败！");
        }
        String res = DESCoder.encrypt(key, inputFile, outputFile);
        if(!res.equals("succeed")) {
            String sql = "INSERT INTO files(username, filename, secretKey, encryptTime) VALUES(?,?,?,?);";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String date = sdf.format(new Date());
            Object[] params = {username,outputFile.getAbsolutePath(), key, date};
            try {
                row = queryRunner.update(sql, params);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("数据库更新失败");
            }
        }

        return row;


    }

    /**
     * 展示加密文件列表
     * @return
     */
    public List<Secret> showAllSecret(String username) {
        String sql = "SELECT * from files where username = (?);";
        Object[] params = {username};
        List<Secret> list = null;
        try {
            list = queryRunner.query(sql, new BeanListHandler<>(Secret.class), params);
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("查询所有加密文件失败！");
        }

    }

    /**
     * 根据密钥解密文件
     * @param inputFile
     * @param outputFile
     * @param key
     */
    public void desDecryptFile(File inputFile, File outputFile, String key) {
        String res = DESCoder.decrypt(key, inputFile, outputFile);
        if (res.equals("success")) {
            System.out.println("解密成功！");
        } else {
            System.out.println("解密失败！");
        }
    }
}
