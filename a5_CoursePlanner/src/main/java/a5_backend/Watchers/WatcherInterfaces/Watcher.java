package a5_backend.Watchers.WatcherInterfaces;


import a5_backend.Watchers.WatcherInterfaces.Observer;

import java.util.List;

public interface Watcher {

    List<String> getListOfChanges();

    long getID();

    long getDepartmentID();

    long getCourseID();

    Observer getObserver();

    String getLatestEvent();

}
