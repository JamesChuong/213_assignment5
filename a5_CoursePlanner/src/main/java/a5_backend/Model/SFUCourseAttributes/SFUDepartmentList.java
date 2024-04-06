package a5_backend.Model.SFUCourseAttributes;

import a5_backend.Model.CourseInterfaces.ClassComponent;
import a5_backend.Model.CourseInterfaces.Department;
import a5_backend.Model.DepartmentList;
import a5_backend.Model.SFUCourseAttributes.SFUDepartment;
import java.lang.Math;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class SFUDepartmentList implements DepartmentList {

    //A hashmap is used to store each department, each department is mapped to its name (CMPT, ENSC, MATH, STAT, etc.)
    private final HashMap<Double, Department> allDepartmentsAtSFU = new HashMap<>();

    public static SFUDepartmentList createDepartmentListWithCSVFile(String CSVFile){
        SFUDepartmentList newDepartmentList = new SFUDepartmentList();
        newDepartmentList.loadCSVFile(CSVFile);
        return newDepartmentList;
    }

    @Override
    public void loadCSVFile(String CSVFile) {
        //NOTE: I don't think we need the CSVReader/Printer interfaces
        try{
            File openedFile = new File(CSVFile);
            Scanner CSVReader = new Scanner(openedFile);
            CSVReader.nextLine();
            while(CSVReader.hasNextLine()){
                //For each line in the file, create an object which implements the Component interface
                //and add it to the course found in the hashmap with the department name
                parseLine(CSVReader.nextLine());
            }
            CSVReader.close();
        } catch (FileNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    private void parseLine(String CSVLine){
        //System.out.println(CSVLine); // raw printing out of the whole extracted string
        Scanner lineScanner = new Scanner(CSVLine);
        lineScanner.useDelimiter(",");
        String semester = lineScanner.next().trim();
        int semesterInt = Integer.parseInt(semester);
        String subject = lineScanner.next().trim();
        String catalogNumber = lineScanner.next().trim();
        String location = lineScanner.next().trim();
        int enrollmentCapacity = lineScanner.nextInt();
        int enrollmentTotal = lineScanner.nextInt();
        List<String> instructors = new ArrayList<>();
        String instructorLine = lineScanner.next();
        if(instructorLine.equals("<null>")){
            instructors.add(" ");
        } else if (instructorLine.contains("\"")){
            instructors.add(instructorLine.trim().replace("\"", ""));
            String nextInstructor = lineScanner.next();
            while(!nextInstructor.contains("\"")){
                instructors.add(nextInstructor.trim());
                nextInstructor = lineScanner.next();
            }
            instructors.add(nextInstructor.replace("\"", "").trim());
        } else {
            instructors.add(instructorLine.trim());
        }
        String componentCode = lineScanner.next().trim();
        ClassComponent newClassComponent = new SFUCourseComponent(enrollmentCapacity, enrollmentTotal,
                instructors, subject, catalogNumber, location, semesterInt, componentCode);
        addComponent(newClassComponent);
    }

    // Adds component to a department's course component list. If the
    // department isn't found, then it creates a new department and
    // adds that course component to the new department.
    private void addComponent(ClassComponent newComponent){
        String departmentName = newComponent.getDepartmentName();
        Department department = allDepartmentsAtSFU.get(hashingFunction(departmentName));
        if (department == null) {
            department = new SFUDepartment(departmentName);
            allDepartmentsAtSFU.put(hashingFunction(departmentName), department);
        }
        department.addNewComponent(newComponent);
    }

    private double hashingFunction(String key){
        double hashCode = 0;
        int constant = 33;
        for(int i = 0; i < key.length(); i++){
            hashCode += key.charAt(i)*Math.pow(constant, i);
        }
        return hashCode;
    }

    private void createNewDepartment(String departmentName, ClassComponent newComponent){
        Department newSFUDepartment = new SFUDepartment(departmentName);
        newSFUDepartment.addNewComponent(newComponent);
        allDepartmentsAtSFU.put(hashingFunction(departmentName), newSFUDepartment);
    }

    @Override
    public void printCSVFile() {
        System.out.println(allDepartmentsAtSFU.size());
        for (Map.Entry<Double, Department> entry : allDepartmentsAtSFU.entrySet()) {
            Department department = entry.getValue();
            System.out.println("Department: " + department.getName());
            department.printAllCourseOfferings();
        }
    }

    @Override
    public Department getDepartment(double departmentID){
        return allDepartmentsAtSFU.get(departmentID);
    }

    // TODO: implement getAllDepartments
    @Override
    public Iterator<? extends Department> getAllDepartments() {
        List<Department> departments = new ArrayList<>();
        for(Map.Entry<Double, Department> entry : allDepartmentsAtSFU.entrySet()){
            departments.add(entry.getValue());
        }
        return departments.iterator();
    }
}
