package a5_backend.Model.SFUCourseAttributes;

import a5_backend.Model.CourseInterfaces.ClassComponent;

import java.util.ArrayList;
import java.util.List;

/**
 * The SFUCourseComponent class represents a component for a class, this could be a lecture, tutorial,
 * lab, seminar, etc. It implements the ClassComponent interface and implements the methods and contains
 * all information about a course offering, plus the type of component it is.
 */
public class SFUCourseComponent implements ClassComponent {

    private final int capacity;
    private final int totalEnrollmentCount;
    private final List<String> instructors;
    private final String departmentName;
    private final String catalogNumber;
    private final String location;
    private final int semester;
    private final String componentCode;     //Could be a lecture, lab, tutorial, etc.


    public SFUCourseComponent(int capacity, int totalEnrollmentCount, List<String> instructors,  String departmentName
            , String catalogNumber,  String location, int semester, String componentType){
        this.capacity = capacity;
        this.totalEnrollmentCount = totalEnrollmentCount;
        this.instructors = instructors;


        this.departmentName = departmentName;
        this.catalogNumber = catalogNumber;
        this.location = location;
        this.semester = semester;
        this.componentCode = componentType;
    }

    @Override
    public int getCapacity() {return capacity;}
    @Override
    public int getEnrollmentTotal() {return totalEnrollmentCount;}
    @Override
    public String getDepartmentName() {return departmentName;}
    @Override
    public String getCatalogNumber() {return catalogNumber;}
    @Override
    public List<String> getInstructors() {return instructors;}
    @Override
    public String getLocation() {return location;}
    @Override
    public int getSemester() {return semester;}
    @Override
    public String getComponentCode() {return componentCode;}

}
