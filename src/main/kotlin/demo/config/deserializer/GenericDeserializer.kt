package demo.config.deserializer

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.common.serialization.Deserializer
import org.slf4j.LoggerFactory

class GenericDeserializer() : Deserializer<Any> {
    private val objectMapper = ObjectMapper()
    private val log = LoggerFactory.getLogger(javaClass)

    override fun deserialize(topic: String?, data: ByteArray?): Any? {
        return try {
            data?.let(::String)
                    ?.let { objectMapper.readValue(it, Any::class.java) }
        } catch (e: Exception) {
            log.error("Error on GenericDeserializer {}", topic, e)
            throw e
        }
    }

    override fun close() {}
}