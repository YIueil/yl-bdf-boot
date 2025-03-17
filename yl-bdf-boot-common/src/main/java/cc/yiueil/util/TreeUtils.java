package cc.yiueil.util;

import cc.yiueil.lang.tree.Tree;
import cc.yiueil.lang.tree.TreeBuilder;
import cc.yiueil.lang.tree.TreeNode;
import cc.yiueil.lang.tree.TreeNodeConfig;
import cc.yiueil.lang.tree.parser.TreeParser;

import java.util.List;

/**
 * TreeUtils 树结构操作工具
 * @author 弋孓 YIueil@163.com
 * @date 2023/5/30 23:38
 * @version 1.0
 */
public class TreeUtils {
    /**
     * 构建树结构
     *
     * @param <T>          id类型
     * @param treeNodeList 扁平集合结构
     * @param rootId       根节点id
     * @return 构建好的树结构
     */
    public static <T> List<Tree<T>> build(List<TreeNode<T>> treeNodeList, T rootId) {
        return TreeBuilder.of(rootId, TreeNodeConfig.DEFAULT_CONFIG)
                .append(treeNodeList, new TreeParser<>())
                .buildList();
    }
}
