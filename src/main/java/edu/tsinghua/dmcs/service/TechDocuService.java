package edu.tsinghua.dmcs.service;

import edu.tsinghua.dmcs.entity.TechDocument;

import java.util.List;

/**
 * Created by caizj on 18-9-6.
 */
public interface TechDocuService {
    public int addTechDocument(TechDocument techDocument);

    public int updateDevice(TechDocument techDocument);

    public int deleteDevice(Long id);

    public List<TechDocument> queryDocuByNumber(int identityNumber);

    public TechDocument getDeviceById(Long id);

}
