package com.xuchen.controller.base;

import com.xuchen.base.Result;
import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

public class UploadFileController extends BaseController{

    @RequestMapping(value = "/uploadImg", method = RequestMethod.POST)
    @ResponseBody
    Result uploadImg(MultipartFile file, String remark) {
        if(file ==  null){
            return Result.fail("图片不存在");
        }
        if (!Pattern.matches("^.*\\.(jpg|png|bmp)$", file.getOriginalFilename())) {
            return Result.fail("图片必须为jpg、png、bmp格式，请重新选择图片");
        }

        String path;
        try {
            BufferedImage image = ImageIO.read(file.getInputStream());
            String extName = ""; // 扩展名格式：
            if (file.getOriginalFilename().lastIndexOf(".") >= 0){
                extName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            }
//            File newFile = new File(Const.TEMP_STOREPATH, "" + System.currentTimeMillis() + extName);
//            if (!newFile.exists()) {
//                if (!newFile.getParentFile().exists()) {
//                    newFile.getParentFile().mkdirs();
//                }
//                newFile.createNewFile();
//            }
//            FileUtils.copyInputStreamToFile(file.getInputStream(), newFile);
//            switch(remark){
//                case "activity_content":
//                    path = Const.IMGSERVER_PATH_CONTENT + NBUtils.getYearMonthDay() + "/";
//                    break;
//                case "activity":
//                    path = Const.IMGSERVER_PATH_IMG_TEMP;
//                    break;
//                case "banner":
//                    path = Const.IMGSERVER_PATH_IMG_TEMP;
//                    break;
//                default:
//                    return Result.fail("未指定图片存储路径");
//            }
//
//            Result result = uploadUpImage(newFile, path);
//            return Result.success(result);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.fail();
    }
}
