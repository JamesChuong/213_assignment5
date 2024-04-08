package a5_backend.Model.SFUCourseAttributes;

import a5_backend.Model.CourseInterfaces.ClassComponent;
import a5_backend.Model.CourseInterfaces.Course;
import a5_backend.Model.CourseInterfaces.Department;
import a5_backend.Model.CourseInterfaces.Section;

import java.lang.Math;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class SFUDepartment implements Department<SFUCourse> {
    private final String departmentName;

    //Maps a course to its course ID, course IDs are assigned via a hashing function
    private final HashMap<Long, Course> courseList = new HashMap<>();

    private Course findCourse(long courseID){
        Course retreivedCourse = courseList.get(courseID);
        if(retreivedCourse == null){
            throw new RuntimeException("Error: Course not found");
        }
        return retreivedCourse;
    }

    public SFUDepartment(String departmentName) {
        this.departmentName = departmentName;
    }

    @Override
    public void addNewCourse(Course newCourse) {

    }

    @Override
    public void addNewComponent(ClassComponent newComponent) {
        long newCourseID = hashingFunction(newComponent.getCatalogNumber());
        boolean sectionIsFound = courseList.containsKey(newCourseID);
        if(sectionIsFound){
            courseList.get(newCourseID)
                    .addNewComponent(newComponent);
        } else {
            SFUCourse newCourse = SFUCourse.createCourseWithComponent(newComponent, newCourseID);
            courseList.put(newCourseID, newCourse);
        }
    }

    @Override
    public SFUCourse getCourseOffering(long courseID, long courseOfferingID) {
        return null;
    }


    private Long hashingFunction(String key){
        double hashCode = 0;
        int constant = 33;
        for(int i = 0; i < key.length(); i++){
            hashCode += key.charAt(i)*Math.pow(constant, i);
        }
        return (long) hashCode;
    }

    @Override
    public void printAllCourseOfferings() {
        Set<Long> allCourseIDs = courseList.keySet();
        for(Long currentCourse: allCourseIDs){
            courseList.get(currentCourse)
                    .printSections();
        }
    }

    @Override
    public String getName() {
        return this.departmentName;
    }


    //TODO: Implement this method
    @Override
    public Iterator<? extends Section> getAllCourseOfferings(long courseID) {
        Course retreivedCourse = findCourse(courseID);
        return retreivedCourse.getAllCourseOfferings();
    }

    @Override
    public Iterator<? extends Course> getAllCourses() {
        return courseList.values().iterator();
    }

    @Override
    public Iterator<? extends ClassComponent> getAllCourseOfferingSections(long courseID, long courseOfferingID) {
        Course retreivedCourse = findCourse(courseID);
        Iterator<? extends ClassComponent> allCourseOfferings = retreivedCourse.getCourseOfferingComponents(courseOfferingID);
        return allCourseOfferings;
    }
}
