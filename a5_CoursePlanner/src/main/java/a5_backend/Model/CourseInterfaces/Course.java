package a5_backend.Model.CourseInterfaces;


public interface Course {

    public void addNewComponent(ClassComponent newComponent);

    String getDepartmentName();

    void printSections();

    String getCatalogNumber();

}
