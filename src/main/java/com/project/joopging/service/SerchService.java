package com.project.joopging.service;

import com.project.joopging.dto.post.PostSearchesDto;
import com.project.joopging.model.BookMark;
import com.project.joopging.model.Post;
import com.project.joopging.model.User;
import com.project.joopging.repository.BookMarkRepository;
import com.project.joopging.repository.PostRepository;
import org.springframework.data.geo.Distance;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class SerchService {
    private final PostRepository postRepository;
    private final BookMarkRepository bookMarkRepository;

    public SerchService(PostRepository postRepository, BookMarkRepository bookMarkRepository) {
        this.postRepository = postRepository;
        this.bookMarkRepository = bookMarkRepository;
    }

    public List<PostSearchesDto> findUseByFilter(String distance, String type, String[] location) {
        List<Post> post = new ArrayList<>();

        
        for (int i = 0; i < location.length; i ++) {
            String enumLocation = location[i];

            if (distance.equals("거리전체")) {
                if (type.equals("장소전체")) {
                    if (location[i].equals("지역전체")) {
                        return returnToPostSearchesDto(postRepository.findAll());
                    }
//                    post = postRepository.findByLocation(enumLocation);
                    post.addAll(postRepository.findByLocation(enumLocation));
                }
                if (!type.equals("장소전체")) {
                    if (location[i].equals("지역전체")) {
                        post = postRepository.findByType(type);
                        return returnToPostSearchesDto(post);
                    } else {
//                        post = postRepository.findByTypeAndLocation(type1, enumLocation);
                        post.addAll(postRepository.findByTypeAndLocation(type, enumLocation));
                    }
                }
            } else if (type.equals("장소전체")) {
                if (location[i].equals("지역전체")) {
                    post = postRepository.findByDistance(distance);
                    return returnToPostSearchesDto(post);
                }
                if (!location[i].equals("지역전체")) {
//                    post = postRepository.findByDistanceAndLocation(distance1, enumLocation);
                    post.addAll(postRepository.findByDistanceAndLocation(distance, enumLocation));
                }
            } else {
                if (location[i].equals("지역전체")) {
                    post = postRepository.findByDistanceAndType(distance, type);
                    return returnToPostSearchesDto(post);
                }
                if (!location[i].equals("지역전체")) {
//                    post = postRepository.findAllByDistanceAndTypeAndLocation(distance1, type1, enumLocation);
                    post.addAll(postRepository.findAllByDistanceAndTypeAndLocation(distance, type, enumLocation));
                }
            }
        }
        return returnToPostSearchesDto(post);
    }

    private List<PostSearchesDto> returnToPostSearchesDto (List<Post> post) {
        List<PostSearchesDto> postSearchesDtos = new ArrayList<>();

        for (Post result : post) {
            User writer = result.getWriter();
            PostSearchesDto postSearchesDto = new PostSearchesDto(result, writer);
            postSearchesDtos.add(postSearchesDto);
        }

        return postSearchesDtos;
    }

    public List<PostSearchesDto> returnAllPosNotLogin() {
        List<Post> result = postRepository.findAll();
        List<PostSearchesDto> postList = new ArrayList<>();

        for (Post post : result) {
            User writer = post.getWriter();
            PostSearchesDto postSearchesDto = new PostSearchesDto(post,writer);

            postList.add(postSearchesDto);
        }
        return postList;
    }

    public List<PostSearchesDto> returnAllPosLogin(User user) {
        List<Post> result = postRepository.findAll();
        List<PostSearchesDto> postList = new ArrayList<>();

        for (Post post : result) {
            User writer = post.getWriter();
            boolean checkBookMark = checkBookMark(user, post);

            PostSearchesDto postSearchesDto = new PostSearchesDto(post,writer,checkBookMark);

            postList.add(postSearchesDto);
        }
        return postList;
    }

    public boolean checkBookMark(User user, Post post) {
        Optional<BookMark> BookMark = bookMarkRepository.findByUserBookMarkAndPostBookMark(user, post);
        if (BookMark.isPresent()) {
            return true;
        } else {
            return false;
        }
    }
}
