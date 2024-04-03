package a5_backend.Model.SFUCourseAttributes;

import a5_backend.Model.CourseInterfaces.ClassComponent;
import a5_backend.Model.CourseInterfaces.Course;
import a5_backend.Model.CourseInterfaces.Department;
import a5_backend.Model.SFUCourseAttributes.SFUCourse;

import java.util.HashMap;

public class SFUDepartment implements Department<SFUCourse> {

    //Maps a course to its catalog number
    private HashMap<String, Course> courseList;

    @Override
    public void addNewCourse(Course newCourse) {


    }

    @Override
    public void addNewComponent(ClassComponent newComponent) {
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
