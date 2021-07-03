package br.yagoserpa.geprof.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Project {

    private Integer id;
    private String subject;
    private String title;
    private String description;
    private Status status;
    private Timestamp registerDate;
    private String keywords;

    public Project() {
    }

    public Project(ResultSet resultSet, int row) throws SQLException {
        id = resultSet.getInt("project_id");
        subject = resultSet.getString("subject");
        title = resultSet.getString("title");
        description = resultSet.getString("description");
        registerDate = resultSet.getTimestamp("register_date");
        status = Status.fromValue(resultSet.getInt("status"));
        keywords = resultSet.getString("keywords");
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Timestamp getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Timestamp registerDate) {
        this.registerDate = registerDate;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public enum Status {
        STARTED,
        IN_PROGRESS,
        ENDED,
        CANCELED;

        public static Status fromValue(int ordinal) {
            for (Status b : Status.values()) {
                if (b.ordinal() == ordinal) {
                    return b;
                }
            }

            return null;
        }
    }
}
