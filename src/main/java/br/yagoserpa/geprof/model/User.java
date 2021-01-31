package br.yagoserpa.geprof.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User {

    private String id;
    private String name;

    public User() {
    }

    public User(ResultSet resultSet, int row) throws SQLException {
        id = resultSet.getString("user_id");
        name = resultSet.getString("name");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
