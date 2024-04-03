package a5_backend.Model.SFUCourseAttributes;

import a5_backend.Model.CourseInterfaces.ClassComponent;
import a5_backend.Model.CourseInterfaces.Section;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CourseSection implements Section, Comparator<CourseSection> {

    private final List<ClassComponent> componentList = new ArrayList<>();

    public String department;
    public String catalogNumber;
    public int semester;
    public String location;
    public String instructor;

    public CourseSection(){}

    public static CourseSection CreateNewSectionWithComponent(ClassComponent newComponent){
        CourseSection newSection = new CourseSection();
        newSection.department = newComponent.getDepartmentName();
        newSection.catalogNumber = newComponent.getCatalogNumber();
        newSection.semester = newComponent.getSemester();
        newSection.location = newComponent.getLocation();
        newSection.instructor = newComponent.getInstructor();
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
        componentList.add(newComponent);
    }

    @Override
    public void printAllComponents() {
        for(ClassComponent newComponent: componentList){
            String componentTitle = String.format("%d in %s by %s", newComponent.getSemester()
                    , newComponent.getLocation(), newComponent.getInstructor());
            String enrollmentTotals = String.format( "Type=%s, Enrollment=%d/%d", newComponent.getComponentCode()
                    , newComponent.getEnrollmentTotal(), newComponent.getCapacity() );
            int padding = (componentTitle.length()-enrollmentTotals.length())/2;    //Padding around the text containing the type of component and capacity
            System.out.println(componentTitle);
            System.out.println(String.format("%"+ padding +"s", enrollmentTotals));
        }
    }

    @Override
    public int compare(CourseSection o1, CourseSection o2) {

        if(o1.getLocation() == "BURNABY" && o2.getLocation() == "SURREY"){
            return -1;
        } else if ( o2.getLocation().equals( o1.getLocation() ) ){
            if(o2.semester == o1.semester){
                return o2.instructor.compareTo(o1.instructor);
            } else {
                return o2.semester - o1.semester;
            }
        } else {
            return 1;
        }

    }


}
