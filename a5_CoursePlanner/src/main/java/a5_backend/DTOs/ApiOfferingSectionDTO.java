package a5_backend.DTOs;

import a5_backend.Model.CourseInterfaces.ClassComponent;

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
}
