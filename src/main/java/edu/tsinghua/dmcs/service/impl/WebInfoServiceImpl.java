package edu.tsinghua.dmcs.service.impl;

import edu.tsinghua.dmcs.entity.WebInformation;
import edu.tsinghua.dmcs.mapper.WebInfoMapper;
import edu.tsinghua.dmcs.service.WebInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by caizj on 18-10-10.
 */
@Component
public class WebInfoServiceImpl implements WebInfoService{

    @Autowired
    WebInfoMapper webInfoMapper;

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

    public WebInformation SelectWebInfo(Long infid){
        return webInfoMapper.selectById(infid);
    }

    public List<WebInformation> GetWebInfo(){
        return webInfoMapper.getWebInfo();
    }

}
