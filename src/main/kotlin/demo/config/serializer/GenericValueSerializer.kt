package demo.config.serializer

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.common.errors.SerializationException
import org.apache.kafka.common.serialization.Serializer
import org.slf4j.LoggerFactory

class GenericValueSerializer : Serializer<Any> {
    private val objectMapper = ObjectMapper()
    private val log = LoggerFactory.getLogger(javaClass)

    override fun serialize(topic: String?, data: Any?): ByteArray {
        return try {
            data?.let(this.objectMapper::writeValueAsBytes)
                    ?: throw SerializationException("Error when serializing value to ByteArray[]")
        } catch (e: Exception) {
            log.error("Error on GenericKeySerializer {}", topic, e)
            throw e
        }
    }

    override fun close() {}
}