package demo.config

import demo.config.deserializer.GenericDeserializer
import org.apache.kafka.clients.consumer.ConsumerConfig
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
        val props: MutableMap<String, Any> = mutableMapOf(
                Pair(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, this.servers),
                Pair(ConsumerConfig.GROUP_ID_CONFIG, "demo"),
                Pair(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, GenericDeserializer::class.java),
                Pair(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, GenericDeserializer::class.java),
                Pair(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"))
        return DefaultKafkaConsumerFactory(props)
    }

    @Bean
    fun kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<Any, Any>? {
        val factory = ConcurrentKafkaListenerContainerFactory<Any, Any>()
        factory.consumerFactory = consumerFactory()
        factory.containerProperties.ackMode = ContainerProperties.AckMode.MANUAL_IMMEDIATE
        factory.containerProperties.isSyncCommits = true;
        return factory
    }
}