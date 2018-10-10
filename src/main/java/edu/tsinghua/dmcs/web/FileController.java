package edu.tsinghua.dmcs.web;

import edu.tsinghua.dmcs.Response;
import edu.tsinghua.dmcs.interceptor.DmcsController;
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

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;

/**
 * Created by caizj on 18-10-9.
 */
@RestController
@RequestMapping(value="/dmcs/api/v1/file")
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(AdminRestController.class);

    @Autowired
    private UserService userService;
    @Autowired
    TockenCache tockenCache;

    @DmcsController(authRequired = true)
    @ApiOperation(value = "addFile",notes="添加文件")
    @RequestMapping(value = "/addFile", method = RequestMethod.POST)
    public Response AddFile(@RequestBody String body, HttpServletRequest request) throws ParseException {
        return Response.FAILWRONG().setErrcode(0);
    }

    @DmcsController(authRequired = true)
    @ApiOperation(value = "deleteFile", notes = "删除文件")
    @RequestMapping(value = "/deleteFile", method = RequestMethod.POST)
    public Response DeleteFile(@RequestBody String body, HttpServletRequest request) throws ParseException {
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
    @ApiOperation(value="getFile", notes = "特殊文件获取")
    @RequestMapping(value = "/getFile", method = RequestMethod.POST)
    public Response GetFile(@RequestBody String body, HttpServletRequest request) throws ParseException {
        return Response.FAILWRONG().setErrcode(0);
    }

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
