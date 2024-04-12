package a5_backend.Model.CourseInterfaces;
import a5_backend.Watchers.Observer;

import java.util.Iterator;
/**
 * This interface represents a department at SFU, and contains operations for adding new courses or components,
 * listing all offerings of a course which is part of the department, and listing all sections
 *
 */
public interface Department {

    void addNewCourse(Course newCourse);

    void addNewComponent(ClassComponent newComponent);

    //Return a course with the ID and offering ID
    Course findCourse(long courseID);

    Iterator<? extends Section> getAllCourseOfferings(long courseID);
    Iterator<? extends Course> getAllCourses();

    //Get all offering a section has for a particular offering of a course
    Iterator<? extends ClassComponent> getAllCourseOfferingSections(long courseID, long courseOfferingID);

    void printAllCourseOfferings();

    String getName();

    void addCourseObserver(long courseID, Observer newObserver);

    void setHashValue(double hashValue);
}
