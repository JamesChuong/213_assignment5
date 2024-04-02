package a5_backend.Model;

import a5_backend.Model.CourseAttributes.Component;
import a5_backend.Model.CourseAttributes.Section;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CourseSection implements Section, Comparator<CourseSection> {

    private final List<Component> componentList = new ArrayList<>();
    public String department;
    public int courseNumber;
    public String semester;
    public String location;
    public String instructor;

    public CourseSection(){}

    public static CourseSection CreateNewSectionWithComponent(Component newComponent){
        CourseSection newSection = new CourseSection();
        newSection.department = newComponent.getDepartmentName();
        newSection.courseNumber = newComponent.getCatalogNumber();
        newSection.semester = newComponent.getSemester();
        newSection.location = newComponent.getLocation();
        newSection.instructor = newComponent.getInstructor();
        return newSection;
    }

    @Override
    public String getSemester() {
        return semester;
    }

    @Override
    public String getLocation() {
        return location;
    }

    @Override
    public int parseSemester() {
        return 0;
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
                .mapToInt(Component::getEnrollmentTotal)
                .sum();
    }

    @Override
    public int getTotalEnrollmentCapacity() {
        return componentList.getFirst()
                .getCapacity();
    }

    @Override
    public void addNewComponent(Component newComponent) {
        componentList.add(newComponent);
    }

    //TODO: Implement this method
    @Override
    public void printAllComponents() {

    }

    @Override
    public int compare(CourseSection o1, CourseSection o2) {

        if(o1.getLocation() == "BURNABY" && o2.getLocation() == "SURREY"){
            return -1;
        } else if (o1.getLocation() == o2.getLocation()){
            return o1.parseSemester() - o2.parseSemester();
        } else {
            return 1;
        }

    }

}
