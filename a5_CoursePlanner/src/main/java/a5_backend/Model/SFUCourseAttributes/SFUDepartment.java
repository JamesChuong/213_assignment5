package a5_backend.Model.SFUCourseAttributes;

import a5_backend.Model.CourseInterfaces.ClassComponent;
import a5_backend.Model.CourseInterfaces.Course;
import a5_backend.Model.CourseInterfaces.Department;

import java.util.HashMap;
import java.util.Set;

public class SFUDepartment implements Department<SFUCourse> {
    private final String departmentName;

    //Maps a course to its catalog number
    private final HashMap<String, Course> courseList = new HashMap<>();

    public SFUDepartment(String departmentName) {
        this.departmentName = departmentName;
    }

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

    @Override
    public void printAllCourseOfferings() {
        Set<String> allCourses = courseList.keySet();
        for(String currentCourse: allCourses){
            courseList.get(currentCourse)
                    .printSections();
        }
    }

    @Override
    public String getName() {
        return this.departmentName;
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
