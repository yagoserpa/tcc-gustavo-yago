package br.yagoserpa.geprof.model;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RecordHasUser {

    private Integer id;
    private Integer projectId;
    private Integer userId;
    private Blob signature;

    public RecordHasUser() {
    }

    public RecordHasUser(ResultSet resultSet, int row) throws SQLException {
        id = resultSet.getInt("record_id");
        projectId = resultSet.getInt("project_id");
        userId = resultSet.getInt("user_id");
        signature = resultSet.getBlob("signature");
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Blob getSignature() {
        return signature;
    }

    public void setSignature(Blob signature) {
        this.signature = signature;
    }
}
