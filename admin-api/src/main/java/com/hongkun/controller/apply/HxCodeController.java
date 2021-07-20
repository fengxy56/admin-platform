package com.hongkun.controller.apply;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hongkun.bean.apply.HxCodeInfo;
import com.hongkun.bean.apply.HxFile;
import com.hongkun.commons.FileEnum;
import com.hongkun.config.ConfigInfo;
import com.hongkun.query.apply.HxCodeInfoQuery;
import com.hongkun.model.vo.ResultVO;
import com.hongkun.service.apply.HxCodeInfoService;
import com.hongkun.service.apply.HxFileService;
import com.hongkun.until.CodeGeneratedUtil;
import com.hongkun.until.ResponseUtil;
import com.hongkun.until.ZxingUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;


/**
 * 画制定logo和制定描述的二维码
 *
 * @author songyz
 */
@RestController
@RequestMapping("/hxCode")
@Api(tags = "二维码信息")
@Slf4j
public class HxCodeController {

    @Resource
    private HxCodeInfoService hxCodeInfoService;

    @Resource
    private HxFileService hxFileService;

    @Resource
    private ConfigInfo configInfo;

    /**
     * @return
     */
    //@PostMapping(value = "/getHxCodeInfoList")
    @RequestMapping(value = "/getHxCodeInfoList", method = RequestMethod.POST)
    @ApiOperation(value = "查询二维码信息", notes = "")
    public ResultVO<HxCodeInfo> getHxCodeInfoList(HttpServletRequest request, @RequestBody HxCodeInfoQuery hxCodeInfoQuery) {

        IPage<HxCodeInfo> pageList = hxCodeInfoService.getHxCodeInfoList(request, hxCodeInfoQuery);

        return ResponseUtil.success(pageList);
    }


    /**
     * @return
     */
    @RequestMapping(value = "/saveOrUpdateHxCodeInfo", method = RequestMethod.POST)
    @ApiOperation(value = "保存或者修改二维码信息", notes = "")
    public ResultVO<Integer> saveOrUpdateHxCodeInfo(@RequestBody HxCodeInfoQuery hxCodeInfoQuery) {


        //File logoFile = new File("D://hkdata/logo.png"); //logo
        String fileId = CodeGeneratedUtil.genUUID();
        String filePath = "qrcode/" + fileId + ".png";
        File QrCodeFile = new File(configInfo.getFilePath() + filePath); //二维码生成地址以及名称格式
        ZxingUtils.drawLogoQRCode(null, QrCodeFile, hxCodeInfoQuery.getGoUrl(), hxCodeInfoQuery.getName());
        hxCodeInfoQuery.setDownUrl(fileId);

        HxFile hxFile = new HxFile();
        hxFile.setSysType("qrcode");
        hxFile.setFileName(hxCodeInfoQuery.getName());
        hxFile.setFileId(fileId);
        ;
        hxFile.setFilePath(filePath);
        hxFile.setFileType(FileEnum.FILE_ENUM_IMAGE.getTitle());
        hxFileService.saveHxFile(hxFile);


        hxCodeInfoService.saveOrUpdateHxCodeInfo(hxCodeInfoQuery);

        return ResponseUtil.success();
    }


//
//    @ApiOperation("获取小程序二维码")
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "query", dataType = "string", name = "uuid", value = "二维码id", example = "519c0d02a67242118ad4ad5ae79104f5", required = true),
//    })
//    @GetMapping(value = "/getMiniCode", produces = MediaType.IMAGE_JPEG_VALUE)
//    public BufferedImage getMiniCode(String uuid) {
//        if (Strings.isBlank(uuid)) {
//            throw new GlobalException(ResultEnum.PARAM_ERROR);
//        }
//        FileInputStream in = null;
//        try {
//            File imageFile = new File(new StringBuilder(configInfo.getFilePath()).append("/xcxCode/").append(uuid).append(".jpeg").toString());
//            if (imageFile.exists()) {
//                //读文件
//                in = new FileInputStream(imageFile);
//            } else {
//                String path = ResourceUtils.getURL("classpath:").getPath();
//                in = new FileInputStream(path + "/static/default.jpg");
//            }
//            return ImageIO.read(in);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            //关闭输入流，释放资源
//            if (in != null) {
//                try {
//                    in.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return null;
//    }


}