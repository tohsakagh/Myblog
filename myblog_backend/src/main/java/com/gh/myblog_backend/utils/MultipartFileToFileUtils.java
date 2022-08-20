package com.gh.myblog_backend.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

/**
 * myblog-backend-master
 * 2022/8/15 14:04
 * Origin
 */
@SuppressWarnings("all")
public class MultipartFileToFileUtils {

    /**
     * MultipartFile 转 File
     * @param file
     * @throws Exception
     */
    public static File multipartFileToFile(MultipartFile multipartFile ) throws Exception {
        String filePath = "D:/temp/" + UUID.randomUUID().toString();
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        multipartFile.transferTo(targetFile);
        return targetFile;
    }

//    private static File inputStreamToFile(InputStream ins, File file) {
//        try {
//            OutputStream os = new FileOutputStream(file);
//            int bytesRead = 0;
//            byte[] buffer = new byte[8192];
//            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
//                os.write(buffer, 0, bytesRead);
//            }
//            os.close();
//            ins.close();
//            return file;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
}
