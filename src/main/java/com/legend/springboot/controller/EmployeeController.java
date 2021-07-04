package com.legend.springboot.controller;

import com.legend.springboot.model.Employee;
import com.legend.springboot.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Embeddable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author iot
 * @Description TODO
 * {@link}
 * @date 2021/6/26下午11:38
 */
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

  @Autowired
  private EmployeeRepository employeeRepository;

  // get all employees
  @GetMapping("/employees")
  public List<Employee> getAllEmployees(){
    return employeeRepository.findAll();
  }

  // create employee rest api
  @PostMapping("/employees")
  public Employee createEmployee(@RequestBody Employee employee) {
    return employeeRepository.save(employee);
  }

  // get employee by id rest api
  @GetMapping("/employees/{id}")
  public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
    Employee employee = employeeRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Employee not exist with id :" + id));
    return ResponseEntity.ok(employee);
  }

  // update employee rest api
  //@PutMapping("/employees/{id}")
  //public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails){
  //  Employee employee = employeeRepository.findById(id)
  //      .orElseThrow(() -> new RuntimeException("Employee not exist with id :" + id));
  //
  //  employee.setFirstName(employeeDetails.getFirstName());
  //  employee.setLastName(employeeDetails.getLastName());
  //  employee.setEmail(employeeDetails.getEmail());
  //
  //  Employee updatedEmployee = employeeRepository.save(employee);
  //  return ResponseEntity.ok(updatedEmployee);
  //}

  // update employee rest api
  @PutMapping("/employees/{id}")
  public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails){

    Optional<Employee> employee = employeeRepository.findById(id);

    if(employee.isPresent()){
      Employee _employee = employee.get();
      _employee.setFirstName(employeeDetails.getFirstName());
      _employee.setLastName(employeeDetails.getLastName());
      _employee.setEmail(employeeDetails.getEmail());
      return new ResponseEntity<>(employeeRepository.save(_employee), HttpStatus.OK);
    }else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
        //.orElseThrow(() -> new RuntimeException("Employee not exist with id :" + id));

    //employee.setFirstName(employeeDetails.getFirstName());
    //employee.setLastName(employeeDetails.getLastName());
    //employee.setEmail(employeeDetails.getEmail());
    //
    //Employee updatedEmployee = employeeRepository.save(employee);
    //return ResponseEntity.ok(updatedEmployee);
  }

  // delete employee rest api
  @DeleteMapping("/employees/{id}")
  public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id){
    Employee employee = employeeRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Employee not exist with id :" + id));

    employeeRepository.delete(employee);
    Map<String, Boolean> response = new HashMap<>();
    response.put("deleted", Boolean.TRUE);
    return ResponseEntity.ok(response);
  }

}
