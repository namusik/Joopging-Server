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

    public List<PostSearchesDto> findUseByFilter(Integer distance, Integer type, Integer[] location, UserDetailsImpl userDetails) {
        List<Post> post = new ArrayList<>();
        User user = userDetails.getUser();

        
        for (int i = 0; i < location.length; i ++) {
            Location location1 = Location.getLocationById(location[i]);
            Type type1 = Type.getTypeById(type);
            Distance distance1 = Distance.getDistanceById(distance);


            if (distance == 0) {
                if (type == 0) {
                    if (location[i] == 0) {
                        return returnToPostSearchesDto(postRepository.findAll());
                    }
//                    post = postRepository.findByLocation(location1);
                    post.addAll(postRepository.findByLocation(location1));
                }
                if (type != 0) {
                    if (location[i] == 0) {
                        post = postRepository.findByType(type1);
                        return returnToPostSearchesDto(post);
                    } else {
//                        post = postRepository.findByTypeAndLocation(type1, location1);
                        post.addAll(postRepository.findByTypeAndLocation(type1, location1));
                    }
                }
            } else if (type == 0) {
                if (location[i] == 0) {
                    post = postRepository.findByDistance(distance1);
                    return returnToPostSearchesDto(post);
                }
                if (location[i] != 0) {
//                    post = postRepository.findByDistanceAndLocation(distance1, location1);
                    post.addAll(postRepository.findByDistanceAndLocation(distance1, location1));
                }
            } else {
                if (location[i] == 0) {
                    post = postRepository.findByDistanceAndType(distance1, type1);
                    return returnToPostSearchesDto(post);
                }
                if (location[i] != 0) {
//                    post = postRepository.findAllByDistanceAndTypeAndLocation(distance1, type1, location1);
                    post.addAll(postRepository.findAllByDistanceAndTypeAndLocation(distance1, type1, location1));
                }
            }
        }
        return returnToPostSearchesDto(post);



    }

    private List<PostSearchesDto> returnToPostSearchesDto (List<Post> post) {
        List<PostSearchesDto> postSearchesDtos = new ArrayList<>();

        for (Post result : post) {
            PostSearchesDto postSearchesDto = new PostSearchesDto(result);
            postSearchesDtos.add(postSearchesDto);
        }

        return postSearchesDtos;
    }
}
