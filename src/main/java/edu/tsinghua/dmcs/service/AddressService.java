package edu.tsinghua.dmcs.service;

import edu.tsinghua.dmcs.entity.UserAddress;

import java.util.List;

/**
 * Created by caizj on 18-9-20.
 */
public interface AddressService {
    public int AddAddress(UserAddress userAddress);

    public int DeleteById(Long addressId);

    public int DeleteByUserId(String userId);

    public int UpdateById(UserAddress userAddress);

    public UserAddress SelectById(Long addressId);

    public List<UserAddress> SelectByUserId(String userId);
}
