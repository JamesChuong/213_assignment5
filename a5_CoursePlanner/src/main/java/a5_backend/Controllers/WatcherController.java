package a5_backend.Controllers;

import a5_backend.DTOs.ApiWatcherDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WatcherController {

    @GetMapping("api/watchers")
    @ResponseStatus(HttpStatus.OK)
    public List<ApiWatcherDTO> getAllWatchers(){
        return null;
    }
}
