package com.caramelheaven.gymdatabase.datasourse.model

class Place {

    private var id_place: String? = null
    private var name_place: String? = null

    constructor() {

    }

    constructor(id_place: String, name_place: String) {
        this.id_place = id_place
        this.name_place = name_place
    }
}
