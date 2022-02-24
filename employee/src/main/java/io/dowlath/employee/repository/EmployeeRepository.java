package io.dowlath.employee.repository;

import io.dowlath.employee.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/***
 * Author : Dowlath Basha G
 * Date   : 2/21/2022
 * Time   : 10:32 PM
 ***/
public interface EmployeeRepository extends JpaRepository<Employee,Long> {

 Optional<Employee> findByEmail(String email);

 //Define custom query with JPQL with queryparam
 @Query("select e from Employee e where e.firstName = ?1 and e.lastName = ?2")
 Employee findByJPQL(String firstName, String lastName);

 //Define custom query with JPQL with namedparam
 @Query("select e from Employee e where e.firstName = :firstName and e.lastName = :lastName")
 Employee findByJPQLNamedParam(@Param("firstName") String firstName, @Param("lastName") String lastName);

 //Define custom query with NativeQuery with namedparam
 @Query(value = "select * from Employee e where e.first_Name = ?1 and e.last_Name = ?2" , nativeQuery = true)
 Employee findByNativeSQL(String firstName , String lastName);

}
