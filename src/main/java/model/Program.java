package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Program {
  @JsonProperty("program")
  private ProgramRef program;

  @JsonProperty("channel")
  private ChannelRef channel;

  @JsonProperty("title")
  private String title;

  @JsonProperty("description")
  private String description;

  @JsonProperty("starttimeutc")
  private String startTime;

  @JsonProperty("endtimeutc")
  private String endTime;

  @JsonProperty("imageurl")
  private String thumbnailLink;

  @JsonProperty("id")
  public int getProgramId() {
    return program.id;
  }

  @JsonProperty("channelId")
  public int getChannelId() {
    return channel.getId();
  }

  public String getProgramTitle() {
    return title;
  }

  public String getDescription() {
    return description;
  }

  public LocalDateTime getStartTime() {
    return parseTime(startTime);
  }

  public LocalDateTime getEndTime() {
    return parseTime(endTime);
  }

  private LocalDateTime parseTime(String timeString) {
    if (timeString == null) {
      return null;
    }
    String numeric = timeString.replaceAll("\\D+", "");
    if (numeric.isEmpty()) {
      return null;
    }

    long millis = Long.parseLong(numeric);
    return LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.of("Europe/Stockholm"));
  }

  private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ISO_DATE;
  private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");

  public String getStartTimeString() {
    return getStartTime() == null ? "" : getStartTime().format(TIME_FORMAT);
  }

  public String getStartDateString() {
    return getStartTime() == null ? "" : getStartTime().format(DATE_FORMAT);
  }

  public String getEndTimeString() {
    return getEndTime() == null ? "" : getEndTime().format(TIME_FORMAT);
  }

  public String getEndDateString() {
    return getEndTime() == null ? "" : getEndTime().format(DATE_FORMAT);
  }

  public String getThumbnailLink() {
    return thumbnailLink;
  }

  public static class ProgramRef {
    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    private String name;

    public int getId() {
      return id;
    }

    public String getName() {
      return name;
    }
  }

  public static class ChannelRef {
    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    private String name;

    public int getId() {
      return id;
    }

    public String getName() {
      return name;
    }
  }
}
