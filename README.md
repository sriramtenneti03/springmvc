

# Student Management System (Spring MVC)

## Overview

The **Student Management System** is a web-based application built using **Spring MVC**, **Thymeleaf**, and **MySQL** to manage student data. The application allows users to **add**, **edit**, **delete**, and **view** student records. It provides a simple interface to manage student information such as **name**, **email**, and **password**.

This project demonstrates the use of **Spring MVC** for the backend and **Thymeleaf** templates for rendering dynamic web pages. The data is stored in a **MySQL** database, and CRUD (Create, Read, Update, Delete) operations are implemented using **Spring Data JPA**.

## Features

- **View All Students**: Displays a list of all students stored in the database.
- **Add Student**: Provides a form for adding new students with their name, email, and password.
- **Edit Student**: Allows the admin to update a student’s details.
- **Delete Student**: Deletes a student’s record from the database.
- **Form Validation**: Basic validation on fields such as email format and password length.
- **Responsive UI**: The frontend is designed using **Bootstrap** to ensure the application is responsive and user-friendly.

## Technologies Used

- **Spring MVC**: For building the web application’s backend with a controller layer to handle HTTP requests and responses.
- **Spring Boot**: For simplifying the development process and managing dependencies.
- **Thymeleaf**: For rendering dynamic web pages with server-side logic.
- **Spring Data JPA**: To interact with the MySQL database and perform CRUD operations.
- **MySQL**: For data storage and management.
- **Bootstrap**: For styling and making the user interface responsive.
- **Java**: For backend logic and integration of Spring framework components.

## Project Structure

### 1. **Frontend (Thymeleaf + Bootstrap)**:

#### **`students.html`**:
Displays a list of all students with options to edit or delete them.

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Students</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <h1 class="text-center mb-4">Students List</h1>
        <table class="table table-bordered table-striped">
            <thead class="thead-dark">
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Password</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <tr th:each="student : ${students}">
                    <td th:text="${student.id}"></td>
                    <td th:text="${student.name}"></td>
                    <td th:text="${student.email}"></td>
                    <td th:text="${student.password}"></td>
                    <td>
                        <a th:href="@{/student/edit/{id}(id=${student.id})}" class="btn btn-warning btn-sm">Edit</a>
                        <a th:href="@{/student/delete/{id}(id=${student.id})}" class="btn btn-danger btn-sm" onclick="return confirm('Are you sure you want to delete this student?')">Delete</a>
                    </td>
                </tr>
            </tbody>
        </table>
        <div class="d-flex justify-content-between">
            <a href="/student/add" class="btn btn-success btn-sm">Add New Student</a>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
```

#### **`add_students.html`**:
Form to add a new student to the database.

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Add Student</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5" style="max-width:500px">
        <h1 class="text-center mb-4">Add Student</h1>
        <form action="/student/save" method="post" th:object="${student}">
            <div class="mb-3">
                <label for="name" class="form-label">Name</label>
                <input type="text" id="name" class="form-control" th:field="*{name}" required />
            </div>
            <div class="mb-3">
                <label for="email" class="form-label">Email</label>
                <input type="email" id="email" class="form-control" th:field="*{email}" required />
            </div>
            <div class="mb-3">
                <label for="password" class="form-label">Password</label>
                <input type="password" id="password" class="form-control" th:field="*{password}" required />
            </div>
            <div class="d-flex justify-content-between">
                <button type="submit" class="btn btn-primary">Add</button>
                <a href="/student" class="btn btn-secondary">Back to Student List</a>
            </div>
        </form>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
```

#### **`edit_students.html`**:
Form to edit an existing student's details.

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Edit Student</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5" style="max-width:500px">
        <h1 class="text-center mb-4">Edit Student</h1>
        <form th:action="@{/student/update/{id}(id=${student.id})}" method="post" th:object="${student}">
            <div class="mb-3">
                <label for="id" class="form-label">ID</label>
                <input type="number" id="id" th:field="*{id}" class="form-control" readonly />
            </div>
            <div class="mb-3">
                <label for="name" class="form-label">Name</label>
                <input type="text" id="name" th:field="*{name}" class="form-control" required />
            </div>
            <div class="mb-3">
                <label for="email" class="form-label">Email</label>
                <input type="email" id="email" th:field="*{email}" class="form-control" required />
            </div>
            <div class="mb-3">
                <label for="password" class="form-label">Password</label>
                <input type="password" id="password" th:field="*{password}" class="form-control" required />
            </div>
            <button type="submit" class="btn btn-success btn-sm">Update</button>
        </form>
        <br />
        <a href="/student" class="btn btn-secondary btn-sm">Back to Student List</a>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
```

### 2. **Backend (Spring MVC)**:

#### **`student.java` (Model)**:
The entity class representing a student.

```java
package com.example.demo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    @Email
    private String email;
    @Size(min = 4, max = 20)
    private String password;
}
```

#### **`studentrepository.java` (Repository)**:
Interface for performing CRUD operations on the `student` entity.

```java
package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;
import com.example.demo.models.student;

public interface studentrepository extends CrudRepository<student, Integer> {

}
```

#### **`studentservice.java` (Service)**:
Implementation of the service layer for student CRUD operations.

```java
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
        return repo.save(s);
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
```

#### **`studentcontroller.java` (Controller)**:
Handles all the HTTP requests related to student operations.

```java
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
        m.addAttribute("student", new student());
        return "add_students";
    }

    @PostMapping("/save")
    public String savestudent(@ModelAttribute("student") student s) {
        service.addstd(s);
        return "redirect:/student";
    }

    @GetMapping("/delete/{id}")
    public String deletebyid

(@PathVariable("id") int id) {
        service.deletebyid(id);
        return "redirect:/student";
    }

    @PostMapping("/update/{id}")
    public String updatestudent(@PathVariable("id") int id, @ModelAttribute student s) {
        s.setId(id);
        service.update(s);
        return "redirect:/student";
    }

    @GetMapping("/edit/{id}")
    public String editform(@PathVariable("id") int id, Model m) {
        m.addAttribute("student", service.getbyid(id));
        return "edit_students";
    }
}
```

## How to Use

1. **Clone the repository** to your local machine.
2. **Set up MySQL Database**:
   - Create a new database in MySQL called `dashboard`.
   - Configure the MySQL connection in `application.properties`.
   
3. **Run the Application**:
   - Import the project into an IDE (e.g., IntelliJ IDEA, Eclipse).
   - Run the Spring Boot application (`mvn spring-boot:run` or run it directly from your IDE).
   - The application will be available at `http://localhost:8089`.

4. **Access the Application**:
   - **Homepage** (`/student`): Displays a list of all students.
   - **Add Student** (`/student/add`): Navigate here to add a new student.
   - **Edit Student** (`/student/edit/{id}`): Edit an existing student's information.
   - **Delete Student** (`/student/delete/{id}`): Delete a student's record from the database.

## Future Enhancements

- **User Authentication**: Implement user authentication (login and registration) to protect student data.
- **Advanced Validation**: Improve form validation with more rules (e.g., checking if the email already exists).
- **Search and Filter**: Implement search and filter functionality to easily find students by name or email.
- **Pagination**: Add pagination for the student list to improve performance with large datasets.
- **Role-based Access**: Add different roles such as Admin, User, or Guest for different levels of access.

## Conclusion

The **Student Management System** is a simple yet effective way to manage student data. Built using **Spring MVC**, **Thymeleaf**, and **MySQL**, this project demonstrates how to create a full-stack application with CRUD functionalities. The modular and extensible design allows for easy future enhancements.
