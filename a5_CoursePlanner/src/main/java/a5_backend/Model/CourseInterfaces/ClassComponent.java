package a5_backend.Model.CourseInterfaces;

import java.util.List;

/**
 * The ClassComponent interface represents a component for any course, what type of
 * component is left to the object implementing it. It supports operations for
 * retrieving information about a course offering, again, the specifics of what
 * the information is like exactly is left to the programmer.
 */
public interface ClassComponent {

    int getCapacity();

    int getEnrollmentTotal();

    String getDepartmentName();

    String getCatalogNumber();

    List<String> getInstructors();

    String getLocation();

    int getSemester();

    String getComponentCode();
}
