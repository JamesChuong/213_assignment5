package a5_backend.Watchers;

import a5_backend.Model.CourseInterfaces.ClassComponent;

public interface CourseObserver {

    void updateEvents(ClassComponent newComponent);

    String getLatestEvent();

}
