package com.hongkun.controller;


import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.hongkun.bean.apply.HxFile;
import com.hongkun.commons.FileEnum;
import com.hongkun.commons.ResultEnum;
import com.hongkun.config.ConfigInfo;
import com.hongkun.exception.GlobalException;
import com.hongkun.model.hx.FileModel;
import com.hongkun.service.apply.HxFileService;
import com.hongkun.until.*;
import com.hongkun.model.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping(value = "/fileUpload")
@Api(tags = {"同事圈图片上传"}, value = "同事圈图片上传")
@Slf4j
public class FileUploadController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HxFileService hxFileService;

    @Autowired
    private ConfigInfo configInfo;

//    @Autowired
//    private FileUtils fileUtils;

    private List<String> imageContentTypes = Lists.newArrayList(
            "image/heic",
            "image/gif",
            "image/jpeg",
            "image/bmp",
            "image/png",
            "image/jpg"
    );


    @ApiOperation(value = "同事圈图片上传")
    @PostMapping(value = "/uploadTsq.do")
    public ResultVO<String> uploadTsq(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request) throws Exception {

        String userName = "fengxiaoyang";

        if (StringUtils.isEmpty(userName)) {
            return ResponseUtil.error(0, "账户不能为空！");
        }


        if (multipartFile.getSize() == 0) {
            return ResponseUtil.error(0, "文件内容为空，请重新选择文件！");
        }

        String fileName = multipartFile.getOriginalFilename();//name
        String tempFilePath = System.getProperty("java.io.tmpdir") + multipartFile.getOriginalFilename();
        File tempFile = new File(tempFilePath);
        multipartFile.transferTo(tempFile);//生成临时文件

        HttpHeaders headers = new HttpHeaders();
        //headers.add( "Accept","image/gif,image/x-xbitmap,image/jpeg,image/pjpeg,application/vnd.ms-excel,application/vnd.ms-powerpoint,application/msword,application/x-shockwave-flash,application/x-quickviewplus,*/*");
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        headers.setContentType(MediaType.parseMediaType("multipart/form-data;charset=UTF-8"));

        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();


        FileSystemResource resource = new FileSystemResource(tempFilePath);//把临时文件变成filesystemresource
        paramMap.add("file", resource);
//        paramMap.add("BizID", userId);

//        String username = UserSession.getUser().getAccountName();

        long timestamp = System.currentTimeMillis();
        //md5加密访问
        String token = MD5Util.getMD5(timestamp + "hkdc20180101" + userName).toUpperCase();


        HttpEntity<MultiValueMap<String, Object>> formEntity = new HttpEntity<>(paramMap, headers);

        String url = configInfo.getTsqUrl() + "/worko-int-web/mains/forward.do?url=upload.do&username=" + userName
                + "&timestamp=" + timestamp + "&token=" + token + "&versionType=hkdc-admin-apply";

        log.info("上传同事圈图片接口,userName:" + userName + ",url:" + url + ",paramMap:" + paramMap.toString());

        String response = restTemplate.postForObject(url, formEntity, String.class);
        JSONObject jsonObject = JSONObject.parseObject(response);
        JSONObject dataObject = (JSONObject) jsonObject.get("data");

        log.info("上传同事圈图片接口,调用接口返回response:" + response);

        return ResponseUtil.success(dataObject);
    }


    /**
     *
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userName", value = "用户账户", example = ""),
            @ApiImplicitParam(paramType = "query", name = "typeId", value = "分类id", example = "")
    })
    @ApiOperation(value = "文件上传")
    @PostMapping("/uploadFile.do")
    @ResponseBody
    public ResultVO uploadFile(HttpServletRequest request, @RequestParam("file") MultipartFile multipartFile,
                               String typeId, String userName, String sysType) {
        FileModel fileModel = null;
        try {

            log.info("上传文件接口uploadFile,projectId:" + CommonUntil.getLogReplace(userName) + ",typeId:" + CommonUntil.getLogReplace(typeId));


            if (multipartFile.getSize() == 0) {
                throw new GlobalException(ResultEnum.PARAM_NOT_NULL);
            }

            if (StringUtils.isBlank(userName) || StringUtils.isBlank(typeId) || StringUtils.isBlank(sysType)) {
                throw new GlobalException(ResultEnum.PARAM_NOT_NULL);
            }

            String filePath = "";
            if (org.apache.commons.lang3.StringUtils.isBlank(typeId)) {
                filePath = configInfo.getFilePath() + "common";
            } else {
                filePath = configInfo.getFilePath() + userName + "/" + typeId;
            }

            HxFile hxFile = new HxFile();
            hxFile.setSysType("signPhoto");
            hxFile.setUserName(userName);
            hxFile.setFileName(multipartFile.getOriginalFilename());
            //判断文件类型是否图片,如果是图片则调用上传图片方法
            if (imageContentTypes.contains(multipartFile.getContentType())) {

                log.info("上传的图片类型contentType:" + CommonUntil.getLogReplace(multipartFile.getContentType()));

                fileModel = FileUtils.uploadImg(multipartFile, filePath);

                hxFile.setFilePath(userName + "/" + typeId + "/" + fileModel.getOriginal());//原图
                hxFile.setImgThumPath(userName + "/" + typeId + "/" + fileModel.getThumbnail());//缩略图
                hxFile.setFileType(FileEnum.FILE_ENUM_IMAGE.getTitle());
                hxFile.setFileId(fileModel.getFileId());


                //返回图片预览图
                fileModel.setOriginal(WorkUntil.getFileUrl(request, fileModel.getFileId(), FileEnum.FILE_ENUM_IMAGE, "0"));//原图
                fileModel.setThumbnail(WorkUntil.getFileUrl(request, fileModel.getFileId(), FileEnum.FILE_ENUM_IMAGE, "1"));//缩略图

            } else {
                fileModel = FileUtils.uploadFile(multipartFile, filePath);

                hxFile.setFilePath(userName + "/" + typeId + "/" + fileModel.getOriginal());//原图
                hxFile.setFileType(FileEnum.FILE_ENUM_VIDEO.getTitle());
                hxFile.setFileId(fileModel.getFileId());

                //返回视频预览图
                fileModel.setOriginal(WorkUntil.getFileUrl(request, fileModel.getFileId(), FileEnum.FILE_ENUM_VIDEO, "0"));//原图

            }

            hxFileService.saveHxFile(hxFile);

        } catch (IOException e) {
            log.info("上传文件接口异常e:" + e);

        }
        return ResponseUtil.success(fileModel);
    }


    /**
     *
     */
    @ApiOperation(value = "文件获取")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "fileId", value = "文件id", example = "77793e11-c8ce-4017-8264-7c8e7a4d9342"),
            @ApiImplicitParam(paramType = "query", name = "imgType", value = "文件类型，0原图，1缩略图", example = "0"),
            @ApiImplicitParam(paramType = "query", name = "fileType", value = "文件类型,img图片,video视频,other其它文件", example = "img")
    })
    @GetMapping(value = "getFileByFileIdAndFileType.do")
    @ResponseBody
    public void getFileByFileIdAndFileType(HttpServletRequest request, HttpServletResponse response,
                                           String fileId, String imgType, String fileType) {

        log.info("文件获取getFileByFileIdAndFileType,fileId:" + CommonUntil.getLogReplace(fileId) + ",imgType:" + CommonUntil.getLogReplace(imgType)
                + ",fileType:" + CommonUntil.getLogReplace(fileType));

        if (org.apache.commons.lang3.StringUtils.isBlank(fileId) || org.apache.commons.lang3.StringUtils.isBlank(imgType) || org.apache.commons.lang3.StringUtils.isBlank(fileType)) {
            throw new GlobalException(ResultEnum.PARAM_NOT_NULL);
        }


        //图片
        if (FileEnum.FILE_ENUM_IMAGE.getTitle().equals(fileType)) {
            this.getImg(request, response, imgType, fileId);
        } else if (FileEnum.FILE_ENUM_VIDEO.getTitle().equals(fileType)) {//视频
            this.getVideo(request, response, fileId);
        }
//
//        this.getImgOrVideo( request,  until,fileId,  imgType,  fileType);


    }


    //获取图片
    private void getImg(HttpServletRequest request, HttpServletResponse response, String imgType, String fileId) {

        FileInputStream in = null;
        BufferedInputStream bis = null;
        OutputStream fos = null;
        BufferedOutputStream bos = null;
        String filePath = "";

        try {

            HxFile hxFile = hxFileService.getHxFileById(fileId);
            if (hxFile != null && "0".equals(imgType)) {
                filePath = configInfo.getFilePath() + "/" + hxFile.getFilePath();
            } else if (hxFile != null && "1".equals(imgType)) {
                filePath = configInfo.getFilePath() + "/" + hxFile.getImgThumPath();
            } else {
                filePath = ResourceUtils.getURL("classpath:").getPath() + "/static/default.png";
            }
            in = new FileInputStream(filePath);

            bis = new BufferedInputStream(in);
            fos = response.getOutputStream();
            bos = new BufferedOutputStream(fos);

            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = bis.read(buffer)) != -1) {
                bos.write(buffer, 0, bytesRead);// 将文件发送到客户端
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭bos输出流，释放资源
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            //关闭bis输出流，释放资源
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //关闭fos输入流，释放资源
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            //关闭输入流，释放资源
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }


    }


    private void getVideo(HttpServletRequest request, HttpServletResponse response, String fileId) {
        try {

            HxFile hxFile = hxFileService.getHxFileById(fileId);
            if (hxFile != null) {
                String filePath = configInfo.getFilePath() + "/" + hxFile.getFilePath();
                File file = new File(filePath);
                this.sendIphoneVideo(request, response, file, fileId);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendIphoneVideo(HttpServletRequest request, HttpServletResponse response, File file, String fileName)
            throws FileNotFoundException, IOException {

        log.info("【视频获取】【iPhone】【开始】file:" + file + ",fileName:" + fileName);

        ServletOutputStream out = null;
        RandomAccessFile randomFile = null;
        try {

            randomFile = new RandomAccessFile(file, "r");//只读模式
            long contentLength = randomFile.length();
            String range = request.getHeader("Range");
            int start = 0, end = 0;
            if (range != null && range.startsWith("bytes=")) {
                String[] values = range.split("=")[1].split("-");
                start = Integer.parseInt(values[0]);
                if (values.length > 1) {
                    end = Integer.parseInt(values[1]);
                }
            }
            int requestSize = 0;
            if (end != 0 && end > start) {
                requestSize = end - start + 1;
            } else {
                requestSize = Integer.MAX_VALUE;
            }

            byte[] buffer = new byte[4096];
            response.setContentType("video/mp4");
            response.setHeader("Accept-Ranges", "bytes");
            response.setHeader("ETag", fileName);
            response.setHeader("Last-Modified", new Date().toString());
            //第一次请求只返回content length来让客户端请求多次实际数据
            if (range == null) {
                response.setHeader("Content-length", contentLength + "");
            } else {
                //以后的多次以断点续传的方式来返回视频数据
                response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);//206
                long requestStart = 0, requestEnd = 0;
                String[] ranges = range.split("=");
                if (ranges.length > 1) {
                    String[] rangeDatas = ranges[1].split("-");
                    requestStart = Integer.parseInt(rangeDatas[0]);
                    if (rangeDatas.length > 1) {
                        requestEnd = Integer.parseInt(rangeDatas[1]);
                    }
                }
                long length = 0;
                if (requestEnd > 0) {
                    length = requestEnd - requestStart + 1;
                    response.setHeader("Content-length", "" + length);
                    response.setHeader("Content-Range", "bytes " + requestStart + "-" + requestEnd + "/" + contentLength);
                } else {
                    length = contentLength - requestStart;
                    response.setHeader("Content-length", "" + length);
                    response.setHeader("Content-Range", "bytes " + requestStart + "-" + (contentLength - 1) + "/" + contentLength);
                }
            }


            out = response.getOutputStream();
            int needSize = requestSize;
            randomFile.seek(start);
            while (needSize > 0) {
                int len = randomFile.read(buffer);
                if (needSize < buffer.length) {
                    out.write(buffer, 0, needSize);
                } else {
                    out.write(buffer, 0, len);
                    if (len < buffer.length) {
                        break;
                    }
                }
                needSize -= buffer.length;
            }
            randomFile.close();
            out.close();

            log.info("【视频获取】【iPhone】【结束】file:" + file + ",fileName:" + fileName);

        } catch (Exception e) {

            log.info("【视频获取】【iPhone】【异常】file:" + file + ",fileName:" + fileName);
        } finally {
            if (out != null) {
                out.close();
            }
            if (randomFile != null) {
                randomFile.close();
            }
        }


    }

}