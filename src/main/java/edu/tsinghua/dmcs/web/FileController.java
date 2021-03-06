package edu.tsinghua.dmcs.web;

import com.alibaba.fastjson.JSONArray;
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
import edu.tsinghua.dmcs.util.CommonTool;
import edu.tsinghua.dmcs.util.TockenCache;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
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

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileInfoService fileInfoService;
    @Autowired
    private UserService userService;
    @Autowired
    TockenCache tockenCache;
    @Autowired
    CommonTool commonTool;
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
        admin_token = commonTool.translateCookie(cookies,"admin_token");
        String userid = tockenCache.getUserid(admin_token);
        String filemanage = commonTool.ProduceToken(userid,this.securitySault);
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
        String admin_token=commonTool.translateCookie(cookies,"admin_token");

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
        Integer moduleid = commonTool.ChangeStringModuleid(module);
        if(moduleid == 0){
            return Response.SUCCESSOK();
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

    //在这个模块需要完成图片，文件以及图片文件的绑定上传。
    //但是通过统一的模块进行处理，发现一些逻辑没办法实现，需要进行分开处理，这个自己需要进行很好的安排。
    @DmcsController(authRequired = true)
    @ApiOperation(value="addFileImage",notes="加入文件与图片绑定关系")
    @RequestMapping(value = "/addFileImage", method = RequestMethod.POST)
    public Response FileWindow( HttpServletRequest request) throws ParseException {

        MultipartHttpServletRequest params=((MultipartHttpServletRequest) request);
        MultipartFile file = params.getFile("file");   // 存储在目录 '/home/dmcs/file/fileWindow' 下
        MultipartFile image = params.getFile("image"); // 存储在目录 '/home/dmcs/image/fileWindow'下
        List<MultipartFile> shortimages =params.getFiles("shortimage"); // 存储在目录'/home/dmcs/image/shortimages'下


        String fileimageDescirp = params.getParameter("description");
        Cookie[] cookies = params.getCookies();

        if(file==null || image ==null ||cookies ==null){
            return Response.FAILWRONG().setMsg("信息缺失");
        }
        System.out.println("description:"+fileimageDescirp);

        String dmcstoken = commonTool.translateCookie(cookies,"dmcstoken");

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
            String fileName, imageName, fileFirstName;
            String FILEPATH, IMAGEPATH;

            fileName = file.getOriginalFilename();
            imageName = image.getOriginalFilename();
            logger.info("上传的文件名为：" + fileName);
            logger.info("上传图片名为：" + imageName);
            FILEPATH = "/home/dmcs/file/fileWindow/";
            fileFirstName= fileName.substring(0,fileName.lastIndexOf("."));
            System.out.println(fileFirstName);

            FILEPATH = FILEPATH + fileFirstName+"/"; // 存储地址 /home/dmcs/image/fileWindow/filename/filename.suffixname
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

            // 附件图片插入程序
            num = 0;
            String shortImagePath, shortImageName,suffinxName;
            String SHORTIMAGEPATH;
            shortImagePath= "/home/dmcs/file/fileWindow/"+fileFirstName+"/";;


            for( MultipartFile item:shortimages){
                shortImageName = item.getOriginalFilename();
                suffinxName = shortImageName.substring(shortImageName.lastIndexOf(".") + 1);
                logger.info("上传文件: "+fileName+" 附加图片"+shortImageName);
                SHORTIMAGEPATH = shortImagePath + shortImageName;
                File destShortImage = new File(SHORTIMAGEPATH);

                if(!destShortImage.getParentFile().exists()) {
                    destShortImage.getParentFile().mkdirs();
                }
                if(destShortImage.createNewFile()) {
                    logger.info("文件"+shortImagePath+"created");
                    destShortImage.setExecutable(true);
                    destShortImage.setWritable(true);
                    destShortImage.setReadable(true);
                }
                item.transferTo(destShortImage);
                num ++;
            }
            if(num<shortimages.size())
                logger.error("pictures were fewer !!!");

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

        String admin_token = commonTool.translateCookie(request.getCookies(),"admin_token");
        String userid = tockenCache.getUserid(admin_token);

        if(userid==null || fileWindow==null){
            return Response.FAILWRONG().setMsg("信息缺失");
        }
        File image = new File(fileWindow.getImagesrc());
        image.delete(); // 删除图片

        // 删除文件所在的目录

        String filename = fileWindow.getFilename();
        filename = filename.substring(0,filename.lastIndexOf("."));
        String FilePath = "/home/dmcs/file/fileWindow/"+filename+"/";

       try{
           this.deletefile(FilePath);
           logger.info("已删除文件"+FilePath);
       }catch(Exception e) {
           e.printStackTrace();
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
        Integer moduleid = commonTool.ChangeStringModuleid(valueSelect);
        // 当moduleid = 0时，表明删除文件与窗口的绑定操作。当moduleid !=0 时，表明添加文件与窗口的绑定操作。
        if(createid == null ){
            return Response.FAILWRONG().setMsg("信息错误");
        }
        FileWindowModule fileWindowModule = fileWindowService.SelectFileWindow(Long.valueOf(createid));
        if(fileWindowModule == null) {
            return Response.FAILWRONG().setMsg("信息错误");
        }
        Integer oldmoduleid = fileWindowModule.getModuleid();
        if(moduleid.equals(0)){
            fileWindowModule.setViewed("false");
            int oldorderid = fileWindowModule.getOrderid();
            //删除关系时，需要对列表中的记录进行重新的刷新操作。
            List<FileWindowModule> fileWindowModuleList = fileWindowService.SelectFileWindowByModule(oldmoduleid);
            for(FileWindowModule fileWindow: fileWindowModuleList){
                int getid = fileWindow.getOrderid();
                if(getid>oldorderid) {
                    fileWindow.setOrderid(getid-1);
                    fileWindowService.UpdateFileWindow(fileWindow);
                }
            }

            fileWindowModule.setOrderid(0);
        }
        else {
            int num = fileWindowService.GetNumberOfModuleid(moduleid);
            fileWindowModule.setOrderid(num+1);
        }
        //选择目前有多少条记录，则插入
        fileWindowModule.setModuleid(moduleid);
        fileWindowService.UpdateFileWindow(fileWindowModule);


        /* 日志记录操作 */

        Cookie[] cookies = request.getCookies();
        String admin_token = commonTool.translateCookie(cookies,"admin_token");
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
    @ApiOperation(value = "updaFIOrdVie", notes = "更改文件的可视性和顺序")
    @RequestMapping(value = "/updaFIOrdVie", method = RequestMethod.POST)
    public Response UpdaFIOrdVie(@RequestBody String body, HttpServletRequest request){
        JSONObject o = JSONObject.parseObject(body);
        String module =o.getString("module");

        JSONArray FilImas = o.getJSONArray("fileImages");
        if(FilImas == null){
            return Response.FAILWRONG().setMsg("信息缺失");
        }
        int i =0;
        int fail=0;
        for (;i<FilImas.size();i++){
            JSONObject object = (JSONObject) FilImas.get(i);

            String createid_Str = object.getString("createid");
            String orderid_Str = object.getString("orderid");
            String viewed = object.getString("viewed");

            FileWindowModule fileWindowModule = fileWindowService.SelectFileWindow(Long.valueOf(createid_Str));
            if(fileWindowModule!=null){
                fileWindowModule.setOrderid(Integer.valueOf(orderid_Str));
                fileWindowModule.setViewed(viewed);
                int num = fileWindowService.UpdateFileWindow(fileWindowModule);
                if(num==0)
                    fail++;
            }
        }

        Cookie[] cookies = request.getCookies();
        String admin_token = commonTool.translateCookie(cookies,"admin_token");
        String userid = tockenCache.getUserid(admin_token);



        SysOperationLog sysOpe = new SysOperationLog();
        sysOpe.setFileid(commonTool.ChangeStringModuleid(module).longValue());
        sysOpe.setFilefullname(module);
        sysOpe.setOpDesc("change orderid or viewed of window: "+ module);
        sysOpe.setUserid(userid);
        sysOperationService.AddOperation(sysOpe);

        if(fail==0) {
            List<FileWindowModule> fileWindowModules = fileWindowService.SelectFileWindowByModule(commonTool.ChangeStringModuleid(module));
            return Response.SUCCESSOK().setMsg("更新成功").setData(fileWindowModules);
        }

        return Response.SUCCESSOK();
    }

    @DmcsController(loginRequired = false)
    @ApiOperation(value = "getPageList", notes = "获取首页文件")
    @RequestMapping(value = "/getPageList", method = RequestMethod.POST)
    public Response GetPageList(@RequestBody String body, HttpServletRequest request) throws ParseException{
        JSONObject o = JSONObject.parseObject(body);
        String valueSelect = o.getString("valueSelect");
        Integer moduleid = commonTool.ChangeStringModuleid(valueSelect);
        List<FileWindowModule> fileWindowModules=fileWindowService.SelectPageListByModule(moduleid);
        return Response.SUCCESSOK().setData(fileWindowModules);
    }

    @DmcsController(loginRequired = false)
    @ApiOperation(value = "getFirstPageList", notes = "获得首页模块的文件")
    @RequestMapping(value = "/getFirstPageList", method = RequestMethod.GET)
    public Response GetFirstPageList(){
        List<FileWindowModule> fileWindowModules=fileWindowService.GetFirstPageList();
        return Response.SUCCESSOK().setData(fileWindowModules);
    }

    public static boolean deletefile(String delpath) throws Exception {
        try{
            File file = new File(delpath);
            if (!file.isDirectory()) {
                file.delete();
            } else if (file.isDirectory()) {
                String[] filelist = file.list();
                for (int i = 0; i < filelist.length; i++) {
                    File delfile = new File(delpath + filelist[i]);
                    if (!delfile.isDirectory()) {
                        delfile.delete();
                        System.out.println(delfile.getAbsolutePath() + "删除文件成功");
                    } else if (delfile.isDirectory()) {
                        deletefile(delpath + filelist[i]);
                    }
                }
                System.out.println(file.getAbsolutePath() + "删除成功");
                file.delete();
            }
        }catch (FileNotFoundException e){
            System.out.println("deletefile() Exception:" + e.getMessage());
        }
        return true;
    }
}
