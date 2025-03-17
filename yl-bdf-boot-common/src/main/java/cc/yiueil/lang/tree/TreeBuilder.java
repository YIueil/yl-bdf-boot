package cc.yiueil.lang.tree;

import cc.yiueil.lang.instance.Builder;
import cc.yiueil.lang.tree.parser.TreeParser;
import cc.yiueil.util.MapUtils;
import cc.yiueil.util.ObjectUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * TreeBuilder
 * @author 弋孓 YIueil@163.com
 * @date 2023/5/30 23:12
 * @version 1.0
 */
public class TreeBuilder<T> implements Builder<Tree<T>>, Serializable {
    private static final long serialVersionUID = 1L;

    private final Tree<T> root;
    private final Map<T, Tree<T>> idTreeMap;
    private boolean isBuild;

    public TreeBuilder(T rootId, TreeNodeConfig treeNodeConfig) {
        root = new Tree<>(treeNodeConfig);
        root.setId(rootId);
        this.idTreeMap = new HashMap<>();
    }

    /**
     * 使用默认配置创建构造器
     * @param rootId 根节点id
     * @param <E> 主键id类型
     * @return this
     */
    public static <E> TreeBuilder<E> of(E rootId) {
        return of(rootId, TreeNodeConfig.DEFAULT_CONFIG);
    }

    /**
     * 携带配置创建构造器
     * @param rootId 根节点id
     * @param treeNodeConfig 树构建配置
     * @param <E> 主键id类型
     * @return this
     */
    public static <E> TreeBuilder<E> of(E rootId, TreeNodeConfig treeNodeConfig) {
        return new TreeBuilder<>(rootId, ObjectUtils.defaultIfNull(treeNodeConfig, TreeNodeConfig.DEFAULT_CONFIG));
    }

    /**
     * 增加节点列表，增加的节点是不带子节点的
     *
     * @param map 节点列表
     * @return this
     */
    private TreeBuilder<T> append(Map<T, Tree<T>> map) {
        checkBuilt();

        idTreeMap.putAll(map);
        return this;
    }

    /**
     * 增加元素
     *
     * @param list       Bean列表
     * @param treeParser 节点转换器，用于定义一个Bean如何转换为Tree节点
     * @return this
     */
    public TreeBuilder<T> append(List<TreeNode<T>> list, TreeParser<T> treeParser) {
        checkBuilt();

        final TreeNodeConfig config = root.getTreeNodeConfig();
        final Map<T, Tree<T>> map = new LinkedHashMap<>(list.size(), 1);
        Tree<T> tree;
        for (TreeNode<T> treeNode : list) {
            tree = new Tree<>(config);
            treeParser.parse(treeNode, tree);
            map.put(tree.getId(), tree);
        }
        return append(map);
    }

    /**
     * 检查是否已经构建过
     */
    private void checkBuilt() {
        if (isBuild) {
            throw new RuntimeException("已经构建过该树");
        }
    }

    /**
     * 核心构建逻辑: 使用已有的扁平结构构建树型结构
     */
    public void buildFromMap() {
        if (MapUtils.isEmpty(idTreeMap)) {
            return;
        }

        final Map<T, Tree<T>> eTreeMap = MapUtils.sortByValue(idTreeMap, false);
        T parentId;
        for (Tree<T> node : eTreeMap.values()) {
            if (null == node) {
                continue;
            }
            parentId = node.getParentId();
            if (root.getId().equals(parentId)) {
                root.addChildren(node);
                continue;
            }

            final Tree<T> parentNode = eTreeMap.get(parentId);
            if (null != parentNode) {
                parentNode.addChildren(node);
            }
        }
    }

    @Override
    public Tree<T> build() {
        checkBuilt();
        buildFromMap();
        isBuild = true;
        idTreeMap.clear();
        return root;
    }

    public List<Tree<T>> buildList() {
        return build().getChildren();
    }

    public Tree<T> getRoot() {
        return root;
    }

    public Map<T, Tree<T>> getIdTreeMap() {
        return idTreeMap;
    }

    public boolean isBuild() {
        return isBuild;
    }

    public void setBuild(boolean build) {
        isBuild = build;
    }
}
