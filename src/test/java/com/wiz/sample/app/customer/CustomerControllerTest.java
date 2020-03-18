package com.wiz.sample.app.customer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wiz.sample.TestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * コントローラークラスのテストクラス。
 * <br>
 * ・アプリケーションコンテキストをロードしてテストする。
 * （@SpringBootTestをつける。
 * 　⇔スタンドアロンモードにする場合は●の部分を参照。
 * <br>
 * ・MockMVCを利用。
 * <br>
 * ・コントローラーのレスポンスJSONをMapに変換し、評価。
 * 　（テストするには、JacksonTesterでシリアライズ・でシリアライズできるが、
 *   今回はMapのため、使用しない。）
 */

@SpringBootTest
@Transactional
@Rollback
@Import(TestConfig.class)
class CustomerControllerTest {

    /**
     * テスト時に使用するコンテキスト
     */
    @Autowired
    WebApplicationContext context;

    /**
     * Mock
     */
    MockMvc mockMvc;

//    /**
//     * JSONテストのサポートを行う。
//     */
//    private JacksonTester<Customer> json;

    /**
     * JSON⇔オブジェクトの変換用インスタンス。
     */
    private ObjectMapper objectMapper;

    /**
     * MockMVC生成
     */
    @BeforeEach
    public void setupMockMvc() {
        // アプリケーションコンテキストからMock生成。
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
//        this.mockMvc = MockMvcBuilders.standaloneSetup(new CustomerController()).build();     // ●

        // 変換用クラスを生成。
        this.objectMapper = new ObjectMapper();
//        JacksonTester.initFields(this, this.objectMapper);
    }

//    @Test
//    void findAll() {
//    }
    // 指定されたリクエストに対して動作するか？
    @Test
    void すべてのカスタマー情報JSONを返すこと() {
    }

    @Test
    void カスタマー情報が0件の場合は正しいメッセージJSONを返すこと() throws Exception {
        String url = "/customer";

        // リクエスト実行、レスポンス取得。
        MockHttpServletResponse response = mockMvc.perform(get(url)).andReturn().getResponse();
        String resultString = response.getContentAsString();

        // Mapに変換。
        Map contentMap = objectMapper.readValue(resultString, new TypeReference<Map<String, Object>>(){});
        Map customerMap = (Map) contentMap.get("customer");

        // テスト
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(false, contentMap.get("success"));
        assertEquals("該当するカスタマー情報はありませんでした。", contentMap.get("message"));
    }

//    @Test
//    void findByCustomerCode() {
//    }
    @Test
    void 該当するカスタマー情報JSONを返すこと() throws Exception {
        String url = "/customer/1000";
        String expected = "{\"success\":true,\"customer\":"
                            + "{\"customerCode\":1000,\"customerName\":\"○×商事\",\"account\":\"000-1234\",\"version\":0,"
                            + "\"orderList\":[{\"orderNo\":4,\"productCode\":3001,\"quantity\":25},{\"orderNo\":5,\"productCode\":1000,\"quantity\":1000}]}}";

        // リクエスト実行、レスポンス取得。
        MockHttpServletResponse response = mockMvc.perform(get(url)).andReturn().getResponse();
        String resultString = response.getContentAsString();

        // Mapに変換。
        Map contentMap = objectMapper.readValue(resultString, new TypeReference<Map<String, Object>>(){});
        Map customerMap = (Map) contentMap.get("customer");
        List orderList = (List) customerMap.get("orderList");

        // テスト
        assertEquals(HttpStatus.OK.value(), response.getStatus());

        assertEquals(true, contentMap.get("success"));

        assertEquals("○×商事", customerMap.get("customerName"));
        assertEquals("000-1234", customerMap.get("account"));

        Map orderMap = (Map) orderList.get(0);
        assertEquals(4, orderMap.get("orderNo"));
        assertEquals(3001, orderMap.get("productCode"));
        assertEquals(25, orderMap.get("quantity"));
    }

    @Test
    void 該当するカスタマー情報がない場合はメッセージを返すこと() throws Exception {
        String url = "/customer/1234";
        String expected = "{\"success\":false,\"message\":\"該当するカスタマー情報はありませんでした。\"}";

        // リクエスト実行、レスポンス取得。
        MockHttpServletResponse response = mockMvc.perform(get(url)).andReturn().getResponse();
        String resultString = response.getContentAsString();

        // Mapに変換。
        Map contentMap = objectMapper.readValue(resultString, new TypeReference<Map<String, Object>>(){});
        Map customerMap = (Map) contentMap.get("customer");

        // テスト
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(false, contentMap.get("success"));
        assertEquals("該当するカスタマー情報はありませんでした。", contentMap.get("message"));
    }

    @Test
    void findByCustomerCodeOrCustomerName() {
    }

//    @Test
//    void insert() throws Exception {
//    }
    @Test
    void 登録できた場合は正しいレスポンスデータを返すこと() throws Exception {

        String url = "/customer/add";
        String content = "{\"customerName\":\"鹿児島テレビ\",\"account\":\"444-4444\",\"version\":0}";

        // リクエスト実行、レスポンス取得。
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.post(url)
                                    .content(content).contentType(MediaType.APPLICATION_JSON))
                            .andReturn().getResponse();
        String resultString = response.getContentAsString();

        // Mapに変換。
        Map contentMap = objectMapper.readValue(resultString, new TypeReference<Map<String, Object>>(){});
        Map customerMap = (Map) contentMap.get("customer");
        /* JSONがCustomerクラスと同じ構造であればparseできる。
                ObjectContent<Customer> content = json.parse("{\"customerCode\":1032,\"customerName\":\"鹿児島テレビ\",\"account\":\"444-4444\",\"version\":0,\"orderList\":null}");
                Customer customer = content.getObject();
         */

        // テスト
        assertEquals(HttpStatus.OK.value(), response.getStatus());

        assertEquals(true, contentMap.get("success"));
        assertEquals("登録されました。", contentMap.get("message"));

        assertEquals("鹿児島テレビ", customerMap.get("customerName"));
        assertEquals("444-4444", customerMap.get("account"));
    }

    @Test
    void バリデーションエラーの場合は登録せずにメッセージを返すこと() throws Exception {
        String url = "/customer/add";

        // 顧客コード：必須
        // 顧客名：必須
        String content = "{\"account\":\"444-4444\",\"version\":0}";
        // 顧客名：長さ
        // 口座：必須

        // リクエスト実行、レスポンス取得。
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.post(url)
                                                .content(content).contentType(MediaType.APPLICATION_JSON))
                                            .andReturn().getResponse();
        String resultString = response.getContentAsString();

        // Mapに変換。
        Map contentMap = objectMapper.readValue(resultString, new TypeReference<Map<String, Object>>(){});
        List errorsList = (List) contentMap.get("errors");
        Map customerMap = (Map) contentMap.get("customer");

        // テスト
        assertEquals(HttpStatus.OK.value(), response.getStatus());

        assertEquals(false, contentMap.get("success"));
        assertEquals("入力エラーがあります。確認してください。", contentMap.get("message"));

        Map error = (Map) errorsList.get(0);
        assertEquals("customerName", error.get("field"));
        assertEquals("顧客名は必須です。入力してください。", error.get("message"));

        assertEquals(null, customerMap.get("customerName"));
        assertEquals("444-4444", customerMap.get("account"));

    }

    @Test
    void 一意制約違反の場合は登録せずにメッセージを返すこと() throws Exception {
    }

//    @Test
//    void update() {
//    }

    @Test
    void 更新できた場合は正しいレスポンスデータを返すこと() throws Exception {
    }

    @Test
    void 更新対象のカスタマー情報が存在しない場合は更新せずにメッセージを返すこと() throws Exception {
    }

    @Test
    void バリデーションエラーの場合は更新せずにメッセージを返すこと() throws Exception {
        // 顧客コード：必須
        // 顧客名：必須
        // 顧客名：長さ
        // 口座：必須
    }

    @Test
    void 排他エラーの場合は更新せずにメッセージを返すこと() throws Exception {

        String url = "/customer/edit";

        // バージョン番号(0⇒1)が一致しないデータで更新。
        String content = "{\"customerCode\":1000,\"customerName\":\"○×商事\",\"account\":\"000-0000\",\"version\": 1}";

        // リクエスト実行、レスポンス取得。
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.put(url)
                .content(content).contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        String resultString = response.getContentAsString();

        // Mapに変換。
        Map contentMap = objectMapper.readValue(resultString, new TypeReference<Map<String, Object>>(){});
        Map customerMap = (Map) contentMap.get("customer");

        // テスト
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(false, contentMap.get("success"));
        assertEquals("排他エラー。もう一度、更新処理をやり直してください。", contentMap.get("message"));

    }

//    @Test
//    void delete() {
//    }

    @Test
    void 削除できた場合は正しいレスポンスデータを返すこと() throws Exception {
    }

    @Test
    void リクエストデータにカスタマーコードが無い場合は削除せずにメッセージを返すこと() throws Exception {
    }

    @Test
    void 削除対象のカスタマー情報が存在しない場合は削除せずにメッセージを返すこと() throws Exception {
    }
}