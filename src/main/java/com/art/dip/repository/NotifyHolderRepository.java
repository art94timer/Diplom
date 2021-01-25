package com.art.dip.repository;

import com.art.dip.model.NotifyHolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NotifyHolderRepository extends JpaRepository<NotifyHolder,Integer> {

    Optional<NotifyHolder> getNotifyHolderByFaculty_Id(Integer id);
}
