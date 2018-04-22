package com.epam.labproject.service;

import com.epam.labproject.entity.Account;
import com.epam.labproject.entity.UnblockRequest;
import com.epam.labproject.repository.UnblockRequestRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UnblockRequestService {

  private UnblockRequestRepository requestRepository;
  private AccountService accountService;
  private UserService userService;
  private DataBaseUserDetailsService detailsService;

  public UnblockRequestService(
      UnblockRequestRepository requestRepository,
      AccountService accountService, UserService userService,
      DataBaseUserDetailsService detailsService) {
    this.requestRepository = requestRepository;
    this.accountService = accountService;
    this.userService = userService;
    this.detailsService = detailsService;
  }

  public List<UnblockRequest> findAllRequest() {
    return requestRepository.findAll();
  }

  public void save(UnblockRequest request) {
    requestRepository.save(request);
  }

  public void createRequest(int accNumber) {
    Account account = accountService.findByNumber(accNumber);
    UnblockRequest request;
    if ((request = requestRepository.findByAccount(account)) != null) {
      requestRepository.delete(request);
    }
    request = new UnblockRequest();
    request.setAccount(account);
    request.setUser(userService.getUser(detailsService.getCurrentUsername()));
    request.setTime(LocalDateTime.now());
    save(request);
  }

  public void delete(int accNumber) {
    UnblockRequest request = requestRepository
        .findByAccount(accountService.findByNumber(accNumber));
    requestRepository.delete(request);
  }


}
