package a5_backend.Model.CourseAttributes;

public interface Section {

    String getSemester();

    String getLocation();

    int parseSemester();

    int getTotalLecEnrollment();

    int getTotalLabEnrollment();

    int getTotalEnrollmentCapacity();

    void addNewComponent(Component newComponent);

    void printAllComponents();

}
