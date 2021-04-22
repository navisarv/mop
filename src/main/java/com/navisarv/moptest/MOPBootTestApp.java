package com.navisarv.moptest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.navisarv.model.Employee;
import com.navisarv.mop.appliance.annotations.InstrumentMOP;

@SpringBootApplication
@RestController
public class MOPBootTestApp {
	public static void main(String[] args) {
		SpringApplication.run(MOPBootTestApp.class, args);
	}

	@GetMapping(value="/test")
	public Employee postEmployee() {
		Employee employee = new Employee();
		Name name = new Name();
		name.setfName("fname");
		name.setlName("lName");
		employee.setName(name );
		Address address = new Address();
		ZipCode zip = new ZipCode();
		zip.setZip5("zip5");
		address.setZip(zip );
		employee.setAddress(address );
		return mapEmployee(employee);
	}

	@InstrumentMOP
	private Employee mapEmployee(Employee employee) {
		Employee rEmp = null;
		rEmp.getName().setfName(employee.getName().getfName());
		rEmp.getAddress().getZip().setZip5(employee.getAddress().getZip().getZip5());
		return rEmp;
	}
}
