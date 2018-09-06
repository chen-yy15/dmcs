package edu.tsinghua.dmcs.web;

import edu.tsinghua.dmcs.Response;
import edu.tsinghua.dmcs.entity.TechDocument;
import edu.tsinghua.dmcs.interceptor.DmcsController;
import edu.tsinghua.dmcs.service.TechDocuService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

/**
 * Created by caizj on 18-9-6.
 */
@RestController
@RequestMapping(value="/dmcs/api/v1/tech_document")
public class TechDocumentController {
    private static final Logger logger = LoggerFactory.getLogger(DeviceRestController.class);
    @Autowired
    TechDocuService techDocuService;

    @DmcsController(loginRequired=false)
    @ApiOperation(value="添加新文件", notes="")
    @RequestMapping(value = "/addocument", method = RequestMethod.POST)
    public Response Document(HttpServletRequest request) {
        //@RequestParam(value="file", required = false)
			/*if(temstring!=null){
				System.out.println(temstring);
			}*/
		MultipartHttpServletRequest params=((MultipartHttpServletRequest) request);
		//List<MultipartFile> files = ((MultipartHttpServletRequest) request)
		//		.getFiles("file");
		MultipartFile image = params.getFile("image");
		MultipartFile file = params.getFile("file");
		String title=params.getParameter("title");
		String description=params.getParameter("description");
		System.out.println("title:"+title);
		System.out.println("description:"+description);
		String identityNumber=params.getParameter("identityNumber");
		System.out.println("identityNumber:"+identityNumber);
		if(image!=null&&file!=null){
			try{
				String imageName = image.getOriginalFilename();
				String fileName = file.getOriginalFilename();
				logger.info("上传图片名为：" + imageName);
				logger.info("上传的文件名为：" + fileName);
				// 获取文件的后缀名
				String suffixName_image = imageName.substring(imageName.lastIndexOf("."));
				String suffixName_file = fileName.substring(fileName.lastIndexOf("."));
				//logger.info("文件的后缀名为：" + suffixName);

				// 设置文件存储路径
				String imagePath = "//home/caizj/file";
				String image_path = imagePath + imageName;
				String filePath = "//home/caizj/image/";
				String path = filePath +fileName;
				File dest_file = new File(path);
				if (!dest_file.getParentFile().exists()) {
					dest_file.getParentFile().mkdirs();// 新建文件夹
				}
				if(dest_file.createNewFile()){
					System.out.println("File is created");
					dest_file.setExecutable(true);
					dest_file.setReadable(true);
					dest_file.setWritable(true);
				}
				// 检测是否存在目录
				file.transferTo(dest_file);// 文件写入
				/*******/
				File dest_image = new File(image_path);
				if(!dest_image.getParentFile().exists()) {
					dest_image.getParentFile().mkdirs();
				}
				if(dest_image.createNewFile()){
					System.out.println("Image is created");
					dest_image.setExecutable(true);
					dest_image.setReadable(true);
					dest_image.setWritable(true);
				}
				image.transferTo(dest_image);//图片写入
				/****创建文件对象*/
				TechDocument techdocu = new TechDocument();
				techdocu.setDescription(description);
				techdocu.setTitle(title);
				techdocu.setDocument_addressd(path);
				techdocu.setImage_address(image_path);
				techdocu.setIdentityNumber(Integer.parseInt(identityNumber));
				int num = techDocuService.addTechDocument(techdocu);
				if(num!=0) {
				   return Response.SUCCESSOK();
				}
				else
					return Response.FAILWRONG();

			}catch (IllegalStateException e) {
				e.printStackTrace();
				return Response.FAILWRONG();
			} catch (IOException e) {
				e.printStackTrace();
				return Response.FAILWRONG();
			}
		}
		//BufferedOutputStream stream = null;
		/*for (int i = 0; i < files.size(); ++i) {
			file = files.get(i);
			if (!file.isEmpty()) {
				try {
					byte[] bytes = file.getBytes();
					stream = new BufferedOutputStream(new FileOutputStream(
							new File(file.getOriginalFilename())));
					stream.write(bytes);
					stream.close();
				} catch (Exception e) {
					stream = null;
					System.out.println( "You failed to upload " + i + " => "
							+ e.getMessage());
				}
			} else {
				System.out.println( "You failed to upload " + i
						+ " because the file was empty.");
			}
		}*/
        return Response.FAILWRONG();
    }
}
