package a5_backend.Watchers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class WatcherList implements WatcherListBuilder {

    private List<Watcher> watcherList = new ArrayList<>();

    @Override
    public void addNewWatcher(Watcher newWatcher){
        watcherList.add(newWatcher);
    }

    @Override
    public Iterator<? extends Watcher> retreiveAllWatchers() {
        return watcherList.iterator();
    }

    @Override
    public Watcher retreiveWatcher(long watcherID) {
        for(Watcher currentWatcher: watcherList){
            if(currentWatcher.getID() == watcherID){
                return currentWatcher;
            }
        }
        throw new RuntimeException("Error: Watcher not found");
    }


}
