package a5_backend.Model.CourseInterfaces;

import a5_backend.Model.CourseInterfaces.Department;
import a5_backend.Model.SFUCourseAttributes.SFUCourse;
import a5_backend.Watchers.Observer;

import java.util.Iterator;

public interface DepartmentList {

    void loadCSVFile(String CSVFile);

    //Dumps all contents of the CSV file to the terminal
    void printCSVFile();

    Department<SFUCourse> getDepartment(double departmentID);

    //Returns an iterator of objects which implement the Department interface
    Iterator<? extends Department<SFUCourse>> getAllDepartments();

    void addNewObserver(long departmentID, long courseID, Observer newObserver);


}
