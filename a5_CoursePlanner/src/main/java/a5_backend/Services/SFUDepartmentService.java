package a5_backend.Services;

import a5_backend.DTOs.ApiDepartmentDTO;
import a5_backend.DTOs.ApiOfferingDataDTO;
import a5_backend.Model.CourseInterfaces.Department;
import a5_backend.Model.CourseInterfaces.DepartmentList;
import a5_backend.Model.SFUCourseAttributes.SFUDepartmentList;
import a5_backend.Watchers.CourseWatcher;
import a5_backend.Watchers.WatcherInterfaces.Watcher;
import a5_backend.Watchers.WatcherList;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class SFUDepartmentService {
    private final DepartmentList DEPARTMENT_LIST = SFUDepartmentList
            .createDepartmentListWithCSVFile("data/course_data_2018.csv");
    private final WatcherList WATCHERS = new WatcherList();
    // Initializes SFUDepartmentService before it is used by the controllers
    @PostConstruct
    public void init() {
        try {
            //sfuDepartmentList.loadCSVFile("data/course_data_2018.csv");
            //sfuDepartmentList.printCSVFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Gets and returns single SFUDepartment from sfuDepartmentList
    public Department getDepartment(double departmentID) {
        //System.out.println(departmentID);
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
}
