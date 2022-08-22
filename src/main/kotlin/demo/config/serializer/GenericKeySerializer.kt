package demo.config.serializer

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.common.serialization.Serializer
import org.slf4j.LoggerFactory

class GenericKeySerializer() : Serializer<Any> {
    private val objectMapper = ObjectMapper()
    private val log = LoggerFactory.getLogger(javaClass)

    override fun serialize(topic: String?, key: Any?): ByteArray? {
        return try {
            key?.let(this.objectMapper::writeValueAsBytes)
        } catch (e: Exception) {
            log.error("Error on GenericKeySerializer {}", topic, e)
            throw e
        }
    }

    override fun close() {}


}