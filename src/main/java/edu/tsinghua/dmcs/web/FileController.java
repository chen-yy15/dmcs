package edu.tsinghua.dmcs.web;

import com.alibaba.fastjson.JSONObject;
import edu.tsinghua.dmcs.Response;
import edu.tsinghua.dmcs.entity.FileInfo;
import edu.tsinghua.dmcs.entity.SysOperationLog;
import edu.tsinghua.dmcs.interceptor.DmcsController;
import edu.tsinghua.dmcs.service.FileInfoService;
import edu.tsinghua.dmcs.service.UserService;
import edu.tsinghua.dmcs.util.TockenCache;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * Created by caizj on 18-10-9.
 */
@RestController
@RequestMapping(value="/dmcs/api/v1/file")
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(AdminRestController.class);

    @Autowired
    private FileInfoService fileInfoService;
    @Autowired
    private UserService userService;
    @Autowired
    TockenCache tockenCache;


    @DmcsController(authRequired = true)
    @ApiOperation(value = "addFile",notes="添加文件")
    @RequestMapping(value = "/addFile", method = RequestMethod.POST)
    public Response AddFile(HttpServletRequest request) throws ParseException {

        MultipartHttpServletRequest params=((MultipartHttpServletRequest) request);
        MultipartFile file = params.getFile("file");
        String title=params.getParameter("title");
        String description=params.getParameter("description");

        Cookie[] cookies = params.getCookies();
        System.out.println("title:"+title);
        System.out.println("description:"+description);

        String dmcstoken=null;
        String admin_token=null;

        if(cookies!=null) {
            for ( Cookie cookie:cookies) {
                if(cookie.getName().equals("admin_token")){
                    admin_token = cookie.getValue();
                    admin_token = URLDecoder.decode(admin_token);
                    System.out.println(admin_token);
                }
                if(cookie.getName().equals("dmcstoken")){
                    dmcstoken = cookie.getValue();
                    dmcstoken = URLDecoder.decode(dmcstoken);
                    System.out.println(dmcstoken);
                }
            }
            String username = tockenCache.getUserNameByToken(dmcstoken);
            String userid = tockenCache.getUserid(admin_token);
            if(userid==null||username==null){
                return Response.FAILWRONG().setMsg("身份验证失败");
            }

            FileInfo fileInfo = new FileInfo();
            fileInfo.setFiledescription(description);
            fileInfo.setFilename(title);
            fileInfo.setInsertTime(new Date());
            fileInfo.setInsertUser(userid);

            try {
                 String fileName,suffixName;
                 String FILEPATH,filePath;
                 if(file!=null){
                     fileName=file.getOriginalFilename();
                     suffixName = fileName.substring(fileName.lastIndexOf(".") + 1);
                     logger.info("上传的文件名为：" + fileName);
                     if(suffixName.equals("pdf") ||suffixName.equals("html")){
                         fileInfo.setFiletype("file");
                         FILEPATH = "/home/dmcs/file/document/";
                         filePath = "/home/dmcs/file/document/";
                         FILEPATH = FILEPATH+fileName;
                         filePath = filePath+fileName;
                     }else{
                         fileInfo.setFiletype("image");
                         FILEPATH = "/home/dmcs/image/document/";
                         filePath = "/home/dmcs/image/document/";
                         FILEPATH = FILEPATH+fileName;
                         filePath = filePath+fileName;
                     }

                     File dest = new File(filePath);
                     if (!dest.getParentFile().exists()) {
                         dest.getParentFile().mkdirs();// 新建文件夹
                     }
                     if (dest.createNewFile()) {
                         System.out.println("File is created");
                         dest.setExecutable(true);
                         dest.setReadable(true);
                         dest.setWritable(true);
                     }
                     file.transferTo(dest);
                     fileInfo.setFilesrc(FILEPATH);
                     fileInfo.setInsertUser(userid);
                     fileInfo.setSuffixname(suffixName);
                 }
                // 设置文件存储路径
                int num = fileInfoService.AddFile(fileInfo);
                if (num != 0) {
                    return Response.SUCCESSOK();
                } else
                    return Response.FAILWRONG().setMsg("数据更新错误");

            } catch (IllegalStateException e) {
                e.printStackTrace();
                return Response.FAILWRONG();
            } catch (IOException e) {
                e.printStackTrace();
                return Response.FAILWRONG();
            }
        }
        return Response.FAILWRONG();
    }

    @DmcsController(authRequired = true)
    @ApiOperation(value = "deleteFile", notes = "删除文件")
    @RequestMapping(value = "/deleteFile", method = RequestMethod.POST)
    public Response DeleteFile(@RequestBody String body, HttpServletRequest request) throws ParseException {
        JSONObject o = JSONObject.parseObject(body);
        System.out.println("删除文件: "+body);
        String fileid = o.getString("fileid");
        Long Fileid = Long.valueOf(fileid);
        FileInfo fileInfo = fileInfoService.SelectFileInfo(Fileid);
        if(fileInfo==null){
            return Response.FAILWRONG().setMsg("文件不存在");
        }
        Cookie[] cookies = request.getCookies();
        String admin_token=null;
        if(cookies!=null){
            for ( Cookie cookie:cookies) {
                if(cookie.getName().equals("admin_token")){
                    admin_token = cookie.getValue();
                    admin_token = URLDecoder.decode(admin_token);
                    System.out.println(admin_token);
                }
            }
        }
        if(admin_token==null)
            return Response.FAILWRONG().setMsg("权限验证错误");
        String userid = tockenCache.getUserid(admin_token);
        int num = 0;
        num = fileInfoService.DeleteFile(Fileid);
        if(num==0){
            return Response.FAILWRONG().setMsg("文件删除失败");
        }
        File file = new File(fileInfo.getFilesrc());
        if(file.delete()){
            logger.info(fileInfo.getFilesrc()+" 文件删除");
        }
        SysOperationLog sysOperationLog = new SysOperationLog();
        sysOperationLog.setFileid(Fileid);
        sysOperationLog.setOperationtime(new Date());
        sysOperationLog.setOpDesc("删除文件");
        sysOperationLog.setUserid(userid);
        sysOperationLog.setFilefullname(fileInfo.getFilesrc() +"="+fileInfo.getFiledescription());
        return Response.FAILWRONG().setErrcode(0);
    }

    @DmcsController(authRequired = true)
    @ApiOperation(value="updatefile",notes = "修改文件")
    @RequestMapping(value = "/updateFile", method = RequestMethod.POST)
    public Response UpdateFile(@RequestBody String body, HttpServletRequest request) throws ParseException {
        return Response.FAILWRONG().setErrcode(0);
    }

    @DmcsController(authRequired = true)
    @ApiOperation(value="updateView",notes = "更改可视性")
    @RequestMapping(value = "/updateView", method = RequestMethod.POST)
    public Response UpdateView(@RequestBody String body, HttpServletRequest request) throws ParseException {
        return Response.FAILWRONG().setErrcode(0);
    }

    @DmcsController(authRequired = true)
    @ApiOperation(value="getFile", notes = "文件获取")
    @RequestMapping(value = "/getFilelist", method = RequestMethod.GET)
    public Response GetFile() throws ParseException {
        List<FileInfo> fileInfos = fileInfoService.SelectFile("file");
        return Response.SUCCESSOK().setData(fileInfos);
    }

    @DmcsController(authRequired = true)
    @ApiOperation(value="getFile", notes = "图片文件获取")
    @RequestMapping(value = "/getImagelist", method = RequestMethod.GET)
    public Response GetImage() throws ParseException {
        List<FileInfo> fileInfos = fileInfoService.SelectFile("image");
        return Response.SUCCESSOK().setData(fileInfos);
    }

    /**针对于firstpage数据的获取*/
    @DmcsController(loginRequired = false)
    @ApiOperation(value="selectFile",notes = "一般性文件获取")
    @RequestMapping(value = "/selectFile", method = RequestMethod.POST)
    public Response SelectFile(@RequestBody String body) throws ParseException {
        return Response.FAILWRONG().setErrcode(0);
    }

    @DmcsController(authRequired = true)
    @ApiOperation(value="fileWindow",notes="文件与窗口绑定")
    @RequestMapping(value = "/fileWindow", method = RequestMethod.POST)
    public Response FileWindow(@RequestBody String body, HttpServletRequest request) throws ParseException {
        return Response.FAILWRONG().setErrcode(0);
    }

    @DmcsController(authRequired = true)
    @ApiOperation(value= "webInformation", notes = "窗口信息发布")
    @RequestMapping(value = "/webInformation", method = RequestMethod.POST)
    public Response WebInformation(@RequestBody String body, HttpServletRequest request) throws ParseException {
        return Response.FAILWRONG().setErrcode(0);
    }

}
