package com.business.controllers;

import java.util.List;

import com.business.dao.entities.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.business.loginCredentials.AdminLogin;
import com.business.services.CarServices;

@Controller
public class HomeController 
{
	@Autowired
	private CarServices carServices;
	@GetMapping("/home")
	public String home()
	{
		return "Home";
	}

	@GetMapping("/cars")
	public String cars( Model model)
	{ 
		List<Car> allCars = this.carServices.getAllCars();
		model.addAttribute("cars", allCars);
		return "Cars";
	}

	@GetMapping("/location")
	public String location()
	{
		return "Locate_us";
	}

	@GetMapping("/about")
	public String about()
	{
		return "About";
	}

	@GetMapping("/login")
	public String login(Model model)
	{
		model.addAttribute("adminLogin",new AdminLogin());
		return "Login";
	}




}
