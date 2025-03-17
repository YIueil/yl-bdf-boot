package cc.yiueil.util;

import java.io.*;

/**
 * IOUtils IO工具类
 *
 * @author 弋孓 YIueil@163.com
 * @version 1.0
 * @date 2024/1/18 16:04
 */
public class IoUtils {
    /**
     * 将 InputStream 转换为 byte[] 数组
     *
     * @param input 输入流
     * @return byte[] 数组
     * @throws IOException IO异常
     */
    public static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
        return output.toByteArray();
    }

    /**
     * 写入：将bytes二进制数组写入到输出流中
     * @param bytes 二进制数组
     * @param outputStream 输出流
     */
    public static void write(byte[] bytes, OutputStream outputStream) throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        // 使用缓存读取
        write(byteArrayInputStream, outputStream);
    }

    /**
     * 写入：将bytes二进制数组写入到输出流中
     * @param inputStream 输入流
     * @param outputStream 输出流
     */
    public static void write(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] buffer = new byte[4096];
        int readIndex;
        while ((readIndex = inputStream.read(buffer, 0, buffer.length)) != -1) {
            outputStream.write(buffer, 0, readIndex);
        }
    }
}
