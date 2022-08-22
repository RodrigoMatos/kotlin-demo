package demo.config.serializer

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.apache.kafka.common.serialization.Serializer
import java.util.Optional

class GenericKeySerializer : Serializer<Any> {
    private val objectMapper = ObjectMapper()
    private val log = LoggerFactory.getLogger(javaClass)

    override fun serialize(topic: String?, key: Any?): ByteArray? {
        return try {
            Optional.ofNullable(key)
                .map(this.objectMapper::writeValueAsBytes)
                .orElse(null)
        } catch (e: Exception) {
            log.error("Error on GenericKeySerializer {}", topic, e)
            throw e
        }
    }

    override fun close() {}
}