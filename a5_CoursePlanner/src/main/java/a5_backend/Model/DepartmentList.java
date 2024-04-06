package a5_backend.Model;

import a5_backend.Model.CourseInterfaces.Department;
import java.util.Iterator;

public interface DepartmentList {

    void loadCSVFile(String CSVFile);

    //Dumps all contents of the CSV file to the terminal
    void printCSVFile();

    Department getDepartment(double departmentID);

    //Returns an iterator of objects which implement the Department interface
    Iterator<? extends Department> getAllDepartments();

}
