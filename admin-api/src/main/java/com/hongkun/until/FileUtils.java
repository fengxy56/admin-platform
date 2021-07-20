package com.hongkun.until;

import com.google.common.collect.Lists;
import com.hongkun.model.hx.FileModel;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * @ClassName FileUtils
 * @Description 这里描述
 * @Author admin
 * @Date 2020/7/27 14:49
 */
@Slf4j
@Component
public class FileUtils {

    private static List<String> SUPPORT_FILE_SUFFIX = Lists.newArrayList("jpg", "png");

    private static List<String> imageContentTypes = Lists.newArrayList(
            "image/heic",
            "image/gif",
            "image/jpeg",
            "image/bmp",
            "image/png",
            "image/jpg"
    );


    /**
     * 检查该path是否是路径
     *
     * @param path
     * @return
     */
    public static boolean checkDirectory(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.isDirectory();
    }

    /**
     * 检测该参数是图片后缀的
     * 暂时只支持jpg png
     *
     * @param path
     * @return
     */
    public static boolean checkImg(String path) {
        File file = new File(path);
        String fileName = file.getName();
        int i = fileName.indexOf(".");
        String suff = "";
        if (i != -1) {
            suff = fileName.substring(i + 1);
        }
        boolean isImg = false;
        if (StringUtils.isNotBlank(suff)) {
            //SUPPORT_FILE_SUFFIX是一个list（"jpg","png"）
            boolean contains = SUPPORT_FILE_SUFFIX.contains(suff);
            if (contains) {
                isImg = true;
            }

        }
        return isImg;
    }


    /**
     * 文件上传接口
     *
     * @param multipartFile
     * @param filePath
     * @return
     * @throws IllegalStateException
     * @throws IOException
     */
    public static FileModel uploadFile(MultipartFile multipartFile, String filePath) throws IOException {

        log.info("上传的文件类型multipartFile:" + CommonUntil.getLogReplace(multipartFile.getContentType()));

        //判断文件类型是否图片,如果是图片则调用上传图片方法
        if (imageContentTypes.contains(multipartFile.getContentType())) {
            log.info("上传的图片类型contentType:" + CommonUntil.getLogReplace(multipartFile.getContentType()));
            return uploadImg(multipartFile, filePath);
        }


        String fileName = multipartFile.getOriginalFilename();
        String fileId = CodeGeneratedUtil.genUUID();
        String fileSuffix = fileName.substring(fileName.lastIndexOf("."));


        File file = new File(filePath, fileId + fileSuffix);
        if (!file.exists()) {
            file.mkdirs();
        }
        // 保存文件
        multipartFile.transferTo(file);

        String original = fileId + fileSuffix;
        return new FileModel(fileId, fileName, original, null, 3);
    }


    public static FileModel uploadImg(MultipartFile multipartFile, String filePath) throws IOException {

        // 原始图片路径
        String originalPath = filePath + "/original/";
        //文件id
        String originalFilename = multipartFile.getOriginalFilename();
        String fileId = CodeGeneratedUtil.genUUID();
        String fileSuffix = originalFilename.substring(originalFilename.lastIndexOf("."));

        log.info("上传图片原始图片路径originalPath:" + CommonUntil.getLogReplace(originalPath) + ",文件fileId:" + CommonUntil.getLogReplace(fileId));
        //String fileName = FilenameUtils.getName(fileId+ fileSuffix);
        File originalFile = new File(originalPath, FilenameUtils.getName(fileId + fileSuffix));
        if (!originalFile.exists()) {
            originalFile.mkdirs();
        }
        // 保存图片
        multipartFile.transferTo(originalFile);


        BufferedImage source = ImageIO.read(new FileInputStream(originalFile));
        int height = source.getHeight();
        int width = source.getWidth();
        float scale = 1;
        if (height > 1024 && width > 768) {
            scale = 1024f / height;
            float temp = 768f / width;
            if (temp > scale) {
                scale = temp;
            }
        }
        //压缩图片显示图片
        Thumbnails.of(source).scale(scale).toFile(filePath + "/" + fileId + fileSuffix);


        //返回上传后的文件信息
        String original = "original/" + fileId + fileSuffix;
        String thumbnail = fileId + fileSuffix;
        return new FileModel(fileId, originalFilename, original, thumbnail, 1);
    }


}
