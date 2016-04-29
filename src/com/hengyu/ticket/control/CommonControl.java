package com.hengyu.ticket.control;

import com.hengyu.ticket.util.CollectionUtil;
import com.hengyu.ticket.util.ConfigUtil;
import com.hengyu.ticket.util.EncryptUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 通用接口
 *
 * @author wwang
 */
@Controller
@RequestMapping(value = "/user")
public class CommonControl {
    //图片上传
    @SuppressWarnings("unchecked")
    @ResponseBody
    @RequestMapping(value = "imageFileUpload.do", method = RequestMethod.POST)
    public String imageFileUpload(HttpServletRequest request) throws Exception {
        Long imageFileMaxSize = Long.parseLong((String) ConfigUtil.get("imageFileMaxSize"));
        String imageFileTypes = (String) ConfigUtil.get("imageFileTypes");
        String imageAccessBaseUrl = (String) ConfigUtil.get("imageAccessBaseUrl");
        String imageSaveBasePath = request.getRealPath("/") + ConfigUtil.get("imageSaveBasePath");
        System.out.print(imageSaveBasePath);
        return doUpload(request, imageSaveBasePath, imageAccessBaseUrl, imageFileMaxSize, imageFileTypes.split("\\|"));
    }

    private String doUpload(HttpServletRequest request, String saveBasePath, String accessBaseUrl, Long fileMaxSize, String[] fileTypes) throws Exception {
        List<String> fileUrlList = new ArrayList<>();
        File repository = new File(saveBasePath);
        if (!repository.exists()) {
            repository.mkdirs();
        }
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        for (String filename : fileMap.keySet()) {
            MultipartFile fileItem = fileMap.get(filename);
            String filePath = fileItem.getOriginalFilename();
            if (StringUtils.isBlank(filePath)) continue;
            Assert.isTrue(fileMaxSize >= fileItem.getSize(), "文件大小超过限制");
            String extName = filePath.substring(filePath.lastIndexOf(".") + 1).toUpperCase();
            Assert.isTrue(CollectionUtil.contain(fileTypes, extName), "无效文件类型");
            String encrytFileName = EncryptUtil.MD5(fileItem.getBytes()) + "." + extName.toLowerCase();
            File tempFile = new File(saveBasePath + File.separator + encrytFileName);
            if (!tempFile.exists()) {
                FileOutputStream fos = new FileOutputStream(tempFile);
                fos.write(fileItem.getBytes());
                fos.close();
            }
            fileUrlList.add(accessBaseUrl + "/" + encrytFileName);
        }
        Assert.isTrue(fileUrlList.size() > 0, "无效文件");
        return fileUrlList.get(0);
    }
}
