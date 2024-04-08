package a5_backend.Model.CourseInterfaces;
import java.util.Iterator;

public interface Course {

    void addNewComponent(ClassComponent newComponent);

    String getDEPARTMENT_NAME();

    void printSections();

    String getCATALOG_NUMBER();

    long getCOURSE_ID();

    Iterator<? extends ClassComponent> getCourseOfferingComponents(long courseOfferingID);

    Iterator<? extends Section> getAllCourseOfferings();

}
