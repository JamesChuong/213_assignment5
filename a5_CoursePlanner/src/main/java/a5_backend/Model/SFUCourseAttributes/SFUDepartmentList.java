package a5_backend.Model.SFUCourseAttributes;

import a5_backend.Model.CourseInterfaces.ClassComponent;
import a5_backend.Model.CourseInterfaces.Department;
import a5_backend.Model.DepartmentList;
import a5_backend.Model.SFUCourseAttributes.SFUDepartment;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class SFUDepartmentList implements DepartmentList {

    //A hashmap is used to store each department, each department is mapped to its name (CMPT, ENSC, MATH, STAT, etc.)
    private HashMap<String, Department> allDepartmentsAtSFU = new HashMap<>();
    @Override
    public void readCSVFile(String CSVFile) {
        //NOTE: I don't think we need the CSVReader/Printer interfaces
        try{
            Scanner CSVReader = new Scanner(new File(CSVFile));
            CSVReader.useDelimiter(",");
            while(CSVReader.hasNext()){
                //NOT IMPLEMENTED YET

                //For each line in the file, create an object which implements the Component interface
                //and add it to the course found in the hashmap with the department name

            }
        } catch (FileNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    private void addComponent(ClassComponent newComponent){
        boolean departmentIsFound = allDepartmentsAtSFU.containsKey(newComponent.getDepartmentName());
        if(!departmentIsFound){
            createNewDepartment(newComponent.getDepartmentName(), newComponent);
        } else{
            createNewDepartment(newComponent.getDepartmentName(), newComponent);
        }
    }

    private void createNewDepartment(String departmentName, ClassComponent newComponent){
        Department newSFUDepartment = new SFUDepartment();
        newSFUDepartment.addNewComponent(newComponent);
        allDepartmentsAtSFU.put(departmentName, newSFUDepartment);
    }
    @Override
    public void printCSVFile() {

    }
}
