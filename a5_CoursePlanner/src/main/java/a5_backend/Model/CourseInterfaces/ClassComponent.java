package a5_backend.Model.CourseInterfaces;

import java.util.List;

public interface ClassComponent {

    int getCapacity();

    int getEnrollmentTotal();

    String getDepartmentName();

    String getCatalogNumber();

    List<String> getInstructors();

    String getLocation();

    int getSemester();

    String getComponentCode();

    String getInstructorsAsString();
}
