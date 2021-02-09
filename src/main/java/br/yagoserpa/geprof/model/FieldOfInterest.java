package br.yagoserpa.geprof.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FieldOfInterest {

    private Integer id;
    private String name;
    private String description;

    public FieldOfInterest() {
    }

    public FieldOfInterest(ResultSet resultSet, int row) throws SQLException {
        id = resultSet.getInt("field_id");
        name = resultSet.getString("name");
        description = resultSet.getString("description");
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
