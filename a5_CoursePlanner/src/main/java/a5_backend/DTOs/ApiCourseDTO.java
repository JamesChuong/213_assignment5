package a5_backend.DTOs;

import a5_backend.Model.CourseInterfaces.Course;

/**
 * The ApiCourseDTO class encapsulates the courseId and catalogNumber of a course
 * This class makes it easy to transport these details
 * between the backend and the HTTP client.
 */
public class ApiCourseDTO {
    public long courseId;
    public String catalogNumber;

    public static ApiCourseDTO createNewCourseDTO(Course newCourse){
        ApiCourseDTO newCourseDTO = new ApiCourseDTO();
        newCourseDTO.courseId = newCourse.getCourseID();
        newCourseDTO.catalogNumber = newCourse.getCatalogNumber();
        return newCourseDTO;
    }

    public String getCatalogNumber() {
        return this.catalogNumber;
    }
}
