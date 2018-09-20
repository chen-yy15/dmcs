package edu.tsinghua.dmcs.mapper;

import edu.tsinghua.dmcs.entity.Avatar;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by caizj on 18-9-19.
 */
@Mapper
public interface AvatarMapper {
    int addAvatar(Avatar avatar);

    int deleteById(Long avatarid);

    int updateById(Avatar avatar);

    Avatar selectById(Long avatarid);

    List<Avatar> querySelectByUserid(String userid);

    int queryDeleteByUserid(String userid);
}
