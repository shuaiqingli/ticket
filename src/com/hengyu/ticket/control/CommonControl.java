package com.hengyu.ticket.control;

import com.baidu.ueditor.ActionEnter;
import com.hengyu.ticket.util.CollectionUtil;
import com.hengyu.ticket.util.ConfigUtil;
import com.hengyu.ticket.util.EncryptUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

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
        List<String> fileUrlList = new ArrayList<String>();
        File repository = new File(saveBasePath);
        if (!repository.exists()) {
            repository.mkdirs();
        }
        DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
        diskFileItemFactory.setRepository(repository);
        diskFileItemFactory.setSizeThreshold(1024 * 6);
        ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);
        for (Object fileItemTemp : servletFileUpload.parseRequest(request)) {
            FileItem fileItem = (FileItem) fileItemTemp;
            String filePath = fileItem.getName();
            if (StringUtils.isBlank(filePath)) continue;
            Assert.isTrue(fileMaxSize >= fileItem.getSize(), "文件大小超过限制");
            String extName = filePath.substring(filePath.lastIndexOf(".") + 1).toUpperCase();
            Assert.isTrue(CollectionUtil.contain(fileTypes, extName), "无效文件类型");
            byte[] b = new byte[(int) fileItem.getSize()];
            fileItem.getInputStream().read(b);
            String encrytFileName = EncryptUtil.MD5(b) + "." + extName.toLowerCase();
            File tempFile = new File(saveBasePath + File.separator + encrytFileName);
            if (!tempFile.exists()) {
                fileItem.write(tempFile);
            }
            fileUrlList.add(accessBaseUrl + "/" + encrytFileName);
        }
        Assert.isTrue(fileUrlList.size() > 0, "无效文件");
        return fileUrlList.get(0);
    }
}
