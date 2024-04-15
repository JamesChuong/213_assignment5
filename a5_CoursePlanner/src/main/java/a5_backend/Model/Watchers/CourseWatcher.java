package a5_backend.Model.Watchers;

import a5_backend.Model.CourseInterfaces.ClassComponent;
import a5_backend.Model.Watchers.WatcherInterfaces.Observer;
import a5_backend.Model.Watchers.WatcherInterfaces.Watcher;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class CourseWatcher implements Watcher {

    //The total number of watchers which have been created
    private static long watcherCount = 1;
    private long watcherID;
    private long departmentID;
    private long courseID;
    //Stores all events which happen
    private final List<String> allChanges = new ArrayList<>();
    private final int SPRING_CODE = 1;
    private final int SUMMER_CODE = 4;

    private Observer courseObserver = new Observer() {
        private String latestEvent = "";
        private int semesterOfChangedEvent = -99999;
        @Override
        public void updateEvents(ClassComponent newComponent) {

            // Get the timezone and set the date format to "day of the week / month / day / time / timezone / year"
            // "now" gets the current timezone and .format applies the specified time format
            ZonedDateTime now = ZonedDateTime.now();
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("E MMM dd HH:mm:ss z yyyy");
            latestEvent = String.format("%s: Added section %s with enrollment (%d/%d) to offering "
                    , now.format(dateFormatter), newComponent.getComponentCode()
                    , newComponent.getEnrollmentTotal(), newComponent.getCapacity());
            semesterOfChangedEvent = newComponent.getSemester();
            allChanges.add(latestEvent + getTerm(semesterOfChangedEvent) + " " + getYear(semesterOfChangedEvent) );
        }
        @Override
        public String getLatestEvent() {
            return latestEvent;
        }
        @Override
        public int getSemesterOfLatestEvent(){
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

}
