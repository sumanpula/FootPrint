package footprint.mobile.com.footprint.dto;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import footprint.mobile.com.footprint.Constants;

/**
 * Created by Suman Pula on 3/5/2018.
 * Copy Right @ Prabhakar Reddy Gudipati.
 * EMAIL : suman07.india@gmail.com.
 */
public class Savings implements Constants {
    private List<String> stringList = new ArrayList<>();
    private static Savings savings;
    private Savings() {

    }

    public static Savings getSavings() {
        if (savings == null) {
            savings = new Savings();
        }
        return savings;
    }

    public List<String> footPrintsList() {
        return stringList;
    }

    public void setFootPrints(final String name) {
        final File file = new File(FILE_PATH+"/"+name);
        stringList.add(name);
        file.mkdir();
    }

    public void deleteFootPrint(final String name) {
        stringList.remove(name);
        final File file = new File(FILE_PATH+"/"+name);
        file.delete();
    }

    public String getSelectedFootPrint() {
        return selectedFootPrint;
    }

    public void setSelectedFootPrint(String selectedFootPrint) {
        this.selectedFootPrint = selectedFootPrint;
    }

    private String selectedFootPrint;
}
