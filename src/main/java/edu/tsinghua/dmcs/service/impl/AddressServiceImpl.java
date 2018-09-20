package edu.tsinghua.dmcs.service.impl;

import edu.tsinghua.dmcs.entity.UserAddress;
import edu.tsinghua.dmcs.mapper.UserAddressMapper;
import edu.tsinghua.dmcs.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by caizj on 18-9-20.
 */
@Component
public class AddressServiceImpl implements AddressService {

    @Autowired
    UserAddressMapper userAddressMapper;


    public int AddAddress(UserAddress userAddress){
        int num = 0;
        if(userAddress!=null){
            num = userAddressMapper.addAddress(userAddress);
        }
        return num;
    }

    public int DeleteById(Long addressId){
        return userAddressMapper.deleteById(addressId);
    }

    public int DeleteByUserId(String userId){
        return userAddressMapper.deleteByUserId(userId);
    }

    public int UpdateById(UserAddress userAddress){
        int num = 0;
        if(userAddress!=null){
            num = userAddressMapper.updateById(userAddress);
        }
        return num;
    }

    public UserAddress SelectById(Long addressId){
        return userAddressMapper.selectById(addressId);
    }

    public List<UserAddress> SelectByUserId(String userId){
        return userAddressMapper.querySelectByUserId(userId);
    }
}
