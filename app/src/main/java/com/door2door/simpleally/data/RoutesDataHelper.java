package com.door2door.simpleally.data;

import com.door2door.simpleally.data.pojo.Segment;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by vezikon on 10/22/16.
 */

public class RoutesDataHelper {

    private final static String MODE_CHANGE = "change";
    private final static String MODE_SETUP = "setup";
    private final static String MODE_PARKING = "parking";

    public String getFromTime(List<Segment> segments) {
        return segments.get(0).getStops().get(0).getDatetime();
    }

    public String getToTime(List<Segment> segments) {
        int segmentSize = segments.size();
        int stopSize = segments.get(segmentSize - 1).getStops().size();

        return segments.get(segmentSize - 1).getStops().get(stopSize - 1).getDatetime();
    }

    public String calculateDuration(List<Segment> segments) throws ParseException {
        String from = parseTime(getFromTime(segments));
        String to = parseTime(getToTime(segments));


        DateTimeFormatter formatter = DateTimeFormat.forPattern("h:mm a");
        DateTime first = formatter.parseDateTime(from);
        DateTime second = formatter.parseDateTime(to);

        Interval interval = new Interval(first, second);
        return interval.toDuration().getStandardMinutes() + " mins";
    }

    public String makeFromToTime(List<Segment> segments) throws ParseException {
        if (segments == null)
            return "";

        //getting starting time from first stop
        String from = getFromTime(segments);
        //getting last stop time
        String to = getToTime(segments);

        return parseTime(from) + " -> " + parseTime(to);
    }

    public String parseTime(String datetime) throws ParseException {
        String pattern = "yyyy-MM-dd'T'HH:mm:ssZ";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date = simpleDateFormat.parse(datetime);
        SimpleDateFormat formatter = new SimpleDateFormat("h:mm a");
        return formatter.format(date);
    }

    public List<Segment> filterSegments(List<Segment> segments) {
        List<Segment> temp = new ArrayList<>();
        for (Segment segment : segments) {
            String travelMode = segment.getTravel_mode();
            if (!travelMode.equalsIgnoreCase(MODE_CHANGE)
                    && !travelMode.equalsIgnoreCase(MODE_PARKING)
                    && !travelMode.equalsIgnoreCase(MODE_SETUP)) {
                temp.add(segment);
            }
        }
        return temp;
    }

    public String toTitleCase(String text) {
        text = text.replace("_", " ");
        String[] arr = text.split(" ");
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < arr.length; i++) {
            sb.append(Character.toUpperCase(arr[i].charAt(0)))
                    .append(arr[i].substring(1)).append(" ");
        }
        return sb.toString().trim();
    }
}
