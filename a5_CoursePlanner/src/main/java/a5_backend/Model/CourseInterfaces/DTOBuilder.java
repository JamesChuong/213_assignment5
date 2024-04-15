package a5_backend.Model.CourseInterfaces;

public interface DTOBuilder<T,k> {
    T createDTO(k newCourseAttribute);

}
