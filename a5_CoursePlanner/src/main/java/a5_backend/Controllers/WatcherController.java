package a5_backend.Controllers;

import a5_backend.DTOs.ApiCourseDTO;
import a5_backend.DTOs.ApiDepartmentDTO;
import a5_backend.DTOs.ApiWatcherCreateDTO;
import a5_backend.DTOs.ApiWatcherDTO;
import a5_backend.Model.CourseInterfaces.Course;
import a5_backend.Model.CourseInterfaces.Department;
import a5_backend.Services.SFUDepartmentService;
import a5_backend.Model.Watchers.WatcherInterfaces.Watcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The WatcherController class manages watchers.It interacts with the SFUDepartmentService to facilitate
 * operations related to watchers, which monitor changes to course and department data.
 *
 * It provides endpoints for creating, listing, and deleting watchers, as well as retrieving events
 * observed by each watcher. This controller handles the interaction between the HTTP requests and
 * the server encapsulated in the SFUDepartmentService, ensuring that watchers are
 * properly managed according to the requests received.
 *
 * When a new watcher is created via a POST request, it registers the watcher with the corresponding course
 * and department. The GET methods retrieve either a list of all watchers with their associated events or
 * the specific events for a watcher. Deleting a watcher is done by DELETE request.
 */
@RestController
public class WatcherController {

    private final SFUDepartmentService DEPARTMENT_MANAGER;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    static class BadRequest extends RuntimeException {
        public BadRequest(String ErrorMsg){
            super(ErrorMsg);
        }
    }

    @Autowired
    public WatcherController(SFUDepartmentService SFUDepartmentService) {
        this.DEPARTMENT_MANAGER = SFUDepartmentService;
    }

    @GetMapping("api/watchers")
    @ResponseStatus(HttpStatus.OK)
    public List<ApiWatcherDTO> getAllWatchers(){
        Iterator<? extends Watcher> allWatchers = DEPARTMENT_MANAGER.getAllWatchers();
        List<ApiWatcherDTO> DTOList = new ArrayList<>();
        while(allWatchers.hasNext()){
            Watcher currentWatcher = allWatchers.next();
            Department observedDepartment = DEPARTMENT_MANAGER.getDepartment(currentWatcher.getDepartmentID());
            Course observedCourse = observedDepartment.findCourse(currentWatcher.getCourseID());
            DTOList.add( ApiWatcherDTO.createNewWatcherDTO( currentWatcher.getID()
                    , ApiDepartmentDTO.createApiDepartmentDTO(observedDepartment)
                    , ApiCourseDTO.createNewCourseDTO(observedCourse) , currentWatcher.getListOfChanges() ) );
        }
        return DTOList;
    }

    @PostMapping("/api/watchers")
    @ResponseStatus(HttpStatus.CREATED)
    public void addNewWatcher(@RequestBody ApiWatcherCreateDTO newWatcherDTO){
        try{
            DEPARTMENT_MANAGER.addNewObserver(newWatcherDTO.deptId, newWatcherDTO.courseId);
        } catch (RuntimeException notFoundException){
            throw new BadRequest(notFoundException.getMessage());
        }
    }

    @GetMapping("api/watchers/{watcherID}")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getWatcherEvents(@PathVariable("watcherID") long watcherID){
        return DEPARTMENT_MANAGER.getWatcherEvents(watcherID);
    }

    @DeleteMapping("api/watchers/{watcherID}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteWatcher(@PathVariable("watcherID") long watcherID){
        DEPARTMENT_MANAGER.deleteWatcher(watcherID);
    }
}
