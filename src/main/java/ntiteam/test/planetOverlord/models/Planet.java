package ntiteam.test.planetOverlord.models;

import javax.persistence.*;

@Entity
@Table(name="planets")
public class Planet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long planetID;

    @Column(nullable = false)
    private String planetName;

    @ManyToOne
    @JoinColumn(name = "overlord_id", referencedColumnName = "overlordID")
    private Overlord overlord;

    public Planet() {
    }

    public Planet(String planetName) {
        this.planetName = planetName;
    }

    public Planet(String planetName, Overlord overlord) {
        this.planetName = planetName;
        this.overlord = overlord;
    }

    public long getPlanetID() {
        return planetID;
    }

    public void setPlanetID(long planetID) {
        this.planetID = planetID;
    }

    public String getPlanetName() {
        return planetName;
    }

    public void setPlanetName(String planetName) {
        this.planetName = planetName;
    }

    public Overlord getOverlord() {
        return overlord;
    }

    public void setOverlord(Overlord overlord) {
        this.overlord = overlord;
    }

    @Override
    public String toString() {
        return "Planet{" +
                "planetID=" + planetID +
                ", planetName='" + planetName + '\'' +
                ", overlord=" + overlord +
                '}';
    }
}
