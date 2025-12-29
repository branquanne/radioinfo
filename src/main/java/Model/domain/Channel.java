package Model.domain;

import java.awt.*;
import java.util.List;

public class Channel {
    private List<Program> programs;
    private int channelId;
    private String channelName;
    private Image thumbnail;

    public List<Program> getPrograms() {
        return programs;
    }

    public int getChannelId() {
        return channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public Image getThumbnail() {
        return thumbnail;
    }
}
