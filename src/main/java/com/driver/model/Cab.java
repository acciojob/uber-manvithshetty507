package com.driver.model;

import javax.persistence.*;

@Entity
@Table(name ="cab")
public class Cab {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int perKmRate;
    private boolean available;

    public Cab() {
    }

    public Cab(int id, int perKmRate, boolean available) {
        this.id = id;
        this.perKmRate = perKmRate;
        this.available = available;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPerKmRate() {
        return perKmRate;
    }

    public void setPerKmRate(int perKmRate) {
        this.perKmRate = perKmRate;
    }

    public boolean getAvailable() {

        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @OneToOne
    @JoinColumn
    Driver dvr;

    public void setDriver(Driver dvr) {
        this.dvr = dvr;
    }

    public Driver getDriver(){
        return dvr;
    }
}
