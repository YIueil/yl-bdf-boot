package cc.yiueil2.repository;

import cc.yiueil2.entity.UserSignEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserSignRepository extends JpaRepository<UserSignEntity, Long> {
    Optional<UserSignEntity> findFirstByFkUserGuid(String guid);
}
