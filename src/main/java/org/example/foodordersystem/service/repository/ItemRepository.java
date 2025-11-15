package org.example.foodordersystem.service.repository;

import org.example.foodordersystem.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface ItemRepository extends JpaRepository<Item, UUID> {
    boolean existsByName(String name);

    @Query("SELECT i FROM Item i WHERE i.id IN (:ids)")
    List<Item> findAllByIdSet(@Param("ids") Set<UUID> ids);
}
