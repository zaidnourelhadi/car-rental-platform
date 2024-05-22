package com.business.dao.repositories;

import org.springframework.data.repository.CrudRepository;

import com.business.dao.entities.Admin;

public interface AdminRepository extends CrudRepository<Admin, Integer>
{
	public Admin findByAdminEmail(String email);
}
