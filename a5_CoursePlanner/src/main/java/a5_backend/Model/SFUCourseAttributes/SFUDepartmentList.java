package a5_backend.Model.SFUCourseAttributes;

import a5_backend.Model.CourseInterfaces.ClassComponent;
import a5_backend.Model.CourseInterfaces.Department;
import a5_backend.Model.DepartmentList;
import a5_backend.Model.SFUCourseAttributes.SFUDepartment;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class SFUDepartmentList implements DepartmentList {

    //A hashmap is used to store each department, each department is mapped to its name (CMPT, ENSC, MATH, STAT, etc.)
    private HashMap<String, Department> allDepartmentsAtSFU = new HashMap<>();
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
                /*
                int enrollmentCapacity = CSVReader.nextInt();
                int enrollmentTotal = CSVReader.nextInt();
                String instructors = CSVReader.next().trim();
            Scanner CSVReader = new Scanner(new File(CSVFile));
            if (CSVReader.hasNextLine()) { CSVReader.nextLine(); } // Skip the header line in the CSV file


            CSVReader.useDelimiter(",");
            while(CSVReader.hasNext()){
                //For each line in the file, create an object which implements the Component interface
                //and add it to the course found in the hashmap with the department name

                String semester = CSVReader.next().trim();
                String subject = CSVReader.next().trim();
                String catalogNumber = CSVReader.next().trim();
                String location = CSVReader.next().trim();
                String enrollmentCapacity = CSVReader.next().trim();
                String enrollmentTotal = CSVReader.next().trim();
                String instructors = CSVReader.next().trim();
                String componentCode = CSVReader.next().trim();

                if (instructors.equals("<null>")) { instructors = "";}

                if (CSVReader.hasNextLine()) { CSVReader.nextLine(); }

                // Change the semester string into an int and create a SFUCourseComponent object
                SFUCourseComponent newClassComponent = new SFUCourseComponent(enrollmentCapacity, enrollmentTotal,
                        instructors, subject, catalogNumber, location, semester, componentCode);

                addComponent(newClassComponent);
                 */

            }

            CSVReader.close();
        } catch (FileNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    private void parseLine(String CSVLine){
        System.out.println(CSVLine);
        Scanner lineScanner = new Scanner(CSVLine);
        lineScanner.useDelimiter(","); // DELIMITER HERE
        try {
            String semester = lineScanner.next().trim();
            int semesterInt = Integer.parseInt(semester);
            String subject = lineScanner.next().trim();
            String catalogNumber = lineScanner.next().trim();
            String location = lineScanner.next().trim();
            int enrollmentCapacity = lineScanner.nextInt();
            int enrollmentTotal = lineScanner.nextInt();

            String instructorLine = "";
            if (lineScanner.hasNext()) {
                instructorLine = lineScanner.next().trim();
            }
            List<String> instructors = new ArrayList<>();
            if ("<null>".equals(instructorLine)) {
                instructors.add("");
            } else {
                String[] instructorArray = instructorLine.split("\\."); //SPLIT LINE HERE
                for (String instructor : instructorArray) {
                    instructors.add(instructor.trim());
                }
            }
            String componentCode = "";
            if (lineScanner.hasNext()) {
                componentCode = lineScanner.next().trim();
            }

            //System.out.println(semester + " " + subject + " " + catalogNumber + " " + enrollmentTotal + " " + enrollmentCapacity + " " + instructors);

            ClassComponent newClassComponent = new SFUCourseComponent(
                    enrollmentCapacity, enrollmentTotal, instructors, subject,
                    catalogNumber, location, semesterInt, componentCode
            );


            addComponent(newClassComponent);
        } catch (NoSuchElementException | NumberFormatException e) {
            System.err.println("Error parsing line: " + CSVLine);
            e.printStackTrace();
        } finally {
            lineScanner.close();
        }
    }


    // Adds component to a department's course component list. If the
    // department isn't found, then it creates a new department and
    // adds that course component to the new department.
    private void addComponent(ClassComponent newComponent){
        String departmentName = newComponent.getDepartmentName();
        Department department = allDepartmentsAtSFU.get(departmentName);
        if (department == null) {
            department = new SFUDepartment(departmentName);
            allDepartmentsAtSFU.put(departmentName, department);
        }
        department.addNewComponent(newComponent);
    }

    private void createNewDepartment(String departmentName, ClassComponent newComponent){
        Department newSFUDepartment = new SFUDepartment(departmentName);
        newSFUDepartment.addNewComponent(newComponent);
        allDepartmentsAtSFU.put(departmentName, newSFUDepartment);
    }
    @Override
    public void printCSVFile() {
        for (Map.Entry<String, Department> entry : allDepartmentsAtSFU.entrySet()) {
            String departmentName = entry.getKey();
            Department department = entry.getValue();

            System.out.println("Department: " + departmentName);
            department.printAllCourseOfferings();
        }


    }

    public void dumpModel() {


    }

    // TODO: implement getAllDepartments
    public Department[] getAllDepartments() {
        return null;
    }
}
