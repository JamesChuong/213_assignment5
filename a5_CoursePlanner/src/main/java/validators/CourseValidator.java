package validators;

import model.Section;

public class CourseValidator {
    public boolean validateCourse(Section previousSection, Section section) {
        if (previousSection.department == section.department
            && previousSection.courseNumber == section.courseNumber
            && previousSection.semester == section.semester
            && previousSection.location == section.location
            && previousSection.instructor == section.instructor) { return true; }
        else { return false; }
    }
}
