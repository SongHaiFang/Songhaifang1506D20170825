package demo.song.com.songhaifang1506d20170825;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.cache.disc.impl.BaseDiskCache;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.orhanobut.logger.Logger;
import com.sn.xlistviewlibrary.XListView;

import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import demo.song.com.songhaifang1506d20170825.db.Song;


/**
 * data:2017/8/25 0025.
 * Created by ：宋海防  song on
 */
public class Fragment01 extends android.support.v4.app.Fragment implements XListView.IXListViewListener {
    private Song song;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            adapter.notifyDataSetChanged();
            CollManager();
        }
    };
    private XListView mylist;
    private DataCleanManager manager;
    private myAdapter adapter;
    private List<MyBean.ResultBean.DataBean> list;
    //    private MyBean.DataBean.MediaInfoBean bean;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment01, container, false);
        mylist = (XListView) view.findViewById(R.id.mylist);
        String url = "http://v.juhe.cn/toutiao/index?type=tiyu&key=d4c18a18c3391f90dc971633f6e6f445";
        getDatas(url);
        song = new Song(getActivity());


        mylist.setPullLoadEnable(true);
        //下拉刷新
        mylist.setPullRefreshEnable(true);
        mylist.setXListViewListener(this);
//        bean = new MyBean.DataBean.MediaInfoBean();
//        mylist.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), MyAct.class);
//                startActivity(intent);
//            }
//        });
        //长按监听
        mylist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setIcon(R.mipmap.ic_launcher).setMessage("请选择操作");
                builder.setNegativeButton("查看缓存", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        manager = new DataCleanManager();
                        String size = manager.getTotalCacheSize(getActivity());
                        Toast.makeText(getActivity(),size,Toast.LENGTH_LONG).show();
                    }
                });
                builder.setPositiveButton("清除缓存", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        manager.clearAllCache(getActivity());
                        Toast.makeText(getActivity(),"清理成功",Toast.LENGTH_LONG).show();
                    }
                }).create().show();

                return true;
            }
        });
        return view;
    }
    //用AsyncTask获取文件
    private void getDatas(String path) {

        new AsyncTask<String, Void, String>() {

            private String s;
            //onPostExecute这个方法需要自己写，相当于handler   RunOnUIThread，更新UI界面
            @Override
            protected void onPostExecute(String s) {

                super.onPostExecute(s);

                //用gson解析网上的json串，需要导入gson依赖
                    Gson gson = new Gson();
                    MyBean dateBean = gson.fromJson(s, MyBean.class);
                list = dateBean.getResult().getData();

                    song.add(String.valueOf(list));

                    adapter = new myAdapter(list);
                    mylist.setAdapter(adapter);
            }

            //就是子线程，用来做耗时操作的
            @Override
            protected String doInBackground(String... strings) {
                try {
                    String string = strings[0];
                    URL url = new URL(string);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(5000);
                    connection.setReadTimeout(5000);
                    int code = connection.getResponseCode();
                    if (code==HttpURLConnection.HTTP_OK){
                        InputStream is = connection.getInputStream();
                        s = StreamTools.readFromFile(is);   //StreamTools工具类，可以看我另外一个

//                        Logger.d(s);
                        return s;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

        }.execute(path); //这个很关键，execute就是告诉 AsyncTask开始执行了，启动了。 一定要写

    }
    //下拉刷新
    @Override
    public void onRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                list.add(0,list.get(2));
                handler.sendEmptyMessage(0);
            }
        }, 1000);
    }
    //上拉加载
    @Override
    public void onLoadMore() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                list.add(list.get(3));
                handler.sendEmptyMessage(0);
            }
        }, 1000);
    }
    public void CollManager() {
        //停止刷新
        mylist.stopRefresh();

        mylist.stopLoadMore();

//        Date date = new Date();
//        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String time = format.format(date);
//        mylist.setRefreshTime(time);
    }

    class myAdapter extends BaseAdapter{
        List<MyBean.ResultBean.DataBean> list;

        public myAdapter(List<MyBean.ResultBean.DataBean> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = View.inflate(getActivity(),R.layout.er,null);
            TextView text = view.findViewById(R.id.item_text);
            ImageView img = view.findViewById(R.id.item_image);


                text.setText(list.get(i).getTitle());
                loderImage(list.get(i).getThumbnail_pic_s(),img);
            return view;
        }
    }
    public  void loderImage(String url,ImageView imageView){
            ImageLoaderConfiguration configuration = ImageLoaderConfiguration
                    .createDefault(getActivity());

            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.mipmap.ic_launcher)
                    .showImageOnFail(R.mipmap.ic_launcher)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .build();

            ImageLoader instance = ImageLoader.getInstance();
            instance.init(configuration);
            instance.displayImage(url, imageView ,options);

            //compile 'com.nostra13.universalimageloader:universal-image-loader:1.9.5'
        }

    public class DataCleanManager {

public  String getTotalCacheSize(Context context)  {
        long cacheSize = 0;
        try {
        cacheSize = getFolderSize(context.getCacheDir());
        } catch (Exception e) {
        e.printStackTrace();
        }
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
        try {
        cacheSize += getFolderSize(context.getExternalCacheDir());
        } catch (Exception e) {
        e.printStackTrace();
        }
        }
        return getFormatSize(cacheSize);
        }


public  void clearAllCache(Context context) {
        deleteDir(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
        deleteDir(context.getExternalCacheDir());
        }
        }

private  boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
        String[] children = dir.list();
        for (int i = 0; i < children.length; i++) {
        boolean success = deleteDir(new File(dir, children[i]));
        if (!success) {
        return false;
        }
        }
        }
        return dir.delete();
        }

// 获取文件
//Context.getExternalFilesDir() --> SDCard/Android/data/你的应用的包名/files/ 目录，一般放一些长时间保存的数据
//Context.getExternalCacheDir() --> SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
public  long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
        File[] fileList = file.listFiles();
        for (int i = 0; i < fileList.length; i++) {
        // 如果下面还有文件
        if (fileList[i].isDirectory()) {
        size = size + getFolderSize(fileList[i]);
        } else {
        size = size + fileList[i].length();
        }
        }
        } catch (Exception e) {
        e.printStackTrace();
        }
        return size;
        }

/**
 * 格式化单位
 *
 * @param size
 * @return
 */
public  String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
        //            return size + "Byte";
        return "0K";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
        BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
        return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
        .toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
        BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
        return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
        .toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
        BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
        return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
        .toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
        + "TB";
        }

        }
}