package a5_backend.Services;
import a5_backend.Model.SFUCourseAttributes.SFUDepartmentList;
import org.springframework.stereotype.Service;

@Service
public class InformationService {
    private final SFUDepartmentList sfuDepartmentList = SFUDepartmentList.createDepartmentListWithCSVFile("a5_CoursePlanner/data/course_data_2018test.csv");

    public void dumpModel() {

        sfuDepartmentList.printCSVFile();
    }

}
