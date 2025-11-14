package cc.yiueil.service;

import cc.yiueil.dto.MessageDto;

import java.util.List;

/**
 * MessageService 消息服务
 *
 * @author 弋孓 YIueil@163.com
 * @version 1.0
 * @date 2024/4/5 19:33
 */
public interface MessageService {
    /**
     * 保存消息
     *
     * @param messageDto message
     */
    void save(MessageDto messageDto);

    /**
     * 发送消息
     *
     * @param senderId    发送人id
     * @param receiverIds 接收人ids
     * @param content     内容
     */
    void send(Long senderId, List<Long> receiverIds, String content);

    /**
     * 生成系统消息
     *
     * @param receiverIds 接收人ids
     * @param content     内容
     */
    void systemMessage(List<Long> receiverIds, String content);

    /**
     * 消息已读
     *
     * @param messageIds 消息id
     */
    void messageRead(List<Long> messageIds);

    /**
     * 消息置顶
     *
     * @param messageId 消息id
     */
    void messageTop(Long messageId);
}
