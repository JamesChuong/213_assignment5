package a5_backend.DTOs;

import a5_backend.Model.CourseInterfaces.Section;

/**
 * The ApiCourseOfferingDTO class includes the offering's ID, location, instructor list, term, semester code,
 * and year. This DTO is primarily used to convey course offering details from the server to the HTTP client.
 */
public class ApiCourseOfferingDTO {
    public long courseOfferingId;
    public String location;
    public String instructors;
    public String term;
    public long semesterCode;
    public int year;

    public static ApiCourseOfferingDTO createNewOfferingDTO(Section newSection){
        ApiCourseOfferingDTO newOfferingDTO = new ApiCourseOfferingDTO();
        newOfferingDTO.courseOfferingId = newSection.getCourseOfferingID();
        newOfferingDTO.location = newSection.getLocation();
        newOfferingDTO.instructors = newSection.getInstructors();
        newOfferingDTO.semesterCode = newSection.getSemester();
        newOfferingDTO.term = newSection.getTerm();
        newOfferingDTO.year = newSection.getYear();
        return newOfferingDTO;
    }

    public long getSemesterCode() { return this.semesterCode; }

}
