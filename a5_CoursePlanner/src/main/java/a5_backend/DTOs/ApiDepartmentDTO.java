package a5_backend.DTOs;

import a5_backend.Model.CourseInterfaces.Department;

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
