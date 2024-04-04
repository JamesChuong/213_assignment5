package a5_backend.Model.SFUCourseAttributes;

import a5_backend.Model.CourseInterfaces.ClassComponent;
import a5_backend.Model.CourseInterfaces.Section;
import java.lang.Math;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CourseSection implements Section, Comparator<CourseSection> {

    private final List<ClassComponent> componentList = new ArrayList<>();
    private boolean hasOtherComponents = false; //True if a class has other components aside from lectures, like labs, tutorials, etc.
    public String department;
    public String catalogNumber;
    public int semester;
    public String location;
    public List<String> instructors;

    public CourseSection(){}

    public static CourseSection CreateNewSectionWithComponent(ClassComponent newComponent){
        CourseSection newSection = new CourseSection();
        newSection.department = newComponent.getDepartmentName();
        newSection.catalogNumber = newComponent.getCatalogNumber();
        newSection.semester = newComponent.getSemester();
        newSection.location = newComponent.getLocation();
        newSection.instructors = newComponent.getInstructors();
        newSection.addNewComponent(newComponent);
        return newSection;
    }

    @Override
    public int getSemester() {
        return semester;
    }

    @Override
    public String getLocation() {
        return location;
    }

    @Override
    public int getTotalLecEnrollment() {
        return componentList.getFirst()
                .getEnrollmentTotal();
    }

    @Override
    public int getTotalLabEnrollment() {
        return componentList.stream()
                .filter(component -> !component.getComponentCode().equals("LEC") )
                .mapToInt(ClassComponent::getEnrollmentTotal)
                .sum();
    }

    @Override
    public int getTotalEnrollmentCapacity() {
        return componentList.getFirst()
                .getCapacity();
    }

    @Override
    public void addNewComponent(ClassComponent newComponent) {
        if(!newComponent.getComponentCode().equals("LEC")){
            hasOtherComponents = true;
            componentList.addLast(newComponent);
        } else {
            componentList.addFirst(newComponent);
        }
    }

    @Override
    public void printAllComponents() {
        String componentTitle = String.format("%d in %s by %s", semester
                , location, componentList.getFirst().getInstructorsAsString());
        System.out.println(componentTitle);
        String lecEnrollmentTotals = String.format( "Type=LEC, Enrollment=%d/%d"
                ,getTotalLecEnrollment(), getTotalEnrollmentCapacity());
        //int padding = Math.abs((componentTitle.length()-lecEnrollmentTotals.length())/2);    //Padding around the text containing the type of component and capacity
            System.out.println(String.format("%5s%s", " ", lecEnrollmentTotals));

        if(hasOtherComponents){
            String otherEnrollmentTotals = String.format("Type=%s, Enrollment=%d/%d"
                    , componentList.getLast().getComponentCode(), getTotalLabEnrollment(), getTotalEnrollmentCapacity());
            //padding = Math.abs((componentTitle.length()-otherEnrollmentTotals.length())/2);    //Padding around the text containing the type of component and capacity
            System.out.println(String.format("%5s%s", " ", otherEnrollmentTotals));
        }
    }

    @Override
    public int compare(CourseSection o1, CourseSection o2) {

        if ( o1.getLocation().equals( o2.getLocation() ) ){
            if(o1.semester == o2.semester){
                return o1.instructors.getFirst().compareTo(o2.instructors.getFirst());
            } else {
                return o1.semester - o2.semester;
            }
        } else {
            return o1.location.compareTo(o2.location);
        }
    }

}
