package com.caramelheaven.gymdatabase.datasourse.model

class GroupWork {
    private var get_people: String? = null
    private var id_group: String? = null
    private var id_group_work: String? = null
    private var name_group: String? = null

    constructor() {}

    constructor(get_people: String, id_group: String, id_group_work: String, name_group: String) {
        this.get_people = get_people
        this.id_group = id_group
        this.id_group_work = id_group_work
        this.name_group = name_group
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other == null || javaClass != other.javaClass) {
            return false
        }
        val groupWork = other as GroupWork?

        if (if (get_people != null) get_people != groupWork!!.get_people else groupWork!!.get_people != null) {
            return false;
        }
        if (if (id_group != null) id_group != groupWork.id_group else groupWork.id_group != null) {
            return false;
        }
        if (if (id_group_work != null) id_group_work != groupWork.id_group_work else groupWork.id_group_work != null) {
            return false;
        }
        return if (if (name_group != null) name_group != groupWork.name_group else groupWork.name_group != null) {
            false
        } else true
    }
}