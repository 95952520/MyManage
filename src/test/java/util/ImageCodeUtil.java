package util;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 识别图片中的文字(识别验证码非常不准)
 */
public class ImageCodeUtil {

    private static ITesseract instance = new Tesseract();
    static {

        instance.setDatapath(new File("").getAbsolutePath()+"/src/test/tessdata");
//        instance.setPageSegMode(8);
//        instance.setLanguage("eng");
//        instance.setPageSegMode(ITessAPI.TessPageSegMode.PSM_SINGLE_LINE);
    }

    public static String getImageCode(String filePath) throws IOException {
        return getImageCode(new File(filePath));
    }

    public static String getImageCode(File file) throws IOException {
        return getImageCode(ImageIO.read(file));
    }

    public static String getImageCode(BufferedImage bi){
        String result = null;
        try {
            result = instance.doOCR(bi);
        } catch (TesseractException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void soutAllResult(String filePath){
        for (int i = 1; i < 14; i++) {
            instance.setPageSegMode(i);
            String result = null;
            try {
                result = getImageCode(filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("PageSegMode:"+i+";result:"+result);
        }
    }
}
