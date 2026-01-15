package view;

import model.Channel;

import javax.swing.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class MenuBar {
    private final JMenuBar menuBar = new JMenuBar();
    private final JMenu channelsMenu = new JMenu("Channels");

    public MenuBar(Runnable showChannelsAction) {
        menuBar.add(channelsMenu);
        JMenuItem showChannels = new JMenuItem("Show Channels");
        showChannels.addActionListener(e -> showChannelsAction.run());
        JMenu viewMenu = new JMenu("View");
        viewMenu.add(showChannels);
        menuBar.add(viewMenu);
    }

    public JMenuBar getMenuBar() {
        return menuBar;
    }

    public void updateMenu(List<Channel> channels, Consumer<Channel> onSelect) {
        channelsMenu.removeAll();
        if (channels == null || channels.isEmpty()) {
            JMenuItem empty = new JMenuItem("No channels");
            empty.setEnabled(false);
            channelsMenu.add(empty);
        } else {
            Map<String, List<Channel>> groups = new LinkedHashMap<>();
            for (Channel ch : channels) {
                String name = ch.getChannelName() == null ? "" : ch.getChannelName();
                String group = name.contains(" ") ? name.split(" ", 2)[0] : name;
                groups.computeIfAbsent(group, k -> new ArrayList<>()).add(ch);
            }

            for (Map.Entry<String, List<Channel>> entry : groups.entrySet()) {
                List<Channel> groupsOfChannels = entry.getValue();

                if (groupsOfChannels.size() == 1) {
                    Channel ch = groupsOfChannels.getFirst();
                    JMenuItem channelItem = new JMenuItem((ch.getChannelName()));
                    channelItem.addActionListener(e -> onSelect.accept(ch));
                    channelsMenu.add(channelItem);
                } else {
                    JMenu groupMenu = new JMenu(entry.getKey());
                    for (Channel ch : groupsOfChannels) {
                        JMenuItem channelItem = new JMenuItem(ch.getChannelName());
                        channelItem.addActionListener(e -> onSelect.accept(ch));
                        groupMenu.add(channelItem);
                    }
                    channelsMenu.add(groupMenu);
                }
            }
        }
    }
}
