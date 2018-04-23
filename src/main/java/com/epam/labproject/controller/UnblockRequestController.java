package com.epam.labproject.controller;

import com.epam.labproject.form.UnblockRequestForm;
import com.epam.labproject.service.AccountService;
import com.epam.labproject.service.UnblockRequestService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UnblockRequestController {

  private final UnblockRequestService requestService;
  private final AccountService accountService;

  /**
   * Constructor.
   *
   * @param requestService Injected instance
   * @param accountService Injected instance
   */
  public UnblockRequestController(UnblockRequestService requestService,
      AccountService accountService) {
    this.requestService = requestService;
    this.accountService = accountService;
  }

  /**
   * Get method
   *
   * @param model instance
   */
  @GetMapping("/admUnblockRequest")
  public String showUnblockRequest(Model model,
      @RequestParam(value = "status", required = false) String status) {
    List<UnblockRequestForm> requests = UnblockRequestForm
        .getFormList(requestService.findAllRequest());
    model.addAttribute("status", status != null);
    model.addAttribute("requests", requests);
    model.addAttribute("unblockRequestForm", new UnblockRequestForm());
    return "admUnblockRequest";
  }

  /**
   * Post method.
   *
   * @param requestForm instance
   */
  @PostMapping("/admUnblockRequest")
  public String changeStatus(@ModelAttribute("unblockRequestForm") UnblockRequestForm requestForm) {
    int accNumber = requestForm.getAccNumber();
    requestService.unblockAccount(accNumber);
    return "redirect:admUnblockRequest?status";
  }

}
