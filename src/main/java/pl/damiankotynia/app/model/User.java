package pl.damiankotynia.app.model;

import javax.persistence.*;
import java.util.List;
import java.util.Map;
@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String passwordHash;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Position> positions;
    @OneToMany(cascade = CascadeType.ALL)
    private List<User> friends;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public List<Position> getPositions() {
        return positions;
    }

    public void setPositions(List<Position> positions) {
        this.positions = positions;
    }

    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }
}
