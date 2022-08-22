package demo.controller

import demo.entity.MessageEntity
import demo.exception.UserNotFound
import demo.services.MessageService
import org.springframework.web.bind.annotation.*

@RestController
class MessageController(
    private val service: MessageService
) {
    @GetMapping
    fun findMessages(): List<MessageEntity> = this.service.findMessages()

    @PostMapping
    fun saveMessage(@RequestBody messageEntity: MessageEntity) = this.service.save(messageEntity)

    @GetMapping("/{id}")
    fun findById(@PathVariable("id") id: String): MessageEntity? {
        return this.service.findById(id)
            .orElseThrow { UserNotFound(id) }
    }
}