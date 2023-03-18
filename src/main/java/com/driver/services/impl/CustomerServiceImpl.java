package com.driver.services.impl;

import com.driver.model.*;
import com.driver.repository.CabRepository;
import com.driver.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.driver.repository.CustomerRepository;
import com.driver.repository.DriverRepository;
import com.driver.repository.TripBookingRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository customerRepository2;

	@Autowired
	DriverRepository driverRepository2;

	@Autowired
	TripBookingRepository tripBookingRepository2;

	@Autowired
	CabRepository cabRepository;

	@Override
	public void register(Customer customer) {
		//Save the customer in database
		customerRepository2.save(customer);
	}

	@Override
	public void deleteCustomer(Integer customerId) {
		// Delete customer without using deleteById function
		customerRepository2.deleteById(customerId);
	}

	@Override
	public TripBooking bookTrip(int customerId, String fromLocation, String toLocation, int distanceInKm) throws Exception{
		//Book the driver with lowest driverId who is free (cab available variable is Boolean.TRUE). If no driver is available, throw "No cab available!" exception
		//Avoid using SQL query

		TripBooking bookedTrip;

		List<Driver> drivers = new ArrayList<>();

		drivers = driverRepository2.findAll();

		for(Driver driver:drivers){
			if(cabRepository.findByDriverId(driver.getDriverId()).isAvailable()){
				bookedTrip = new TripBooking();
				bookedTrip.setFromLocation(fromLocation);
				bookedTrip.setToLocation(toLocation);
				bookedTrip.setDistanceInKm(distanceInKm);
				bookedTrip.setTripStatus(TripStatus.CONFIRMED);

				bookedTrip.setDriver(driver);
				bookedTrip.setCustomer(customerRepository2.findById(customerId).get());
				driver.getTrips().add(bookedTrip);

				driverRepository2.save(driver);
				return bookedTrip;
			}
		}
		throw new Exception("Not found");
	}

	@Override
	public void cancelTrip(Integer tripId){
		//Cancel the trip having given trip Id and update TripBooking attributes accordingly
		TripBooking bookedTrip = tripBookingRepository2.findById(tripId).get();
		bookedTrip.setTripStatus(TripStatus.CANCELED);
		cabRepository.findByDriverId(bookedTrip.getDriver().getDriverId()).setAvailable(true);
		driverRepository2.save(bookedTrip.getDriver());
	}

	@Override
	public void completeTrip(Integer tripId){
		//Complete the trip having given trip Id and update TripBooking attributes accordingly
		TripBooking bookedTrip = tripBookingRepository2.findById(tripId).get();
		bookedTrip.setTripStatus(TripStatus.COMPLETED);
		int rate = cabRepository.findByDriverId(bookedTrip.getDriver().getDriverId()).getPerKmRate();
		bookedTrip.setBill(rate*bookedTrip.getDistanceInKm());
		cabRepository.findByDriverId(bookedTrip.getDriver().getDriverId()).setAvailable(true);
		driverRepository2.save(bookedTrip.getDriver());
	}
}
