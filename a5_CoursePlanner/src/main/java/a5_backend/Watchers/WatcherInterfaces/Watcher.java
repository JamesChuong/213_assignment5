package a5_backend.Watchers.WatcherInterfaces;

import java.util.List;

public interface Watcher {

    List<String> getListOfChanges();

    long getID();

    long getDepartmentID();

    long getCourseID();

    Observer getObserver();

    String getLatestEvent();

}
