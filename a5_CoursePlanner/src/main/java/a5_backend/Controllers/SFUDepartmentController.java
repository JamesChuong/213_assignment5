package a5_backend.Controllers;

import a5_backend.Model.CourseInterfaces.Department;
import a5_backend.Model.SFUCourseAttributes.SFUDepartment;
import a5_backend.Services.SFUDepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SFUDepartmentController {
    private SFUDepartmentService sfuDepartmentService;

    public SFUDepartmentController(SFUDepartmentService sfuDepartmenttService) {
        this.sfuDepartmentService = sfuDepartmenttService;
    }

    @GetMapping("/api/departments/{departmentName}")
    public ResponseEntity<Department> getDepartment(@PathVariable String departmentName) {
        Department department = sfuDepartmentService.getDepartment(departmentName);
        if (department == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(department, HttpStatus.OK);
    }
}
