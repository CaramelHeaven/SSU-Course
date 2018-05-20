package com.caramelheaven.gymdatabase.datasourse.model

class KindOfSport {

    private var id_kind: String? = null
    private var name_kind: String? = null

    constructor() {

    }

    constructor(id_kind: String, name_kind: String) {
        this.id_kind = id_kind
        this.name_kind = name_kind
    }

}