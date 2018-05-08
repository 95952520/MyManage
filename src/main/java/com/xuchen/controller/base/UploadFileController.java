package com.xuchen.controller.base;

import com.xuchen.base.Result;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

@Controller
@RequestMapping("uploadFile")
public class UploadFileController extends BaseController {

    @RequestMapping(value = "/uploadImg", method = RequestMethod.POST)
    @ResponseBody
    Result uploadImg(MultipartFile file, String remark) throws IOException {
        if (file == null) {
            return Result.fail("图片不存在");
        }
        if (!Pattern.matches("^.*\\.(jpg|png|bmp)$", file.getOriginalFilename())) {
            return Result.fail("图片必须为jpg、png、bmp格式，请重新选择图片");
        }

        File newFile = new File(imgPath + getSessionUserId() + "-temp.jpg");
        newFile.createNewFile();
        BufferedImage image = ImageIO.read(file.getInputStream());
        ImageIO.write(image, "jpg", newFile);
//        return Result.success(newFile.getPath());
        return Result.success("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1525704277448&di=86769a13a86161e6c1fdd469abe77205&imgtype=0&src=http%3A%2F%2Fwww.chinairn.com%2FUserFiles%2Fimage%2F20180507%2F20180507144852_5184.jpg");
    }
}
