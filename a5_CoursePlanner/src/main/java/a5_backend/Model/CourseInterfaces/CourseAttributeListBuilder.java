package a5_backend.Model.CourseInterfaces;

import a5_backend.Model.SFUCourseAttributes.SFUCourse;
import java.util.Iterator;

/**
 * Interface for obtaining a collection of course attributes (courses, sections, or components), and supports
 * operations for obtaining an iterator of attributes and creating the corresponding DTOs
 * @param <k> Represents the attribute
 */
public interface CourseAttributeListBuilder<k> {
    Iterator<? extends k> getDTOIterator(Department newDepartment);
}
