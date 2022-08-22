package demo.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("MESSAGES")
data class MessageEntity(@Id val id: String?, val text: String)