package a5_backend.Controllers;

import a5_backend.DTOs.ApiCourseDTO;
import a5_backend.DTOs.ApiCourseOfferingDTO;
import a5_backend.DTOs.ApiDepartmentDTO;
import a5_backend.DTOs.ApiOfferingSectionDTO;
import a5_backend.Model.CourseInterfaces.*;
import a5_backend.Model.SFUCourseAttributes.SFUCourse;
import a5_backend.Services.SFUDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
public class SFUDepartmentController {

    private final SFUDepartmentService sfuDepartmentService;
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
        this.sfuDepartmentService = sfuDepartmentService;
        //this.SFUDepartmentController = SFUDepartmentController;
    }

    @GetMapping("/api/departments")
    public List<ApiDepartmentDTO> getAllDepartments() {
        List<ApiDepartmentDTO> departments = sfuDepartmentService.getAllDepartments();
        return departments;
    }

    @GetMapping("/api/departments/{departmentID}")
    public ResponseEntity<Department<SFUCourse>> getDepartmentById(@PathVariable double departmentID) {
        Department<SFUCourse> department = sfuDepartmentService.getDepartment(departmentID);
        if (department == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(department, HttpStatus.OK);
    }

    //Generic method for obtaining a list of course attributes (courses, sections/offerings, and components)
    //Has 2 type parameters, T and k, k represents the course attribute we want a list of, and T is the
    //corresponding DTO for attribute k.
    private <T, k> List<T> getListFromDepartment(long departmentID, CourseAttributeListBuilder<T, k> filter){
        try {
            Department<SFUCourse> department = sfuDepartmentService.getDepartment(departmentID);
            if(department == null) {
                throw new RequestNotFound("Department with ID " + departmentID + " not found.");
            }
            List<T> DTOList = new ArrayList<>();
            Iterator<? extends k> courseAttributeIterator = filter.getDTOIterator(department);
            while(courseAttributeIterator.hasNext()){
                k currentObject = courseAttributeIterator.next();
                DTOList.add(filter.createDTO(currentObject));
            }
            return DTOList;
        } catch (RuntimeException AttributeNotFound){
            throw new BadRequest(AttributeNotFound.getMessage());
        }
    }

    @GetMapping("api/departments/{departmentID}/courses")
    public List<ApiCourseDTO> getDepartmentCourses(@PathVariable long departmentID){
        CourseAttributeListBuilder<ApiCourseDTO, Course> CourseDTOFilter = new CourseAttributeListBuilder<>() {
            @Override
            public Iterator<? extends Course> getDTOIterator(Department<SFUCourse> newDepartment) {
                return newDepartment.getAllCourses();
            }
            @Override
            public ApiCourseDTO createDTO(Course newCourseAttribute) {
                return ApiCourseDTO.createNewCourseDTO(newCourseAttribute);
            }
        };

        return getListFromDepartment(departmentID, CourseDTOFilter);
    }

    @GetMapping("api/departments/{departmentID}/courses/{courseID}/offerings/{courseOfferingID}")
    public List<ApiOfferingSectionDTO> getCourseOfferingComponents(@PathVariable long departmentID
            , @PathVariable long courseID, @PathVariable long courseOfferingID){
        CourseAttributeListBuilder<ApiOfferingSectionDTO, ClassComponent> componentList
                = new CourseAttributeListBuilder<>() {
            @Override
            public Iterator<? extends ClassComponent> getDTOIterator(Department<SFUCourse> newDepartment) {
                Iterator<? extends ClassComponent> courseOfferingIterator =
                        newDepartment.getAllCourseOfferingSections(courseID, courseOfferingID);
                return courseOfferingIterator;
            }

            @Override
            public ApiOfferingSectionDTO createDTO(ClassComponent newCourseAttribute) {
                return ApiOfferingSectionDTO.createSectionDTO(newCourseAttribute);
            }
        };
        return getListFromDepartment(departmentID, componentList);
    }

    @GetMapping("api/departments/{departmentID}/courses/{courseID}/offerings")
    public List<ApiCourseOfferingDTO> getAllCourseOfferings(@PathVariable long departmentID
            , @PathVariable long courseID){
        CourseAttributeListBuilder<ApiCourseOfferingDTO, Section> courseOfferingList =
                new CourseAttributeListBuilder<>() {
            @Override
            public Iterator<? extends Section> getDTOIterator(Department<SFUCourse> newDepartment) {
                Iterator<? extends Section> allCourseOfferings = newDepartment.getAllCourseOfferings(courseID);
                return allCourseOfferings;
            }

            @Override
            public ApiCourseOfferingDTO createDTO(Section newCourseAttribute) {
                return ApiCourseOfferingDTO.createNewOfferingDTO(newCourseAttribute);
            }
        };
        return getListFromDepartment(departmentID, courseOfferingList);
    }
}
