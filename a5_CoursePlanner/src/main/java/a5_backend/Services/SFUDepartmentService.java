package a5_backend.Services;

import a5_backend.DTOs.ApiDepartmentDTO;
import a5_backend.Model.CourseInterfaces.Department;
import a5_backend.Model.SFUCourseAttributes.SFUDepartmentList;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SFUDepartmentService {
    private final SFUDepartmentList sfuDepartmentList = new SFUDepartmentList();

    @PostConstruct
    public void init() {
        try {
            sfuDepartmentList.loadCSVFile("a5_CoursePlanner/data/course_data_2018.csv");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Department getDepartment(String departmentName) {
        return null;
    }

    public List<ApiDepartmentDTO> getAllDepartments() {
        List<ApiDepartmentDTO> departmentDTOs = new ArrayList<>();
        for (Department department : sfuDepartmentList.getAllDepartments()) {
            // TODO: implement this
        }
        return departmentDTOs;
    }
    }
