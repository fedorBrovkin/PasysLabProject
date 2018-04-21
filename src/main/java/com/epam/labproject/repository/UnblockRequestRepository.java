package com.epam.labproject.repository;

import com.epam.labproject.entity.Account;
import com.epam.labproject.entity.UnblockRequest;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnblockRequestRepository extends JpaRepository<UnblockRequest, Long> {

  public List<UnblockRequest> findAll();

  public UnblockRequest findByAccount(Account account);
}
