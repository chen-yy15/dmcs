package edu.tsinghua.dmcs.web;

import com.alibaba.fastjson.JSONObject;
import edu.tsinghua.dmcs.Response;
import edu.tsinghua.dmcs.entity.FileInfo;
import edu.tsinghua.dmcs.entity.FileWindowModule;
import edu.tsinghua.dmcs.entity.SysOperationLog;
import edu.tsinghua.dmcs.interceptor.DmcsController;
import edu.tsinghua.dmcs.service.FileInfoService;
import edu.tsinghua.dmcs.service.FileWindowService;
import edu.tsinghua.dmcs.service.SysOperationService;
import edu.tsinghua.dmcs.service.UserService;
import edu.tsinghua.dmcs.util.TockenCache;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import sun.misc.BASE64Encoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
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
    @Autowired
    private SysOperationService sysOperationService;
    @Autowired
    private FileWindowService fileWindowService;

    @Value("${security.sault.login}")
    private String securitySault;


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

    /**权限验证*/
    @DmcsController(authRequired = true)
    @ApiOperation(value = "getFileToken", notes = "获取文件更改能力")
    @RequestMapping(value = "/getFileToken", method = RequestMethod.GET)
    public Response GetFileToken(HttpServletRequest request,HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        String admin_token=null;
        if(cookies==null)
            return Response.FAILWRONG();
        if(cookies!=null){
            for(Cookie cookie:cookies){
                if(cookie.getName().equals("admin_token")){
                    admin_token = cookie.getValue();
                    admin_token = URLDecoder.decode(admin_token);
                }
            }
        }
        String userid = tockenCache.getUserid(admin_token);
        String filemanage = this.produceToken(userid);
        if(filemanage!=null){
            try{
                Cookie cookie = new Cookie("managefile",filemanage);
                cookie.setMaxAge(3600);
                cookie.setPath("/");
                response.addCookie(cookie);
                tockenCache.setTokenForUser(filemanage,userid);
            }catch (Exception e){
                logger.info("设置cookie出错");
            }
            return Response.SUCCESSOK().setMsg("获取令牌成功");
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
        String type = o.getString("type");
        Long Fileid = Long.valueOf(fileid);
        FileInfo fileInfo = fileInfoService.SelectFileInfo(Fileid);
        if(fileInfo==null){
            return Response.FAILWRONG().setMsg("文件不存在");
        }
        Cookie[] cookies = request.getCookies();
        String admin_token=this.translateCookie(cookies,"admin_token");

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
        num = sysOperationService.AddOperation(sysOperationLog);
        if(num==0){
            return Response.FAILWRONG().setMsg("记录更新失败");
        }
        if(type.equals("file")){
            List<FileInfo> fileInfos = fileInfoService.SelectFile("file");
            return Response.SUCCESSOK().setData(fileInfos);
        }
        if(type.equals("image")) {
            List<FileInfo> fileInfos = fileInfoService.SelectFile("image");
            return Response.SUCCESSOK().setData(fileInfos);
        }
        return Response.FAILWRONG();
    }

    @DmcsController(authRequired = true)
    @ApiOperation(value="updatefile",notes = "修改文件")
    @RequestMapping(value = "/updateFile", method = RequestMethod.POST)
    public Response UpdateFile(@RequestBody String body, HttpServletRequest request) throws ParseException {
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
    @ApiOperation(value="getFileImage",notes="获取文件与图片的关系")
    @RequestMapping(value = "/getFileImage", method = RequestMethod.POST)
    public Response GetFileImage(@RequestBody String body) throws ParseException {
        JSONObject o = JSONObject.parseObject(body);
        String module = o.getString("module");
        Integer moduleid = this.ChangeStringModuleid(module);
        if(moduleid ==null){
            return Response.FAILWRONG().setMsg("信息丢失");
        }
        List<FileWindowModule> fileWindowModules = fileWindowService.SelectFileWindowByModule(moduleid);
        return Response.SUCCESSOK().setData(fileWindowModules);
    }

    @DmcsController(authRequired = true)
    @ApiOperation(value="getCommonFileImage",notes="获取一般的文件信息")
    @RequestMapping(value = "/getCommonFileImage", method = RequestMethod.GET)
    public Response GetCommonFileImage() throws ParseException {
        List<FileWindowModule> fileWindowModules = fileWindowService.NoSelectFileWindow();
        return Response.SUCCESSOK().setData(fileWindowModules);
    }

    @DmcsController(authRequired = true)
    @ApiOperation(value="addFileImage",notes="加入文件与图片绑定关系")
    @RequestMapping(value = "/addFileImage", method = RequestMethod.POST)
    public Response FileWindow( HttpServletRequest request) throws ParseException {

        MultipartHttpServletRequest params=((MultipartHttpServletRequest) request);
        MultipartFile file = params.getFile("file");
        MultipartFile image = params.getFile("image");
        String fileimageDescirp = params.getParameter("description");
        Cookie[] cookies = params.getCookies();

        if(file==null || image ==null ||cookies ==null){
            return Response.FAILWRONG().setMsg("信息缺失");
        }
        System.out.println("description:"+fileimageDescirp);

        String dmcstoken = this.translateCookie(cookies,"dmcstoken");

        String username = tockenCache.getUserNameByToken(dmcstoken);
        if(username==null){
            return Response.FAILWRONG().setMsg("身份验证失败");
        }
        FileWindowModule fileWindowModule =new FileWindowModule();
        fileWindowModule.setFileimagedescrip(fileimageDescirp);
        fileWindowModule.setInsertUser(username);
        fileWindowModule.setOrderid(0);
        fileWindowModule.setModuleid(0);
        fileWindowModule.setWindowid(0);

        try {
            String fileName, imageName;
            String FILEPATH, IMAGEPATH;

            fileName = file.getOriginalFilename();
            imageName = image.getOriginalFilename();
            logger.info("上传的文件名为：" + fileName);
            logger.info("上传图片名为：" + imageName);
            FILEPATH = "/home/dmcs/file/fileWindow/";
            FILEPATH = FILEPATH + fileName;

            IMAGEPATH = "/home/dmcs/image/fileWindow/";
            IMAGEPATH = IMAGEPATH + imageName;

            File destfile = new File(FILEPATH);
            File destimage = new File(IMAGEPATH);

            if (!destfile.getParentFile().exists()) {
                destfile.getParentFile().mkdirs();
            }
            if (!destimage.getParentFile().exists()) {
                destimage.getParentFile().mkdirs();
            }

            if (destfile.createNewFile()) {
                System.out.println("File is created");
                destfile.setExecutable(true);
                destfile.setWritable(true);
                destfile.setReadable(true);
            }
            if (destimage.createNewFile()) {
                System.out.println("ImageFile is created");
                destimage.setExecutable(true);
                destimage.setReadable(true);
                destimage.setWritable(true);
            }

            file.transferTo(destfile);
            fileWindowModule.setFilename(fileName);
            fileWindowModule.setFilesrc(FILEPATH);

            image.transferTo(destimage);
            fileWindowModule.setImagename(imageName);
            fileWindowModule.setImagesrc(IMAGEPATH);
            fileWindowModule.setViewed("false");
            int num = fileWindowService.AddFileWindow(fileWindowModule);
            if(num == 0){
                return Response.FAILWRONG().setMsg("插入失败");
            }
        }catch (IllegalStateException e) {
            e.printStackTrace();
            return Response.FAILWRONG();
        } catch (IOException e) {
            e.printStackTrace();
            return Response.FAILWRONG();
        }

        List<FileWindowModule> fileWindowModules = fileWindowService.NoSelectFileWindow();
        return Response.SUCCESSOK().setData(fileWindowModules);
    }

    @DmcsController(authRequired = true)
    @ApiOperation(value="deleteFileImage",notes = "删除文件绑定")
    @RequestMapping(value = "/deleteFileImage", method = RequestMethod.POST)
    public Response DeleteFileWindow(@RequestBody String body, HttpServletRequest request) throws ParseException {
        JSONObject o = JSONObject.parseObject(body);
        String createid = o.getString("createid");
        if(createid==null){
            return Response.FAILWRONG().setMsg("信息丢失");
        }
        FileWindowModule fileWindow =  fileWindowService.SelectFileWindow(Long.valueOf(createid));

        String admin_token = this.translateCookie(request.getCookies(),"admin_token");
        String userid = tockenCache.getUserid(admin_token);

        if(userid==null || fileWindow==null){
            return Response.FAILWRONG().setMsg("信息缺失");
        }

        File file = new File(fileWindow.getFilesrc());
        File image = new File(fileWindow.getImagesrc());
        if(file.delete() && image.delete() ){
            logger.info(fileWindow.getFilesrc()+" 文件删除");
            logger.info(fileWindow.getImagesrc()+" 图片删除");
        }

        fileWindowService.DeleteFileWindow(Long.valueOf(createid));

        SysOperationLog sysOpe = new SysOperationLog();
        sysOpe.setFileid(fileWindow.getCreateid());
        sysOpe.setOperationtime(new Date());
        sysOpe.setFilefullname(fileWindow.getFilename());
        sysOpe.setOpDesc(fileWindow.getFilename()+" /与/ "+fileWindow.getImagename()+"删除");
        sysOpe.setUserid(userid);
        sysOperationService.AddOperation(sysOpe);

        List<FileWindowModule> fileWindowModules = fileWindowService.NoSelectFileWindow();
        return Response.SUCCESSOK().setData(fileWindowModules);
    }

    // 这里包括文章与窗口的绑定操作， 文章与窗口关系的删除
    @DmcsController(authRequired = true)
    @ApiOperation(value="updateFileImage",notes="更改图片与文章的关系")
    @RequestMapping(value = "/updateFileImage", method = RequestMethod.POST)
    public Response UpdateFileWindow(@RequestBody String body, HttpServletRequest request) throws ParseException {

        JSONObject o = JSONObject.parseObject(body);
        String createid = o.getString("createid");
        String valueSelect = o.getString("valueSelect");
        Integer moduleid = this.ChangeStringModuleid(valueSelect);
        if(createid == null ){
            return Response.FAILWRONG().setMsg("信息错误");
        }
        FileWindowModule fileWindowModule = fileWindowService.SelectFileWindow(Long.valueOf(createid));
        if(fileWindowModule == null) {
            return Response.FAILWRONG().setMsg("信息错误");
        }
        Integer oldmoduleid = fileWindowModule.getModuleid();
        fileWindowModule.setModuleid(moduleid);
        fileWindowService.UpdateFileWindow(fileWindowModule);


        /* 日志记录操作 */

        Cookie[] cookies = request.getCookies();
        String admin_token = this.translateCookie(cookies,"admin_token");
        String userid = tockenCache.getUserid(admin_token);


        SysOperationLog sysOpe = new SysOperationLog();
        sysOpe.setFileid(fileWindowModule.getCreateid());
        sysOpe.setOperationtime(new Date());
        sysOpe.setFilefullname(fileWindowModule.getFilename());

        sysOpe.setOpDesc(fileWindowModule.getFilename()+" /与窗口 "+valueSelect+ (moduleid.equals(0)? "解绑":"绑定"));
        sysOpe.setUserid(userid);
        sysOperationService.AddOperation(sysOpe);

        List<FileWindowModule> fileWindowModules = fileWindowService.SelectFileWindowByModule(moduleid.equals(0)?oldmoduleid:moduleid);
        return Response.SUCCESSOK().setData(fileWindowModules);
    }

    @DmcsController(authRequired = true)
    @ApiOperation(value= "webInformation", notes = "窗口信息发布")
    @RequestMapping(value = "/webInformation", method = RequestMethod.POST)
    public Response WebInformation(@RequestBody String body, HttpServletRequest request) throws ParseException {
        return Response.FAILWRONG().setErrcode(0);
    }

    private String  produceToken(String Userid) {
        String token = Userid + this.securitySault + System.currentTimeMillis();
        String securetoken  = null;
        try {
            MessageDigest Digest= MessageDigest.getInstance("md5");
            byte [] By=Digest.digest(token.getBytes());
            securetoken = new  String(By);
            securetoken = securetoken + "|" + (System.currentTimeMillis() + 1000*3600);
            securetoken = new BASE64Encoder().encode(securetoken.getBytes());
        }catch( Exception e) {
            logger.error("fail to get md5 algorithm");
        }
        securetoken = URLEncoder.encode(securetoken);
        return securetoken;
    }

    private String translateCookie(Cookie[] cookies, String cookname){
        if(cookies==null||cookname==null){
            return null;
        }
        String token =null;
        for(Cookie cookie:cookies){
            if(cookie.getName().equals(cookname)){
                token = cookie.getValue();
                token = URLDecoder.decode(token);
            }
        }
        return token;
    }

    private Integer ChangeStringModuleid(String module) {
       Integer moduleid = 0;
       char a = module.charAt(0);
       switch (a){
           case 'a':
               if(module.equals("aa"))
                   moduleid = 1010;
               if(module.equals("ab"))
                   moduleid = 1020;
               if(module.equals("ac"))
                   moduleid = 1030;
               if(module.equals("ad"))
                   moduleid = 1040;
               break;
           case 'b':  moduleid = 2000; break;
           case 'c':  moduleid = 3000; break;
           case 'd':  moduleid = 4000; break;
           case 'e':  moduleid = 5000; break;
           case 'f':  moduleid = 6000; break;
           case 'g':  moduleid = 7000; break;
           case 'h':  moduleid = 8000; break;
           case 'i':  moduleid = 9000; break;
           default: moduleid = 0; break;
       }
       return moduleid;
    }

}
