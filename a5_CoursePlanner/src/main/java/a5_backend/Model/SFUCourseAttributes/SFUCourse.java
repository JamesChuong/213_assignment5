package a5_backend.Model.SFUCourseAttributes;

import a5_backend.Model.CourseInterfaces.ClassComponent;
import a5_backend.Model.CourseInterfaces.Course;
import a5_backend.Model.CourseInterfaces.Section;
import a5_backend.Model.Watchers.WatcherInterfaces.Observer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The SFUCourse class represents a course at SFU, it implements the Course interface
 * and implements the methods defined there. It contains a list of sections, which correspond
 * to offerings of that course, the department name, and catalog number.
 */
public class SFUCourse implements Course {

    private final String DEPARTMENT_NAME;
    private final String CATALOG_NUMBER;
    private final long COURSE_ID;
    private int indexOfLastSection = -1;
    private final List<SFUCourseSection> courseSections = new ArrayList<>(){};
    private final List<Observer> observers = new ArrayList<>(){};

    public SFUCourse(String departmentName, String catalogNumber, long courseID){
        this.DEPARTMENT_NAME = departmentName;
        this.CATALOG_NUMBER =  catalogNumber;
        this.COURSE_ID = courseID;
    }

    public static SFUCourse createCourseWithComponent(ClassComponent newComponent, long courseID){
        SFUCourse newCourse = new SFUCourse(newComponent.getDepartmentName(), newComponent.getCatalogNumber(), courseID);
        newCourse.addNewComponent(newComponent);
        return newCourse;
    }

    @Override
    public void addNewComponent(ClassComponent newComponent) {
        //Check if the component belongs to any section stored
        boolean componentIsPartOfSection = courseSections.stream()
                .anyMatch( section -> section.semester == newComponent.getSemester()
                        && section.location.equals(newComponent.getLocation()) );
        //Notify observers that a new component has been added
        observers.forEach(observers -> observers.updateEvents(newComponent));
        if(componentIsPartOfSection){
            courseSections.stream()
                    .filter( section -> section.semester == newComponent.getSemester()
                            && section.location.equals(newComponent.getLocation()) )
                    .forEach(section -> section.addNewComponent(newComponent));
        } else{     //If the component doesn't belong to any section stored, create a new one and add it
            indexOfLastSection++;
            courseSections.add(SFUCourseSection.CreateNewSectionWithComponent(newComponent, indexOfLastSection));
            courseSections.sort(new SFUCourseSection());
        }
    }

    @Override
    public String getDepartmentname() {
        return DEPARTMENT_NAME;
    }

    @Override
    public void printSections() {
        for(SFUCourseSection currentSection: courseSections){
            System.out.println(DEPARTMENT_NAME + " " + CATALOG_NUMBER);
            currentSection.printAllComponents();
        }
    }

    @Override
    public String getCatalogNumber() {
        return CATALOG_NUMBER;
    }

    @Override
    public long getCourseID() {
        return COURSE_ID;
    }

    @Override
    public Iterator<? extends ClassComponent> getCourseOfferingComponents(long courseOfferingID) {
            for(Section section: courseSections){
                if(section.getCourseOfferingID() == courseOfferingID){
                    return section.getAllComponents();
                }
            }
            throw new RuntimeException("Error: Course offering/section not found");
    }

    @Override
    public Iterator<? extends Section> getAllCourseOfferings() {
        return courseSections.iterator();
    }

    @Override
    public void addObserver(Observer newObserver) {
        observers.add(newObserver);
    }

}
