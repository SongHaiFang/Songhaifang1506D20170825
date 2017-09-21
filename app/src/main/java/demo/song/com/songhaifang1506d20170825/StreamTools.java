package demo.song.com.songhaifang1506d20170825;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * data:2017/8/25 0025.
 * Created by ：宋海防  song on
 */
public class StreamTools {
    public static String readFromFile(InputStream is) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;

            while ((len =is.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            baos.close();
            is.close();
            return baos.toString();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }
}
