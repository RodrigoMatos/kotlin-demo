package demo.services

import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.header.Headers
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.stereotype.Service
import org.springframework.util.concurrent.ListenableFuture
import java.util.*

@Service
class KafkaService(
    @Autowired val kafkaTemplate: KafkaTemplate<Any, Any>
) {
    private val log = LoggerFactory.getLogger(javaClass)

    fun producer(topic: String, key: Any? = null, value: Any, handlerHeader: Headers.() -> Unit = {}): ListenableFuture<SendResult<Any?, Any>> {
        log.info("Kafka Producing topic: {}, key: {}, value: {}", topic, key, value)
        val record = Optional.ofNullable(key)
            .map { ProducerRecord(topic, key, value) }
            .orElse(ProducerRecord(topic, value))
        record.headers().add("X-Custom-Header", "Custom header here")
        handlerHeader(record.headers())
        return this.kafkaTemplate.send(record)
    }
}

private val charset = Charsets.UTF_8
fun Headers.add(key: String, value: String) : Headers {
    return this.add(key, value.toByteArray(charset))
}