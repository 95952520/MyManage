package util.api.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

public class QrCode {

    private static MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
    private static HashMap map;
    private static int width = 400;
    private static int height = 400;
    private static final int QRCOLOR = 0xFF000000;   //默认是黑色
    private static final int BGWHITE = 0xFFFFFFFF;   //背景颜色

    static {
        //设置编码
        map = new HashMap();
        map.put(EncodeHintType.CHARACTER_SET, "utf-8");
        //设置纠错等级
        map.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        map.put(EncodeHintType.MARGIN, 0);
    }

    /**
     * 生成二维码
     * @param content 文字
     * @param resultFile file对象
     * @throws IOException
     */
    public static void getQrCodeImage(String content,File resultFile) throws IOException, WriterException {
        BitMatrix bitMatrix = null;
        bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, width, height, map);
        BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
        createQrCodeFile(bufferedImage,resultFile);
    }

    /**
     * 生成带logo的二维码
     * @param content 文字
     * @param logoFile logo的图片file
     */
    public static void getQrLogoCodeImage(String content, File logoFile,File resultFile) throws IOException {
        BufferedImage bufferedImage = createBufferedImage(content);
        updateLogo(bufferedImage, logoFile);
        createQrCodeFile(bufferedImage,resultFile);
    }

    /**
     * 生成带logo的二维码
     * @param content 文字
     * @param logoUrl logo的图片url
     */
    public static void getQrLogoCodeImage(String content, String logoUrl,File resultFile) throws IOException {
        BufferedImage image = createBufferedImage(content);
        updateLogo(image,ImageIO.read(new URL(logoUrl)));
        createQrCodeFile(image,resultFile);
    }

    /**
     * 生成二维码OutputStream
     * @param content 文字
     * @throws IOException
     */
    public static ByteArrayOutputStream getQrCodeImageStream(String content) throws IOException {
        BitMatrix bitMatrix = null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, width, height, map);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);
        outputStream.flush();
        ImageIO.write(image,"jpg",outputStream);
        return outputStream;
    }

    /**
     * 生成带logo的二维码OutputStream
     * @param content 文字
     * @param logoFile logo的图片file
     */
    public static ByteArrayOutputStream getQrLogoCodeImageStream(String content, File logoFile) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        BufferedImage image = createBufferedImage(content);
        updateLogo(image, logoFile);
        outputStream.flush();
        ImageIO.write(image,"jpg",outputStream);
        return outputStream;
    }

    /**
     * 生成带logo的二维码OutputStream
     * @param content 文字
     * @param logoUrl logo的图片url
     */
    public static ByteArrayOutputStream getQrLogoCodeImageStream(String content, String logoUrl) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        BufferedImage image = createBufferedImage(content);
        updateLogo(image,ImageIO.read(new URL(logoUrl)));
        outputStream.flush();
        ImageIO.write(image,"jpg",outputStream);
        return outputStream;
    }

    private static void createQrCodeFile(BufferedImage read,File resultFile) throws IOException {
        if (!resultFile.getName().endsWith(".jpg")){
            resultFile = new File(resultFile.getAbsolutePath()+".jpg");
        }
        ImageIO.write(read,"jpg",resultFile);
    }

    private static BufferedImage createBufferedImage(String content) {
        MultiFormatWriter multiFormatWriter;
        BitMatrix bm;
        BufferedImage image = null;
        try {
            multiFormatWriter = new MultiFormatWriter();
            // 参数顺序分别为：编码内容，编码类型，生成图片宽度，生成图片高度，设置参数
            bm = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, width, height, map);
            int w = bm.getWidth();
            int h = bm.getHeight();
            image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
            // 开始利用二维码数据创建Bitmap图片，分别设为黑（0xFFFFFFFF）白（0xFF000000）两色
            for (int x = 0; x < w; x++) {
                for (int y = 0; y < h; y++) {
                    image.setRGB(x, y, bm.get(x, y) ? QRCOLOR : BGWHITE);
                }
            }
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return image;
    }

    private static void updateLogo(BufferedImage bim, File logoPic) throws IOException {
        BufferedImage logo = ImageIO.read(logoPic);
        updateLogo(bim,logo);
    }

    private static void updateLogo(BufferedImage bim, BufferedImage logo) throws IOException {
        BufferedImage image = bim;
        Graphics2D g = image.createGraphics();
        int widthLogo = logo.getWidth(null) > image.getWidth() * 1 / 5 ? (image.getWidth() * 1 / 5) : logo.getWidth(null),
                heightLogo = logo.getHeight(null) > image.getHeight() * 1 / 5 ? (image.getHeight() * 1 / 5) : logo.getWidth(null);
        int x = (image.getWidth() - widthLogo) / 2;
        int y = (image.getHeight() - heightLogo) / 2;
        g.drawImage(logo, x, y, widthLogo, heightLogo, null);
        g.dispose();
        logo.flush();
        image.flush();
    }
}
