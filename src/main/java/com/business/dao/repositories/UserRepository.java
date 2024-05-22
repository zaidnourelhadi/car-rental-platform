package com.business.dao.repositories;

import org.springframework.data.repository.CrudRepository;

import com.business.dao.entities.User;

public interface UserRepository extends CrudRepository<User,Integer>
{
public User findUserByUemail(String email);
}
