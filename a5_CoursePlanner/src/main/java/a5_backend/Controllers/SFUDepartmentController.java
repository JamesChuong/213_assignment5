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

    //Generic method for obtaining a list of course attributes (courses, sections/offerings, and components)
    //Has 2 type parameters, T and k, k represents the course attribute we want a list of, and T is the
    //corresponding DTO for attribute k.
    private <T, k> List<T> getListFromDepartment(long departmentID, CourseAttributeListBuilder<T, k> filter
            , Comparator<T> comparator){
        try {
            Department department = DEPARTMENT_MANAGER.getDepartment(departmentID);
            if(department == null) {
                throw new RequestNotFound("Department with ID " + departmentID + " not found.");
            }
            List<T> DTOList = new ArrayList<>();
            Iterator<? extends k> courseAttributeIterator = filter.getDTOIterator(department);
            while(courseAttributeIterator.hasNext()){
                k currentObject = courseAttributeIterator.next();
                T dto = filter.createDTO(currentObject);
                DTOList.add(dto);
            }

            DTOList.sort(comparator);

            return DTOList;
        } catch (RuntimeException AttributeNotFound){
            throw new BadRequest(AttributeNotFound.getMessage());
        }
    }

    @GetMapping("api/departments/{departmentID}/courses")
    @ResponseStatus(HttpStatus.OK)
    public List<ApiCourseDTO> getDepartmentCourses(@PathVariable("departmentID") long departmentID){
        CourseAttributeListBuilder<ApiCourseDTO, Course> CourseDTOFilter = new CourseAttributeListBuilder<>() {
            @Override
            public Iterator<? extends Course> getDTOIterator(Department newDepartment) {
                return newDepartment.getAllCourses();
            }
            @Override
            public ApiCourseDTO createDTO(Course newCourseAttribute) {
                return ApiCourseDTO.createNewCourseDTO(newCourseAttribute);
            }
        };

        Comparator<ApiCourseDTO> comparator = Comparator.comparing(ApiCourseDTO::getCatalogNumber);
        return getListFromDepartment(departmentID, CourseDTOFilter, comparator);
    }

    @GetMapping("api/departments/{departmentID}/courses/{courseID}/offerings/{courseOfferingID}")
    @ResponseStatus(HttpStatus.OK)
    public List<ApiOfferingSectionDTO> getCourseOfferingComponents(@PathVariable("departmentID") long departmentID
            , @PathVariable("courseID") long courseID, @PathVariable("courseOfferingID") long courseOfferingID){
        CourseAttributeListBuilder<ApiOfferingSectionDTO, ClassComponent> componentList
                = new CourseAttributeListBuilder<>() {
            @Override
            public Iterator<? extends ClassComponent> getDTOIterator(Department newDepartment) {
                Iterator<? extends ClassComponent> courseOfferingIterator =
                        newDepartment.getAllCourseOfferingSections(courseID, courseOfferingID);
                return courseOfferingIterator;
            }

            @Override
            public ApiOfferingSectionDTO createDTO(ClassComponent newCourseAttribute) {
                return ApiOfferingSectionDTO.createSectionDTO(newCourseAttribute);
            }
        };

        Comparator<ApiOfferingSectionDTO> comparator = Comparator.comparing(ApiOfferingSectionDTO::getType);
        return getListFromDepartment(departmentID, componentList, comparator);
    }

    @GetMapping("api/departments/{departmentID}/courses/{courseID}/offerings")
    @ResponseStatus(HttpStatus.OK)
    public List<ApiCourseOfferingDTO> getAllCourseOfferings(@PathVariable("departmentID") long departmentID
            , @PathVariable("courseID") long courseID){
        CourseAttributeListBuilder<ApiCourseOfferingDTO, Section> courseOfferingList =
                new CourseAttributeListBuilder<>() {
            @Override
            public Iterator<? extends Section> getDTOIterator(Department newDepartment) {
                Iterator<? extends Section> allCourseOfferings = newDepartment.getAllCourseOfferings(courseID);
                return allCourseOfferings;
            }

            @Override
            public ApiCourseOfferingDTO createDTO(Section newCourseAttribute) {
                return ApiCourseOfferingDTO.createNewOfferingDTO(newCourseAttribute);
            }
        };

        Comparator<ApiCourseOfferingDTO> comparator = Comparator.comparing(ApiCourseOfferingDTO::getSemesterCode);
        return getListFromDepartment(departmentID, courseOfferingList, comparator);
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
