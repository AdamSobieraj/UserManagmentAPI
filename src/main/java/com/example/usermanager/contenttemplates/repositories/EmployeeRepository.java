package com.example.usermanager.contenttemplates.repositories;

import com.example.usermanager.contenttemplates.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE employee SET name = :name, surname = :surname, grade= :grade, salary=:salary WHERE id= :id", nativeQuery = true)
    void updateUser(Integer id, String name, String surname,Integer grade, Integer salary);
}
