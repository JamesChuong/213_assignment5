package a5_backend.Services;

import a5_backend.Controllers.SFUDepartmentController;
import a5_backend.DTOs.*;
import a5_backend.Model.CourseInterfaces.*;
import a5_backend.Model.SFUCourseAttributes.SFUDepartmentList;
import a5_backend.Watchers.CourseWatcher;
import a5_backend.Watchers.WatcherInterfaces.Watcher;
import a5_backend.Watchers.WatcherList;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

@Service
public class SFUDepartmentService {
    private final DepartmentList DEPARTMENT_LIST = SFUDepartmentList
            .createDepartmentListWithCSVFile("data/course_data_2018.csv");
    private final WatcherList WATCHERS = new WatcherList();
    // Initializes SFUDepartmentService before it is used by the controllers


    // Gets and returns single SFUDepartment from sfuDepartmentList
    public Department getDepartment(double departmentID) {
        return DEPARTMENT_LIST.getDepartment(departmentID);
    }

    // Gets the SFUDepartments from sfuDepartmentList and iterates through them then creates
    // ApiDepartmentDTOs from the iterated departments and adds it to the departmentDTOs list to return
    public List<ApiDepartmentDTO> getAllDepartments() {
        List<ApiDepartmentDTO> departmentDTOs = new ArrayList<>();
        Iterator<? extends Department> allDepartments = DEPARTMENT_LIST.getAllDepartments();
        while(allDepartments.hasNext()) {
            Department newSFUDepartment = allDepartments.next();
            ApiDepartmentDTO newApiDepartmentDTO = ApiDepartmentDTO.createApiDepartmentDTO(newSFUDepartment);
            departmentDTOs.add(newApiDepartmentDTO);
        }
        return departmentDTOs;
    }

    public void addNewObserver(long departmentID, long courseID){
        Watcher newWatcher = CourseWatcher.createNewCourseWatcher(departmentID, courseID);
        WATCHERS.addNewWatcher(newWatcher);
        DEPARTMENT_LIST.addNewObserver(departmentID, courseID, newWatcher.getObserver());
    }

    public Iterator<? extends Watcher> getAllWatchers(){
        return WATCHERS.retreiveAllWatchers();
    }

    public void dumpModel(){
        DEPARTMENT_LIST.printCSVFile();
    }

    public List<String> getWatcherEvents(long courseID){
        return WATCHERS.retreiveEventsOfWatcher(courseID);
    }

    public void addOffering(ApiOfferingDataDTO dto) {
        DEPARTMENT_LIST.parseApiOfferingDataDTO(dto);
    }

    public void deleteWatcher(long watcherID){
        WATCHERS.deleteWatcher(watcherID);
    }

    //Generic method for obtaining a list of course attributes (courses, sections/offerings, and components)
    //Has 2 type parameters, T and k, k represents the course attribute we want a list of, and T is the
    //corresponding DTO for attribute k.
    private <T, k> List<T> getListFromDepartment(long departmentID, CourseAttributeListBuilder<T, k> filter
            , Comparator<T> comparator) {
        Department department = getDepartment(departmentID);
        if (department == null) {
            throw new RuntimeException("Department with ID " + departmentID + " not found.");
        }
        List<T> DTOList = new ArrayList<>();
        Iterator<? extends k> courseAttributeIterator = filter.getDTOIterator(department);
        while (courseAttributeIterator.hasNext()) {
            k currentObject = courseAttributeIterator.next();
            T dto = filter.createDTO(currentObject);
            DTOList.add(dto);
        }
        DTOList.sort(comparator);
        return DTOList;
    }

    public List<ApiCourseDTO> getDepartmentCourses(long departmentID){
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

    public List<ApiOfferingSectionDTO> getCourseOfferingComponents(long departmentID
            , long courseID, long courseOfferingID){
        CourseAttributeListBuilder<ApiOfferingSectionDTO, ClassComponent> componentList = new CourseAttributeListBuilder<>() {
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

    public List<ApiCourseOfferingDTO> getAllCourseOfferings( long departmentID
            ,  long courseID){
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



}
