package com.caramelheaven.gymdatabase.datasourse.model;

public class GroupSchedule {

    private String day_of_week;
    private String groud_id;
    private String id_group_schedule;
    private String place_id;
    private String time_end;
    private String time_start;
    private String trainer_id;
    private String type_of_sport_id;

    public GroupSchedule() {

    }

    public GroupSchedule(String day_of_week, String groud_id, String id_group_schedule,
                         String place_id, String time_end, String time_start, String trainer_id,
                         String type_of_sport_id) {
        this.day_of_week = day_of_week;
        this.groud_id = groud_id;
        this.id_group_schedule = id_group_schedule;
        this.place_id = place_id;
        this.time_end = time_end;
        this.time_start = time_start;
        this.trainer_id = trainer_id;
        this.type_of_sport_id = type_of_sport_id;
    }

    public String getDay_of_week() {
        return day_of_week;
    }

    public String getGroud_id() {
        return groud_id;
    }

    public String getId_group_schedule() {
        return id_group_schedule;
    }

    public String getPlace_id() {
        return place_id;
    }

    public String getTime_end() {
        return time_end;
    }

    public String getTime_start() {
        return time_start;
    }

    public String getTrainer_id() {
        return trainer_id;
    }

    public String getType_of_sport_id() {
        return type_of_sport_id;
    }

    public void setDay_of_week(String day_of_week) {
        this.day_of_week = day_of_week;
    }

    public void setGroud_id(String groud_id) {
        this.groud_id = groud_id;
    }

    public void setId_group_schedule(String id_group_schedule) {
        this.id_group_schedule = id_group_schedule;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public void setTime_end(String time_end) {
        this.time_end = time_end;
    }

    public void setTime_start(String time_start) {
        this.time_start = time_start;
    }

    public void setTrainer_id(String trainer_id) {
        this.trainer_id = trainer_id;
    }

    public void setType_of_sport_id(String type_of_sport_id) {
        this.type_of_sport_id = type_of_sport_id;
    }
}
