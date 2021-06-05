package ntiteam.test.planetOverlord.models;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name="overlords")
public class Overlord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long overlordID;

    @Column(nullable = false)
    private String overlordName;

    @Column(nullable = false)
    private int overlordAge;

    @OneToMany(mappedBy = "overlord")
    private List<Planet> planets;

    public Overlord() {
    }

    public Overlord(String overlordName, int overlordAge) {
        this.overlordName = overlordName;
        this.overlordAge = overlordAge;
    }

    public long getOverlordID() {
        return overlordID;
    }

    public void setOverlordID(long overlordID) {
        this.overlordID = overlordID;
    }

    public String getOverlordName() {
        return overlordName;
    }

    public void setOverlordName(String overlordName) {
        this.overlordName = overlordName;
    }

    public int getOverlordAge() {
        return overlordAge;
    }

    public void setOverlordAge(int overlordAge) {
        this.overlordAge = overlordAge;
    }

    public void setPlanetForOverlord(Planet planet) {
        planets.add(planet);
    }

    public void removePlanetForOverlord(Planet planet) {
        planets.remove(planet);
    }

    @Override
    public String toString() {
        return "Overlord{" +
                "overlordID=" + overlordID +
                ", overlordName='" + overlordName + '\'' +
                ", overlordAge=" + overlordAge +
                ", planets=" + planets +
                '}';
    }
}
