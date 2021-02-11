package br.yagoserpa.geprof.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FieldOfInterestHasUser {

    private Integer id;
    private Integer userId;

    public FieldOfInterestHasUser() {
    }

    public FieldOfInterestHasUser(ResultSet resultSet, int row) throws SQLException {
        id = resultSet.getInt("field_id");
        userId = resultSet.getInt("user_id");
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
}
