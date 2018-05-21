package com.caramelheaven.gymdatabase.datasourse.model

class ClientTrace {
    var client_date_end: String? = null
    var client_date_start: String? = null
    var client_id: String? = null
    var id_group_client: String? = null

    constructor() {

    }

    constructor(client_date_end: String, client_date_start: String, client_id: String, id_group_client: String) {
        this.client_date_end = client_date_end
        this.client_date_start = client_date_start
        this.client_id = client_id
        this.id_group_client = id_group_client
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o == null || javaClass != o.javaClass) {
            return false
        }

        val client = o as ClientTrace?

        if (if (client_id != null) client_id != client!!.client_id else client!!.client_id != null) {
            return false
        }
        if (if (id_group_client != null) id_group_client != client.id_group_client else client.id_group_client != null) {
            return false
        }
        return if (if (id_group_client != null) id_group_client != client.id_group_client else client.id_group_client != null) {
            false
        } else false
    }
}