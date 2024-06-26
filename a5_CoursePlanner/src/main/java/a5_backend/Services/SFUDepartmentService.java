package a5_backend.Services;
import a5_backend.DTOs.*;
import a5_backend.Model.CourseInterfaces.*;
import a5_backend.Model.SFUCourseAttributes.SFUDepartmentList;
import a5_backend.Model.Watchers.CourseWatcher;
import a5_backend.Model.Watchers.WatcherInterfaces.Watcher;
import a5_backend.Model.Watchers.WatcherList;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 *  The SFUDepartmentService acts as the interface or "facade" for the controllers
 *  in the API, it contains operations that the endpoints need, but redirects to
 *  other operations defined in other classes. It has both a list of departments
 *  and watchers.
 */
@Service
public class SFUDepartmentService {
    private final DepartmentList DEPARTMENT_LIST = SFUDepartmentList
            .createDepartmentListWithCSVFile("data/course_data_2018.csv");
    private final WatcherList WATCHERS = new WatcherList();

    // Gets and returns single SFU Department from DEPARTMENT_LIST
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

    //Print all contents of the model to the terminal
    public void dumpModel(){
        DEPARTMENT_LIST.dumpModel();
    }

    //Get all events stored by a specific watcher
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
    private <T, k> List<T> getListFromDepartment(long departmentID, CourseAttributeListBuilder<k> listBuilder
            , DTOBuilder<T,k> DTOcreator, Comparator<T> comparator) {
        Department department = getDepartment(departmentID);
        if (department == null) {
            throw new RuntimeException("Department with ID " + departmentID + " not found.");
        }
        List<T> DTOList = new ArrayList<>();
        Iterator<? extends k> courseAttributeIterator = listBuilder.getDTOIterator(department);
        while (courseAttributeIterator.hasNext()) {
            k currentObject = courseAttributeIterator.next();
            T dto = DTOcreator.createDTO(currentObject);
            DTOList.add(dto);
        }
        DTOList.sort(comparator);
        return DTOList;
    }

    public List<ApiCourseDTO> getDepartmentCourses(long departmentID){
        CourseAttributeListBuilder<Course> courseList = newDepartment -> newDepartment.getAllCourses();
        DTOBuilder<ApiCourseDTO, Course> courseDTOBuilder
                = newCourseAttribute -> ApiCourseDTO.createNewCourseDTO(newCourseAttribute);
        Comparator<ApiCourseDTO> comparator = Comparator.comparing(ApiCourseDTO::getCatalogNumber);
        return getListFromDepartment(departmentID, courseList, courseDTOBuilder, comparator);
    }

    public List<ApiOfferingSectionDTO> getCourseOfferingComponents(long departmentID
            , long courseID, long courseOfferingID){
        CourseAttributeListBuilder<ClassComponent> componentList
                = newDepartment -> newDepartment.getAllCourseOfferingSections(courseID, courseOfferingID);
        DTOBuilder<ApiOfferingSectionDTO, ClassComponent> sectionDTOBuilder
                = newCourseAttribute -> ApiOfferingSectionDTO.createSectionDTO(newCourseAttribute);
        Comparator<ApiOfferingSectionDTO> comparator = Comparator.comparing(ApiOfferingSectionDTO::getType);
        return getListFromDepartment(departmentID, componentList, sectionDTOBuilder, comparator);
    }

    public List<ApiCourseOfferingDTO> getAllCourseOfferings( long departmentID
            ,  long courseID){
        CourseAttributeListBuilder<Section> courseOfferingList
                = newDepartment -> newDepartment.getAllCourseOfferings(courseID);
        DTOBuilder<ApiCourseOfferingDTO, Section> offeringDTOBuilder
                = newCourseAttribute -> ApiCourseOfferingDTO.createNewOfferingDTO(newCourseAttribute);

        Comparator<ApiCourseOfferingDTO> comparator = Comparator.comparing(ApiCourseOfferingDTO::getSemesterCode);
        return getListFromDepartment(departmentID, courseOfferingList, offeringDTOBuilder, comparator);
    }

}
