package a5_backend.Controllers;

import a5_backend.DTOs.ApiCourseDTO;
import a5_backend.DTOs.ApiCourseOfferingDTO;
import a5_backend.DTOs.ApiDepartmentDTO;
import a5_backend.DTOs.ApiOfferingSectionDTO;
import a5_backend.Model.CourseInterfaces.ClassComponent;
import a5_backend.Model.CourseInterfaces.Course;
import a5_backend.Model.CourseInterfaces.Department;
import a5_backend.Model.SFUCourseAttributes.SFUCourse;
import a5_backend.Services.SFUDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
public class SFUDepartmentController {

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

    private final SFUDepartmentService sfuDepartmentService;

    @Autowired
    public SFUDepartmentController(SFUDepartmentService sfuDepartmentService) {
        this.sfuDepartmentService = sfuDepartmentService;
    }


    @GetMapping("/api/departments")
    public List<ApiDepartmentDTO> getAllDepartments() {
        List<ApiDepartmentDTO> departments = sfuDepartmentService.getAllDepartments();
        return departments;
    }


    @GetMapping("/api/departments/{departmentID}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable double departmentID) {
        Department department = sfuDepartmentService.getDepartment(departmentID);
        if (department == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(department, HttpStatus.OK);
    }

    @GetMapping("api/departments/{departmentID}/courses")
    public List<ApiCourseDTO> getDepartmentCourses(@PathVariable double departmentID){
        Department<SFUCourse> department = sfuDepartmentService.getDepartment(departmentID);
        if(department == null){
            throw new BadRequest("Error: Department Not found");
        }
        List<ApiCourseDTO> CourseDTOList = new ArrayList<>();
        Iterator<? extends Course> courseOfferings = department.getAllCourses();
        while(courseOfferings.hasNext()){
            Course currentCourse = courseOfferings.next();
            ApiCourseDTO newCourseDTO = ApiCourseDTO.createNewCourseDTO( currentCourse.getCourseID()
                    , currentCourse.getCatalogNumber() );
            CourseDTOList.add(newCourseDTO);
        }
        return CourseDTOList;
    }

    @GetMapping("api/departments/{departmentID}/courses/{courseID}/offerings/{courseOfferingID}")
    public List<ApiOfferingSectionDTO> getCourseSections(@PathVariable double departmentID
            , @PathVariable long courseID, long courseOfferingID){
        Department<SFUCourse> courseDepartment = sfuDepartmentService.getDepartment(departmentID);

        Iterator<? extends ClassComponent> allCourseOfferings =
                courseDepartment.getAllCourseOfferings(courseID, courseOfferingID);

        List<ApiOfferingSectionDTO> offeringList = new ArrayList<>();
        while (allCourseOfferings.hasNext()){
            offeringList.add( ApiOfferingSectionDTO.createSectionDTO( allCourseOfferings.next() ) );
        }
        return offeringList;
    }
}
