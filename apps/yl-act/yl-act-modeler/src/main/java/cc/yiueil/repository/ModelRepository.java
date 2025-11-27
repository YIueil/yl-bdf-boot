package cc.yiueil.repository;

import cc.yiueil.domain.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ModelRepository extends JpaRepository<Model, String> {

    @Query("from Model as model where (lower(model.name) like :filter or lower(model.description) like :filter) and model.modelType = :modelType")
    List<Model> findModelsByModelType(@Param("modelType") Integer paramInteger, @Param("filter") String paramString);

    @Query("from Model as model where model.modelType = :modelType")
    List<Model> findModelsByModelType(@Param("modelType") Integer paramInteger);

    @Query("from Model as model where model.id = :parentModelId")
    List<Model> findModelsByParentModelId(@Param("parentModelId") String paramString);

    Model findFirstByName(String name);

    boolean existsByName(String name);
}

