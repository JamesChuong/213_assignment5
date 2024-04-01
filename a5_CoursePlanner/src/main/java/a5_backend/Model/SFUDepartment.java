package a5_backend.Model;

import a5_backend.Model.CourseAttributes.Component;
import a5_backend.Model.CourseAttributes.Course;
import a5_backend.Model.CourseAttributes.Department;

import java.util.HashMap;

public class SFUDepartment implements Department<SFUCourse> {

    //Maps a course to its catalog number
    private HashMap<Integer, Course> courseList;

    @Override
    public void addNewCourse(Course newCourse) {

    }

    @Override
    public void addNewComponent(Component newComponent) {
        boolean sectionIsFound = courseList.containsKey(newComponent.getCatalogNumber());
        if(sectionIsFound){
            courseList.get(newComponent.getCatalogNumber())
                    .addNewComponent(newComponent);
        } else {
            SFUCourse newCourse = SFUCourse.createCourseWithComponent(newComponent);
            courseList.put(newCourse.getCatalogNumber(), newCourse);
        }
    }

    //TODO: Implement method for printing to terminal
    @Override
    public void printAllCourseOfferings() {

    }

    // THESE 3 METHODS ARE NOT IMPORTANT FOR PART 1 (I think)
    @Override
    public SFUCourse getCourseOfferings(int courseID, int courseOfferingID) {
        return null;
    }

    @Override
    public void listAllCourseOfferings(int courseID) {

    }

    @Override
    public void listAllCourseSections(int courseID, int courseOfferingID) {

    }


}
