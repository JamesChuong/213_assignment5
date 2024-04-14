package a5_backend.Model.SFUCourseAttributes;

import a5_backend.DTOs.ApiOfferingDataDTO;
import a5_backend.Model.CourseInterfaces.ClassComponent;
import a5_backend.Model.CourseInterfaces.Department;
import a5_backend.Model.CourseInterfaces.DepartmentList;
import a5_backend.Watchers.WatcherInterfaces.Observer;

import java.lang.Math;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class SFUDepartmentList implements DepartmentList {

    // The first 14 entries of the old allDepartmentsAtSFU since it fills it 24 times
    private final List<Double> hashValuesList = new ArrayList<>();

    //A hashmap is used to store each department, each department is mapped to its name (CMPT, ENSC, MATH, STAT, etc.)
    private final HashMap<Double, Department> allDepartmentsAtSFU = new HashMap<>();
    private final int HASH_CONSTANT = 33;

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
                //and add it to the course found in the hashmap with the department ID
                parseLine(CSVReader.nextLine());
            }
            CSVReader.close();
        } catch (FileNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    private void parseLine(String CSVLine){
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

    @Override
    public void parseApiOfferingDataDTO(ApiOfferingDataDTO dto){
        String semester = dto.semester;
        int semesterInt = Integer.parseInt(semester);
        String subject = dto.subjectName;
        String catalogNumber = dto.catalogNumber;
        String location = dto.location;
        int enrollmentCapacity = dto.enrollmentCap;
        int enrollmentTotal = dto.enrollmentTotal;
        List<String> instructors = new ArrayList<>();
        String instructorLine = dto.instructor;
        if (instructorLine.equals("<null>")) {
            instructors.add("");
        } else {
            String[] parts = instructorLine.split(",");
            for (String part : parts) {
                instructors.add(part.trim().replaceAll("\"", ""));
            }
        }
        String componentCode = dto.component;
        ClassComponent newClassComponent = new SFUCourseComponent(enrollmentCapacity, enrollmentTotal,
                instructors, subject, catalogNumber, location, semesterInt, componentCode);
        addComponent(newClassComponent);
    }

    // Adds component to a department's course component list. If the
    // department isn't found, then it creates a new department and
    // adds that course component to the new department.
    private void addComponent(ClassComponent newComponent){
        String departmentName = newComponent.getDepartmentName();
        Double hashValue = hashingFunction(departmentName);
        Department department;
        if (!allDepartmentsAtSFU.containsKey(hashValue)) {
            department = new SFUDepartment(departmentName);
            allDepartmentsAtSFU.put(hashValue, department);
            hashValuesList.add(hashValue);
            department.addNewComponent(newComponent);
            department.setHashValue(hashValue);
            return;
        }
        department = allDepartmentsAtSFU.get(hashValue);
        department.addNewComponent(newComponent);
        department.setHashValue(hashValue);
    }

    //Maps department names to a unique key using a polynomial hashing technique
    //Keys are created via c0*z^0 + c1*z^1 + ..+ cK*z^k +.. c(N-1)*z^(N-1), where
    //cK is the integer representation of the kth letter in the name and z is a
    //constant raised to the kth power
    private double hashingFunction(String key){
        double hashCode = 0;
        for(int i = 0; i < key.length(); i++){
            hashCode += key.charAt(i)*Math.pow(HASH_CONSTANT, i);
        }
        return hashCode;
    }

    @Override
    public void printCSVFile() {
        for (Map.Entry<Double, Department> entry : allDepartmentsAtSFU.entrySet()) {
            Department department = entry.getValue();
            department.printAllCourseOfferings();
        }
    }

    @Override
    public Department getDepartment(double departmentID){
        Double key = departmentID;
        Department retrievedDepartment = null;
        for(Double hashValue : hashValuesList) {
            if (allDepartmentsAtSFU.get(hashValue).hashCode() == key.intValue()) {
                retrievedDepartment = allDepartmentsAtSFU.get(hashValue);
                return retrievedDepartment;
            }
        }
        throw new RuntimeException("Error: Department not found");


    }

    @Override
    public Iterator<? extends Department> getAllDepartments() {
        List<Department> departments = new ArrayList<>(allDepartmentsAtSFU.values());
        Comparator<Department> comparator = Comparator.comparing(Department::getName);
        departments.sort(comparator);
        return departments.iterator();
    }

    @Override
    public void addNewObserver(long departmentID, long courseID, Observer newObserver) {
        Department retreivedDepartment = getDepartment(departmentID);
        retreivedDepartment.findCourse(courseID)
                .addObserver(newObserver);
    }
}
