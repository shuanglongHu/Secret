package tools;

import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

public abstract class Coder {

    public static byte[] decryptBASE64(String key) throws Exception{
        Decoder decoder = Base64.getDecoder();
        byte[] buffer = decoder.decode(key);
        return buffer;
    }
    public static String encryptBASE64(byte[] key){
        Encoder encoder = Base64.getEncoder();
        String encode = encoder.encodeToString(key);
        return encode;
    }

}
