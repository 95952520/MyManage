package util.api.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.xuchen.util.MyUtils;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;


//@Controller
//@RequestMapping("qrCode")
public class QrCodeController{

    /**
     * 获取页面二维码
     */
//    @RequestMapping(value = "/getQrCode")
    public void getQrCode(HttpServletResponse response, String content) {
        try {
            ByteArrayOutputStream outputStream = QrCode.getQrCodeImageStream(content);
            ServletOutputStream out = response.getOutputStream();
            outputStream.writeTo(out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
