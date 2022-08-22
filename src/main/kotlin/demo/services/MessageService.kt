package demo.services

import demo.entity.MessageEntity
import demo.dto.MessageDTO
import demo.repository.MessageRepository
import org.springframework.stereotype.Service
import java.util.Optional
import java.util.UUID

@Service
class MessageService(
    private val repository: MessageRepository,
    private val kafkaService: KafkaService
) {
    private val topicName: String = "message"

    fun findMessages(): List<MessageEntity> = this.repository.findMessages()

    fun save(messageEntity: MessageEntity): MessageEntity {
        val entitySaved = this.repository.save(messageEntity)
        val msg = MessageDTO(entitySaved.id!!, entitySaved.text)
        this.kafkaService.producer(this.topicName, msg.id, msg) {
            this.add("random-uuid", UUID.randomUUID().toString())
        }
        return entitySaved
    }

    fun findById(id: String): Optional<MessageEntity> = this.repository.findById(id)
}