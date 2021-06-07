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
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            String key = DESCoder.initKey();
            if(inputFile.isDirectory()) {
                System.out.println("文件输入错误，不能加密文件夹");
            } else if (inputFile.exists()) {
                /**
                 * 读取文件，并分段加密并存储数据
                 */
                fileInputStream = new FileInputStream(inputFile);
                fileOutputStream = new FileOutputStream(outputFile);
                byte[] buf = new byte[1024 * 8];
                int readCount = 0;
                while((readCount = fileInputStream.read(buf)) != -1) {
                    buf = DESCoder.encrypt(buf, key);
                    fileOutputStream.write(buf, 0, buf.length);
                }
                /**
                 * 将加密后的文件所属用户，文件路径，密钥，及加密时间写入数据库
                 */
                String sql = "INSERT INTO files(username, filename, secretKey, encryptTime) VALUES(?,?,?,?);";
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String date = sdf.format(new Date());
                Object[] params = {username,outputFile.getAbsolutePath(), key, date};
                int row = queryRunner.update(sql, params);
                return row;
            } else {
                System.out.println("文件不存在，请重新选择！");
            }
            return 0;
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new RuntimeException("文件加密出错！");
        } finally {
            if(fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 展示加密文件列表
     * @return
     */
    public List<Secret> showAllSecret(String username) {
        String sql = "SELECT * from files where username = {?};";
        Object[] params = {username};
        List<Secret> list = null;
        try {
            list = queryRunner.query(sql, new BeanListHandler<>(Secret.class));
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
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            if (inputFile.isDirectory()) {
                System.out.println("文件输入错误，不能解密文件夹！");
            } else if (inputFile.exists()) {
                fileInputStream = new FileInputStream(inputFile);
                fileOutputStream = new FileOutputStream(outputFile);
                byte[] buf = new byte[1024 * 8];
                int readCount = 0;
                while((readCount = fileInputStream.read(buf)) != -1) {
                    buf = DESCoder.decrypt(buf, key);
                    fileOutputStream.write(buf, 0, buf.length);
                }
            } else {
                System.out.println("文件不存在，请重新选择！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("文件解密失败！");
        } finally {
            if(fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
