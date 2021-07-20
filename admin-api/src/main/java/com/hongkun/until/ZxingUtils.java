package com.hongkun.until;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.hongkun.exception.GlobalException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Created by zhao_xinhu
 * On 2018/10/18
 * 生成二维码utils
 */
@Slf4j
public class ZxingUtils {

    private static Logger logger = LoggerFactory.getLogger(ZxingUtils.class);

    private static final int width = 300;//默认二维码宽度

    private static final int height = 300;//默认二维码高度

    private static final String format = "png";//默认二维码格式

    private static final Map<EncodeHintType, Object> hints = new HashMap<>();//二维码参数

    private static final int logo_width = 60;//默认logo宽度

    private static final int logo_height = 60;//默认logo高度


    private static final int QRCOLOR = 0xFF000000; // 默认是黑色
    private static final int BGWHITE = 0xFFFFFFFF; // 背景颜色
    private static final int WIDTH = 400; // 二维码宽
    private static final int HEIGHT = 400; // 二维码高


    static {
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");//字符编码
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);//容错等级L/M/Q/H其中L为最低，H为最高
        hints.put(EncodeHintType.MARGIN, 2);//二维码与图片边距
    }


    // 生成带logo的二维码图片
    public static void drawLogoQRCode(File logoFile, File codeFile, String qrUrl, String note) {
        try {
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            // 参数顺序分别为：编码内容，编码类型，生成图片宽度，生成图片高度，设置参数
            BitMatrix bm = multiFormatWriter.encode(qrUrl, BarcodeFormat.QR_CODE, WIDTH, HEIGHT, hints);
            BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

            // 开始利用二维码数据创建Bitmap图片，分别设为黑（0xFFFFFFFF）白（0xFF000000）两色
            for (int x = 0; x < WIDTH; x++) {
                for (int y = 0; y < HEIGHT; y++) {
                    image.setRGB(x, y, bm.get(x, y) ? QRCOLOR : BGWHITE);
                }
            }

            int width = image.getWidth();
            int height = image.getHeight();
            if (Objects.nonNull(logoFile) && logoFile.exists()) {
                // 构建绘图对象
                Graphics2D g = image.createGraphics();
                // 读取Logo图片
                BufferedImage logo = ImageIO.read(logoFile);
                // 开始绘制logo图片
                g.drawImage(logo, width * 2 / 5, height * 2 / 5, width * 2 / 10, height * 2 / 10, null);
                g.dispose();
                logo.flush();
            }

            // 自定义文本描述
            if (StringUtils.isNotEmpty(note)) {
                // 新的图片，把带logo的二维码下面加上文字
                BufferedImage outImage = new BufferedImage(400, 445, BufferedImage.TYPE_4BYTE_ABGR);
                Graphics2D outg = outImage.createGraphics();
                // 画二维码到新的面板
                outg.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
                // 画文字到新的面板
                outg.setColor(Color.BLACK);
                outg.setFont(new Font("楷体", Font.BOLD, 30)); // 字体、字型、字号
                int strWidth = outg.getFontMetrics().stringWidth(note);
                if (strWidth > 399) {
                    // //长度过长就截取前面部分
                    // 长度过长就换行
                    String note1 = note.substring(0, note.length() / 2);
                    String note2 = note.substring(note.length() / 2, note.length());
                    int strWidth1 = outg.getFontMetrics().stringWidth(note1);
                    int strWidth2 = outg.getFontMetrics().stringWidth(note2);
                    outg.drawString(note1, 200 - strWidth1 / 2, height + (outImage.getHeight() - height) / 2 + 12);
                    BufferedImage outImage2 = new BufferedImage(400, 485, BufferedImage.TYPE_4BYTE_ABGR);
                    Graphics2D outg2 = outImage2.createGraphics();
                    outg2.drawImage(outImage, 0, 0, outImage.getWidth(), outImage.getHeight(), null);
                    outg2.setColor(Color.BLACK);
                    outg2.setFont(new Font("黑体", Font.BOLD, 30)); // 字体、字型、字号
                    outg2.drawString(note2, 200 - strWidth2 / 2, outImage.getHeight() + (outImage2.getHeight() - outImage.getHeight()) / 2 + 5);
                    outg2.dispose();
                    outImage2.flush();
                    outImage = outImage2;
                } else {
                    outg.drawString(note, 200 - strWidth / 2, height + (outImage.getHeight() - height) / 2 + 12); // 画文字
                }
                outg.dispose();
                outImage.flush();
                image = outImage;
            }

            image.flush();


            log.info("codeFile.getCanonicalPath():" + codeFile.getCanonicalPath());

            File originalFile = new File(codeFile.getCanonicalPath());
            if (!originalFile.exists()) {
                originalFile.mkdirs();
            }

            ImageIO.write(image, "png", codeFile); // TODO
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {

        File logoFile = new File("D://hkdata/logo.png"); //logo
        File QrCodeFile = new File("D://hkdata/123.png"); //二维码生成地址以及名称格式
        String url = "https://www.baidu.com/"; // 扫描二维码进入网址
        String note = "周年庆签到";    //标题
        ZxingUtils.drawLogoQRCode(logoFile, QrCodeFile, url, note);
//
//
//        String srcUrl = "http://www.baidu.com";
//        String imgPath = "d:/QR/";
//        int imgWidth = 400;
//        int imgHeight = 400;
//        String logoPath = "D://hkdata/logo.png";
//        boolean needCompress = true;
//
//        try{
//            //生成 默认尺寸，默认名称，无logo
//            String orCode = ZxingUtils.createORCode(srcUrl, imgPath);
//            //生成 指定尺寸，默认名称，无logo
//            String orCode2 = ZxingUtils.createORCode(srcUrl,imgPath,imgWidth,imgHeight);
//            //生成指定尺寸，指定名称，无logo
//            String orCode3 = ZxingUtils.createORCode(srcUrl,imgPath,imgWidth,imgHeight,"指定的名称");
//            System.out.println("默认尺寸、默认名称、无logo地址：" + orCode);
//            System.out.println("指定尺寸、默认名称、无logo地址：" + orCode2);
//            System.out.println("指定尺寸、指定名称、无logo地址：" + orCode3);
//            //生成 默认尺寸，默认名称，有logo
//            String orCodeLogo = ZxingUtils.createORCodeLogo(srcUrl,imgPath,logoPath,needCompress);
//            //生成 指定尺寸 默认名称，有logo
//            String orCodeLogo2 = ZxingUtils.createORCodeLogo(srcUrl,imgPath,imgWidth,imgHeight,logoPath,needCompress);
//            //生成 指定尺寸，指定名称，有logo
//            String orCodeLogo3 = ZxingUtils.createORCodeLogo(srcUrl,imgPath,imgWidth,imgHeight,"包含logo指定名称",logoPath,needCompress);
//            System.out.println("默认尺寸、默认名称、有logo地址：" + orCodeLogo);
//            System.out.println("指定尺寸、默认名称、有logo地址：" + orCodeLogo2);
//            System.out.println("指定尺寸、指定名称、有logo地址：" + orCodeLogo3);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return;

    }


    /**
     * 生成 默认尺寸、默认名字 无logo 二维码
     *
     * @param srcUrl
     * @param imgPath
     * @return
     * @throws Exception
     */
    public static String createORCode(String srcUrl, String imgPath) throws Exception {
        String finalPath = createORCode(srcUrl, imgPath, width, height, null);
        return finalPath;
    }

    /**
     * 生成 指定大小 指定名称 无logo 二维码
     *
     * @param srcUrl
     * @param imgPath
     * @param imgWidth
     * @param imgHeight
     * @param imgName
     * @return
     */
    public static String createORCode(String srcUrl, String imgPath, int imgWidth, int imgHeight, String imgName) throws Exception {
        String orCode = createORCode(srcUrl, imgPath, imgWidth, imgHeight, imgName, null, false);
        return orCode;
    }

    /**
     * 生成 指定尺寸，默认名称，无logo 的二维码
     *
     * @param srcUrl
     * @param imgPath
     * @param imgWidth
     * @param imgHeight
     * @return
     * @throws WriterException
     * @throws IOException
     */
    public static String createORCode(String srcUrl, String imgPath, int imgWidth, int imgHeight) throws Exception {
        String finalPath = createORCode(srcUrl, imgPath, imgWidth, imgHeight, null, null, false);
        return finalPath;
    }

    /**
     * 生成 默认尺寸，默认名称，包含logo 二维码
     *
     * @param srcUrl
     * @param imgPath
     * @param logoPath
     * @param needCompress
     * @return
     */
    public static String createORCodeLogo(String srcUrl, String imgPath, String logoPath, boolean needCompress) throws Exception {
        String orCodeLogo = createORCodeLogo(srcUrl, imgPath, width, height, logoPath, needCompress);
        return orCodeLogo;
    }

    /**
     * 生成 指定大小 默认名称 包含logo 二维码
     *
     * @param srcUrl
     * @param imgPath
     * @param imgWidth
     * @param imgHeight
     * @param logoPath
     * @param needCompress
     * @return
     */
    public static String createORCodeLogo(String srcUrl, String imgPath, int imgWidth, int imgHeight, String logoPath, boolean needCompress) throws Exception {
        String orCodeLogo = createORCodeLogo(srcUrl, imgPath, imgWidth, imgHeight, null, logoPath, needCompress);
        return orCodeLogo;
    }

    /**
     * 生成 指定大小 指定名称 包含logo 二维码
     *
     * @param srcUrl
     * @param imgPath
     * @param imgWidth
     * @param imgHeight
     * @param imgName
     * @param logoPath
     * @param needCompress
     * @return
     * @throws Exception
     */
    public static String createORCodeLogo(String srcUrl, String imgPath, int imgWidth, int imgHeight, String imgName, String logoPath, boolean needCompress) throws Exception {
        String orCode = createORCode(srcUrl, imgPath, imgWidth, imgHeight, imgName, logoPath, needCompress);
        return orCode;
    }

    /**
     * 生成 指定尺寸、指定名称、包含logo 的二维码
     * 如果logoPath为null，则默认生成不包含logo的二维码
     * 如果确定生成不包含logo的二维码，则可以调用另外指定方法
     *
     * @param srcUrl       需要生成二维码地址
     * @param imgPath      需要保存二维码的目录地址
     * @param imgWidth     需要生成二维码的宽度
     * @param imgHeight    需要生成二维码的高度
     * @param logoPath     需要生成二维码的中间logo
     * @param needCompress logo是否需要压缩
     * @return
     * @throws Exception
     */
    private static String createORCode(String srcUrl, String imgPath, int imgWidth, int imgHeight, String imgName, String logoPath, boolean needCompress) throws Exception {

        boolean isDirectory = FileUtils.checkDirectory(imgPath);
        if (!isDirectory) {
            logger.info("请用户选择正确的保存二维码的路径");
            throw new GlobalException(0, "错误图片路径");
        }
        String finalPath = "";
        //如果logo图片是null，生成不包含logo的二维码，否则生成含有logo的二维码
        if (StringUtils.isNotBlank(logoPath)) {
            boolean b = FileUtils.checkImg(logoPath);
            if (b) {
                finalPath = createORCodeForLogo(srcUrl, imgPath, imgWidth, imgHeight, imgName, logoPath, needCompress);
            } else {
                logger.info("请用户选择正确的logo格式jpg or png");
                throw new GlobalException(0, "错误图片后缀");
            }
        } else {
            finalPath = createORCodeNotLogo(srcUrl, imgPath, imgWidth, imgHeight, imgName);
        }
        return finalPath;
    }


    /**
     * 创建一个 指定大小、指定名称、包含logo 二维码
     *
     * @param srcUrl
     * @param imgPath
     * @param imgWidth
     * @param imgHeight
     * @param imgName
     * @param logoPath
     * @param needCompress
     * @throws WriterException
     * @throws IOException
     */
    private static String createORCodeForLogo(String srcUrl, String imgPath, int imgWidth, int imgHeight, String imgName, String logoPath, boolean needCompress) throws WriterException, IOException {

        BufferedImage bufferedImage = drawQR(srcUrl, imgWidth, imgHeight);
        Image src = enterFinalLogoImg(logoPath, needCompress);
        insertLogo(bufferedImage, src);
        String finalPath = outImgToPath(bufferedImage, format, imgPath, imgName);
        return finalPath;
    }

    /**
     * 生成指定大小，指定名称，无logo的二维码
     *
     * @param srcUrl
     * @param imgPath
     * @param imgWidth
     * @param imgHeight
     * @param imgName
     * @return
     * @throws WriterException
     * @throws IOException
     */
    private static String createORCodeNotLogo(String srcUrl, String imgPath, int imgWidth, int imgHeight, String imgName) throws WriterException, IOException {
        BufferedImage bufferedImage = drawQR(srcUrl, imgWidth, imgHeight);
        String s = outImgToPath(bufferedImage, format, imgPath, imgName);
        return s;
    }

    /**
     * 绘制二维码
     *
     * @param srcUrl
     * @param imgWidth
     * @param imgHeight
     * @return
     * @throws WriterException
     */
    private static BufferedImage drawQR(String srcUrl, int imgWidth, int imgHeight) throws WriterException {
        BitMatrix bitMatrix = new MultiFormatWriter().encode(srcUrl, BarcodeFormat.QR_CODE, imgWidth, imgHeight, hints);

        BufferedImage bufferedImage = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_BGR);
        //绘制成二维码（应该）
        for (int x = 0; x < imgWidth; x++) {
            for (int y = 0; y < imgHeight; y++) {
                bufferedImage.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000
                        : 0xFFFFFFFF);
            }
        }
        return bufferedImage;
    }

    /**
     * 确定最终需要添加的logo大小（是否需要压缩）
     *
     * @param logoPath
     * @param needCompress
     * @return
     * @throws IOException
     */
    private static Image enterFinalLogoImg(String logoPath, boolean needCompress) throws IOException {
        //插入logo
        BufferedImage logoImage = ImageIO.read(new File(logoPath));
        int tempWidth = logoImage.getWidth(null);
        int tempHeight = logoImage.getHeight(null);
        //最终确定的logo图片
        Image src = logoImage;
        //需要压缩
        if (needCompress) {
            if (tempWidth > logo_width) {
                tempWidth = logo_width;
            }
            if (tempHeight > logo_height) {
                tempHeight = logo_height;
            }
            Image scaledInstance = logoImage.getScaledInstance(tempWidth, tempHeight, Image.SCALE_SMOOTH);
            BufferedImage tag = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics graphics = tag.getGraphics();
            graphics.drawImage(scaledInstance, 0, 0, null);
            graphics.dispose();
            src = scaledInstance;
        }
        return src;
    }

    /**
     * 将二维码中间插入logo
     *
     * @param img
     * @param logo
     */
    private static void insertLogo(BufferedImage img, Image logo) {
        Graphics2D graphics2D = img.createGraphics();
        int imgWidth = img.getWidth(null);
        int imgHeight = img.getHeight(null);
        //将logo定位到中间位置
        int logoWidth = logo.getWidth(null);
        int logoHeight = logo.getHeight(null);
        int x = (imgWidth - logoWidth) / 2;
        int y = (imgHeight - logoHeight) / 2;
        graphics2D.drawImage(logo, x, y, logoWidth, logoHeight, null);
        Shape shape = new RoundRectangle2D.Float(x, y, logoWidth, logoHeight, 6, 6);
        graphics2D.setStroke(new BasicStroke(3f));
        graphics2D.draw(shape);
        graphics2D.dispose();
    }

    /**
     * 将二维码保存到指定位置
     *
     * @param image
     * @param format  生成二维码格式、暂时png格式
     * @param imgPath 生成二维码路径
     * @param imgName 生成二维码名称
     * @return 返回生成二维码的图片地址
     * @throws IOException
     */
    private static String outImgToPath(BufferedImage image, String format, String imgPath, String imgName) throws IOException {
        if (StringUtils.isBlank(imgName))
            imgName = UUID.randomUUID().toString();
        String finalImgPath = imgPath + imgName + "." + format;
        ImageIO.write(image, format, new File(finalImgPath));
        return finalImgPath;
    }
}