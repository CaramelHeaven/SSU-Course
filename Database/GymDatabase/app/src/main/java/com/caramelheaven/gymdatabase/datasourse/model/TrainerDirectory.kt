package com.caramelheaven.gymdatabase.datasourse.model

class TrainerDirectory {
    var first_name: String? = null
    var id_trainer: String? = null
    var last_name: String? = null
    var salary: String? = null

    constructor() {}

    constructor(first_name: String, id_trainer: String, last_name: String, salary: String) {
        this.first_name = first_name
        this.id_trainer = id_trainer
        this.last_name = last_name
        this.salary = salary
    }
}