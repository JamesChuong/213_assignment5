package a5_backend.Controllers;


import a5_backend.Services.InformationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class InformationController {
    private InformationService informationService;

    @GetMapping("api/dump-model")
    public void dumpAllClasses() {
        informationService.dumpModel();


    }

}
