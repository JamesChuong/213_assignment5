package a5_backend.Model.CourseInterfaces;

import java.util.Iterator;

public interface Section {

    int getSemester();

    String getLocation();

    int getTotalLecEnrollment();

    int getTotalLabEnrollment();

    int getTotalEnrollmentCapacity();

    int getTotalLabEnrollmentCapacity();

    void addNewComponent(ClassComponent newComponent);

    void printAllComponents();

    Iterator<? extends ClassComponent> getAllComponents();

}
