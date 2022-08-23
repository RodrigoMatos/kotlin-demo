package demo.repository

import demo.entity.MessageEntity
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository

interface MessageRepository : CrudRepository<MessageEntity, String> {

    @Query("select * from messages")
    fun findMessages(): List<MessageEntity>
}