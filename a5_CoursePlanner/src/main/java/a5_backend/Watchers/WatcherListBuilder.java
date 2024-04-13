package a5_backend.Watchers;

import java.util.Iterator;
import java.util.List;

public interface WatcherListBuilder {

    void addNewWatcher(Watcher newWatcher);

    Iterator<? extends Watcher> retreiveAllWatchers();

    Watcher retreiveWatcher(long watcherID);

    List<String> retreiveEventsOfWatcher(long watcherID);

}
