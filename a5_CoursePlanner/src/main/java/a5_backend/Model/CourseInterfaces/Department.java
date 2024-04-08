package a5_backend.Model.CourseInterfaces;

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
    T getCourseOfferings(int courseID, int courseOfferingID);

    void listAllCourseOfferings(int courseID);

    void listAllCourseSections(int courseID, int courseOfferingID);

    void printAllCourseOfferings();

}
