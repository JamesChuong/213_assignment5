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

    //TODO: Implement this method
    @Override
    public void printAllComponents() {
        for(ClassComponent currentComponent: componentList){

        }
    }

    @Override
    public int compare(CourseSection o1, CourseSection o2) {

        if ( o1.location.equals(o2.location) ){
            return o2.semester - o1.semester;
        } else {
            return o2.location.compareTo(o1.location);
        }

    }


}
