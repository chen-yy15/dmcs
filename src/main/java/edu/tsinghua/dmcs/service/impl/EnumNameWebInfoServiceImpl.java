package edu.tsinghua.dmcs.service.impl;

import edu.tsinghua.dmcs.entity.EnumName;
import edu.tsinghua.dmcs.entity.WebInformation;
import edu.tsinghua.dmcs.mapper.EnumnameMapper;
import edu.tsinghua.dmcs.mapper.WebInfoMapper;
import edu.tsinghua.dmcs.service.EnumNameWebInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by caizj on 18-11-8.
 */
@Component
public class EnumNameWebInfoServiceImpl implements EnumNameWebInfoService {

    @Autowired
    WebInfoMapper webInfoMapper;

    @Autowired
    EnumnameMapper enumnameMapper;

    public int DeleteInfo(Long infid){
        int num = 0;
        num = webInfoMapper.deleteById(infid);
        return num;
    }

    public int AddWebInfo(WebInformation webInformation){
        int num = 0;
        if(webInformation!=null){
            num = webInfoMapper.addWebInfo(webInformation);
        }
        return num;
    }

    public int UpdateWebInfo(WebInformation webInformation){
        int num = 0;
        if(webInformation!=null) {
            num = webInfoMapper.updateWebInfo(webInformation);
        }
        return num;
    }

    public int UpdateWebInfoVT(WebInformation webInformation){
        int num = 0;
        if(webInformation!=null) {
            num = webInfoMapper.updateWebInfoVT(webInformation);
        }
        return num;
    }

    public WebInformation SelectWebInfo(Long infid){
        return webInfoMapper.selectById(infid);
    }

    public List<WebInformation> GetWebInfo(){
        return webInfoMapper.getWebInfo();
    }

    public List<WebInformation> GetShowWebInfo(){
        return webInfoMapper.getShowWebInfo();
    }

    public int UpdateNamedetail(EnumName enumName){
        int num = 0;
        if(enumName!=null){
           num = enumnameMapper.UpdateNamedetail(enumName);
        }
        return num;
    }

    public EnumName GetEnumNameByid(Integer nameid){
        return enumnameMapper.getEnumNameByid(nameid);
    }
    public List<EnumName> GetFirPageEnums(){
        return enumnameMapper.GetFirPageEnums();
    }
}
