package a5_backend.Controllers;


import a5_backend.DTOs.ApiAboutDTO;
import a5_backend.Services.SFUDepartmentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The InformationController class functions as access point for retrieving information
 * about the application, and handling dump model calls. This controller handles
 * user requests concerning application details and data model state from the frontend and provides
 * a link to backend service, SFUDepartmentService, to fetch and manage the required data.
 *
 * When a request is received for application information, the controller constructs and returns
 * an ApiAboutDTO containing the names of the developers and the application name. For requests to
 * dump the current state of the data model, it delegates to the SFUDepartmentService to perform
 * the action, serving as a bridge between the API and the service layer.
 */
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
