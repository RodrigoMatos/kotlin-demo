package demo.kafka.consumer

import com.fasterxml.jackson.databind.ObjectMapper
import demo.dto.MessageDTO
import demo.services.MessageService
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component

@Component
class MessageConsumer(
        private val objectMapper: ObjectMapper,
        private val messageService: MessageService
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @KafkaListener(topics = ["message"], groupId = "demo")
    fun listenMessage(consumerRecord: ConsumerRecord<Any, Any>, ack: Acknowledgment) {
        try {
            val message = consumerRecord.payload<MessageDTO>()
            logger.info("Message {} received {}", consumerRecord.key(), message)
            messageService.findById(message.id)?.let {
                logger.info("MessageDTO validated. Entity: {}", it)
            } ?: logger.warn("MessageDTO not validated. Id: {}", message.id)
        } catch (e: Exception) {
            logger.error("Error on consumer message: {}", consumerRecord, e)
        } finally {
            ack.acknowledge()
        }
    }

    private inline fun <reified T> ConsumerRecord<Any, Any>.payload(): T {
        return objectMapper.convertValue(this.value(), T::class.java)
    }
}