package a5_backend.DTOs;

public class ApiCourseDTO {
    public long courseId;
    public String catalogNumber;

    public static ApiCourseDTO createNewCourseDTO(long courseId, String catalogNumber){
        ApiCourseDTO newCourseDTO = new ApiCourseDTO();
        newCourseDTO.courseId = courseId;
        newCourseDTO.catalogNumber = catalogNumber;
        return newCourseDTO;
    }
}
