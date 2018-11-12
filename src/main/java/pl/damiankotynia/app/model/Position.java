package pl.damiankotynia.app.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
public class Position {
    @Id
    @GeneratedValue
    private long id;
    private double latitude;
    private double longitude;
    @ManyToOne(cascade = CascadeType.ALL)
    private User user;
    private Timestamp timestamp;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Position{" +
                "id=" + id +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", user=" + user +
                ", timestamp=" + timestamp +
                '}';
    }
}
