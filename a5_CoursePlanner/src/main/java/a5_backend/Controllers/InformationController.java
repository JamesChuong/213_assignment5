package a5_backend.Controllers;


import a5_backend.DTOs.ApiAboutDTO;
import a5_backend.Services.SFUDepartmentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class InformationController {
    private SFUDepartmentService informationService = new SFUDepartmentService();

    @GetMapping("/api/about")
    public ApiAboutDTO getAbout() {
        ApiAboutDTO aboutDTO = new ApiAboutDTO("Moe and James' Amazing Course Planner",
                "Mohamed Mustafa and James Chuong");
        return aboutDTO;
    }

    @GetMapping("/api/dump-model")
    public void dumpAllClasses() {
        informationService.dumpModel();

    }

}
