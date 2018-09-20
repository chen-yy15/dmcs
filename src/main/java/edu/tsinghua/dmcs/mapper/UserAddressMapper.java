package edu.tsinghua.dmcs.mapper;

import edu.tsinghua.dmcs.entity.UserAddress;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by caizj on 18-9-20.
 */

/****
 * 把这个数据直接放入到userservice当中去
 */
@Mapper
public interface UserAddressMapper {
    int addAddress(UserAddress userAddress);

    int deleteById(Long addressid);

    int deleteByUserId(String userid);

    int updateById(UserAddress userAddress);

    UserAddress selectById(Long addressid);

    List<UserAddress> querySelectByUserId(String userid);

}
