package a5_backend.Model.SFUCourseAttributes;

import a5_backend.Model.CourseInterfaces.ClassComponent;

public class SFUCourseComponent implements ClassComponent {

    private final int capacity;
    private final int totalEnrollmentCount;
    private final String instructor;
    private final String departmentName;
    private final String catalogNumber;
    private final String location;
    private final int semester;
    private final String componentCode;     //Could be a lecture, lab, tutorial, etc.

    public SFUCourseComponent(int capacity, int totalEnrollmentCount, String instructor,  String departmentName
            , String catalogNumber,  String location, int semester, String componentType){
        this.capacity = capacity;
        this.totalEnrollmentCount = totalEnrollmentCount;
        this.instructor = instructor;
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
    public String getInstructor() {return instructor;}
    @Override
    public String getLocation() {return location;}
    @Override
    public int getSemester() {return semester;}
    @Override
    public String getComponentCode() {return componentCode;}
}
