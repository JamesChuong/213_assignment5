package a5_backend.Watchers;

import a5_backend.Model.CourseInterfaces.ClassComponent;
import a5_backend.Watchers.WatcherInterfaces.Observer;
import a5_backend.Watchers.WatcherInterfaces.Watcher;

import java.util.ArrayList;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static java.time.LocalTime.now;

public class CourseWatcher implements Watcher {

    //The total number of watchers which have been created
    private static long watcherCount = 0;
    private long watcherID;
    private long departmentID;
    private long courseID;
    //Stores all events which happen
    private List<String> allChanges = new ArrayList<>();
    private final int SPRING_CODE = 1;
    private final int SUMMER_CODE = 4;

    private Observer courseObserver = new Observer() {
        private String latestEvent = "";
        private int semesterOfChangedEvent = -99999;
        @Override
        public void updateEvents(ClassComponent newComponent) {
            //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E MMM dd HH:mm:ss z yyyy");
            latestEvent = String.format("%s: Added section %s with enrollment (%d/%d) to offering "
                    , now(), newComponent.getComponentCode()
                    , newComponent.getEnrollmentTotal(), newComponent.getCapacity());
            semesterOfChangedEvent = newComponent.getSemester();
        }
        @Override
        public String getLatestEvent() {
            return latestEvent;
        }
        @Override
        public int getSemester(){
            return semesterOfChangedEvent;
        }
    };

    public static CourseWatcher createNewCourseWatcher(long departmentID, long courseID){
        CourseWatcher newCourseWatcher = new CourseWatcher();
        newCourseWatcher.watcherID = watcherCount++;
        newCourseWatcher.courseID = courseID;
        newCourseWatcher.departmentID = departmentID;
        return newCourseWatcher;
    }

    @Override
    public List<String> getListOfChanges() {
        if(!courseObserver.getLatestEvent().isEmpty()){
            String completedEvent = courseObserver.getLatestEvent();
            allChanges.add(completedEvent);
            allChanges = allChanges.stream().distinct().collect(Collectors.toList());
        }
        return allChanges;
    }

    private String getTerm(int semester) {
        //Get the last digit in the semester code
        int term = semester%10;
        return switch (term) {
            case SPRING_CODE -> "Spring";
            case SUMMER_CODE -> "Summer";
            default -> "Fall";
        };
    }

    private int getYear(int semester) {
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
    public long getID() {
        return watcherID;
    }

    @Override
    public long getDepartmentID() {
        return departmentID;
    }

    @Override
    public long getCourseID() {
        return courseID;
    }

    @Override
    public Observer getObserver() {
        return courseObserver;
    }

    @Override
    public String getLatestEvent() {
        try{
            return allChanges.getLast();
        } catch (NoSuchElementException err){
            throw new RuntimeException("No changes have been made");
        }
    }


}
