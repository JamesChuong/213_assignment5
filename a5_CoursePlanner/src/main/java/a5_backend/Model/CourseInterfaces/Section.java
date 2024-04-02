package a5_backend.Model.CourseInterfaces;

public interface Section {

    int getSemester();

    String getLocation();

    int getTotalLecEnrollment();

    int getTotalLabEnrollment();

    int getTotalEnrollmentCapacity();

    void addNewComponent(ClassComponent newComponent);

    void printAllComponents();

}
