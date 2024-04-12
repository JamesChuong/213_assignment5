package a5_backend.DTOs;
import java.util.List;

public class ApiWatcherDTO {
    public long id;
    public ApiDepartmentDTO department;
    public ApiCourseDTO course;
    public List<String> events;

    public static ApiWatcherDTO createNewWatcherDTO(long ID, ApiDepartmentDTO department
            , ApiCourseDTO course, List<String> events){
        ApiWatcherDTO newWatcherDTO = new ApiWatcherDTO();
        newWatcherDTO.id = ID;
        newWatcherDTO.department = department;
        newWatcherDTO.course = course;
        newWatcherDTO.events = events;
        return newWatcherDTO;
    }

}
