package footprint.mobile.com.footprint.dto;

import android.graphics.Bitmap;

import java.util.List;

/**
 * Created by Suman Pula on 3/4/2018.
 * Copy Right @ Prabhakar Reddy Gudipati.
 * EMAIL : suman07.india@gmail.com.
 */
public class FootPrints {
    private List<Bitmap> bitmaps;

    public List<Bitmap> getBitmaps() {
        return bitmaps;
    }

    public void setBitmaps(List<Bitmap> bitmaps) {
        this.bitmaps = bitmaps;
    }

    public List<String> getFileNames() {
        return fileNames;
    }

    public void setFileNames(List<String> fileNames) {
        this.fileNames = fileNames;
    }

    private List<String> fileNames;

}
