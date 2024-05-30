package com.phy.ota_demo.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import androidx.core.content.FileProvider;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

/**
 * 文件工具类 其他应用接受到升级文件时可以通过PhyOTA直接打开。
 */
public class FileUtil {
    private static final String FOLDER_NAME = "PhyOTA";
    private static final String TAG = FileUtil.class.getSimpleName();
    private static FileUtil mInstance;
    private static Context mContext;

    public FileUtil(Context context) {
        mContext = context;
    }

    public static FileUtil getInstance(Context context) {
        if (mInstance == null) {
            synchronized (FileUtil.class) {
                if (mInstance == null) {
                    mInstance = new FileUtil(context);
                }
            }
        }
        return mInstance;
    }

    public void createFolder() {
        String folderPath;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            folderPath = mContext.getExternalFilesDir(null).getAbsolutePath() +
                    "/" + FOLDER_NAME;
            Log.d(TAG, "createFolder: Android 11");
        } else {
            folderPath = Environment.getExternalStorageDirectory().getPath() +
                    "/" + FOLDER_NAME;
            Log.d(TAG, "createFolder: Android 11以下");
        }
        File file = new File(folderPath);
        if (!file.exists()) {
            boolean mkdirs = file.mkdirs();//创建文件夹
            if (mkdirs) {
                Log.d(TAG, "createFolder: 创建成功！");
            } else {
                Log.d(TAG, "createFolder: 创建失败！");
            }
        }
    }

    //通过微信URI解析文件地址，并保存到根目录下
    public String uriToFile(Uri uri) {
        if (uri == null) {
            Log.d(TAG, "uri is null.");
            return null;
        }
        if (uri.getPath() == null) {
            Log.d(TAG, "getPath is null.");
            return null;
        }
        //获得ContentResolver，用于访问其它应用数据
        ContentResolver resolver = mContext.getContentResolver();
        //获得URI路径
        String pathUri = uri.getPath().toLowerCase();
        //获取文件名称
        String fileName = pathUri.substring(pathUri.lastIndexOf("/") + 1);
        //新文件的路径
        String filePath;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            filePath = mContext.getExternalFilesDir(null).getAbsolutePath();
        } else {
            filePath = Environment.getExternalStorageDirectory().getPath();
        }
        //创建文件
        File file = new File(filePath, fileName);
        File parentFile = file.getParentFile();

        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        Log.d(TAG, "getPath: " + file.getPath());
        Log.d(TAG, "getAbsolutePath: " + file.getAbsolutePath());
        if (file.exists()) {
            Log.d(TAG, "uriToFile: 文件已存在");
            return "文件已存在";
        }
        InputStream is = null;
        try {
            file.createNewFile();
            is = resolver.openInputStream(uri);
            OutputStream os = new FileOutputStream(file.getAbsolutePath());
            write(is, os);
            Log.d(TAG, "uriToFile: 写入数据");
            return "文件保存到本地，升级时可以直接使用。";
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "uriToFile: " + e.getMessage());
            return "错误异常：" + e.getMessage();
        }
    }

    //将输入流的数据拷贝到输出流
    public static void write(InputStream is, OutputStream os) {
        byte[] buffer = new byte[1024 * 1024];
        while (true) {
            try {
                int len = is.read(buffer);
                if (len < 0) break;
                os.write(buffer, 0, len);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            os.flush();
            is.close();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Uri getUri(String filePath) {
        File tempFile = new File(filePath);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            try {
                return FileProvider.getUriForFile(mContext, mContext.getPackageName() + ".fileprovider", tempFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return Uri.fromFile(tempFile);
        }
        return null;
    }

    public byte[] getBinFile(String filePath) {
        Uri uri = getUri(filePath);
        try {
            return streamToBytes(mContext.getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int copy(final InputStream input, final OutputStream output) throws IOException {
        final long count = copyLarge(input, output);
        if (count > Integer.MAX_VALUE) {
            return -1;
        }
        return (int) count;
    }

    public static long copyLarge(final InputStream input, final OutputStream output)
            throws IOException {
        return copy(input, output, 1024 * 4);
    }

    public static long copy(final InputStream input, final OutputStream output, final int bufferSize)
            throws IOException {
        return copyLarge(input, output, new byte[bufferSize]);
    }

    public static long copyLarge(final InputStream input, final OutputStream output, final byte[] buffer)
            throws IOException {
        long count = 0;
        int n;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }

    public static byte[] streamToBytes(InputStream input) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int n = 0;
        try {
            while (-1 != (n = input.read(buffer))) {
                output.write(buffer, 0, n);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output.toByteArray();
    }

    // 根据Android版本的不同 获取设备外部存储路径
    public static String getSDPath(Context context){
        String sdPath = "";
        boolean isSDExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED); //判断SD卡是否存在
        if (isSDExist) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                File externalFileRootDir = context.getExternalFilesDir("");
                do {
                    externalFileRootDir = Objects.requireNonNull(externalFileRootDir).getParentFile();
                } while (Objects.requireNonNull(externalFileRootDir).getAbsolutePath().contains("/Android"));
                sdPath = Objects.requireNonNull(externalFileRootDir).getAbsolutePath();
            } else {
                sdPath = Environment.getExternalStorageDirectory().getAbsolutePath();
            }
        } else {
            sdPath = Environment.getRootDirectory().toString();//获取跟目录
        }
        return sdPath;
    }

    /**
     * 是SLB升级模式的升级文件
     */
    public boolean isSlbFile(String fileName) {
        return fileName.endsWith(".bin");
    }

    /**
     * 是Single Bank升级模式下的升级文件
     * @param fileName
     * @return
     */
    public boolean isSbhFile(String fileName) {
        return fileName.endsWith(".hex16")
                || fileName.endsWith(".hex")
                || fileName.endsWith(".hexe")
                || fileName.endsWith(".res")
                || fileName.endsWith(".hexe16");
    }

    public boolean isSbhHexFile(String fileName) {
        return fileName.endsWith(".hex16")
                || fileName.endsWith(".hex")
                || fileName.endsWith(".hexe");
    }

    public boolean isSbhResFile(String fileName) {
        return fileName.endsWith(".res");
    }

    public boolean isSbhEncryptFile(String fileName) {
        return fileName.endsWith(".hexe16");
    }


}
