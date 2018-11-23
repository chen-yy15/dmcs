package edu.tsinghua.dmcs.service;

import edu.tsinghua.dmcs.entity.EnumName;
import edu.tsinghua.dmcs.entity.WebInformation;

import java.util.List;

/**
 * Created by caizj on 18-11-8.
 */
public interface EnumNameWebInfoService {

    public int DeleteInfo(Long infid);

    public int AddWebInfo(WebInformation webInformation);

    public int UpdateWebInfo(WebInformation webInformation);

    public int UpdateWebInfoVT(WebInformation webInformation); //仅更改可视性和内容

    public WebInformation SelectWebInfo(Long infid);

    public List<WebInformation> GetWebInfo();

    public List<WebInformation> GetShowWebInfo();

    public int UpdateNamedetail(EnumName enumName);

    public EnumName GetEnumNameByid(Integer nameid);

    public List<EnumName> GetFirPageEnums();
}
