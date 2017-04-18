package me.noreach.uhcwars.util;

/**
 * Created by Ryan on 18/04/2017.
 */
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.*;

/**
 * Created by Ryan on 01/02/2017.
 */
public class ScoreboardManager {

    private String[] cutUnranked(String[] content) {
        String[] elements = Arrays.copyOf(content, 15);
        for (int i = 0; i < elements.length; i++) {
            if (elements[i] == null) {
                continue;
            }
            if (elements[i].length() > 48) {
                elements[i] = elements[i].substring(0, 48);
            }
        }
        return elements;
    }

    private String cutName(String name) {
        if (name == null) {
            name = "Unamed board";
        }
        if (name.length() > 32) {
            name = name.substring(0, 32);
        }
        return name;
    }

    public boolean unrankedSidebarDisplay(final Player p, String name, String[] elements) {
        elements = cutUnranked(elements);
        name = cutName(name);
        try {
            if (p.getScoreboard() == null || p.getScoreboard() == Bukkit.getScoreboardManager().getMainScoreboard()
                    || p.getScoreboard().getObjectives().size() != 1) {
                p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
            }
            if (p.getScoreboard().getObjective(p.getUniqueId().toString().substring(0, 16)) == null) {
                p.getScoreboard().registerNewObjective(p.getUniqueId().toString().substring(0, 16), "dummy");
                p.getScoreboard().getObjective(p.getUniqueId().toString().substring(0, 16))
                        .setDisplaySlot(DisplaySlot.SIDEBAR);
            }
            p.getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(name);
            for (int i = 0; i < elements.length; i++) {
                if (!(elements[i] == null)) {
                    if (elements[i].length() <= 16) {
                        if (p.getScoreboard().getObjective(DisplaySlot.SIDEBAR).getScore(elements[i])
                                .getScore() != elements.length - i) {
                            p.getScoreboard().getObjective(DisplaySlot.SIDEBAR).getScore(elements[i])
                                    .setScore(elements.length - i);
                        }
                    } else if (elements[i].length() <= 32) {
                        Team temp = p.getScoreboard().getTeam(elements[i].substring(0, 16));
                        if (temp == null) {
                            temp = p.getScoreboard().registerNewTeam(elements[i].substring(0, 16));
                        }
                        temp.addEntry(elements[i].substring(0, 16));
                        temp.setSuffix(elements[i].substring(16, elements[i].length()));
                        if (p.getScoreboard().getObjective(DisplaySlot.SIDEBAR).getScore(elements[i].substring(0, 16))
                                .getScore() != elements.length - i) {
                            p.getScoreboard().getObjective(DisplaySlot.SIDEBAR).getScore(elements[i].substring(0, 16))
                                    .setScore(elements.length - i);
                        }
                    } else if (elements[i].length() <= 48) {
                        Team temp = p.getScoreboard().getTeam(elements[i].substring(16, 32));
                        if (temp == null) {
                            temp = p.getScoreboard().registerNewTeam(elements[i].substring(16, 32));
                        }
                        temp.setPrefix(elements[i].substring(0, 16));
                        temp.addEntry(elements[i].substring(16, 32));
                        temp.setSuffix(elements[i].substring(32, elements[i].length()));
                        if (p.getScoreboard().getObjective(DisplaySlot.SIDEBAR).getScore(elements[i].substring(16, 32))
                                .getScore() != elements.length - i) {
                            p.getScoreboard().getObjective(DisplaySlot.SIDEBAR).getScore(elements[i].substring(16, 32))
                                    .setScore(elements.length - i);
                        }
                    }
                    for (String entry : p.getScoreboard().getEntries()) {
                        if (p.getScoreboard().getObjective(DisplaySlot.SIDEBAR).getScore(entry)
                                .getScore() == elements.length - i) {
                            if (!(elements[i].length() > 16 ? (elements[i].length() > 32 ? elements[i].substring(16, 32)
                                    : elements[i].substring(0, 16)) : elements[i]).equals(entry)) {
                                p.getScoreboard().resetScores(entry);
                            }
                        }
                    }
                }
            }
            List<String> tempArray = Arrays.asList(elements);
            for (String entry : p.getScoreboard().getEntries()) {
                boolean toErase = true;
                for (String element : elements) {
                    if (element != null
                            && (element.length() > 16
                            ? (element.length() > 32 ? element.substring(16, 32) : element.substring(0, 16))
                            : element).equals(entry)
                            && p.getScoreboard().getObjective(DisplaySlot.SIDEBAR).getScore(entry)
                            .getScore() == elements.length - tempArray.indexOf(element)) {
                        toErase = false;
                        break;
                    }
                }
                if (toErase) {;
                    p.getScoreboard().resetScores(entry);
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}