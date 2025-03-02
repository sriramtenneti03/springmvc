package com.example.demo.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.models.student;
import com.example.demo.services.studentservices;

@Controller
@RequestMapping("/student")
public class studentcontroller {

	@Autowired
	private studentservices service;
	
	@GetMapping
	public String getallstudents(Model m) {
		m.addAttribute("students", service.getall()); 
		return "students";
	}
	
	@GetMapping("/add")
	public String formreset(Model m) {
		m.addAttribute("student",new student());
		return "add_students";
	}
	
	@PostMapping("/save")
	public String savestudent(@ModelAttribute("student") student s) {
		service.addstd(s);
		return "redirect:/student";
	}
	
	@GetMapping("/delete/{id}")
	public String deletebyid(@PathVariable("id") int id) {
		service.deletebyid(id);
		return "redirect:/student";
	}
	
	@PostMapping("/update/{id}")
	public String updatestudent(@PathVariable("id") int id,@ModelAttribute student s) {
		s.setId(id);
		service.update(s);
		return "redirect:/student";
	}
	
	@GetMapping("/edit/{id}")
	public String editform(@PathVariable("id") int id,Model m) {
		m.addAttribute("student",service.getbyid(id));
		return "edit_students";
	}
	
}
