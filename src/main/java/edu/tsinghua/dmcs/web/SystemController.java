package edu.tsinghua.dmcs.web;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import edu.tsinghua.dmcs.Response;
import edu.tsinghua.dmcs.entity.EnumName;
import edu.tsinghua.dmcs.entity.SysOperationLog;
import edu.tsinghua.dmcs.entity.WebInformation;
import edu.tsinghua.dmcs.interceptor.DmcsController;
import edu.tsinghua.dmcs.service.EnumNameWebInfoService;
import edu.tsinghua.dmcs.service.SysOperationService;
import edu.tsinghua.dmcs.util.CommonTool;
import edu.tsinghua.dmcs.util.TockenCache;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Date;
import java.util.List;


/**
 * Created by caizj on 18-11-8.
 */
@RestController
@RequestMapping(value = "/dmcs/api/v1/system")
public class SystemController {

    @Autowired
    private EnumNameWebInfoService enumNameWebInfoService;

    @Autowired
    private SysOperationService sysOperationService;

    @Autowired
    TockenCache tockenCache;

    @Autowired
    CommonTool commonTool;

    private static final Logger logger = LoggerFactory.getLogger(SystemController.class);

    //
   /* @DmcsController(authRequired = true)
    @ApiOperation(value= "changeEnumname", notes = "修改对应关系")
    @RequestMapping(value="/changeEnumname", method = RequestMethod.POST)
    public Response ChangeEnumname(@RequestBody String body) throws ParseException {
        JSONObject o = JSONObject.parseObject(body);
        String nameid_Str = o.getString("nameid");
        // String namedetail = o.getString("namedetail");
        Integer nameid =
        EnumName enumName = enumNameWebInfoService.GetEnumNameByid(Integer.valueOf(nameid_Str));
        if(enumName!=null){

        }
        return Response.FAILWRONG();
    } */

    @DmcsController(loginRequired = false)
    @ApiOperation(value = "getEnumname", notes = "获取对应关系")
    @RequestMapping(value = "/getEnumname", method = RequestMethod.GET)
    public Response GetEnumname(){
        List<EnumName> enumNames = enumNameWebInfoService.GetFirPageEnums();
        if(enumNames.size()<12){
            return Response.FAILWRONG();
        }
        return Response.SUCCESSOK().setData(enumNames);
    }

    /***获取网站全部公告内容**/
    @DmcsController(authRequired = true)
    @ApiOperation(value="getwebinfo", notes = "获取网站全部公告的内容")
    @RequestMapping(value = "/getwebinfo", method = RequestMethod.GET)
    public Response GetWebInfo(){
        List<WebInformation> webInformations = enumNameWebInfoService.GetWebInfo();
        return Response.SUCCESSOK().setData(webInformations);
    }

    @DmcsController(authRequired = true)
    @ApiOperation(value= "addwebInfo", notes = "添加网站公告信息")
    @RequestMapping(value = "/addwebInfo", method = RequestMethod.POST)
    public Response AddWebInfo(@RequestBody String body, HttpServletRequest request) throws ParseException {
        logger.info(body);
        JSONObject o= JSONObject.parseObject(body);
        String inftxt=o.getString("inftxt"); //解析变量
        String linksrc=o.getString("linksrc");

        Cookie[] cookies = request.getCookies(); //获取cookie值
        String admin_token = commonTool.translateCookie(cookies,"admin_token");
        String userid = tockenCache.getUserid(admin_token);
        if(userid==null)
            return Response.FAILWRONG();
        WebInformation webInformation =new WebInformation();
        webInformation.setViewed("false");
        webInformation.setInftxt(inftxt);
        webInformation.setLinksrc(linksrc);
        webInformation.setInsertTime(new Date());
        webInformation.setInserUser(userid); //插入数据库
        enumNameWebInfoService.AddWebInfo(webInformation);
        List<WebInformation> webinfos=enumNameWebInfoService.GetWebInfo();

        return Response.SUCCESSOK().setData(webinfos);
    }

    @DmcsController(authRequired = true)
    @ApiOperation(value= "deletewebInfo", notes = "删除网站公告信息")
    @RequestMapping(value = "/deleteWebinfo", method = RequestMethod.POST)
    public Response DeleteWebInfo(@RequestBody String body, HttpServletRequest request) throws ParseException {
        logger.info(body);
        JSONObject o= JSONObject.parseObject(body);
        String infid_Str=o.getString("infid"); //获得文件的编号
        Long infid = Long.valueOf(infid_Str);

        Cookie[] cookies = request.getCookies(); //获取用户的信息
        String admin_token = commonTool.translateCookie(cookies,"admin_token");
        String userid = tockenCache.getUserid(admin_token);
        if(userid==null)
            return Response.FAILWRONG();
        WebInformation webInformation =enumNameWebInfoService.SelectWebInfo(infid);
        if(webInformation==null)
            return Response.FAILWRONG();

        SysOperationLog sys = new SysOperationLog();
        sys.setUserid(userid);
        sys.setFileid(0-infid);
        sys.setFilefullname(webInformation.getInftxt());
        sys.setOperationtime(new Date());
        sys.setOpDesc("删除网站公告");
        sysOperationService.AddOperation(sys);
        enumNameWebInfoService.DeleteInfo(infid);

        List<WebInformation> webinfos = enumNameWebInfoService.GetWebInfo();
        return Response.SUCCESSOK().setData(webinfos);
    }

    @DmcsController(authRequired = true)
    @ApiOperation(value= "updateWebinfo", notes = "改变窗口的可视性")
    @RequestMapping(value = "/updateWebinfo", method = RequestMethod.POST)
    public Response ChangeViewInfo(@RequestBody String body, HttpServletRequest request) throws ParseException {
        logger.info("改变窗口的可视性");
        JSONObject o = JSONObject.parseObject(body);
        JSONArray WEBINFOS = o.getJSONArray("webinfos");
        if(WEBINFOS == null)
            return Response.FAILWRONG().setMsg("信息丢失");

        Cookie[] cookies = request.getCookies();
        String admin_token = commonTool.translateCookie(cookies,"admin_token");
        String userid = tockenCache.getUserid(admin_token);

        int i =0;
        int fail = 0;
        for(;i<WEBINFOS.size();i++){
            JSONObject webinfo = (JSONObject) WEBINFOS.get(i);

            String infid_Str = webinfo.getString("infid");
            Long infid = Long.valueOf(infid_Str);
            String inftxt = webinfo.getString("inftxt");
            String viewed = webinfo.getString("viewed");
            WebInformation w = enumNameWebInfoService.SelectWebInfo(infid);
            if(w!=null) {
                if(!w.getInftxt().equals(inftxt) || !w.getViewed().equals(viewed)){
                    SysOperationLog sysope = new SysOperationLog();
                    sysope.setFileid(0-infid);
                    sysope.setOperationtime(new Date());
                    sysope.setUserid(userid);
                    sysope.setFilefullname(w.getInftxt());
                    sysope.setOpDesc("更改可视性或公告内容");
                    sysOperationService.AddOperation(sysope); //日志记录s
                }
                w.setViewed(viewed);
                w.setInftxt(inftxt);
                if(enumNameWebInfoService.UpdateWebInfoVT(w)==0) //更改并判断是否正常
                    fail++;
            }
        }

        List<WebInformation> webinfos = enumNameWebInfoService.GetWebInfo();
        return Response.SUCCESSOK().setData(webinfos);
    }
}
