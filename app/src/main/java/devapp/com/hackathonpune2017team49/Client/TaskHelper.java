package devapp.com.hackathonpune2017team49.Client;

import com.google.android.gms.location.places.Place;

/**
 * Created by root on 23/9/17.
 */

public class TaskHelper {

    Place place;
    String name;
    String time;
    String details;

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
