package a5_backend.Services;
import a5_backend.Model.SFUCourseAttributes.SFUDepartmentList;
import org.springframework.stereotype.Service;

@Service
public class InformationService {
    private SFUDepartmentList sfuDepartmentList = new SFUDepartmentList();

    public void dumpModel() {

        sfuDepartmentList.dumpModel();
    }

}
