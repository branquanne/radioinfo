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

    public void setPrograms(List<Program> programs) {
        this.programs = programs;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public void setThumbnail(Image thumbnail) {
        this.thumbnail = thumbnail;
    }
}
