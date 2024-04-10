package a5_backend.Model.CourseInterfaces;
import a5_backend.Watchers.CourseObserver;

import java.util.Iterator;

public interface Course {

    void addNewComponent(ClassComponent newComponent);

    String getDepartmentname();

    void printSections();

    String getCatalogNumber();

    long getCourseID();

    Iterator<? extends ClassComponent> getCourseOfferingComponents(long courseOfferingID);

    Iterator<? extends Section> getAllCourseOfferings();

    void addObserver(CourseObserver newObserver);

}
