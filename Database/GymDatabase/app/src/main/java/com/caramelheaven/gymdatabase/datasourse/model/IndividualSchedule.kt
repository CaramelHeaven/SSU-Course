package com.caramelheaven.gymdatabase.datasourse.model

class IndividualSchedule {

    private var client_id: String? = null
    private var id_individual_work: String? = null
    private var time_end: String? = null
    private var time_start: String? = null
    private var trainer_id: String? = null

    constructor() {}

    constructor(client_id: String, id_individual_work: String, time_end: String, time_start: String,
                trainer_id: String) {
        this.client_id = client_id
        this.id_individual_work = id_individual_work
        this.time_end = time_end
        this.time_start = time_start
        this.trainer_id = trainer_id
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other == null || javaClass != other.javaClass) {
            return false
        }
        val individualSchedule = other as IndividualSchedule?

        if (if (client_id != null) client_id != individualSchedule!!.client_id else individualSchedule!!.client_id != null) {
            return false;
        }
        if (if (id_individual_work != null) id_individual_work != individualSchedule.id_individual_work else individualSchedule.id_individual_work != null) {
            return false;
        }
        return if (if (trainer_id != null) trainer_id != individualSchedule.trainer_id else individualSchedule.trainer_id != null) {
            return false;
        } else true
    }
}