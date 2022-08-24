package demo.config

import demo.config.deserializer.GenericDeserializer
import org.apache.kafka.clients.consumer.ConsumerConfig.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.listener.ContainerProperties


@Configuration
class KafkaConsumerConfig(
        @Value("\${kafka.bootstrapAddress}")
        private val servers: String
) {
    @Bean
    fun consumerFactory(): ConsumerFactory<Any?, Any?> {
        return DefaultKafkaConsumerFactory(
                mutableMapOf<String, Any>(BOOTSTRAP_SERVERS_CONFIG to this.servers,
                        GROUP_ID_CONFIG to "demo",
                        KEY_DESERIALIZER_CLASS_CONFIG to GenericDeserializer::class.java,
                        VALUE_DESERIALIZER_CLASS_CONFIG to GenericDeserializer::class.java,
                        AUTO_OFFSET_RESET_CONFIG to "earliest"))
    }

    @Bean
    fun kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<Any, Any>? {
        return ConcurrentKafkaListenerContainerFactory<Any, Any>()
                .apply {
                    consumerFactory = consumerFactory()
                    containerProperties.ackMode = ContainerProperties.AckMode.MANUAL_IMMEDIATE
                    containerProperties.isSyncCommits = true
                }
    }
}