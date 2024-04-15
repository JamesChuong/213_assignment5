package a5_backend.Model.SFUCourseAttributes;

import a5_backend.DTOs.ApiOfferingDataDTO;
import a5_backend.Model.CourseInterfaces.ClassComponent;
import a5_backend.Model.CourseInterfaces.Department;
import a5_backend.Model.CourseInterfaces.DepartmentList;
import a5_backend.Model.Watchers.WatcherInterfaces.Observer;

import java.lang.Math;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * The SFUDepartmentList class is responsible for containing all departments (which also contain all of their courses)
 * in the model. It supports loading a CSV file, printing out the contents that are currently stored, returning
 * certain information stored (like departments, courses, or sections), and adding information (like components or watchers).
 */
public class SFUDepartmentList implements DepartmentList {

    // The first 14 entries of the old SFUDepartmentList since it fills it 24 times
    private final List<Double> hashValuesList = new ArrayList<>();

    //A hashmap is used to store each department, each department is mapped to its name (CMPT, ENSC, MATH, STAT, etc.)
    private final HashMap<Double, Department> SFUDepartmentList = new HashMap<>();

    //Constant used when mapping departments to a unique key
    private final int HASH_CONSTANT = 33;

    public static SFUDepartmentList createDepartmentListWithCSVFile(String CSVFile){
        SFUDepartmentList newDepartmentList = new SFUDepartmentList();
        newDepartmentList.loadCSVFile(CSVFile);
        return newDepartmentList;
    }

    @Override
    public void loadCSVFile(String CSVFile) {
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

    //How a line in a CSV file is parsed in order to extract important information
    //which is then parsed into a component object and added to the correct department
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
        String instructorLine = lineScanner.next();
        List<String> instructors = parseInstructors(instructorLine, lineScanner);
        String componentCode = lineScanner.next().trim();
        ClassComponent newClassComponent = new SFUCourseComponent(enrollmentCapacity, enrollmentTotal,
                instructors, subject, catalogNumber, location, semesterInt, componentCode);
        addComponent(newClassComponent);
    }

    //Extracts information about instructors from a string
    private List<String> parseInstructors(String instructorLine, Scanner lineScanner) {
        List<String> instructors = new ArrayList<>();
        //If there are no instructors, add an empty string to the component
        if(instructorLine.equals("<null>")){
            instructors.add("");
        //If there are multiple instructors, parse each one
        } else if (instructorLine.contains("\"")){
            instructors.add(instructorLine.trim().replace("\"", ""));
            String nextInstructor = lineScanner.next();
            //Add all instructors in the middle, since we need to remove the quotes on both the first and last instructor listed
            while(!nextInstructor.contains("\"")){
                instructors.add(nextInstructor.trim());
                nextInstructor = lineScanner.next();
            }
            instructors.add(nextInstructor.replace("\"", "").trim());   //Remove the quotes
        //There is only 1 instructor
        } else {
            instructors.add(instructorLine.trim());
        }
        return instructors;
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
        String instructorLine = dto.instructor;

        List<String> instructors = new ArrayList<>();
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
        boolean departmentFound = SFUDepartmentList.containsKey(hashValue);
        if (departmentFound) {
            department = SFUDepartmentList.get(hashValue);
            department.addNewComponent(newComponent);
            department.setHashValue(hashValue);
        } else{
            department = new SFUDepartment(departmentName);
            SFUDepartmentList.put(hashValue, department);
            hashValuesList.add(hashValue);
            department.addNewComponent(newComponent);
            department.setHashValue(hashValue);
        }
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
    public void dumpModel() {
        for (Map.Entry<Double, Department> entry : SFUDepartmentList.entrySet()) {
            Department department = entry.getValue();
            department.printAllCourseOfferings();
        }
    }

    @Override
    public Department getDepartment(double departmentID){
        Double key = departmentID;
        Department retrievedDepartment = null;
        for(Double hashValue : hashValuesList) {
            if (SFUDepartmentList.get(hashValue).hashCode() == key.intValue()) {
                retrievedDepartment = SFUDepartmentList.get(hashValue);
                return retrievedDepartment;
            }
        }
        throw new RuntimeException("Error: Department not found");
    }

    @Override
    public Iterator<? extends Department> getAllDepartments() {
        List<Department> departments = new ArrayList<>(SFUDepartmentList.values());
        Comparator<Department> comparator = Comparator.comparing(Department::getDepartmentName);
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
