package service;

import dao.SecretDao;
import domain.Secret;

import java.io.File;
import java.util.List;

/**
 * 业务层，接收控制层的数据；调用数据访问层，完成对数据库的操作
 */
public class SecretService {
    private SecretDao dao = new SecretDao();

    public int desEncryptFile(String username, File inputFile, File outputFile) {
        return dao.desEncryptFile(username, inputFile,outputFile);
    }

    public List<Secret> showAllSecret(String username) {
        return dao.showAllSecret(username);
    }

    public void desDecryptFile(File inputFile, File outputFile, String key) {
        dao.desDecryptFile(inputFile, outputFile, key);
    }
}
