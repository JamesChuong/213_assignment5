package a5_backend.DTOs;

public class ApiCourseDTO {
    public long courseId;
    public String catalogNumber;

    public ApiCourseDTO(long courseId, String catalogNumber){
        this.courseId = courseId;
        this.catalogNumber = catalogNumber;
    }


}
