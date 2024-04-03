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
                //For each line in the file, create an object which implements the Component interface
                //and add it to the course found in the hashmap with the department name

                int enrollmentCapacity = CSVReader.nextInt();
                int enrollmentTotal = CSVReader.nextInt();
                String instructors = CSVReader.next().trim();
                String subject = CSVReader.next().trim();
                String catalogNumber = CSVReader.next().trim();
                String location = CSVReader.next().trim();
                String semester = CSVReader.next().trim();
                String componentCode = CSVReader.next().trim();

                if (instructors.equals("<null>")) { instructors = "";}

                if (CSVReader.hasNextLine()) { CSVReader.nextLine(); }

                // Change the semester string into an int and create a SFUCourseComponent object
                int semesterInt = Integer.parseInt(semester);
                ClassComponent newClassComponent = new SFUCourseComponent(enrollmentCapacity, enrollmentTotal,
                        instructors, subject, catalogNumber, location, semesterInt, componentCode);

                addComponent(newClassComponent);
            }
            CSVReader.close();
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
