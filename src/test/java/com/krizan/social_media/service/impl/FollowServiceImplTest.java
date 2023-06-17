package com.krizan.social_media.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.krizan.social_media.controller.exception.IllegalOperationException;
import com.krizan.social_media.controller.exception.NotFoundException;
import com.krizan.social_media.model.AppUser;
import com.krizan.social_media.model.Follow;
import com.krizan.social_media.repository.FollowRepository;
import com.krizan.social_media.service.api.AppUserService;
import java.util.ArrayList;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@DisplayNameGeneration(DisplayNameGenerator.Simple.class)
class FollowServiceImplTest {

    @Mock
    private AppUserService appUserService;
    @Mock
    private FollowRepository followRepository;
    @InjectMocks
    private FollowServiceImpl followService;

    private AppUser currentUser;
    private AppUser followedUser;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        currentUser = AppUser.builder()
            .id(1L)
            .username("follower")
            .followers(new ArrayList<>())
            .following(new ArrayList<>())
            .build();
        followedUser = AppUser.builder()
            .id(2L)
            .username("followed")
            .followers(new ArrayList<>())
            .following(new ArrayList<>())
            .build();
    }

    @Test
    public void followShouldSaveFollow() {
        //  Arrange
        when(appUserService.getCurrentAppUser()).thenReturn(currentUser);
        when(appUserService.getAppUserById(followedUser.getId())).thenReturn(followedUser);
        when(
            followRepository.findByFollower_IdAndFollowed_Id(
                currentUser.getId(),
                followedUser.getId()
            )
        ).thenReturn(Optional.empty());

        Follow expectedFollow = Follow.builder()
            .follower(currentUser)
            .followed(followedUser)
            .build();
        when(followRepository.save(any(Follow.class))).thenReturn(expectedFollow);

        //  Act
        Follow result = followService.follow(followedUser.getId());

        //  Verify
        verify(appUserService).getCurrentAppUser();
        verify(appUserService).getAppUserById(followedUser.getId());
        verify(followRepository).findByFollower_IdAndFollowed_Id(
            currentUser.getId(),
            followedUser.getId()
        );
        verify(followRepository).save(any(Follow.class));

        assertEquals(expectedFollow, result);
        assertFalse(currentUser.getFollowing().isEmpty());
        assertFalse(followedUser.getFollowers().isEmpty());
    }

    @Test
    public void followShouldThrowIllegalOperationException() {
        //  Arrange
        when(appUserService.getCurrentAppUser()).thenReturn(currentUser);
        when(appUserService.getAppUserById(followedUser.getId())).thenReturn(followedUser);
        when(
            followRepository.findByFollower_IdAndFollowed_Id(
                currentUser.getId(),
                followedUser.getId()
            )
        ).thenReturn(Optional.of(new Follow()));

        //  Act & assert
        assertThrows(
            IllegalOperationException.class,
            () -> followService.follow(followedUser.getId())
        );
        verify(appUserService).getCurrentAppUser();
        verify(appUserService).getAppUserById(followedUser.getId());
        verify(followRepository).findByFollower_IdAndFollowed_Id(
            currentUser.getId(),
            followedUser.getId()
        );
        verifyNoMoreInteractions(followRepository);
    }

    @Test
    public void unfollowShouldDeleteFollow() {
        //  Arrange
        Follow existingFollow = Follow.builder()
            .follower(currentUser)
            .followed(followedUser)
            .build();

        when(appUserService.getCurrentAppUser()).thenReturn(currentUser);
        when(appUserService.getAppUserById(followedUser.getId())).thenReturn(followedUser);
        when(
            followRepository.findByFollower_IdAndFollowed_Id(
                currentUser.getId(),
                followedUser.getId()
            )
        ).thenReturn(Optional.of(existingFollow));

        //  Act
        Follow result = followService.unfollow(followedUser.getId());

        // Assert
        verify(appUserService).getCurrentAppUser();
        verify(appUserService).getAppUserById(followedUser.getId());
        verify(followRepository).findByFollower_IdAndFollowed_Id(
            currentUser.getId(),
            followedUser.getId()
        );
        verify(followRepository).delete(existingFollow);

        assertEquals(existingFollow, result);
        assertTrue(currentUser.getFollowing().isEmpty());
        assertTrue(followedUser.getFollowers().isEmpty());
    }

    @Test
    public void unfollowShouldThrowNotFoundException() {
        //  Arrange
        when(appUserService.getCurrentAppUser()).thenReturn(currentUser);
        when(appUserService.getAppUserById(followedUser.getId())).thenReturn(followedUser);
        when(
            followRepository.findByFollower_IdAndFollowed_Id(
                currentUser.getId(),
                followedUser.getId()
            )
        ).thenReturn(Optional.empty());

        //  Act & assert
        assertThrows(
            NotFoundException.class,
            () -> followService.unfollow(followedUser.getId())
        );

        verify(appUserService).getCurrentAppUser();
        verify(appUserService).getAppUserById(followedUser.getId());
        verify(followRepository).findByFollower_IdAndFollowed_Id(
            currentUser.getId(),
            followedUser.getId()
        );
        verifyNoMoreInteractions(followRepository);
    }
}