package a5_backend.Model.SFUCourseAttributes;

import a5_backend.Model.CourseInterfaces.ClassComponent;
import a5_backend.Model.CourseInterfaces.Section;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class CourseSection implements Section, Comparator<CourseSection> {
    private final List<ClassComponent> componentList = new ArrayList<>();
    //True if a class has other components aside from lectures, like labs, tutorials, etc.
    private boolean hasOtherComponents = false;
    public String department;
    public String catalogNumber;
    public int semester;
    public String location;
    public List<String> instructors;
    private long courseOfferingID;
    private final int COMPONENT_PADDING = 12;
    private final int SECTION_HEADER_PADDING = 6;
    private final int SPRING_CODE = 1;
    private final int SUMMER_CODE = 4;

    public static CourseSection CreateNewSectionWithComponent(ClassComponent newComponent, long courseOfferingID){
        CourseSection newSection = new CourseSection();
        newSection.department = newComponent.getDepartmentName();
        newSection.catalogNumber = newComponent.getCatalogNumber();
        newSection.semester = newComponent.getSemester();
        newSection.location = newComponent.getLocation();
        newSection.instructors = newComponent.getInstructors();
        newSection.courseOfferingID = courseOfferingID;
        newSection.addNewComponent(newComponent);
        return newSection;
    }

    @Override
    public int getSemester() {
        return semester;
    }

    @Override
    public String getLocation() {
        return location;
    }

    @Override
    public int getTotalLecEnrollment() {
        return componentList.getFirst()
                .getEnrollmentTotal();
    }

    @Override
    public int getTotalLabEnrollment() {
        return componentList.stream()
                .filter(component -> !component.getComponentCode().equals("LEC") )
                .mapToInt(ClassComponent::getEnrollmentTotal)
                .sum();
    }

    @Override
    public int getTotalLecEnrollmentCapacity() {
        return componentList.getFirst()
                .getCapacity();
    }

    @Override
    public int getTotalLabEnrollmentCapacity() {
        return componentList.stream()
                .filter(component -> !component.getComponentCode().equals("LEC") )
                .mapToInt(ClassComponent::getCapacity)
                .sum();
    }

    @Override
    public long getCourseOfferingID() {
        return courseOfferingID;
    }

    @Override
    public void addNewComponent(ClassComponent newComponent) {
        if(!newComponent.getComponentCode().equals("LEC")){
            hasOtherComponents = true;
            componentList.addLast(newComponent);
        } else {
            componentList.addFirst(newComponent);
        }
        addNewInstructors(newComponent.getInstructors());
    }
    private void addNewInstructors(List<String> instructorList) {
        for(String instructor: instructorList){
            if(!instructors.contains(instructor) && !instructor.isEmpty()){
                instructors.add(instructor);
            }
        }
    }
    @Override
    public void printAllComponents() {

        String componentTitle = String.format("%" + SECTION_HEADER_PADDING + "s%d in %s by %s", " ", semester
                , location, componentList.getFirst().getInstructorsAsString());
        System.out.println(componentTitle);
            String lecEnrollmentTotals = String.format( "Type=LEC, Enrollment=%d/%d"
                    ,getTotalLecEnrollment(), getTotalLecEnrollmentCapacity());
            System.out.printf("%" + COMPONENT_PADDING + "s%s%n", " ", lecEnrollmentTotals);
        if(hasOtherComponents){
            String otherEnrollmentTotals = String.format("Type=%s, Enrollment=%d/%d"
                    , componentList.getLast().getComponentCode(), getTotalLabEnrollment()
                    , getTotalLabEnrollmentCapacity());
            System.out.printf("%" + COMPONENT_PADDING + "s%s%n", " ", otherEnrollmentTotals);
        }
    }

    @Override
    public String getInstructors() {
        StringBuilder instructorString = new StringBuilder();
        for(int i = 0; i < instructors.size()-1; i++){
            instructorString.append(instructors.get(i)).append(", ");
        }
        instructorString.append(instructors.getLast());
        return instructorString.toString();
    }

    @Override
    public String getTerm() {
        //Get the last digit in the semester code
        int term = semester%10;
        return switch (term) {
            case SPRING_CODE -> "Spring";
            case SUMMER_CODE -> "Summer";
            default -> "Fall";
        };
    }

    @Override
    public int getYear() {
        int tempSemester = semester;
        int year = 0;
        tempSemester/=10;
        year += tempSemester%10;
        tempSemester/=10;
        year += 10*(tempSemester%10);
        tempSemester/=10;
        year += 100*(tempSemester%10);
        year += 1900;
        return year;
    }



    @Override
    public Iterator<? extends ClassComponent> getAllComponents() {
        Comparator<ClassComponent> comparator = Comparator.comparing(ClassComponent::getComponentCode)
                .thenComparing(ClassComponent::getEnrollmentTotal);
        List<ClassComponent> sortedComponents = new ArrayList<>(componentList);
        sortedComponents.sort(comparator);
        return sortedComponents.iterator();
    }

    @Override
    public int compare(CourseSection o1, CourseSection o2) {
        if ( o1.getLocation().equals( o2.getLocation() ) ){
            if(o1.semester == o2.semester){
                return o1.instructors.getFirst().compareTo(o2.instructors.getFirst());
            } else {
                return o1.semester - o2.semester;
            }
        } else {
            return o1.location.compareTo(o2.location);
        }
    }

}
