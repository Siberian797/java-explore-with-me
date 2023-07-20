package ru.practicum.main.user.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.main.user.model.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE ((:users) IS NULL OR u.id IN :users)")
    List<User> getAllOrIdInUsers(@Param("users") List<Long> users, Pageable pageable);

    User findByName(String name);
}