package demo.config

import demo.config.serializer.GenericKeySerializer
import demo.config.serializer.GenericValueSerializer
import org.apache.kafka.clients.producer.ProducerConfig.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory

@Configuration
class KafkaProducerConfig(
    @Value("\${kafka.bootstrapAddress}")
    private val servers: String
) {
    @Bean
    fun producerFactory(): ProducerFactory<Any, Any> {
        return DefaultKafkaProducerFactory(mutableMapOf<String, Any>(
                BOOTSTRAP_SERVERS_CONFIG to this.servers,
                KEY_SERIALIZER_CLASS_CONFIG to GenericKeySerializer::class.java,
                VALUE_SERIALIZER_CLASS_CONFIG to GenericValueSerializer::class.java
        ))
    }

    @Bean
    fun kafkaTemplate(): KafkaTemplate<Any, Any> {
        return KafkaTemplate(this.producerFactory())
    }
}