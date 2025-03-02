package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.models.student;

public interface studentrepository extends CrudRepository<student, Integer>{

}
