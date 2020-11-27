package quasar.rest.models;

import java.util.ArrayList;
import java.util.List;

public class ThreadSafeSatellites {

    //This is a thread safe singleton, because the post "/topsecret_split/{...}" could receive multiple requests

    private ThreadSafeSatellites(){ }

    private static class Holder {
        private static final List<Satelites> INSTANCE = new ArrayList<>();
    }

    public static List<Satelites> getInstance() {
        return Holder.INSTANCE;
    }
}
