package a5_backend.Model.CourseInterfaces;

import a5_backend.DTOs.ApiOfferingDataDTO;
import a5_backend.Model.Watchers.WatcherInterfaces.Observer;

import java.util.Iterator;

/**
 * The DepartmentList interface represents a list of departments at a school. It
 * supports operations for loading information from a CSV file, printing the contents
 * of it, returning a list of all departments or retrieving just 1 specific department,
 * and adding new components or observers.
 */
public interface DepartmentList {

    void loadCSVFile(String CSVFile);

    //Dumps all contents of the CSV file to the terminal
    void dumpModel();

    Department getDepartment(double departmentID);

    //Returns an iterator of objects which implement the Department interface
    Iterator<? extends Department> getAllDepartments();

    void addNewObserver(long departmentID, long courseID, Observer newObserver);

    // Parse a DTO
    void parseApiOfferingDataDTO(ApiOfferingDataDTO dto);
}
