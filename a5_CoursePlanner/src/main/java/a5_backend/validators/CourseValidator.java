package a5_backend.validators;

import a5_backend.Model.SFUCourseAttributes.CourseSection;

public class CourseValidator {
    public boolean validateCourse(CourseSection previousSection, CourseSection section) {
        if (previousSection.department == section.department
            && previousSection.catalogNumber == section.catalogNumber
            && previousSection.semester == section.semester
            && previousSection.location == section.location
            && previousSection.instructors == section.instructors) { return true; }
        else { return false; }
    }
}
