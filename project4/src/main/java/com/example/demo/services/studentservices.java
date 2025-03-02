package com.example.demo.services;

import java.util.List;


import com.example.demo.models.student;


public interface studentservices {
	List<student> getall();
	student addstd(student s);
	student getbyid(int id);
	void deletebyid(int id);
	void update(student s);
}
