package demo.config

import org.apache.kafka.clients.admin.NewTopic
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class KafkaTopics {
    @Bean
    fun message(): NewTopic = NewTopic("message", 3, 1)

    @Bean
    fun product(): NewTopic = NewTopic("product", 3, 1)
}