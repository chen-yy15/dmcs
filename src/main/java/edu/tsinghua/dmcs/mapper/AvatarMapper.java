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

    int deleteById(Long avatarId);

    int updateById(Avatar avatar);

    Avatar selectById(Long avatarId);

    List<Avatar> querySelectByUserid(String userId);

    int queryDeleteByUserid(String userId);
}
