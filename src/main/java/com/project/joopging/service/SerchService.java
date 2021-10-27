package com.project.joopging.service;

import com.project.joopging.enums.Distance;
import com.project.joopging.enums.Location;
import com.project.joopging.enums.Type;
import com.project.joopging.model.Post;
import com.project.joopging.repository.PostRepository;
import com.project.joopging.security.UserDetailsImpl;
import org.springframework.stereotype.Service;


@Service
public class SerchService {
    private final PostRepository postRepository;

    public SerchService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post findUseByFilter(Integer distance, Integer type, Integer[] location, UserDetailsImpl userDetails) {
        Location location1 = Location.getLocationById(location[1]);
        Type type1 = Type.getTypeById(type);
        Distance distance1 = Distance.getDistanceById(distance);

        return postRepository.findByDistanceAndTypeAndLocation(distance1, type1, location1);

    }
}
