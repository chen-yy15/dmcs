package edu.tsinghua.dmcs.mapper;

import edu.tsinghua.dmcs.entity.EnumName;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by caizj on 18-11-8.
 */
@Mapper
public interface EnumnameMapper {

    EnumName getEnumNameByid (Integer nameid);

    int UpdateNamedetail (EnumName enumName);

    List<EnumName> GetFirPageEnums();
}
