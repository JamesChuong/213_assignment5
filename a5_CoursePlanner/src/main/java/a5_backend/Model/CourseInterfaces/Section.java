package a5_backend.Model.CourseInterfaces;

import java.util.Iterator;
import java.util.List;

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
