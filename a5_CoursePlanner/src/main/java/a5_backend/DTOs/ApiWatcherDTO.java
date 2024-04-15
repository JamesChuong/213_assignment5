package a5_backend.DTOs;
import java.util.List;

/**
 * The ApiWatcherDTO class encapsulates details of a course watcher including its identifier,
 * associated department, and course information. It also lists all events observed by the watcher
 * This class is used to transfer watcher data to the client
 */
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
