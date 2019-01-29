package zhou.com.vpn.receiver;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.text.TextUtils;

import static android.content.Context.DOWNLOAD_SERVICE;

/**
 * Created by zhou
 * on 2018/8/7.
 */

public class DownloadCompleteReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //log.verbose("onReceive. intent:{}", intent != null ? intent.toUri(0) : null);
        if (intent != null) {
            if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(intent.getAction())) {
                long downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
               // log.debug("downloadId:{}", downloadId);
                DownloadManager downloadManager = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
                String type = downloadManager.getMimeTypeForDownloadedFile(downloadId);
                //log.debug("getMimeTypeForDownloadedFile:{}", type);
                if (TextUtils.isEmpty(type)) {
                    type = "*/*";
                }
                Uri uri = downloadManager.getUriForDownloadedFile(downloadId);
               // log.debug("UriForDownloadedFile:{}", uri);
                if (uri != null) {
                    Intent handlerIntent = new Intent(Intent.ACTION_VIEW);
                    handlerIntent.setDataAndType(uri, type);
                    context.startActivity(handlerIntent);
                }
            }
        }
    }
}
