package br.yagoserpa.geprof.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProjectHasUser {

    private Integer id;
    private Integer userId;
    private Boolean committee;
    private Boolean coop;

    public ProjectHasUser() {
    }

    public ProjectHasUser(ResultSet resultSet, int row) throws SQLException {
        id = resultSet.getInt("project_id");
        userId = resultSet.getInt("user_id");
        committee = resultSet.getBoolean("committee");
        coop = resultSet.getBoolean("coop");
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Boolean getCommittee() {
        return committee;
    }

    public void setCommittee(Boolean committee) {
        this.committee = committee;
    }

    public Boolean getCoop() {
        return coop;
    }

    public void setCoop(Boolean coop) {
        this.coop = coop;
    }
}
