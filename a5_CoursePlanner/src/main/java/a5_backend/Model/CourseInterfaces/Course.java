package a5_backend.Model.CourseInterfaces;
import a5_backend.Model.Watchers.WatcherInterfaces.Observer;

import java.util.Iterator;

/**
 * The Course interface represents any course that could be offered at any school,
 * exact details are left to the programmer. This interface supports operations
 * for retrieving information about a course, this being its department, catalog
 * number, and ID. It also has operations to return a collection of course attributes,
 * this could be the offerings of that course, or the components that make up an offering.
 * There are also operations to print information or add observers
 */
public interface Course {

    void addNewComponent(ClassComponent newComponent);

    String getDepartmentname();

    void printSections();

    String getCatalogNumber();

    long getCourseID();

    Iterator<? extends ClassComponent> getCourseOfferingComponents(long courseOfferingID);

    Iterator<? extends Section> getAllCourseOfferings();

    void addObserver(Observer newObserver);

}
