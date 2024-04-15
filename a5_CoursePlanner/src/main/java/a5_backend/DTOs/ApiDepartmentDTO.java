package a5_backend.DTOs;

import a5_backend.Model.CourseInterfaces.Department;

/**
 * The ApiDepartmentDTO class includes methods to create an instance from a Department object
 * and a deptID long that is derived from the hash code, and name.
 */
public class ApiDepartmentDTO {
    public long deptId;
    public String name;

    public static ApiDepartmentDTO createApiDepartmentDTO(Department inputDepartment) {
        ApiDepartmentDTO newApiDepartmentDTO = new ApiDepartmentDTO();
        newApiDepartmentDTO.deptId = inputDepartment.hashCode();
        newApiDepartmentDTO.name = inputDepartment.getDepartmentName();

        return newApiDepartmentDTO;
    }

}
