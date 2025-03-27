package cc.yiueil.repository;

import cc.yiueil.entity.UserSignEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserSignRepository extends JpaRepository<UserSignEntity, Long> {
    Optional<UserSignEntity> findFirstByFkUserGuid(String guid);
}
