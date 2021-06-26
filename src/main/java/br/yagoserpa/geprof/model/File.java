package br.yagoserpa.geprof.model;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class File {

    private Integer id;
    private Integer projectId;
    private String name;
    private Integer version;
    private String file;
    private Timestamp registerDate;
    private String content;
    private Blob comments;

    public File() {
    }

    public File(ResultSet resultSet, int row) throws SQLException {
        id = resultSet.getInt("file_id");
        projectId = resultSet.getInt("project_id");
        name = resultSet.getString("name");
        version = resultSet.getInt("version");
        file = resultSet.getString("file");
        registerDate = resultSet.getTimestamp("register_date");
        content = resultSet.getString("content");
        comments = resultSet.getBlob("comments");
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Timestamp getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Timestamp registerDate) {
        this.registerDate = registerDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Blob getComments() {
        return comments;
    }

    public void setComments(Blob comments) {
        this.comments = comments;
    }
}
