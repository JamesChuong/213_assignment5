package a5_backend.Watchers;

import java.util.Iterator;

public interface WatcherListBuilder {

    void addNewWatcher(Watcher newWatcher);

    Iterator<? extends Watcher> retreiveAllWatchers();

    Watcher retreiveWatcher(long watcherID);


}
