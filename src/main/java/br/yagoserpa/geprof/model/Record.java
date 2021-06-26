package br.yagoserpa.geprof.model;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

public class Record {

    private Integer id;
    private Integer projectId;
    private Integer thesisId;
    private Date thesisDate;
    private Time beginTime;
    private Time endTime;
    private String location;
    private Evaluation evaluation;
    private Integer deadline;
    private Float grade;

    public Record() {
    }

    public Record(ResultSet resultSet, int row) throws SQLException {
        id = resultSet.getInt("record_id");
        projectId = resultSet.getInt("project_id");
        thesisId = resultSet.getInt("thesis_id");
        thesisDate = resultSet.getDate("thesis_date");
        beginTime = resultSet.getTime("begin_time");
        endTime = resultSet.getTime("end_time");
        location = resultSet.getString("location");
        evaluation = Evaluation.fromValue(resultSet.getInt("evaluation"));
        deadline = resultSet.getInt("deadline");
        grade = resultSet.getFloat("grade");
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getThesisId() {
        return thesisId;
    }

    public void setThesisId(Integer thesisId) {
        this.thesisId = thesisId;
    }

    public Date getThesisDate() {
        return thesisDate;
    }

    public void setThesisDate(Date thesisDate) {
        this.thesisDate = thesisDate;
    }

    public Time getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Time beginTime) {
        this.beginTime = beginTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Evaluation getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(Evaluation evaluation) {
        this.evaluation = evaluation;
    }

    public Integer getDeadline() {
        return deadline;
    }

    public void setDeadline(Integer deadline) {
        this.deadline = deadline;
    }

    public Float getGrade() {
        return grade;
    }

    public void setGrade(Float grade) {
        this.grade = grade;
    }

    public enum Evaluation {
        PASSED,
        PASSED_WITH_MODIFICATIONS,
        FAILED;

        public static Evaluation fromValue(int ordinal) {
            for (Evaluation b : Evaluation.values()) {
                if (b.ordinal() == ordinal) {
                    return b;
                }
            }

            return null;
        }
    }
}
