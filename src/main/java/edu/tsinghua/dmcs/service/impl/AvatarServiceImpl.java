package edu.tsinghua.dmcs.service.impl;

import edu.tsinghua.dmcs.entity.Avatar;
import edu.tsinghua.dmcs.mapper.AvatarMapper;
import edu.tsinghua.dmcs.service.AvatarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by caizj on 18-9-19.
 */
@Component
public class AvatarServiceImpl implements AvatarService {
    @Autowired
    AvatarMapper avatarMapper;

    public int AddAvatar(Avatar avatar){
        int num = 0;
        if(avatar != null){
            num = avatarMapper.addAvatar(avatar);
        }
        return num;
    }

    public int DeleteAvatar(Long avatarId){
        int num = avatarMapper.deleteById(avatarId);
        return num;
    }

    public int UpdateAvatar(Avatar avatar){
        int num =0;
        if(avatar!=null){
            num = avatarMapper.updateById(avatar);
        }
        return num;
    }

    public Avatar SelectAvatar(Long avatarId){
        return avatarMapper.selectById(avatarId);
    }

    public List<Avatar> QueryAvatar(String userId){
        return avatarMapper.querySelectByUserid(userId);
    }

    public int QueryDeleteAvatar(String userId){
        int num =0;
        num = avatarMapper.queryDeleteByUserid(userId);
        return num;
    }
}
