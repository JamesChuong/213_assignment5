package a5_backend.Model.SFUCourseAttributes;

import a5_backend.Model.CourseInterfaces.ClassComponent;
import a5_backend.Model.CourseInterfaces.Course;

import java.util.ArrayList;
import java.util.List;

public class SFUCourse implements Course {

    private final String departmentName;

    private final String catalogNumber;

    private final List<CourseSection> courseSections = new ArrayList<>(){};

    public SFUCourse(String departmentName, String catalogNumber){
        this.departmentName = departmentName;
        this.catalogNumber =  catalogNumber;
    }

    public static SFUCourse createCourseWithComponent(ClassComponent newComponent){
        SFUCourse newCourse = new SFUCourse(newComponent.getDepartmentName(), newComponent.getCatalogNumber());
        newCourse.addNewComponent(newComponent);
        return newCourse;
    }

    @Override
    public void addNewComponent(ClassComponent newComponent) {
        //Check if the component belongs to any section stored
        boolean componentIsPartOfSection = courseSections.stream()
                .anyMatch( section -> section.semester == newComponent.getSemester()
                        && section.location.equals(newComponent.getLocation())
                        && section.instructors.equals(newComponent.getInstructors()) );
        if(componentIsPartOfSection){
            courseSections.stream()
                    .filter( section -> section.semester == newComponent.getSemester()
                            && section.location.equals(newComponent.getLocation())
                            && section.instructors.equals(newComponent.getInstructors()) )
                    .forEach(section -> section.addNewComponent(newComponent));
        } else{     //If the component doesn't belong to any section stored, create a new one and add it
            courseSections.add(CourseSection.CreateNewSectionWithComponent(newComponent));
            courseSections.sort(new CourseSection());
        }
    }

    @Override
    public String getDepartmentName() {
        return departmentName;
    }

    @Override
    public void printSections() {
        System.out.println(departmentName + " " + catalogNumber);
        for(CourseSection currentSection: courseSections){
            currentSection.printAllComponents();
        }
    }

    @Override
    public String getCatalogNumber() {
        return catalogNumber;
    }
}
