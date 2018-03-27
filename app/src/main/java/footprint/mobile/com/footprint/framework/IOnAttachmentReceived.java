package footprint.mobile.com.footprint.framework;

import android.net.Uri;

import java.io.File;

/**
 * Created by Suman Pula on 3/4/2018.
 * Copy Right @ Prabhakar Reddy Gudipati.
 * EMAIL : suman07.india@gmail.com.
 */
public interface IOnAttachmentReceived {
    void onUploadedFileReceived(Uri uri) throws Exception ;
    void onCapturedImage() throws Exception ;
    void onUserCancelled() throws Exception ;
    String fileName() throws Exception;
}
