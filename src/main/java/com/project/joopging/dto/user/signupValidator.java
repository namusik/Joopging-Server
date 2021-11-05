package com.project.joopging.dto.user;

import com.project.joopging.enums.Distance;
import com.project.joopging.enums.Location;
import com.project.joopging.enums.Type;
import com.project.joopging.enums.UserRoleEnum;
import com.project.joopging.exception.CustomErrorException;
import com.project.joopging.model.User;
import com.project.joopging.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class signupValidator {
    private final UserRepository repository;
    private final PasswordEncoder  passwordEncoder;

    public User validate(SignupRequestDto requestDto) {
        String email = requestDto.getEmail();
        String nickname = requestDto.getNickname();
        String password = requestDto.getPassword();
        UserRoleEnum role = UserRoleEnum.USER;
        Optional<User> emailFound = repository.findByEmail(email);
        Optional<User> nicknameFound = repository.findByNickname(nickname);
        Integer distance = requestDto.getDistance();
        Integer location = requestDto.getLocation();
        Integer type = requestDto.getType();


        if (emailFound.isPresent()) {
            throw new CustomErrorException("중복된 이메일 입니다 ");
        } else if (nicknameFound.isPresent()) {
            throw new CustomErrorException("중복된 닉네임 입니다 ");
        } else if (!isValidEmail(email)) {
            throw new CustomErrorException("이메일 형식이 올바르지 않습니다");
        } else if (password.length() < 6 || password.length() > 12) {
            throw new CustomErrorException("비밀번호를 6자 이상  12자 이하로 입력하세요");
        } else if (password.contains(email)) {
            throw new CustomErrorException("패스워드는 아이디를 포함할 수 없습니다.");
        }

        // 패스워드 인코딩
        password = passwordEncoder.encode(password);
        requestDto.setPassword(password);

        //location enum으로 바꾸기
        Location enumLocation = Location.getLocationById(location);

        //type enum 으로 바꾸기
        Type enumType = Type.getTypeById(type);

        //distance enum 으로 바꾸기
        Integer stringToDistance = Distance.getDistanceById(distance).getNum();
        Distance enumDistance = Distance.getDistanceById(stringToDistance);

        return new User(nickname, password, email, role, enumLocation, enumType, enumDistance);
    }

    public static boolean isValidEmail(String email) {
        boolean err = false;
        String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        if(m.matches()) { err = true; } return err; }
}
