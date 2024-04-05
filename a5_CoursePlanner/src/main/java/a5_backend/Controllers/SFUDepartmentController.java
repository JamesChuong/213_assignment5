package a5_backend.Controllers;

import a5_backend.DTOs.ApiDepartmentDTO;
import a5_backend.Model.CourseInterfaces.Department;
import a5_backend.Services.SFUDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SFUDepartmentController {
    private SFUDepartmentService sfuDepartmentService;

    @Autowired
    public SFUDepartmentController(SFUDepartmentService sfuDepartmentService) {
        this.sfuDepartmentService = sfuDepartmentService;
    }

    @GetMapping("/api/departments")
    public List<ApiDepartmentDTO> getAllDepartments() {
        List<ApiDepartmentDTO> departments = sfuDepartmentService.getAllDepartments();
        return departments;
    }

    @GetMapping("/api/departments/{departmentId}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable String departmentId) {
        Department department = sfuDepartmentService.getDepartment(departmentId);
        if (department == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(department, HttpStatus.OK);
    }
}
