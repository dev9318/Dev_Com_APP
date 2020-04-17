package com.example.myapplication

class Chat {
    private var sender: String? = ""
    private var Id: String?= ""
    private var message: String? = ""
    private var messageId: String? = ""

    fun getSender(): String?{
        return sender
    }
    fun setSender(sender: String) {
        this.sender = sender
    }
    fun getMessage(): String?{
        return message
    }
    fun setMessage(message: String) {
        this.message = message
    }
    fun getMessageId(): String?{
        return messageId
    }
    fun setMessageId(messageKey: String) {
        this.messageId = messageKey
    }

    fun getId(): String?{
        return Id
    }
    fun setId(messageKey: String?) {
        this.Id = messageKey
    }

}