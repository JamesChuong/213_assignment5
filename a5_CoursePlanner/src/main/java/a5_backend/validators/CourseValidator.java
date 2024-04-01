package a5_backend.validators;

import a5_backend.Model.CourseSection;

public class CourseValidator {
    public boolean validateCourse(CourseSection previousSection, CourseSection section) {
        if (previousSection.department == section.department
            && previousSection.courseNumber == section.courseNumber
            && previousSection.semester == section.semester
            && previousSection.location == section.location
            && previousSection.instructor == section.instructor) { return true; }
        else { return false; }
    }
}
