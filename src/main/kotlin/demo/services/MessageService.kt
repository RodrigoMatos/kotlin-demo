package demo.services

import demo.dto.MessageDTO
import demo.entity.MessageEntity
import demo.repository.MessageRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class MessageService(
    private val repository: MessageRepository,
    private val kafkaService: KafkaService
) {
    private val topicName: String = "message"

    fun findMessages(): List<MessageEntity> = this.repository.findMessages()

    @Transactional
    fun save(messageEntity: MessageEntity): MessageEntity {
        val entitySaved = this.repository.save(messageEntity)
        val msg = MessageDTO(entitySaved.id!!, entitySaved.text)
        this.kafkaService.produce(this.topicName, msg.id, msg) {
            this.add("random-uuid", UUID.randomUUID().toString())
        }
        return entitySaved
    }

    fun findById(id: String): MessageEntity? = this.repository.findByIdOrNull(id)
}