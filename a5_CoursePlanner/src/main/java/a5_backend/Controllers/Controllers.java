package a5_backend.Controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import a5_backend.DTOs.*;


@RestController
public class Controllers {

    @GetMapping("api/dump-model")
    public void dumpAllClasses() {

    }

}
