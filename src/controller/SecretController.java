package controller;

import domain.Secret;
import service.SecretService;

import java.io.File;
import java.util.List;

/**
 * 控制器层，接收视图层的数据，调用业务层，完成后续操作
 */
public class SecretController {
    private SecretService service = new SecretService();

    public int desEncryptFile(String username, File inputFile, File outputFile){
        return service.desEncryptFile(username, inputFile,outputFile);
    }

    public List<Secret> showAllSecret(String username){
        return service.showAllSecret(username);
    }

    public void desDecryptFile(File inputFile, File outputFile, String key){
        service.desDecryptFile(inputFile,outputFile,key);
    }
}
