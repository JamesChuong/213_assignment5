package a5_backend.Model;

import a5_backend.Model.CourseAttributes.Component;
import a5_backend.Model.CourseAttributes.Course;

import java.util.ArrayList;
import java.util.List;

public class SFUCourse implements Course {

    private final String departmentName;

    private final int catalogNumber;

    private final List<CourseSection> courseSections = new ArrayList<>(){};

    public SFUCourse(String departmentName, int catalogNumber){
        this.departmentName = departmentName;
        this.catalogNumber =  catalogNumber;
    }

    public static SFUCourse createCourseWithComponent(Component newComponent){
        SFUCourse newCourse = new SFUCourse(newComponent.getDepartmentName(), newComponent.getCatalogNumber());
        newCourse.addNewComponent(newComponent);
        return newCourse;
    }

    @Override
    public void addNewComponent(Component newComponent) {
        //Check if the component belongs to any section stored
        boolean componentIsPartOfSection = courseSections.stream()
                .anyMatch(section -> section.semester.equals(newComponent.getSemester())
                        && section.location.equals(newComponent.getLocation())
                        && section.instructor.equals(newComponent.getInstructor()));
        if(componentIsPartOfSection){
            courseSections.stream()
                    .filter(section -> section.semester.equals(newComponent.getSemester())
                            && section.semester.equals(newComponent.getLocation())
                            && section.instructor.equals(newComponent.getInstructor()))
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
    public int getCatalogNumber() {
        return catalogNumber;
    }
}
