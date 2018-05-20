package com.caramelheaven.gymdatabase.datasourse.model

class TrainerDirectory {
    private var first_name: String? = null
    private var id_trainer: String? = null
    private var last_name: String? = null

    constructor() {}

    constructor(first_name: String, id_trainer: String, last_name: String) {
        this.first_name = first_name
        this.id_trainer = id_trainer
        this.last_name = last_name
    }
}