package ru.practicum.main.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.exception.model.EntityConflictException;
import ru.practicum.main.exception.model.EntityNotFoundException;
import ru.practicum.main.user.dto.NewUserRequestDto;
import ru.practicum.main.user.dto.UserDto;
import ru.practicum.main.user.mapper.UserMapper;
import ru.practicum.main.user.repository.UserRepository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public List<UserDto> getAllUsers(List<Long> ids, PageRequest pageRequest) {
        return userRepository.getAllOrIdInUsers(ids, pageRequest).stream().map(UserMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserDto createUser(NewUserRequestDto newUserRequestDto) {
        if (Objects.nonNull(userRepository.findByName(newUserRequestDto.getName()))) {
            throw new EntityConflictException("user", null);
        }

        return UserMapper.toDto(userRepository.save(UserMapper.toEntity(newUserRequestDto)));
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        userRepository.delete(userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("user", userId)));
    }
}
