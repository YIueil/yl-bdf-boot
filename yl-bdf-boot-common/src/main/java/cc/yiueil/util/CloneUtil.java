package cc.yiueil.util;

import java.io.*;

/**
 * DeepCopyUtil 对象拷贝对象
 * @author 弋孓 YIueil@163.com
 * @date 2023/5/30 23:35
 * @version 1.0
 */
public class CloneUtil {
    /**
     * 对象深度拷贝 通过序列化的方式来拷贝对象, 需要拷贝对象和子对象都支持序列化
     * @param obj 待拷贝的对象
     * @return 拷贝后的新对象
     * @throws IOException IO异常
     * @throws ClassNotFoundException 类不存在异常
     */
    @SuppressWarnings("all")
    public static <T extends Serializable> T deepClone(T obj) throws IOException, ClassNotFoundException, NotSerializableException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(obj);
        oos.flush();
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
        return (T) ois.readObject();
    }
    
}