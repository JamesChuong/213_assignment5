package a5_backend.DTOs;

import a5_backend.Model.CourseInterfaces.Course;


public class ApiCourseDTO {
    public long courseId;
    public String catalogNumber;

    public static ApiCourseDTO createNewCourseDTO(Course newCourse){
        ApiCourseDTO newCourseDTO = new ApiCourseDTO();
        newCourseDTO.courseId = newCourse.getCOURSE_ID();
        newCourseDTO.catalogNumber = newCourse.getCATALOG_NUMBER();
        return newCourseDTO;
    }
}
