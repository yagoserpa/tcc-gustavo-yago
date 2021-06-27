package br.yagoserpa.geprof.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RegisterToken {

    private Integer id;
    private Long userId;
    private String token;

    public RegisterToken() {
    }

    public RegisterToken(Long userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    public RegisterToken(ResultSet resultSet, int row) throws SQLException {
        id = resultSet.getInt("register_token_id");
        userId = resultSet.getLong("user_id");
        token = resultSet.getString("token");
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
