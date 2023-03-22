package com.driver.controllers;

import com.driver.model.Customer;
import com.driver.model.TripBooking;
import com.driver.services.CustomerService;
import org.hibernate.loader.collection.OneToManyJoinWalker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	CustomerService customerService;
	@PostMapping("/register")
	public ResponseEntity<String> registerCustomer(@RequestBody Customer customer){
		try {
			customerService.register(customer);
		}catch(Exception e){
			return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_IMPLEMENTED);
		}
		return new ResponseEntity<>("success",HttpStatus.OK);
	}


	@DeleteMapping("/delete")
	public void deleteCustomer(@RequestParam int customerId){
		customerService.deleteCustomer(customerId);
	}

	@PostMapping("/bookTrip")
	public ResponseEntity<Integer> bookTrip(@RequestParam Integer customerId, @RequestParam String fromLocation, @RequestParam String toLocation, @RequestParam Integer distanceInKm) throws Exception {
		TripBooking bookedTrip;
		try{
			bookedTrip = customerService.bookTrip(customerId,fromLocation,toLocation,distanceInKm);
		}catch(Exception e){
			return new ResponseEntity<>(null,HttpStatus.NOT_ACCEPTABLE);
		}
		return new ResponseEntity<>(bookedTrip.getTripBookingId(), HttpStatus.CREATED);
	}

	@DeleteMapping("/complete")
	public void completeTrip(@RequestParam Integer tripId){
	}

	@DeleteMapping("/cancelTrip")
	public void cancelTrip(@RequestParam Integer tripId){
	}
}
