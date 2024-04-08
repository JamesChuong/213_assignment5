package a5_backend.Model.CourseInterfaces;
import java.util.Iterator;
/**
 * This interface represents a department at SFU, and contains operations for adding new courses or components,
 * listing all offerings of a course which is part of the department, and listing all sections
 *
 * @param <T> An object which implements the Course interface
 */
public interface Department<T extends Course> {

    void addNewCourse(Course newCourse);

    void addNewComponent(ClassComponent newComponent);

    //Return a course with the ID and offering ID
    T getCourseOfferings(long courseID, long courseOfferingID);

    Iterator<? extends Course> getAllCourses();

    Iterator<? extends ClassComponent> getAllCourseOfferings(long courseID, long courseOfferingID);

    void printAllCourseOfferings();

    String getName();
}
