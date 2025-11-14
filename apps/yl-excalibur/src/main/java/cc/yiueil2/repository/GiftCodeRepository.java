package cc.yiueil2.repository;

import cc.yiueil2.entity.GiftCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GiftCodeRepository extends JpaRepository<GiftCodeEntity, Long> {

}
