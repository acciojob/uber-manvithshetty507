package com.driver.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int driverId;
    private String mobNo;
    private String password;

    @OneToOne(mappedBy = "dvr",cascade = CascadeType.ALL)
    Cab cab;

    @OneToMany(mappedBy = "driver",cascade = CascadeType.ALL)
    List<TripBooking> trips = new ArrayList<>();

    public Driver() {
    }

    public Driver(int driverId, String mobNo, String password, Cab cab, List<TripBooking> trips) {

        this.driverId = driverId;
        this.mobNo = mobNo;
        this.password = password;
        this.cab = cab;
        this.trips = trips;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public String getMobile() {
        return mobNo;
    }

    public void setMobile(String mobNo) {
        this.mobNo = mobNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Cab getCab() {
        return cab;
    }

    public void setCab(Cab cab) {
        this.cab = cab;
    }

    public List<TripBooking> getTripBookings() {
        return trips;
    }

    public void setTripBookings(List<TripBooking> trips) {
        this.trips = trips;
    }
}
