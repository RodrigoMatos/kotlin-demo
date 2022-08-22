package demo.config

import demo.config.serializer.GenericKeySerializer
import demo.config.serializer.GenericValueSerializer
import org.apache.kafka.clients.producer.ProducerConfig
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
        val configProps: MutableMap<String, Any> = HashMap()
        configProps[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = this.servers
        configProps[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = GenericKeySerializer::class.java
        configProps[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = GenericValueSerializer::class.java
        return DefaultKafkaProducerFactory(configProps)
    }

    @Bean
    fun kafkaTemplate(): KafkaTemplate<Any, Any> {
        return KafkaTemplate(this.producerFactory())
    }
}