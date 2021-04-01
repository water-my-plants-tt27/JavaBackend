package com.watermyplants.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@ApiModel(value = "Plants", description = "Plants for the user")
@Entity
@Table(name = "plants")
public class Plant extends Auditable
{
    @ApiModelProperty(name = "plantsid",
        value = "Primary Key for the Plant",
        required = true,
        example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long plantsid;

    @ApiModelProperty(name = "species",
        value = "Species of the Plant",
        required = true,
        example = "sunflower")
    @Column(nullable = false)
    private String species;

    @ApiModelProperty(name = "name",
        value = "The name you as the User give the plant",
        required = true,
        example = "sunny")
    @Column(nullable = false)
    private String name;

    @ApiModelProperty(name = "location",
        value = "Location the plant is at",
        required = true,
        example = "Living Room")
    @Column(nullable = false)
    private String location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid",
        nullable = false)
    @JsonIgnoreProperties({"plants", "hibernateLazyInitializer" })
    private User user;

    @ApiModelProperty(name = "schedule",
        value = "Schedule when to water your plant (days per week)",
        required = true,
        example = "3")
    @Column(nullable = false)
    private int schedule;

    public Plant() {
    }

    public Plant(String name)
    {
        this.name = name;
    }

    public Plant(String species, String name, String location, User user, int schedule) {
        this.species = species;
        this.name = name;
        this.location = location;
        this.user = user;
        this.schedule = schedule;
    }

    public int getSchedule() {
        return schedule;
    }

    public void setSchedule(int schedule) {
        this.schedule = schedule;
    }

    public Plant(long plantsid, User currentUser) {
        super();
    }

    public long getPlantid() {
        return plantsid;
    }

    public void setPlantid(long plantid) {
        this.plantsid = plantid;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}