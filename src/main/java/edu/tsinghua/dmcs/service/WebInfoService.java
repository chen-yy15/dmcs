package edu.tsinghua.dmcs.service;

import edu.tsinghua.dmcs.entity.WebInformation;

import java.util.List;

/**
 * Created by caizj on 18-10-10.
 */
public interface WebInfoService {

    public int DeleteInfo(Long infid);

    public int AddWebInfo(WebInformation webInformation);

    public int UpdateWebInfo(WebInformation webInformation);

    public WebInformation SelectWebInfo(Long infid);

    public List<WebInformation> GetWebInfo();
}
