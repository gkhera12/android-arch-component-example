package com.android.eightleaves.workout.db.entity;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.android.eightleaves.workout.model.Workout;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "workouts")
public class WorkoutEntity implements Workout {
    @PrimaryKey
    private int id;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Total Time")
    @Expose
    private String totalTime;
    @SerializedName("Warm-Up")
    @Expose
    private String warmup;
    @SerializedName("Terrain")
    @Expose
    private String terrain;
    @SerializedName("Training Zone")
    @Expose
    private String trainingZone;
    @SerializedName("Level")
    @Expose
    private String trainingLevel;
    @SerializedName("Workout Time")
    @Expose
    private String workoutTime;
    @SerializedName("Cool Down")
    @Expose
    private String coolDownTime;
    @SerializedName("Type")
    @Expose
    private String workoutType;
    @SerializedName("Notes")
    @Expose
    private String intervals;

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    @Override
    public String getWarmup() {
        return warmup;
    }

    public void setWarmup(String warmup) {
        this.warmup = warmup;
    }

    @Override
    public String getTerrain() {
        return terrain;
    }

    public void setTerrain(String terrain) {
        this.terrain = terrain;
    }

    @Override
    public String getTrainingZone() {
        return trainingZone;
    }

    public void setTrainingZone(String trainingZone) {
        this.trainingZone = trainingZone;
    }

    @Override
    public String getTrainingLevel() {
        return trainingLevel;
    }

    public void setTrainingLevel(String trainingLevel) {
        this.trainingLevel = trainingLevel;
    }

    @Override
    public String getWorkoutTime() {
        return workoutTime;
    }

    public void setWorkoutTime(String workoutTime) {
        this.workoutTime = workoutTime;
    }

    @Override
    public String getCoolDownTime() {
        return coolDownTime;
    }

    public void setCoolDownTime(String coolDownTime) {
        this.coolDownTime = coolDownTime;
    }

    @Override
    public String getWorkoutType() {
        return workoutType;
    }

    public void setWorkoutType(String workoutType) {
        this.workoutType = workoutType;
    }

    @Override
    public String getIntervals() {
        return intervals;
    }

    public void setIntervals(String intervals) {
        this.intervals = intervals;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public WorkoutEntity() {
    }

    public WorkoutEntity(Workout workout) {
        this.id = workout.getId();
        this.name = workout.getName();
        this.totalTime = workout.getTotalTime();
        this.warmup = workout.getWarmup();
        this.terrain = workout.getTerrain();
        this.trainingZone = workout.getTrainingZone();
        this.trainingLevel = workout.getTrainingLevel();
        this.workoutTime = workout.getWorkoutTime();
        this.coolDownTime = workout.getCoolDownTime();
        this.workoutType = workout.getWorkoutType();
        this.intervals = workout.getIntervals();
    }
}
