package io.jpelczar.core.event


open class Event {

    lateinit var type: String
    lateinit var data: EventData

    constructor()

    constructor(type: String, data: EventData) {
        this.type = type
        this.data = data
    }

}