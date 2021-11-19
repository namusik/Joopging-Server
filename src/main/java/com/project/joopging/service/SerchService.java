package com.project.joopging.service;

import com.project.joopging.dto.post.PostSearchesDto;
import com.project.joopging.model.Post;
import com.project.joopging.model.User;
import com.project.joopging.repository.PostRepository;
import org.springframework.data.geo.Distance;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class SerchService {
    private final PostRepository postRepository;

    public SerchService(PostRepository postRepository) {
        this.postRepository = postRepository;
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

    public List<PostSearchesDto> returnAllPost() {
        List<Post> result = postRepository.findAll();
        List<PostSearchesDto> postList = new ArrayList<>();

        for (Post post : result) {
            PostSearchesDto postSearchesDto = new PostSearchesDto(post);

            postList.add(postSearchesDto);
        }
        return postList;
    }
}
