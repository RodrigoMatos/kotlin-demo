package demo.services

import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.header.Headers
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.stereotype.Service
import org.springframework.util.concurrent.ListenableFuture

@Service
class KafkaService(
        private val kafkaTemplate: KafkaTemplate<Any, Any>
) {
    private val log = LoggerFactory.getLogger(javaClass)

    fun produce(topic: String, key: Any? = null, value: Any, handlerHeader: Headers.() -> Unit = {}): ListenableFuture<SendResult<Any?, Any>> {
        log.info("Kafka Producing topic: {}, key: {}, value: {}", topic, key, value)
        return (key?.let { ProducerRecord(topic, it, value) } ?: ProducerRecord(topic, value))
                .let {
                    it.headers().add("X-Custom-Header", "Custom header here")
                    handlerHeader(it.headers())
                    kafkaTemplate.send(it)
                }
    }
}

private val charset = Charsets.UTF_8
fun Headers.add(key: String, value: String): Headers {
    return this.add(key, value.toByteArray(charset))
}