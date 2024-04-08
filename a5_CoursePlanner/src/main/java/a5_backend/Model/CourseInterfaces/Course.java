package a5_backend.Model.CourseInterfaces;
import java.util.Iterator;

public interface Course {

    void addNewComponent(ClassComponent newComponent);

    String getDepartmentName();

    void printSections();

    String getCatalogNumber();

    long getCourseID();

    Iterator<? extends ClassComponent> getCourseOfferings(long courseOfferingID);

}
