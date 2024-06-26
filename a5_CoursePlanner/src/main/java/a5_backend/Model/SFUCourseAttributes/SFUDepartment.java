package a5_backend.Model.SFUCourseAttributes;

import a5_backend.Model.CourseInterfaces.ClassComponent;
import a5_backend.Model.CourseInterfaces.Course;
import a5_backend.Model.CourseInterfaces.Department;
import a5_backend.Model.CourseInterfaces.Section;
import a5_backend.Model.Watchers.WatcherInterfaces.Observer;

import java.lang.Math;
import java.util.*;

/**
 * The SFUDepartment class represents a department as SFU, it implements
 * the Department interface. It contains the name of the department and
 * the courses belonging to that department.
 */
public class SFUDepartment implements Department {
    private final String departmentName;

    //Maps a course to its course ID, course IDs are assigned via a hashing function
    private final HashMap<Long, Course> courseList = new HashMap<>();
    private double hashValue;


    public SFUDepartment(String departmentName) {
        this.departmentName = departmentName;
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

    private Long hashingFunction(String key){
        double hashCode = 0;
        int constant = 33;
        for(int i = 0; i < key.length(); i++){
            hashCode += key.charAt(i)*Math.pow(constant, i);
        }
        return (long) hashCode;
    }

    @Override
    public Course findCourse(long courseID){
        if(courseList.containsKey(courseID)){
            Course retreivedCourse = courseList.get(courseID);
            return retreivedCourse;
        } else {
            throw new RuntimeException("Error: Course not found");
        }
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
    public String getDepartmentName() {
        return this.departmentName;
    }

    @Override
    public void addCourseObserver(long courseID, Observer newObserver) {
            courseList.get(courseID);
    }

    @Override
    public void setHashValue(double hashValue) {
        this.hashValue = hashValue;
    }

    //TODO: Implement this method
    @Override
    public Iterator<? extends Section> getAllCourseOfferings(long courseID) {
        Course retreivedCourse = findCourse(courseID);
        return retreivedCourse.getAllCourseOfferings();
    }

    @Override
    public Iterator<? extends Course> getAllCourses() {
        List<Course> sortedCourses = new ArrayList<>(courseList.values());
        Comparator<Course> comparator = Comparator.comparing(Course::getCatalogNumber);
        sortedCourses.sort(comparator);
        return sortedCourses.iterator();
    }

    @Override
    public Iterator<? extends ClassComponent> getAllCourseOfferingSections(long courseID, long courseOfferingID) {
        Course retreivedCourse = findCourse(courseID);

        Iterator<? extends ClassComponent> allCourseOfferings = retreivedCourse.getCourseOfferingComponents(courseOfferingID);
        return allCourseOfferings;
    }
}
