package a5_backend.DTOs;

/**
 * The ApiAboutDTO class encapsulates information to be shared with the client in the about section.
 * It holds the application's name and the author's name. This class acts as a data transfer
 * object, serving primarily to structure and transport these data elements to client-side
 * consumers, ensuring consistent API responses.
 */
public class ApiAboutDTO {
    public String appName;
    public String authorName;

    public ApiAboutDTO(String appName, String authorName) {
        this.appName = appName;
        this.authorName = authorName;
    }
}
