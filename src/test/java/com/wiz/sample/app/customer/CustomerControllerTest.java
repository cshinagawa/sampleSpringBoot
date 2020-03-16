package com.wiz.sample.app.customer;

import com.wiz.sample.TestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * MockMVCを利用したテストクラス。
 *
 * アプリケーションコンテキストをロードしてテストする。
 * （@SpringBootTestをつける。
 * 　⇔スタンドアロンモードにする場合は●の部分を参照。
 *
 * TODO：JSONの内容をテストするには、@JsonTestでシリアライズ・でシリアライズできる
 *
 */

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
@Rollback
@Import(TestConfig.class)
class CustomerControllerTest {

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
//    void findAll() {
//    }

    // 指定されたリクエストに対して動作するか？
    @Test
    void すべてのカスタマー情報JSONを返すこと() {
    }

    @Test
    void カスタマー情報が0件の場合は正しいメッセージJSONを返すこと() {
    }

//    @Test
//    void findByCustomerCode() {
//    }

    @Test
    void 該当するカスタマー情報JSONを返すこと() throws Exception {

        ResultActions result =
            mockMvc.perform(get("/customer/1000"))
                    .andExpect(status().isOk())
                    .andExpect(content().string(
                            "{\"errorCode\":null,\"customer\":{\"customerCode\":1000,\"customerName\":\"○×商事\",\"account\":\"000-1234\",\"version\":0}}"));
    }

    @Test
    void リクエストデータにカスタマーコードが無い場合はメッセージを返すこと() {
    }

    @Test
    void 該当するカスタマー情報がない場合はメッセージを返すこと() {
    }

    @Test
    void findByCustomerCodeOrCustomerName() {
    }

//    @Test
//    void insert() throws Exception {
//    }

    @Test
    void 登録できた場合は正しいレスポンスデータを返すこと() {
//        mockMvc.perform(MockMvcRequestBuilders.post("/customer/add")
//                .content("{\"customerCode\": 1005,\"customerName\": \"鹿児島テレビ\",\"account\": \"444-4444\"}")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
////                        .andExpect(content().string("{\"customerCode\":1000,\"customerName\":\"○×商事\",\"account\":\"000-1234\"}"));
    }

    @Test
    void バリデーションエラーの場合は登録せずにメッセージを返すこと() {
    }

    @Test
    void 一意制約違反の場合は登録せずにメッセージを返すこと() {
    }

//    @Test
//    void update() {
//    }

    @Test
    void 更新できた場合は正しいレスポンスデータを返すこと() {
    }

    @Test
    void 更新対象のカスタマー情報が存在しない場合は更新せずにメッセージを返すこと() {
    }

    @Test
    void バリデーションエラーの場合は更新せずにメッセージを返すこと() {
    }

    @Test
    void 排他エラーの場合は更新せずにメッセージを返すこと() {
    }

//    @Test
//    void delete() {
//    }

    @Test
    void 削除できた場合は正しいレスポンスデータを返すこと() {
    }

    @Test
    void リクエストデータにカスタマーコードが無い場合は削除せずにメッセージを返すこと() {
    }

    @Test
    void 削除対象のカスタマー情報が存在しない場合は削除せずにメッセージを返すこと() {
    }
}