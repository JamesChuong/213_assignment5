package a5_backend.Model.CourseInterfaces;


public interface Course {

    void addNewComponent(ClassComponent newComponent);

    String getDepartmentName();

    void printSections();

    String getCatalogNumber();

}
