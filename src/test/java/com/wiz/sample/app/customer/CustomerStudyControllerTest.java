package com.wiz.sample.app.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * MockMVCを利用したテストクラス。
 *
 * アプリケーションコンテキストをロードしてテストする。
 * （⇔スタンドアロンモードの場合は●の部分）
 * 　SpringBootTestアノテーションを指定
 *
 */

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
@Rollback
class CustomerStudyControllerTest {

    /**
     * テスト時に使用するコンテキスト
     */
    @Autowired
    WebApplicationContext context;

    MockMvc mockMvc;

    //    @Autowired
    private TestRestTemplate restTemplate;

    /**
     * MockMVC生成
     */
    @BeforeEach
    public void setupMockMvc() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();

//        ●
//        this.mockMvc = MockMvcBuilders.standaloneSetup(new CustomerController()).build();

        this.restTemplate = new TestRestTemplate();
    }

    @Test
    void init() {
    }

//    @Test
//    void home() throws Exception {
//        mockMvc.perform(get("/"))
//                .andExpect(status().isOk())
//                .andExpect(content().string("Hello World!"));
//    }
// ●
//    void home() throws Exception {
//        mockMvc.perform(get("/"))
//                .andExpect(status().isOk())
//                .andExpect(content().string("Hello World!"));
//    }

    @Test
    void find() {
    }

    @Test
    void 指定したコードのカスタマー情報を取得できること() throws Exception {
        System.out.println(System.getProperty("file.encoding") + "--------------------------------------------------");
        System.out.println(mockMvc.perform(get("/customerStudy/1000")) + "--------------------------------------------------");
        ResultActions result =
                mockMvc.perform(get("/customerStudy/1000"))
                        .andExpect(status().isOk())
                        .andExpect(content().string("{\"customerCode\":1000,\"customerName\":\"○×商事\",\"account\":\"000-1234\"}"));
//                .andExpect(content().encoding("utf-8").string("{\"customerCode\":1000,\"customerName\":\"○×商事\",\"account\":\"000-1234\"}"));
//        Customer customer = result.;

//        Customer customer = restTemplate.getForObject("http://localhost:8080/customerStudy/1000", Customer.class);
//        System.out.println(customer.getCustomerName());
    }

    @Test
    void findByCustomerCode() {
    }

    @Test
    void findByCustomerCodeOrCustomerName() {
    }

    @Test
    void insert() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/customerStudy/add")
                .content("{\"customerCode\": 1005,\"customerName\": \"鹿児島テレビ\",\"account\": \"444-4444\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
//                        .andExpect(content().string("{\"customerCode\":1000,\"customerName\":\"○×商事\",\"account\":\"000-1234\"}"));


    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}