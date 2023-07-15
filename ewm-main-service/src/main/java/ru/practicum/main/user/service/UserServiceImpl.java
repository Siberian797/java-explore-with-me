package ru.practicum.main.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.exception.model.EntityNotFoundException;
import ru.practicum.main.user.dto.NewUserRequestDto;
import ru.practicum.main.user.dto.UserDto;
import ru.practicum.main.user.mapper.UserMapper;
import ru.practicum.main.user.model.User;
import ru.practicum.main.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public List<UserDto> getAllUsers(List<Long> ids, PageRequest pageRequest) {
        List<User> users = userRepository.getAllOrIdInUsers(ids, pageRequest);
        return users.stream().map(UserMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserDto createUser(NewUserRequestDto newUserRequestDto) {
        User user = UserMapper.toEntity(newUserRequestDto);
        return UserMapper.toDto(userRepository.save(user));
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("user", userId));
        userRepository.delete(user);
    }
}
