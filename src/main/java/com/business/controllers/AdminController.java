package com.business.controllers;

import java.util.Date;
import java.util.List;

import com.business.dao.entities.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.business.basiclogics.Logic;
import com.business.dao.entities.Admin;
import com.business.dao.entities.Orders;
import com.business.dao.entities.User;
import com.business.loginCredentials.AdminLogin;
import com.business.loginCredentials.UserLogin;
import com.business.services.AdminServices;
import com.business.services.OrderServices;
import com.business.services.CarServices;
import com.business.services.UserServices;

@Controller
public class AdminController {
	@Autowired
	private UserServices services;
	@Autowired
	private AdminServices adminServices;
	@Autowired
	private CarServices carServices;
	@Autowired
	private OrderServices orderServices;

	private String email;
	private User user;

	private List<Orders> userOrders;

	// Validating login 
	@GetMapping("/adminLogin")
	public String  getAllData(  @ModelAttribute("adminLogin") AdminLogin login, Model model)
	{
		String email=login.getEmail();
		String password=login.getPassword();
		if(adminServices.validateAdminCredentials(email, password))
		{
			return "redirect:/admin/services";
		}
		else {
			model.addAttribute("error", "Invalid email or password");
			return "Login";
		}

	}

	@GetMapping("/userlogin")
	public String userLogin( @ModelAttribute("userLogin") UserLogin login,Model model)
	{

		email=login.getUserEmail();
		String password=login.getUserPassword();
		if(services.validateLoginCredentials(email, password))
		{
			user = this.services.getUserByEmail(email);
//			List<Orders> orders = this.orderServices.getOrdersForUser(user);
			userOrders = this.orderServices.getOrdersForUser(user);
//			model.addAttribute("orders", orders);
			model.addAttribute("orders", userOrders);
			model.addAttribute("name", user.getUname());
			List<Car> allCars = this.carServices.getAllCars();
			model.addAttribute("cars", allCars);

//			return "BuyCar";
			return "CarsUser";
		}
		else
		{
			model.addAttribute("error2", "Invalid email or password");
			return "Login";
		}

	}
	//Searching Car By Name
	@PostMapping("/car/search")
	public String seachHandler(@RequestParam("carName") String name,Model model)
	{

		Car car =this.carServices.getCarByName(name);
		if(car ==null)
		{
			model.addAttribute("message", "SORRY...!  Car Unavailable");
			model.addAttribute("car", car);
			List<Orders> orders = this.orderServices.getOrdersForUser(user);
			model.addAttribute("orders", orders);
			return "BuyCar";
		}
		List<Orders> orders = this.orderServices.getOrdersForUser(user);
		model.addAttribute("orders", orders);
		model.addAttribute("car", car);
		return "BuyCar";

	}

	//Providing services 
	@GetMapping("/admin/services")
	public String returnBack(Model model)
	{
		List<User> users= this.services.getAllUser();
		List<Admin>admins=this.adminServices.getAll(); 
		List<Car> cars =this.carServices.getAllCars();
		List<Orders> orders = this.orderServices.getOrders();
		model.addAttribute("users",users);
		model.addAttribute("admins", admins);
		model.addAttribute("cars", cars);
		model.addAttribute("orders", orders);

		return "Admin_Page";
	}

	//Invoking addAdmin Page
	@GetMapping("/addAdmin")
	public String addAdminPage()
	{
		return "Add_Admin";
	}

	//Handling AddAdmin
	@PostMapping("addingAdmin")
	public String addAdmin( @ModelAttribute Admin admin)
	{

		this.adminServices.addAdmin(admin);
		return "redirect:/admin/services";

	}

	//invoking updateAdmin Page
	@GetMapping("/updateAdmin/{adminId}")
	public String update(@PathVariable("adminId") int id,Model model)
	{
		Admin admin = this.adminServices.getAdmin(id);
		model.addAttribute("admin", admin);
		return "Update_Admin";
	}

	//Handling Update Page
	@GetMapping("/updatingAdmin/{id}")
	public String updateAdmin(@ModelAttribute Admin admin,@PathVariable("id") int id)
	{
		this.adminServices.update(admin, id);
		return "redirect:/admin/services";
	}

	//IHandling delete operation
	@GetMapping("/deleteAdmin/{id}")
	public String deleteAdmin(@PathVariable("id") int id)
	{
		this.adminServices.delete(id);
		return "redirect:/admin/services";
	}

	//Invoking AddCar Page
	@GetMapping("/addCar")
	public String addCar()
	{
		return "Add_Car";
	}

	//Invoking Update Car Page
	@GetMapping("/updateCar/{carId}")
	public String updateCar(@PathVariable("carId") int id,Model model)
	{
		Car car =this.carServices.getCar(id);
		System.out.println(car);
		model.addAttribute("car", car);
		return "Update_Car";
	}

	//Invoking AddUser Page
	@GetMapping("/addUser")
	public String addUser()
	{
		return "Add_User";
	}

	//Invoking UpdateUser Page
	@GetMapping("/updateUser/{userId}")
	public String updateUserPage(@PathVariable("userId") int id,Model model)
	{
		User user = this.services.getUser(id);
		model.addAttribute("user", user);
		return "Update_User";
	}
	//Placing  Order
	@PostMapping("/car/order")
	public String orderHandler(@ModelAttribute() Orders order,Model model)
	{
		double  totalAmount = Logic.countTotal(order.getoPrice(),order.getoQuantity());
		order.setTotalAmmout(totalAmount);
		order.setUser(user);
		Date d=new Date();
		order.setOrderDate(d);
		this.orderServices.saveOrder(order);
		model.addAttribute("amount",totalAmount);
		return "Order_success";
	}

	@GetMapping("/car/back")
	public String back(Model model)
	{
		List<Orders> orders = this.orderServices.getOrdersForUser(user);
		model.addAttribute("orders", orders);
		return "BuyCar";
	}

	@GetMapping("/car/showOrders")
	public String showOrders(Model model)
	{
		List<Orders> orders = this.orderServices.getOrdersForUser(user);
		model.addAttribute("orders", orders);
		return "BuyCar";
	}

	@GetMapping("/carsuser")
	public String carsUser( Model model)
	{
		List<Car> allCars = this.carServices.getAllCars();
		model.addAttribute("cars", allCars);
		model.addAttribute("user", user);
		return "CarsUser";
	}



}
