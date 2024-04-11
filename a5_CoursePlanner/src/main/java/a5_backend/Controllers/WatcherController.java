package a5_backend.Controllers;

import a5_backend.DTOs.ApiWatcherCreateDTO;
import a5_backend.DTOs.ApiWatcherDTO;
import a5_backend.Services.SFUDepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WatcherController {

    private SFUDepartmentService SFUDepartmentService = new SFUDepartmentService();

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

    @GetMapping("api/watchers")
    @ResponseStatus(HttpStatus.OK)
    public List<ApiWatcherDTO> getAllWatchers(){
        return null;
    }

    @PostMapping("/api/watchers")
    @ResponseStatus(HttpStatus.CREATED)
    public void addNewWatcher(@RequestBody ApiWatcherCreateDTO newWatcher){

    }
}
