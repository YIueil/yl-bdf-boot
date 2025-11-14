package cc.yiueil.service.impl;

import cc.yiueil.dto.MessageDto;
import cc.yiueil.entity.MessageEntity;
import cc.yiueil.enums.MessageStateEnum;
import cc.yiueil.enums.MessageTypeEnum;
import cc.yiueil.exception.ResourceNotFoundException;
import cc.yiueil.repository.OrupBaseDao;
import cc.yiueil.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * MessageServiceImpl 消息服务
 *
 * @author 弋孓 YIueil@163.com
 * @version 1.0
 * @date 2024/4/5 20:18
 */
@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    OrupBaseDao baseDao;

    @Override
    public void save(MessageDto messageDto) {
        baseDao.save(messageDto);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void send(Long senderId, List<Long> receiverIds, String content) {
        receiverIds.forEach(receiverId -> {
            MessageEntity messageEntity = new MessageEntity();
            messageEntity.setType(MessageTypeEnum.user.getName());
            messageEntity.setState(MessageStateEnum.unRead.getName());
            messageEntity.setSenderId(senderId);
            messageEntity.setReceiverId(receiverId);
            messageEntity.setContent(content);
        });
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void systemMessage(List<Long> receiverIds, String content) {
        receiverIds.forEach(receiverId -> {
            MessageEntity messageEntity = new MessageEntity();
            messageEntity.setType(MessageTypeEnum.system.getName());
            messageEntity.setState(MessageStateEnum.unRead.getName());
            messageEntity.setReceiverId(receiverId);
            messageEntity.setContent(content);
        });
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void messageRead(List<Long> messageIds) {
        messageIds.forEach(messageId -> {
            MessageEntity messageEntity = baseDao.findById(MessageEntity.class, messageId).orElseThrow(() -> new ResourceNotFoundException("消息不存在"));
            messageEntity.setState(MessageStateEnum.read.getName());
            baseDao.save(messageEntity);
        });
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void messageTop(Long messageId) {
        MessageEntity messageEntity = baseDao.findById(MessageEntity.class, messageId).orElseThrow(() -> new ResourceNotFoundException("消息不存在"));
        messageEntity.setSort(9999);
        baseDao.save(messageEntity);
    }
}
