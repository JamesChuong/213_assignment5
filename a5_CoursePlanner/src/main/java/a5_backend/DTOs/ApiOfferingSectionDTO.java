package a5_backend.DTOs;

import a5_backend.Model.CourseInterfaces.ClassComponent;

/**
 * The ApiOfferingSectionDTO class encapsulates type, enrollment capacity, and total enrollment variables
 * It has a method to create instances of itself from ClassComponent objects
 */
public class ApiOfferingSectionDTO {
    public String type;
    public int enrollmentCap;
    public int enrollmentTotal;

    public static ApiOfferingSectionDTO createSectionDTO(ClassComponent newComponent){
        ApiOfferingSectionDTO newSectionDTO = new ApiOfferingSectionDTO();
        newSectionDTO.type = newComponent.getComponentCode();
        newSectionDTO.enrollmentCap = newComponent.getCapacity();
        newSectionDTO.enrollmentTotal = newComponent.getEnrollmentTotal();
        return newSectionDTO;
    }

    public String getType() { return this.type; }
}
