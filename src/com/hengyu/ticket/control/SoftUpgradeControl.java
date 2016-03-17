package com.hengyu.ticket.control;

import com.hengyu.ticket.config.TicketConfig;
import com.hengyu.ticket.entity.Page;
import com.hengyu.ticket.entity.SoftUpgrade;
import com.hengyu.ticket.service.SoftUpgradeService;
import com.hengyu.ticket.util.DateHanlder;
import com.hengyu.ticket.util.Log;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

/**
 * Created by LGF on 2016/2/15 0015.
 */

@Controller
@RequestMapping("/user")
public class SoftUpgradeControl {

    @Autowired
    private SoftUpgradeService softService;
    @Autowired
    private TicketConfig conf;

    @RequestMapping("applist")
    public String applist(Page page,Model m) throws Exception {
        List<SoftUpgrade> data = softService.findlist(page);
        page.setData(data);
        m.addAttribute(page);
        return "user/applist";
    }

    @RequestMapping("appupload")
    public String appupload(@RequestParam("app") MultipartFile app,HttpServletRequest req) throws Exception {
        Assert.notNull(req.getParameter("versionCode"),"版本号不能为空！");
        Assert.isTrue(app!=null&&app.getSize()!=0,"无效文件！");
        int version = Integer.parseInt(req.getParameter("versionCode"));
        int sort = Integer.parseInt(req.getParameter("sort"));
        String desc = req.getParameter("softDesc");
        String filename = app.getOriginalFilename().replaceAll("\\s","");
        String url = req.getParameter("fileUrl");

        SoftUpgrade soft = new SoftUpgrade();
        soft.setSort(sort);
        soft.setAppname(filename);
        soft.setSoftdesc(desc);
        soft.setMakedate(DateHanlder.getCurrentDateTime());
        soft.setVersioncode(version);
        if(org.apache.commons.lang.StringUtils.isEmpty(url)){
            soft.setFileurl(conf.getDomain()+filename);
        }else{
            soft.setFileurl(url);
        }
        String path = req.getSession().getServletContext().getRealPath("/");
        File file = new File(path+filename);
        FileUtils.writeByteArrayToFile(file,app.getBytes());
        Assert.isTrue(softService.save(soft)==1,"App上传失败！");
        return "redirect:applist";
    }

}
