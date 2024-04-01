package a5_backend.Model.CourseAttributes;

public interface Component {

    int getCapacity();

    int getEnrollmentTotal();

    String getDepartmentName();

    int getCatalogNumber();

    String getInstructor();

    String getLocation();

    String getSemester();

    int parseSemester();

    String getComponentCode();
}
