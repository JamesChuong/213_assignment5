package a5_backend.Model.SFUCourseAttributes;

import a5_backend.Model.CourseInterfaces.ClassComponent;
import a5_backend.Model.CourseInterfaces.Department;
import a5_backend.Model.CourseInterfaces.DepartmentList;
import java.lang.Math;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class SFUDepartmentList implements DepartmentList {

    // The first 14 entries of the old allDepartmentsAtSFU since it fills it 24 times
    private final List<Double> hashValuesList = new ArrayList<>();

    //A hashmap is used to store each department, each department is mapped to its name (CMPT, ENSC, MATH, STAT, etc.)
    private final HashMap<Double, Department<SFUCourse>> allDepartmentsAtSFU = new HashMap<>();
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
        Double hashValue = hashingFunction(departmentName);
        Department<SFUCourse> department;
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

    /*
    private void createNewDepartment(String departmentName, ClassComponent newComponent){
        Department<SFUCourse> newSFUDepartment = new SFUDepartment(departmentName);
        newSFUDepartment.addNewComponent(newComponent);
        allDepartmentsAtSFU.put(hashingFunction(departmentName), newSFUDepartment);
    }
     */

    @Override
    public void printCSVFile() {
        for (Map.Entry<Double, Department<SFUCourse>> entry : allDepartmentsAtSFU.entrySet()) {
            Department<SFUCourse> department = entry.getValue();
            department.printAllCourseOfferings();
        }
    }

    @Override
    public Department<SFUCourse> getDepartment(double departmentID){
        /*
        Department<SFUCourse> retreivedDepartment = allDepartmentsAtSFU.get(departmentID);
        System.out.println(allDepartmentsAtSFU.get(departmentID));
        System.out.println(departmentID);
        /* SFUDepartmentList shouldn't throw exceptions, SFUDepartmentService should
        if(retreivedDepartment == null){
            throw new RuntimeException("Error: Department not found");

        return retreivedDepartment;
        */

        Double key = departmentID;
        Department<SFUCourse> retrievedDepartment = null;
        for(Double hashValue : hashValuesList) {
            //System.out.println("The current hashvalue is: " + allDepartmentsAtSFU.get(hashValue).hashCode());
            if (allDepartmentsAtSFU.get(hashValue).hashCode() == key.intValue()) {
                //System.out.println("true: " + allDepartmentsAtSFU.get(hashValue).hashCode() + " " + key);
                retrievedDepartment = allDepartmentsAtSFU.get(hashValue);
            }
        }
        //System.out.println("Retrieved Department: " + retrievedDepartment);
        //System.out.println("Department ID: " + departmentID);
        //System.out.println("key: " + key.intValue());
        return retrievedDepartment;

    }

    @Override
    public Iterator<? extends Department<SFUCourse>> getAllDepartments() {
        List<Department<SFUCourse>> departments = new ArrayList<>(allDepartmentsAtSFU.values());
        Comparator<Department<SFUCourse>> comparator = Comparator.comparing(Department::getName);
        departments.sort(comparator);

        return departments.iterator();
    }
}
