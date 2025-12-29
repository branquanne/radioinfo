package Model.domain;

import java.awt.*;
import java.time.LocalDateTime;

public class Program {
    private int programId;
    private String programTitle;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Image thumbnail;

    public int getProgramId() {
        return programId;
    }

    public String getProgramTitle() {
        return programTitle;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public Image getThumbnail() {
        return thumbnail;
    }
}
