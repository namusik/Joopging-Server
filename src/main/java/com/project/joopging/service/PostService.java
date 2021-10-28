package com.project.joopging.service;

import com.project.joopging.dto.post.PostCreateRequestDto;
import com.project.joopging.dto.post.PostDetailResponseDto;
import com.project.joopging.dto.post.PostUpdateRequestDto;
import com.project.joopging.dto.user.MyApplicationPostListResponseDto;
import com.project.joopging.exception.CustomErrorException;
import com.project.joopging.model.Party;
import com.project.joopging.model.Post;
import com.project.joopging.model.User;
import com.project.joopging.repository.PartyRepository;
import com.project.joopging.repository.PostRepository;
import com.project.joopging.repository.UserRepository;
import com.project.joopging.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PartyRepository partyRepository;



    //게시글 만들기
    @Transactional
    public void createPost(PostCreateRequestDto requestDto, User user) {
        Post post = Post.of(requestDto,user);


        // fetch Lazy 유저를 진짜 유저로 변환
        Long userId = user.getId();
        User writer = userRepository.findById(userId).orElseThrow(
                () -> new CustomErrorException("유저 정보를 찾을 수 없습니다")
        );
        //유저에도 포스트 추가
        List<Post> postList = writer.getPost();
        postList.add(post);
        postRepository.save(post);
    }

    //게시글 업데이트
    @Transactional
    public void updatePost(Long postId, PostUpdateRequestDto requestDto, User user) {
        Post post = getPostById(postId);
        if (post.isWrittenBy(user)) {
            post.update(requestDto);
        } else {
            throw new CustomErrorException("모임의 작성자가 아닙니다");
        }
    }

    //게시글 삭제
    public void deletePost(Long postId, User user) {
        Post post = getPostById(postId);
        if(post.isWrittenBy(user)) {
            postRepository.delete(post);
        } else {
            throw new CustomErrorException("모임의 작성자가 아닙니다");
        }
    }



    public Post getDetailPostById(Long postId) {
        Post post = getPostById(postId);
//        List<Comment> commentList = post.getComments();
        post.setViewCount(post.getViewCount() + 1);
        postRepository.save(post);
        return post;
    }


    private Post getPostById(Long postId) {
        return postRepository.findById(postId).orElseThrow(
                () -> new CustomErrorException("게시글을 찾을 수 없습니다.")
        );
    }


    public PostDetailResponseDto toSetPostDetailResponseDto(Post post, UserDetailsImpl userDetails) {
        boolean joinCheck;
        if (userDetails == null) {
            return post.toBuildDetailPost(null, false);
        } else {
            User user = userDetails.getUser();
            joinCheck = partyRepository.findByUserJoinAndPostJoin(user, post).isPresent();
        }

        return post.toBuildDetailPost(userDetails, joinCheck);

    }

    public MyApplicationPostListResponseDto getMyApplicationPostListByUser(User user) {

        List<Post> applicationPostList = new ArrayList<>();
        Long userId = user.getId();
        Optional<User> myUser =  userRepository.findById(userId);
        List<Party> partyList = partyRepository.findAllByUserJoin(myUser);
        for (Party party : partyList) {
            Post applicationPost = party.getPostJoin();
            applicationPostList.add(applicationPost);
        }
        return user.toBuildApplicationPostList(applicationPostList);

    }
}
