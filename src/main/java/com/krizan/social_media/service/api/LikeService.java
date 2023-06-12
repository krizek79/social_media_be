package com.krizan.social_media.service.api;

import com.krizan.social_media.model.Like;

public interface LikeService {

    Like likePost(Long id);
    Like likeComment(Long id);
    Like unlikePost(Long id);
    Like unlikeComment(Long id);
}
