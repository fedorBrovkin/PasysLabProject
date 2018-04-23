package com.epam.labproject.controller;

import com.epam.labproject.form.UnblockRequestForm;
import com.epam.labproject.service.AccountService;
import com.epam.labproject.service.UnblockRequestService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
public class UnblockRequestControllerTests {

    @MockBean
    private UnblockRequestService requestService;

    @MockBean
    private AccountService accountService;

    private List<UnblockRequestForm> requests;

    private UnblockRequestForm unblockRequestForm;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("classpath:templates/");
        viewResolver.setSuffix(".html");

        mockMvc = MockMvcBuilders.standaloneSetup(new UnblockRequestController(requestService, accountService))
                .setViewResolvers(viewResolver)
                .build();

        requests = new ArrayList<>();

        unblockRequestForm = new UnblockRequestForm();

        Mockito.when(UnblockRequestForm.getFormList(requestService.findAllRequest())).thenReturn(requests);
    }

    @Test
    public void testGetShowUnblockRequest() throws Exception {
        mockMvc.perform(get("/admUnblockRequest"))
                .andExpect(model().attribute("requests", notNullValue()))
                .andExpect(model().attribute("requests", hasSize(0)))
                .andExpect(model().attribute("unblockRequestForm", notNullValue()))
                .andExpect(status().isOk())
                .andExpect(view().name("admUnblockRequest"));

        verify(requestService, times(1)).findAllRequest();
    }

    @Test
    public void testPostChangeStatus() throws Exception {
        mockMvc.perform(post("/admUnblockRequest")
                .flashAttr("unblockRequestForm", unblockRequestForm)
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("admUnblockRequest?status"));

        verify(requestService, times(1)).delete(anyInt());
        verify(accountService, times(1)).changeStatus(anyInt());
    }


}
