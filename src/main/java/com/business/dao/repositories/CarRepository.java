package com.business.dao.repositories;

import com.business.dao.entities.Car;
import org.springframework.data.repository.CrudRepository;

public interface CarRepository extends CrudRepository<Car,Integer>
{
	public Car findByPname(String name);

}
