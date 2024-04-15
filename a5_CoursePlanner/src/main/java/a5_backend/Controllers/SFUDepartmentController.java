package a5_backend.Controllers;

import a5_backend.DTOs.*;
import a5_backend.Model.CourseInterfaces.*;
import a5_backend.Services.SFUDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

@RestController
public class SFUDepartmentController {

    private final SFUDepartmentService DEPARTMENT_MANAGER;
    //private final a5_backend.Controllers.SFUDepartmentController SFUDepartmentController;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    static class BadRequest extends RuntimeException {
        public BadRequest(String ErrorMsg){
            super(ErrorMsg);
        }
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    static class RequestNotFound extends RuntimeException {
        public RequestNotFound(String ErrorMsg){
            super(ErrorMsg);
        }
    }


    @Autowired
    public SFUDepartmentController(SFUDepartmentService sfuDepartmentService) {
        this.DEPARTMENT_MANAGER = sfuDepartmentService;
        //this.SFUDepartmentController = SFUDepartmentController;
    }

    @GetMapping("/api/departments")
    public List<ApiDepartmentDTO> getAllDepartments() {
        List<ApiDepartmentDTO> departments = DEPARTMENT_MANAGER.getAllDepartments();
        return departments;
    }

    @GetMapping("/api/departments/{departmentID}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Department> getDepartmentById(@PathVariable("departmentID") double departmentID) {
        Department department = DEPARTMENT_MANAGER.getDepartment(departmentID);
        if (department == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(department, HttpStatus.OK);
    }


    @GetMapping("api/departments/{departmentID}/courses")
    @ResponseStatus(HttpStatus.OK)
    public List<ApiCourseDTO> getDepartmentCourses(@PathVariable("departmentID") long departmentID){
        return DEPARTMENT_MANAGER.getDepartmentCourses(departmentID);
    }

    @GetMapping("api/departments/{departmentID}/courses/{courseID}/offerings/{courseOfferingID}")
    @ResponseStatus(HttpStatus.OK)
    public List<ApiOfferingSectionDTO> getCourseOfferingComponents(@PathVariable("departmentID") long departmentID
            , @PathVariable("courseID") long courseID, @PathVariable("courseOfferingID") long courseOfferingID){
        return DEPARTMENT_MANAGER.getCourseOfferingComponents(departmentID, courseID, courseOfferingID);
    }

    @GetMapping("api/departments/{departmentID}/courses/{courseID}/offerings")
    @ResponseStatus(HttpStatus.OK)
    public List<ApiCourseOfferingDTO> getAllCourseOfferings(@PathVariable("departmentID") long departmentID
            , @PathVariable("courseID") long courseID){
        return DEPARTMENT_MANAGER.getAllCourseOfferings(departmentID, courseID);
    }

    @PostMapping("api/addoffering")
    @ResponseStatus(HttpStatus.CREATED)
    public void addOffering(@RequestBody ApiOfferingDataDTO newDTO) {
        try {
            DEPARTMENT_MANAGER.addOffering(newDTO);
        } catch (Exception e) {
            throw new RequestNotFound("Course ID not found");
        }
    }
}
