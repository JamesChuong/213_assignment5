package a5_backend.Services;

import a5_backend.Model.CourseInterfaces.Department;
import a5_backend.Model.SFUCourseAttributes.SFUDepartmentList;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

@Service
public class SFUDepartmentService {
    private SFUDepartmentList sfuDepartmentList = new SFUDepartmentList();

    @PostConstruct
    public void init() {
        sfuDepartmentList.loadCSVFile("data/course_data_2018.csv");
    }

    public Department getDepartment(String departmentName) {
        return null;
    }
}
