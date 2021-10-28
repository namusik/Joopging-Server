package com.project.joopging.service;

import com.project.joopging.dto.post.PostMainPageResponseDto;
import com.project.joopging.dto.post.PostSearchesDto;
import com.project.joopging.enums.Distance;
import com.project.joopging.enums.Location;
import com.project.joopging.enums.Type;
import com.project.joopging.model.Post;
import com.project.joopging.model.User;
import com.project.joopging.repository.PostRepository;
import com.project.joopging.security.UserDetailsImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class SerchService {
    private final PostRepository postRepository;

    public SerchService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<PostSearchesDto> findUseByFilter(Integer distance, Integer type, Integer[] location) {
        List<Post> post = new ArrayList<>();

        
        for (int i = 0; i < location.length; i ++) {
            Location enumLocation = Location.getLocationById(location[i]);
            Type enumType = Type.getTypeById(type);
            Distance enumDistane = Distance.getDistanceById(distance);


            if (distance == 0) {
                if (type == 0) {
                    if (location[i] == 0) {
                        return returnToPostSearchesDto(postRepository.findAll());
                    }
//                    post = postRepository.findByLocation(enumLocation);
                    post.addAll(postRepository.findByLocation(enumLocation));
                }
                if (type != 0) {
                    if (location[i] == 0) {
                        post = postRepository.findByType(enumType);
                        return returnToPostSearchesDto(post);
                    } else {
//                        post = postRepository.findByTypeAndLocation(type1, enumLocation);
                        post.addAll(postRepository.findByTypeAndLocation(enumType, enumLocation));
                    }
                }
            } else if (type == 0) {
                if (location[i] == 0) {
                    post = postRepository.findByDistance(enumDistane);
                    return returnToPostSearchesDto(post);
                }
                if (location[i] != 0) {
//                    post = postRepository.findByDistanceAndLocation(distance1, enumLocation);
                    post.addAll(postRepository.findByDistanceAndLocation(enumDistane, enumLocation));
                }
            } else {
                if (location[i] == 0) {
                    post = postRepository.findByDistanceAndType(enumDistane, enumType);
                    return returnToPostSearchesDto(post);
                }
                if (location[i] != 0) {
//                    post = postRepository.findAllByDistanceAndTypeAndLocation(distance1, type1, enumLocation);
                    post.addAll(postRepository.findAllByDistanceAndTypeAndLocation(enumDistane, enumType, enumLocation));
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
}
