package a5_backend.Model.CourseInterfaces;

import java.util.Iterator;
import java.util.List;

/**
 * The Section interface represents an offering of a course, it supports
 * operations for retrieving information about the offering, like semester,
 * location, capacity, enrollment count for lectures and labs, instructors,
 * etc. There are also operations for adding new components to an offering and
 * printing the contents of the offering.
 */
public interface Section {

    int getSemester();

    String getLocation();

    int getTotalLecEnrollment();

    int getTotalLabEnrollment();

    int getTotalLecEnrollmentCapacity();

    int getTotalLabEnrollmentCapacity();

    long getCourseOfferingID();

    void addNewComponent(ClassComponent newComponent);

    void printAllComponents();

    String getInstructors();

    String getTerm();

    int getYear();

    Iterator<? extends ClassComponent> getAllComponents();

}
