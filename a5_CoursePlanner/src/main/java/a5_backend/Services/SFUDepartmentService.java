package a5_backend.Services;

import a5_backend.DTOs.ApiDepartmentDTO;
import a5_backend.Model.CourseInterfaces.Department;
import a5_backend.Model.CourseInterfaces.DepartmentList;
import a5_backend.Model.SFUCourseAttributes.SFUCourse;
import a5_backend.Model.SFUCourseAttributes.SFUDepartmentList;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class SFUDepartmentService {
    private final DepartmentList sfuDepartmentList = SFUDepartmentList.createDepartmentListWithCSVFile("data/course_data_2018.csv");

    // Initializes SFUDepartmentService before it is used by the controllers
    @PostConstruct
    public void init() {
        try {
            //sfuDepartmentList.loadCSVFile("data/course_data_2018.csv");
            //sfuDepartmentList.printCSVFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Gets and returns single SFUDepartment from sfuDepartmentList
    public Department<SFUCourse> getDepartment(double departmentID) {
        return sfuDepartmentList.getDepartment(departmentID);
    }

    // Gets the SFUDepartments from sfuDepartmentList and iterates through them then creates
    // ApiDepartmentDTOs from the iterated departments and adds it to the departmentDTOs list to return
    public List<ApiDepartmentDTO> getAllDepartments() {
        List<ApiDepartmentDTO> departmentDTOs = new ArrayList<>();
        Iterator<? extends Department<SFUCourse>> allDepartments = sfuDepartmentList.getAllDepartments();
        while(allDepartments.hasNext()) {
            Department<SFUCourse> newSFUDepartment = allDepartments.next();
            ApiDepartmentDTO newApiDepartmentDTO = ApiDepartmentDTO.createApiDepartmentDTO(newSFUDepartment);
            departmentDTOs.add(newApiDepartmentDTO);
        }
        return departmentDTOs;
    }

    public void dumpModel(){
        sfuDepartmentList.printCSVFile();
    }
}
