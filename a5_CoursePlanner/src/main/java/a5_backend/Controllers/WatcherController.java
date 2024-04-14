package a5_backend.Controllers;

import a5_backend.DTOs.ApiCourseDTO;
import a5_backend.DTOs.ApiDepartmentDTO;
import a5_backend.DTOs.ApiWatcherCreateDTO;
import a5_backend.DTOs.ApiWatcherDTO;
import a5_backend.Model.CourseInterfaces.Course;
import a5_backend.Model.CourseInterfaces.Department;
import a5_backend.Services.SFUDepartmentService;
import a5_backend.Watchers.WatcherInterfaces.Watcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
public class WatcherController {

    private final SFUDepartmentService DEPARTMENT_MANAGER;

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
