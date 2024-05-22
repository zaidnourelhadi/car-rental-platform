package com.business.services;

import java.util.List;
import java.util.Optional;

import com.business.dao.entities.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.business.dao.repositories.CarRepository;
@Component
public class CarServices
{
	@Autowired
	private CarRepository carRepository;

	//add Car
	public void addCar(Car p)
	{
		this.carRepository.save(p);
	}


	//getAll cars
	public List<Car> getAllCars()
	{
		List<Car> cars =(List<Car>)this.carRepository.findAll();
		return cars;
	}

	//get Single Car
	public Car getCar(int id)
	{
		Optional<Car> optional = this.carRepository.findById(id);
		Car car =optional.get();
		return car;
	}

	//update Car
	public void updatecar(Car p, int id)
	{
		p.setPid(id);
		Optional<Car> optional = this.carRepository.findById(id);
		Car prod=optional.get();

		if(prod.getPid()==id)
		{
			this.carRepository.save(p);
		}
	}
	//delete car
	public void deleteCar(int id)
	{
		this.carRepository.deleteById(id);
	}

	//Get Car By Name
	public Car getCarByName(String name)
	{
		
		Car car = this.carRepository.findByPname(name);
		if(car !=null)
		{
			return car;
		}
		return null;
	
	}
}