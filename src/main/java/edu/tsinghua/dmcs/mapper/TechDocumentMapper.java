package edu.tsinghua.dmcs.mapper;

import edu.tsinghua.dmcs.entity.TechDocument;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by caizj on 18-9-5.
 */
@Mapper
public interface TechDocumentMapper {
    int deleteByPrimaryKey(Long id);//

    int insert(TechDocument record);//

    TechDocument selectByPrimaryKey(Long id);//

    int updateByPrimaryKeySelective(TechDocument record);//

    int updateByPrimaryKey(TechDocument record);//

    List <TechDocument> queryDocuByNumber(int identityNumber); //提取全部的文件信息

    //List<Device> queryUnbindDevices(@Param("page") Integer page, @Param("size") Integer size);
}
