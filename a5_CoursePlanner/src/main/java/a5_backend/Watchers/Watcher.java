package a5_backend.Watchers;

import java.util.Iterator;

public interface Watcher {

    Iterator<String> getListOfChanges();

    long getID();



}
