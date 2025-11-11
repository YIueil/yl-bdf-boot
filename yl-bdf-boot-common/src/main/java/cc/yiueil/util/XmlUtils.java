package cc.yiueil.util;

import org.dom4j.*;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.InputStream;
import java.util.*;

/**
 * XmlUtils 基于dom4j的xml解析工具类
 * @author 弋孓 YIueil@163.com
 * @date 2023/5/30 23:39
 * @version 1.0
 */
public class XmlUtils {
    /**
     * dom解析xml
     * @param inputStream xml流
     * @return 解析结果document类
     * @throws DocumentException 文档解析异常
     */
    public static Document parse(InputStream inputStream) throws DocumentException {
        SAXReader reader = new SAXReader();
        return reader.read(inputStream);
    }

    /**
     * dom解析xml
     * @param file xml文件
     * @return 解析结果document类
     * @throws DocumentException 文档解析异常
     */
    public static Document parse(File file) throws DocumentException {
        SAXReader reader = new SAXReader();
        return reader.read(file);
    }

    /**
     * 打印xml文档
     * @param document 文档类
     */
    public static void print(Document document) {
        Element root = document.getRootElement();
        System.out.println("根节点:" + root.getName());
        for (Iterator<Element> it = root.elementIterator(); it.hasNext();) {
            Element element = it.next();
            System.out.println("子节点:" + element.getName());
        }
    }

    /**
     * 解析带命名空间的xml, 使用xpath表达式获取节点列表
     *
     * @param document        文档类
     * @param xpathExpression xpath表达式
     * @param namespace 命名空间
     * @return 获取到的节点集合
     */
    public static List<Node> selectNodes(Document document, String xpathExpression, String namespace) {
        Element root = document.getRootElement();
        Map<String, String> map = new HashMap<>(16);
        map.put(namespace, root.getNamespaceURI());
        XPath xPath = document.createXPath(xpathExpression);
        xPath.setNamespaceURIs(map);
        return xPath.selectNodes(document);
    }

    /**
     * 解析带命名空间的xml, 使用xpath表达式获取第一个节点
     *
     * @param document        文档类
     * @param xpathExpression xpath表达式
     * @param namespace 命名空间
     * @return 获取到的节点集合
     */
    public static Optional<Node> selectSingleNode(Document document, String xpathExpression, String namespace) {
        Node node = null;
        List<Node> nodes = selectNodes(document, xpathExpression, namespace);
        if (CollectionUtils.isNotEmpty(nodes)) {
            node = nodes.get(0);
        }
        return Optional.ofNullable(node);
    }
}
