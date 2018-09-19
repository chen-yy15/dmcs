package edu.tsinghua.dmcs.service;

import edu.tsinghua.dmcs.entity.Avatar;

import java.util.List;

/**
 * Created by caizj on 18-9-19.
 */
public interface AvatarService {
    public int AddAvatar(Avatar avatar);

    public int DeleteAvatar(Long avatarId);

    public int UpdateAvatar(Avatar avatar);

    public Avatar SelectAvatar(Long avatarId);

    public List<Avatar> QueryAvatar(String userId);

    public int QueryDeleteAvatar(String userId);
}
