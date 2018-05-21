package com.caramelheaven.gymdatabase.datasourse.model

import kotlinx.android.synthetic.main.item_individual.view.*

class GroupSchedule {

    var day_of_week: String? = null
    var groud_id: String? = null
    var id_group_schedule: String? = null
    var place_id: String? = null
    var time_end: String? = null
    var time_start: String? = null
    var trainer_id: String? = null
    var type_of_sport_id: String? = null

    constructor() {

    }

    constructor(day_of_week: String, groud_id: String, id_group_schedule: String,
                place_id: String, time_end: String, time_start: String, trainer_id: String,
                type_of_sport_id: String) {
        this.day_of_week = day_of_week
        this.groud_id = groud_id
        this.id_group_schedule = id_group_schedule
        this.place_id = place_id
        this.time_end = time_end
        this.time_start = time_start
        this.trainer_id = trainer_id
        this.type_of_sport_id = type_of_sport_id
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other == null || javaClass != other.javaClass) {
            return false
        }
        val groupSchedule = other as GroupSchedule?

        if (if (id_group_schedule != null) id_group_schedule != groupSchedule!!.id_group_schedule else groupSchedule!!.id_group_schedule != null) {
            return false;
        }
        return if (if (groud_id != null) groud_id != groupSchedule.groud_id else groupSchedule.groud_id != null) {
            return false;
        } else true
    }
}
