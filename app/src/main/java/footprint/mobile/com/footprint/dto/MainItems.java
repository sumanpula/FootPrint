package footprint.mobile.com.footprint.dto;

import java.util.List;

/**
 * Created by Suman Pula on 3/4/2018.
 * Copy Right @ Prabhakar Reddy Gudipati.
 * EMAIL : suman07.india@gmail.com.
 */
public class MainItems {
    public String[] getMainItems() {
        return mainItems;
    }

    public void setMainItems(String[] mainItems) {
        this.mainItems = mainItems;
    }

    private String[] mainItems;

    public int[] getMainIcons() {
        return mainIcons;
    }

    public void setMainIcons(int[] mainIcons) {
        this.mainIcons = mainIcons;
    }

    private int[] mainIcons;
}
