package KosherFiles;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.SimpleTimeZone;

import static java.nio.file.FileVisitResult.*;

class ChangeMetadata extends java.nio.file.SimpleFileVisitor<Path> {


    private final SimpleTimeZone timeZone = new SimpleTimeZone(7200000, "Estonia",
            Calendar.MARCH, -1, Calendar.SUNDAY, 10800000,
            Calendar.OCTOBER, -1, Calendar.SUNDAY,
            10800000, 3600000);


    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attr) {
        try {
            FileTime creation = attr.creationTime();
            LocalDateTime creationTime = LocalDateTime.ofInstant(creation.toInstant(), ZoneId.systemDefault());
            String locationName = "Estonia";
            double latitude = 59.436962;
            double longitude = 24.753574;
            double elevation = 13;
            HebrewTime ht = new HebrewTime(locationName, timeZone, latitude, longitude, elevation);
            if (ht.isShabbat(creationTime)) {
                LocalDateTime alteredTime = creationTime.minusDays(1L);
                FileTime ft = FileTime.from(alteredTime.atZone(ZoneId.systemDefault()).toInstant());
                Files.setAttribute(file, "creationTime", ft);
                System.out.println(file.getFileName());
            }
        } catch (IOException e) {
            System.err.println("Cannot change the creation time. " + e);
        }
        return CONTINUE;
    }
}
