package zhou.com.vpn.down;

/**
 * Created by zhou
 * on 2018/8/22.
 */

public interface ProgressListener {
    //已完成的 总的文件长度 是否完成
    void onProgress(long currentBytes, long contentLength, boolean done);
}

