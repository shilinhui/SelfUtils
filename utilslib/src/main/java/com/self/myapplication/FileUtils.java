package com.self.myapplication;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtils {
    public static final String TAG = "FileUtils";
    private final static String SIZE_NAME[] ={"B","KB","MB","GB"};

    /**
     * 创建文件
     */
    public static boolean createFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            deleteFileOrDir(path);
        }
        String parentDir = file.getParent();
        if (parentDir != null && !fileExist(parentDir)) {
            new File(parentDir).mkdirs();
        }

        boolean success = true;
        try {
            file.createNewFile();
        } catch (IOException e) {
            Log.d(TAG, "createFile, path:" + path + ",error:" + e.getMessage());
            success = false;
        }
        return success;
    }

    /**
     * 获取文件大小
     */
    public static long getFileSize(String path) {
        if (path == null) {
            Log.d(TAG, "given path null");
            return -1;
        }
        File file = new File(path);
        if (!file.exists()) {
            Log.d(TAG, "getFileSize file is not exists");
            return -1;
        }
        if (!file.isFile()) {
            Log.d(TAG, " given path it is not a file,or the file is not exist now path:" + path);
            return -1;
        }

        return file.length();
    }

    /**
     * 文件是否存在
     */
    public static boolean fileExist(String path) {
        File file = new File(path);
        return file.exists();
    }

    /**
     * 删除文件
     */
    public static boolean deleteFileOrDir(String dir) {
        boolean r = deleteFileOrDirQuietly(dir);
        if(r){
            Log.d(TAG, "deleteFileOrDir success, " + dir);
        }
        return r;
    }

    public static boolean deleteFileOrDirQuietly(String dir) {
        boolean bSuccess = false;
        File fileDir = null;
        try {
            fileDir = new File(dir);
            if (!fileDir.exists()) {
                return false;
            }

        } catch (Exception e) {
            Log.d(TAG, "delete file or dir : " + dir + ",failed, "
                    + e.getMessage());
            return false;
        }

        if (fileDir.isFile()) {
            return fileDir.delete();
        }
        File[] filesInDir = fileDir.listFiles();
        if (filesInDir == null || fileDir.length() <= 0) {
            fileDir.delete();
            return true;
        }

        boolean bSomeFailed = false;
        for (File file : filesInDir) {
            if (!file.exists()) {
                break;
            }
            if (file.isFile()) {
                if (!file.delete()) {
                    bSomeFailed = true;
                }
            }
            if (file.isDirectory()) {
                if (!deleteFileOrDir(file.getAbsolutePath())) {
                    bSomeFailed = true;
                }
            }
        }
        bSuccess = !bSomeFailed;
        fileDir.delete();

        return bSuccess;

    }

    /**
     * 复制文件
     */
    public static boolean copyFile(String source, String target) {
        boolean state = false;
        if (!isFile(source)) {
            Log.d(TAG, source + " is not a file!!!");
            return false;
        }

        if (!createFile(target)) {
            Log.d(TAG, "create file error::" + target);
            return false;
        }

        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(source);
            fos = new FileOutputStream(target);
            byte [] buffer = new byte[1024];
            while(true) {
                int len = fis.read(buffer);
                if (len == -1) {
                    break;
                }
                fos.write(buffer, 0, len);
            }
        } catch (FileNotFoundException e) {
            state = false;
            e.printStackTrace();
        } catch (IOException e) {
            state = false;
            e.printStackTrace();
        } finally {
            try {
                fis.close();
                fos.close();
                state = true;
            } catch (Exception e) {
                state = false;
                e.printStackTrace();
            }
        }
        return state;
    }

    public static boolean isFile(String path) {
        return new File(path).isFile();
    }

    /**
     * 将字节转化为单位大小
     */
    public static String getSizeName(long fileSize){
        double size = fileSize;
        if(fileSize <0)
            return "0";
        int index = 0;
        while(size/1024>1){
            index++;
            size = size/1024;
        }
        return String.format("%.2f", size) + SIZE_NAME[index];
    }
}
