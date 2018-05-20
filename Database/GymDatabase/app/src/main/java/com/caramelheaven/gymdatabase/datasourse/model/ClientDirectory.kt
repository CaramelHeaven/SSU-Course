package com.caramelheaven.gymdatabase.datasourse.model

class ClientDirectory {

    var first_name: String? = null
    var gym_membership_id: String? = null
    var id_client: String? = null
    var last_name: String? = null

    constructor(first_name: String, gym_membership_id: String, id_client: String, last_name: String) {
        this.first_name = first_name
        this.gym_membership_id = gym_membership_id
        this.id_client = id_client
        this.last_name = last_name
    }

    constructor(first_name: String, last_name: String) {
        this.first_name = first_name
        this.last_name = last_name
    }

    constructor() {}

    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o == null || javaClass != o.javaClass) {
            return false
        }

        val client = o as ClientDirectory?

        if (if (id_client != null) id_client != client!!.id_client else client!!.id_client != null) {
            return false
        }
        if (if (last_name != null) last_name != client.last_name else client.last_name != null) {
            return false
        }
        return if (if (gym_membership_id != null) gym_membership_id != client.gym_membership_id else client.gym_membership_id != null) {
            false
        } else false
    }
}
