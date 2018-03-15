package KosherFiles;

import net.sourceforge.zmanim.ComplexZmanimCalendar;
import net.sourceforge.zmanim.util.GeoLocation;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.GregorianCalendar;
import java.util.SimpleTimeZone;

import static java.time.temporal.ChronoUnit.SECONDS;

class HebrewTime {
    private final ComplexZmanimCalendar czc;

    public HebrewTime(String locationName, SimpleTimeZone timeZone, double latitude,
                      double longitude, double elevation) {

        GeoLocation location =  new GeoLocation(locationName, latitude, longitude, elevation, timeZone);
        this.czc = new ComplexZmanimCalendar(location);
    }

    public boolean isShabbat(LocalDateTime creationTime) {
        DayOfWeek weekday = creationTime.getDayOfWeek();
        switch (weekday.getValue()) {
            case 6:                  // Checks if it is Saturday
                czc.setCalendar(new GregorianCalendar(creationTime.getYear(),
                        creationTime.getMonthValue(), creationTime.getDayOfMonth()));
                // If sun has not already set, then it is still Shabbat
                // If the sun has set, Shabbat is over
                return creationTime.until(LocalDateTime.ofInstant(czc.getSunset().toInstant(), ZoneId.systemDefault()), SECONDS) >= 0;
            case 5:           // If it is Friday
                // Sets calendar date to one day before creationTime
                czc.setCalendar(new GregorianCalendar(creationTime.getYear(),
                        creationTime.getMonthValue(), creationTime.getDayOfMonth() - 1));
                // If the creationTime is after sunset, it is already Shabbat
                return creationTime.until(LocalDateTime.ofInstant(czc.getSunset().toInstant(), ZoneId.systemDefault()), SECONDS) <= 0;
            default:                                         // If not Friday or Saturday,
                // then it cannot be Shabbat
                return false;
        }
    }
}
