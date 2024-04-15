package a5_backend.Model.Watchers;

import a5_backend.Model.Watchers.WatcherInterfaces.Watcher;
import a5_backend.Model.Watchers.WatcherInterfaces.WatcherListBuilder;

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

    @Override
    public List<String> retreiveEventsOfWatcher(long watcherID){
        for(Watcher currentWatcher: watcherList){
            if(currentWatcher.getID() == watcherID){
                return currentWatcher.getListOfChanges();
            }
        }
        throw new RuntimeException("Error: Watcher not found");
    }

    @Override
    public void deleteWatcher(long watcherID) {
        Iterator<Watcher> watcherIterator = watcherList.iterator();
        while (watcherIterator.hasNext()){
            Watcher currentWatcher = watcherIterator.next();
            if (currentWatcher.getID() == watcherID) {
                watcherIterator.remove();
                break;
            }
        }
    }


}
