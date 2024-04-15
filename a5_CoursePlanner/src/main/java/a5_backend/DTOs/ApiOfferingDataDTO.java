package a5_backend.DTOs;

/**
 * The ApiOfferingDataDTO class encapsulates details of a course offering.
 * It includes semester, subject name, catalog number, location, enrollment capacity,
 * component type, total enrollment, and instructor's name for the HTTP client
 */
public class ApiOfferingDataDTO {
    public String semester;
    public String subjectName;
    public String catalogNumber;
    public String location;
    public int enrollmentCap;
    public String component;
    public int enrollmentTotal;
    public String instructor;

}
