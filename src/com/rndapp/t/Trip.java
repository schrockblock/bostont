package com.rndapp.t;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Trip implements Serializable {

    /**
     * Key for the Blue line.
     */
    public final static String BLUE = "blue";

    /**
     * Key for the Orange line.
     */
    public final static String ORANGE = "orange";

    /**
     * Key for the Red line.
     */
    public final static String RED = "red";

    /**
     * Key for the Green line.
     */
    public final static String GREEN = "green";

    /**
     * The subway line the trip is on.
     */
    private String line;

    /**
     * The trip's destination.
     */
    private String destination;

    /**
     * A list of stops along the way to the destination.
     */
    private ArrayList<Stop> stops;

    /**
     * Constructs a {@code Trip}.
     * @param trip The {@code JSONObject} that contains the {@code Trip}'s
     *             destination and a list of predictions.
     * @param line The {@code Trip}'s line color.
     */
    public Trip(JSONObject trip, String line) {
        super();
        destination = "";
        this.line = line;
        stops = new ArrayList<Stop>();
        try {
            this.destination = trip.getString("Destination");
            initStops();
            incorporatePredictions(trip.getJSONArray("Predictions"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param predictions An array of stops and their predicted seconds.
     */
    public void incorporatePredictions(JSONArray predictions) {
        // For each prediction...
        for (int i = 0; i < predictions.length(); i++) {

            // We haven't added the prediction yet...
            boolean added = false;

            // Get the prediction
            JSONObject prediction = null;
            try {
                prediction = predictions.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // Predicted stop
            Stop predictedStop = new Stop(prediction);

            // For each stop...
            for (int j = 0; j < stops.size(); j++) {
                Stop stop = stops.get(j);
                try {
                    // If the stops have the same destination...
                    if (predictedStop.equals(stop)) {
                        // Add the predicted seconds to get to the stop.
                        stop.seconds.add(prediction.getLong("Seconds"));
                        added = true;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if (!added) {
                stops.add(predictedStop);
            }
        }
    }

    /**
     * Returns this {@code Trip}'s list of stops.
     * @return this {@code Trip}'s list of stops.
     */
    public ArrayList<Stop> getStops() {
        return stops;
    }

    /**
     * Returns this {@code Trip}'s destination.
     * @return this {@code Trip}'s destination.
     */
    public String getDestination() {
        return destination;
    }

    /**
     * Checks if this {@code Trip} is equal to another {@code Trip}.
     *
     * @param o The other {@code Trip}.
     * @return True if this {@code Trip} and the other {@code Trip}
     * have the same destinations.
     */
    @Override
    public boolean equals(Object o) {
        if (o.getClass() != Trip.class)
            return false;
        return ((Trip) o).destination.equalsIgnoreCase(this.destination);
    }

    /**
     * Populates the list of stops, depending on this {@code Trip}'s destination.
     */
    private void initStops() {
        String[][] lines = {
                // BLUE
                {"Wonderland",
                        "Revere Beach",
                        "Beachmont",
                        "Suffolk Downs",
                        "Orient Heights",
                        "Wood Island",
                        "Airport",
                        "Maverick",
                        "Aquarium",
                        "State Street",
                        "Government Center",
                        "Bowdoin"},
                // ORANGE
                {"Oak Grove",
                        "Malden Center",
                        "Wellington",
                        "Sullivan",
                        "Community College",
                        "North Station",
                        "Haymarket",
                        "State Street",
                        "Downtown Crossing",
                        "Chinatown",
                        "Tufts Medical",
                        "Back Bay",
                        "Mass Ave",
                        "Ruggles",
                        "Roxbury Crossing",
                        "Jackson Square",
                        "Stony Brook",
                        "Green Street",
                        "Forest Hills"},
                // RED
                {"Alewife",
                        "Davis",
                        "Porter Square",
                        "Harvard Square",
                        "Central Square",
                        "Kendall/MIT",
                        "Charles/MGH",
                        "Park Street",
                        "Downtown Crossing",
                        "South Station",
                        "Broadway",
                        "Andrew",
                        "JFK/UMass",
                        "North Quincy",
                        "Wollaston",
                        "Quincy Center",
                        "Quincy Adams",
                        "Braintree",
                        "Savin Hill",
                        "Fields Corner",
                        "Shawmut",
                        "Ashmont"},
                // RED
                {"Alewife",
                        "Davis",
                        "Porter Square",
                        "Harvard Square",
                        "Central Square",
                        "Kendall/MIT",
                        "Charles/MGH",
                        "Park Street",
                        "Downtown Crossing",
                        "South Station",
                        "Broadway",
                        "Andrew",
                        "JFK/UMass",
                        "North Quincy",
                        "Wollaston",
                        "Quincy Center",
                        "Quincy Adams",
                        "Braintree"},
                // RED
                {"Alewife",
                        "Davis",
                        "Porter Square",
                        "Harvard Square",
                        "Central Square",
                        "Kendall/MIT",
                        "Charles/MGH",
                        "Park Street",
                        "Downtown Crossing",
                        "South Station",
                        "Broadway",
                        "Andrew",
                        "JFK/UMass",
                        "Savin Hill",
                        "Fields Corner",
                        "Shawmut",
                        "Ashmont"}};
        String[] list = {""};
        if (line.equalsIgnoreCase(BLUE)) {
            list = lines[0];
        } else if (line.equalsIgnoreCase(ORANGE)) {
            list = lines[1];
        } else if (line.equalsIgnoreCase(RED)) {
            if (destination.equals("Alewife")) {
                list = lines[2];
            } else if (destination.equals("Braintree")) {
                list = lines[3];
            } else if (destination.equals("Ashmont")) {
                list = lines[4];
            }
        }

        if (!destination.equalsIgnoreCase(list[0])) {
            for (int i = 0; i < list.length; i++) {
                stops.add(new Stop(list[i]));
            }
        } else {
            for (int i = 0; i < list.length; i++) {
                stops.add(new Stop(list[list.length - 1 - i]));
            }
        }

    }
}
