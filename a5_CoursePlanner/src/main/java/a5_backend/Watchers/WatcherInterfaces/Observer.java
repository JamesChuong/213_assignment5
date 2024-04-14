package a5_backend.Watchers.WatcherInterfaces;

import a5_backend.Model.CourseInterfaces.ClassComponent;

public interface Observer {

    void updateEvents(ClassComponent newComponent);

    String getLatestEvent();

    int getSemesterOfLatestEvent();



}
