package demo.config

import org.apache.kafka.clients.admin.AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.KafkaAdmin

@Configuration
class KafkaConfig(
        @Value("\${kafka.bootstrapAddress}")
        private val servers: String
) {
    @Bean
    fun kafkaAdmin(): KafkaAdmin {
        val configs: MutableMap<String, Any?> = mutableMapOf(BOOTSTRAP_SERVERS_CONFIG to servers)
        return KafkaAdmin(configs)
    }
}