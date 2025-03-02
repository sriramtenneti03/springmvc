package com.example.demo.services.imply;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.student;
import com.example.demo.repository.studentrepository;
import com.example.demo.services.studentservices;

@Service
public class studentserviceimple implements studentservices {

	@Autowired
	private studentrepository repo;
	
	@Override
	public List<student> getall() {
		return (List<student>) repo.findAll();
	}

	@Override
	public student addstd(student s) {
	  return  repo.save(s);

	}

	@Override
	public student getbyid(int id) {
		return repo.findById(id).orElse(null);
	}

	@Override
	public void deletebyid(int id) {
		repo.deleteById(id);

	}

	@Override
	public void update(student s) {
		repo.save(s);
	}

}
