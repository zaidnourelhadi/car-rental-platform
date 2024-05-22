package com.business.controllers;

import com.business.dao.entities.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.business.services.CarServices;

@Controller
public class CarController
{
	@Autowired
	private CarServices carServices;

	//	AddCar
	@PostMapping("/addingCar")
	public String addCar(@ModelAttribute Car car)
	{

		this.carServices.addCar(car);
		return "redirect:/admin/services";
	}

	//	UpdateCar
	@GetMapping("/updatingCar/{carId}")
	public String updateCar(@ModelAttribute Car car, @PathVariable("carId") int id)
	{

		this.carServices.updatecar(car, id);
		return "redirect:/admin/services";
	}
	//DeleteCar
	@GetMapping("/deleteCar/{carId}")
	public String delete(@PathVariable("carId") int id)
	{
		this.carServices.deleteCar(id);
		return "redirect:/admin/services";
	}
	
}
