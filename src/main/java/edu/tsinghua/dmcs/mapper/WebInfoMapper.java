package edu.tsinghua.dmcs.mapper;

import edu.tsinghua.dmcs.entity.WebInformation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by caizj on 18-10-9.
 */
@Mapper
public interface WebInfoMapper {

    int deleteById(Long infid);

    int addWebInfo(WebInformation webInformation);

    int updateWebInfo(WebInformation webInformation);

    int updateWebInfoVT(WebInformation webInformation);

    WebInformation selectById(Long infid);

    List<WebInformation> getWebInfo();

    List<WebInformation> getShowWebInfo();
}
