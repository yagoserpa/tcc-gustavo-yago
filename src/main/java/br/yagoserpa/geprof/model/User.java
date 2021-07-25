package br.yagoserpa.geprof.model;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class User {

    private Long id;
    private String name;
    private String email;
    private String password;
    private String currentPassword;
    private Type userType;
    private String dre;
    private String siape;
    private Timestamp registerDate;
    private String gender;
    private Status status;
    private String title;
    private String position;
    private String room;
    private String lattes;
    private String userProfile;
    private String course;
    private String origin;

    private Boolean committee;
    private Boolean coop;

    private Blob signature;

    public User() {
    }

    public User(ResultSet resultSet, int row) throws SQLException {
        id = resultSet.getLong("user_id");
        name = resultSet.getString("name");
        email = resultSet.getString("email");
        userType = Type.fromValue(resultSet.getInt("user_type"));
        dre = resultSet.getString("dre");
        siape = resultSet.getString("siape");
        registerDate = resultSet.getTimestamp("register_date");
        gender = resultSet.getString("gender");
        status = Status.fromValue(resultSet.getInt("status"));
        title = resultSet.getString("title");
        position = resultSet.getString("position");
        room = resultSet.getString("room");
        lattes = resultSet.getString("lattes");
        userProfile = resultSet.getString("user_profile");
        course = resultSet.getString("course");
        origin = resultSet.getString("origin");

        try {
            committee = resultSet.getBoolean("committee");
            coop = resultSet.getBoolean("coop");
        } catch (Exception ignored) {
        }

        try {
            signature = resultSet.getBlob("signature");
        } catch (Exception ignored) {
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public Type getUserType() {
        return userType;
    }

    public void setUserType(Type userType) {
        this.userType = userType;
    }

    public String getDre() {
        return dre;
    }

    public void setDre(String dre) {
        this.dre = dre;
    }

    public String getSiape() {
        return siape;
    }

    public void setSiape(String siape) {
        this.siape = siape;
    }

    public Timestamp getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Timestamp registerDate) {
        this.registerDate = registerDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getLattes() {
        return lattes;
    }

    public void setLattes(String lattes) {
        this.lattes = lattes;
    }

    public String getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(String userProfile) {
        this.userProfile = userProfile;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public Boolean getCommittee() {
        return committee == null ? false : committee;
    }

    public void setCommittee(Boolean committee) {
        this.committee = committee;
    }

    public Boolean getCoop() {
        return coop == null? false : coop;
    }

    public void setCoop(Boolean coop) {
        this.coop = coop;
    }

    public Blob getSignature() {
        return signature;
    }

    public void setSignature(Blob signature) {
        this.signature = signature;
    }

    public enum Status {
        INACTIVE,
        ACTIVE,
        DELETED;

        public static Status fromValue(int ordinal) {
            for (Status b : Status.values()) {
                if (b.ordinal() == ordinal) {
                    return b;
                }
            }

            return null;
        }
    }

    public enum Type {
        EXTERNAL_TEACHER,
        TEACHER,
        STUDENT;

        public static Type fromValue(int ordinal) {
            for (Type b : Type.values()) {
                if (b.ordinal() == ordinal) {
                    return b;
                }
            }

            return null;
        }
    }
}
