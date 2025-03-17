package cc.yiueil.lang.instance;

/**
 * ModelTransform 作为 Model Transform 类 具备两点功能：
 * 1、转换为 entity 的方法
 * 2、使用 entity 来构造实例的能力
 * @author 弋孓 YIueil@163.com
 * @date 2023/5/30 22:55
 * @version 1.0
 */
public interface ModelTransform<DTO, Entity> {
    /**
     * DTO 转 Entity
     * @param dto 数据传输类
     * @return 实体类
     */
    Entity dto2Entity(DTO dto);

    /**
     * Entity 转 DTO
     * @param t 实体类
     * @return 数据传输类
     */
    DTO entity2Dto(Entity t);
}
