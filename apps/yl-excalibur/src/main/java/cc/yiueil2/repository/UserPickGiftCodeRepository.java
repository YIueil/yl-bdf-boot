package cc.yiueil2.repository;

import cc.yiueil2.entity.UserPickGiftCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPickGiftCodeRepository extends JpaRepository<UserPickGiftCodeEntity, Long> {
    Boolean existsUserPickGiftCodeEntitiesByFkGiftCodeGuidAndFkUserGuid(String fkGiftCodeGuid, String fkUserGuid);
}
