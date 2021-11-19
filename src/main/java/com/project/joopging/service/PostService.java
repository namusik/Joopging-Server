package com.project.joopging.service;

import com.project.joopging.dto.comment.AllCommentResponseDto;
import com.project.joopging.dto.post.PostCreateRequestDto;
import com.project.joopging.dto.post.PostDetailResponseDto;
import com.project.joopging.dto.post.PostUpdateRequestDto;
import org.springframework.transaction.annotation.Transactional;
import com.project.joopging.dto.user.MyApplicationPostListResponseDto;
import com.project.joopging.dto.user.MyBookmarkListResponseDto;
import com.project.joopging.dto.user.MyPostPageListResponseDto;
import com.project.joopging.exception.CustomErrorException;
import com.project.joopging.model.*;
import com.project.joopging.repository.*;
import com.project.joopging.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CrewRepository crewRepository;
    private final BookMarkRepository bookMarkRepository;



    //게시글 만들기
    //만든사람 크루 참여하게 하기
    @Transactional
    public void createPost(PostCreateRequestDto requestDto, User user) {
        Post post = Post.of(requestDto,user);
        Crew crew = Crew.of(user,post);
        // fetch Lazy 유저를 진짜 유저로 변환
        Long userId = user.getId();
        User writer = userRepository.findById(userId).orElseThrow(
                () -> new CustomErrorException("유저 정보를 찾을 수 없습니다.")
        );
        //유저에 포스트와 크루 추가 , 포스트에 크루 추가
        List<Post> postList = writer.getPost();
        postList.add(post);
        List<Crew> crewList = writer.getCrews();
        crewList.add(crew);
        List<Crew> crews = post.getCrew();
        crews.add(crew);
        //먼저 저장하고 pk 키를 발급받은후 crew 를 세이브시켜야함
        postRepository.save(post);
        crewRepository.save(crew);


    }

    //게시글 업데이트
    @Transactional
    public void updatePost(Long postId, PostUpdateRequestDto requestDto, User user) {
        Post post = getPostById(postId);
        if (post.isWrittenBy(user)) {
            post.update(requestDto);
        } else {
            throw new CustomErrorException("모임의 작성자가 아닙니다.");
        }
    }

    //게시글 삭제
    public void deletePost(Long postId, User user) {
        Post post = getPostById(postId);
        if(post.isWrittenBy(user)) {
            postRepository.delete(post);
        } else {
            throw new CustomErrorException("모임의 작성자가 아닙니다.");
        }
    }



    public Post getDetailPostById(Long postId) {
        Post post = getPostById(postId);
        post.setViewCount(post.getViewCount() + 1);
        postRepository.save(post);
        return post;
    }


    public Post getPostById(Long postId) {
        return postRepository.findById(postId).orElseThrow(
                () -> new CustomErrorException("게시글을 찾을 수 없습니다.")
        );
    }

    //디테일 페이지 (북마크 추가)
    //북마크 카운트 추가
    //댓글 작성자 닉네임 이미지 추가
    @Transactional(readOnly = true)
    public PostDetailResponseDto toSetPostDetailResponseDto(Post post, UserDetailsImpl userDetails) {
        boolean joinCheck;
        boolean bookmarkInfo;
        String runningDateToString = getRunningDateToString(post);
        if (userDetails == null) {
            return post.toBuildDetailPost(null, false, false, runningDateToString);
        } else {
            User user = userDetails.getUser();
            joinCheck = crewRepository.findByUserJoinAndPostJoin(user, post).isPresent();
            bookmarkInfo = bookMarkRepository.findByUserBookMarkAndPostBookMark(user, post).isPresent();
        }
        return post.toBuildDetailPost(userDetails, joinCheck, bookmarkInfo, runningDateToString);
    }

    //댓글 정보 수정해서 내보내기
    //아무곳이나 끌어써도 됨
    @Transactional(readOnly = true)
    public List<AllCommentResponseDto> getAllCommentResponseDtos(Post post) {
        List<AllCommentResponseDto> allCommentResponseDtos = new ArrayList<>();
        List<Comment> commentList= post.getComments();
        for (Comment comment : commentList) {
            String modifiedAtToString = getModifiedAtToString(comment);
            AllCommentResponseDto responseDto = comment.toBuildDetailComment(modifiedAtToString);
            allCommentResponseDtos.add(responseDto);
        }
        return allCommentResponseDtos;
    }

    //댓글 수정날짜 스트링으로 변환
    private String getModifiedAtToString(Comment comment) {
        LocalDateTime modifiedAt = comment.getModifiedAt();
        String day = modifiedAt.getDayOfWeek().getDisplayName(TextStyle.NARROW, Locale.KOREAN);
        String date = String.valueOf(modifiedAt);
//        System.out.println("date = " + date);
        String[] ts = date.split("T");
        return ts[0] + " ("+day+") " + ts[1];
    }

    //날짜 스트링으로 변환
    private String getRunningDateToString(Post post) {
        LocalDateTime runningDate = post.getRunningDate();
        String day = runningDate.getDayOfWeek().getDisplayName(TextStyle.NARROW, Locale.KOREAN);
        String date = String.valueOf(runningDate);
//        System.out.println("date = " + date);
        String[] ts = date.split("T");
        return ts[0] + " ("+day+") " + ts[1];
    }

    //내 신청내역 (북마크 추가)
    //북마크 카운트 추가
    @Transactional(readOnly = true)
    public List<MyApplicationPostListResponseDto> getMyApplicationPostListByUser(User user) {
        boolean bookmarkInfo;
        List<MyApplicationPostListResponseDto> applicationPostList = new ArrayList<>();
        Long userId = user.getId();
        //Optional 유저를 쓰거나 .orElseThrow 를 쓰거나
        User myUser = userRepository.findById(userId).orElseThrow(
                () -> new CustomErrorException("존재하지 않는 유저입니다.")
        );
        List<Crew> crewList = crewRepository.findAllByUserJoin(myUser);
        for (Crew crew : crewList) {
            Post applicationPost = crew.getPostJoin();
            boolean attendation = crew.isAttendation();
            String runningDateToString = getRunningDateToString(applicationPost);
            bookmarkInfo = bookMarkRepository.findByUserBookMarkAndPostBookMark(myUser, applicationPost).isPresent();
            MyApplicationPostListResponseDto responseDto = applicationPost.toBuildMyApplicationPost(bookmarkInfo, runningDateToString, attendation);
            applicationPostList.add(responseDto);
        }

        return applicationPostList;

    }

    // 내 모집관리
    // 북마크 수 추가
    @Transactional(readOnly = true)
    public List<MyPostPageListResponseDto> getMyPostListByUser(User user) {
        Long userId = user.getId();
        User myUser = userRepository.findById(userId).orElseThrow(
                () -> new CustomErrorException("유저 정보가 없습니다.")
        );
        List<Post> myPostList = myUser.getPost();
        List<MyPostPageListResponseDto> myPostListRes = new ArrayList<>();
        for (Post post : myPostList) {
            String runningDateToString = getRunningDateToString(post);
            MyPostPageListResponseDto responseDto = post.toBuildMyCreatePost(runningDateToString);
            myPostListRes.add(responseDto);
        }
        return myPostListRes;
    }

    //북마크 ON/OFF
    @Transactional
    public boolean getBookMarkInfo(User user, Long postId) {
        Long userId = user.getId();
        User myUser = userRepository.findById(userId).orElseThrow(
                () -> new CustomErrorException("존재하지 않는 유저입니다.")
        );
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new CustomErrorException("존재하지 않는 게시글입니다.")
        );
        Optional<BookMark> bookMark = bookMarkRepository.findByUserBookMarkAndPostBookMark(myUser, post);
        if (bookMark.isPresent()) {
            bookMarkRepository.delete(bookMark.get());
            return false;
        } else {
            BookMark myBookMark = BookMark.of(myUser, post);
            bookMarkRepository.save(myBookMark);
            List<BookMark> userBookMark = myUser.getBookMarks();
            userBookMark.add(myBookMark);
            List<BookMark> postBookMark = post.getBookMarks();
            postBookMark.add(myBookMark);
            return true;
        }



    }
    //내 북마크 리스트
    @Transactional(readOnly = true)
    public List<MyBookmarkListResponseDto> getMyBookmarkListByUser(User user) {
        boolean joinCheck;
        List<MyBookmarkListResponseDto> myBookmarkList = new ArrayList<>();
        Long userId = user.getId();
        User myUser = userRepository.findById(userId).orElseThrow(
                () -> new  CustomErrorException("유저를 정보가 없습니다.")
        );
        List<BookMark> bookMarkList = bookMarkRepository.findAllByUserBookMark(myUser);
        for (BookMark bookMark : bookMarkList) {
            Post myBookmarkPost = bookMark.getPostBookMark();
            String runningDateToString = getRunningDateToString(myBookmarkPost);
            joinCheck = crewRepository.findByUserJoinAndPostJoin(myUser, myBookmarkPost).isPresent();
            MyBookmarkListResponseDto responseDto = myBookmarkPost.toBuildMyBookmarkPost(joinCheck, runningDateToString);
            myBookmarkList.add(responseDto);
        }
        return myBookmarkList;
    }
}
