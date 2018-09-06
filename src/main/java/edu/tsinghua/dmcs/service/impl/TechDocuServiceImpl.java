package edu.tsinghua.dmcs.service.impl;

import edu.tsinghua.dmcs.entity.TechDocument;
import edu.tsinghua.dmcs.mapper.TechDocumentMapper;
import edu.tsinghua.dmcs.service.TechDocuService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by caizj on 18-9-6.
 */
@Component
public class TechDocuServiceImpl implements TechDocuService {
    @Autowired
    TechDocumentMapper techDocumentMapper;
    public int addTechDocument(TechDocument techDocument){
        int num = 0;
        if(techDocument!=null){
            num = techDocumentMapper.insert(techDocument);
        }
        return num;
    }
    public int updateDevice(TechDocument techDocument){
        int num = 0;
        if(techDocument!=null){
            num = techDocumentMapper.updateByPrimaryKey(techDocument);
        }
        return num;
    }
    public int deleteDevice(Long id){
        int num = techDocumentMapper.deleteByPrimaryKey(id);
        return num;
    }
    @ApiOperation(value="通过模式idnumber查询文档信息", notes="")
    @RequestMapping("/queryDocuByNumber")
    public List<TechDocument> queryDocuByNumber(int identityNumber){
         List<TechDocument> techDocuments = techDocumentMapper.queryDocuByNumber(identityNumber);
         return techDocuments;
    }
    public TechDocument getDeviceById(Long id){
        return techDocumentMapper.selectByPrimaryKey(id);
    }
}
