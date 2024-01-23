package application;

import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class Program2 {

	public static void main(String[] args) {
		DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
		Scanner sc = new Scanner(System.in);
		
		System.out.println("=== test 1: department findById ===");
		Department department = departmentDao.findById(2);
		System.out.println(department);
		
		System.out.println("=== test 2: department update ===");
		department = departmentDao.findById(1);
		department.setName("Food");
		departmentDao.update(department);
		System.out.println("Update Completed!");
		
		System.out.println("=== test 3: department findAll ===");
		List<Department> list = departmentDao.findAll();
		for(Department dep : list) {
			System.out.println(dep);
		}
		
		System.out.println("=== test 4: department insert ===");
		Department newDepartment = new Department(null, "Delivery");
		departmentDao.insert(newDepartment);
		System.out.println("Inserted! New ID = " + newDepartment.getId());
		
		System.out.println("=== test 5: department delete ===");
		int id = sc.nextInt();
		departmentDao.deleteById(id);
		System.out.println("Delete Completed!");
		
		sc.close();
	}

}
