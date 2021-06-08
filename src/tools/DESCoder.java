package tools;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Key;
import java.security.SecureRandom;


public abstract class DESCoder extends Coder {
    public static final String ALGORITHM = "DES";
    private static final String CIPHER_ALGORITHM = "DES/CBC/PKCS5Padding";


    //des 密钥生成
    private static Key toKey(byte[] key) throws Exception {
        DESKeySpec dks = new DESKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        SecretKey secretKey = keyFactory.generateSecret(dks);

        return secretKey;
    }

    //解密文件算法
    public static String decrypt(String key, File inputFile, File outputFile){
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        CipherOutputStream cipherOutputStream = null;
        String res = null;
        try {
            if(inputFile.isDirectory()) {
                System.out.println("文件输入错误，不能解密文件夹！");
                res = "fail";
            } else if (inputFile.exists()) {
                /**
                 * 读取文件，并分段加密并存储数据
                 */
                Key k = toKey(decryptBASE64(key));
                Cipher cipher = Cipher.getInstance(ALGORITHM);
                cipher.init(Cipher.DECRYPT_MODE, k);
                fileInputStream = new FileInputStream(inputFile);
                fileOutputStream = new FileOutputStream(outputFile);
                cipherOutputStream = new CipherOutputStream(fileOutputStream,cipher);
                byte[] buffer = new byte[1024];
                int readCount = 0;
                while((readCount = fileInputStream.read(buffer)) > 0) {
                    cipherOutputStream.write(buffer, 0, readCount);
                }
                res = "success";
            } else {
                System.out.println("文件不存在，请重新选择！");
                res = "fail";
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("文件加密出错！");
        } finally {
            if (fileInputStream != null) {
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
            if(cipherOutputStream != null) {
                try {
                    cipherOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return res;
    }

    //加密文件算法
    public static String encrypt(String key, File inputFile, File outputFile){
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        CipherInputStream cipherInputStream = null;
        String res = null;
        try {
            if(inputFile.isDirectory()) {
                System.out.println("文件输入错误，不能加密文件夹");
                res = "fail";
            } else if (inputFile.exists()) {
                /**
                 * 读取文件，并分段加密并存储数据
                 */
                Key k = toKey(decryptBASE64(key));
                Cipher cipher = Cipher.getInstance(ALGORITHM);
                cipher.init(Cipher.ENCRYPT_MODE, k);
                fileInputStream = new FileInputStream(inputFile);
                fileOutputStream = new FileOutputStream(outputFile);
                cipherInputStream = new CipherInputStream(fileInputStream, cipher);
                byte[] buffer = new byte[1024];
                int readCount = 0;
                while((readCount = fileInputStream.read(buffer)) > 0) {
                    fileOutputStream.write(buffer, 0, readCount);
                }
                res = "success";
            } else {
                System.out.println("文件不存在，请重新选择！");
                res = "fail";
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("文件加密出错！");
        } finally {
            if (fileInputStream != null) {
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
            if(cipherInputStream != null) {
                try {
                    cipherInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return res;
    }


    public static String initKey() throws  Exception{
        return initKey(null);
    }


    public static String initKey(String seed) throws Exception {
        SecureRandom secureRandom = null;

        if (seed != null) {
            secureRandom = new SecureRandom(decryptBASE64(seed));
        } else {
            secureRandom = new SecureRandom();
        }

        KeyGenerator kg = KeyGenerator.getInstance(ALGORITHM);
        kg.init(secureRandom);

        SecretKey secretKey = kg.generateKey();

        return encryptBASE64 (secretKey.getEncoded());

    }
}
