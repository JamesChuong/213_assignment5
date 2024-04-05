package a5_backend.Model;

import a5_backend.Model.SFUCourseAttributes.SFUDepartmentList;

public class ModelManager {
    private final DepartmentList SFUDepartments = new SFUDepartmentList();

    public ModelManager(){
        SFUDepartments.loadCSVFile("data/course_data_2018test.csv");
    }

    public void dumpModel(){
        SFUDepartments.printCSVFile();
    }
}
