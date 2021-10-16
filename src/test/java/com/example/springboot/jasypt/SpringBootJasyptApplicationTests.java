package com.example.springboot.jasypt;

import com.example.springboot.jasypt.model.Type;
import com.example.springboot.jasypt.model.request.Decryption;
import com.example.springboot.jasypt.model.request.Encryption;
import com.gitlab.residwi.spring.library.common.helper.JsonHelper;
import org.hamcrest.Matchers;
import org.jasypt.digest.StandardStringDigester;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJson;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureJson
@AutoConfigureMockMvc
@SpringBootTest
class SpringBootJasyptApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JsonHelper jsonHelper;

    @Test
    void testOneWayEncryption() throws Exception {
        var encryption = new Encryption();
        encryption.setPlainText("secretText");
        encryption.setType(Type.ONE_WAY);

        mockMvc.perform(post("/api/encrypt")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonHelper.toJson(encryption)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("data.result").exists());
    }

    @Test
    void testOneWayDecryption() throws Exception {
        var digester = new StandardStringDigester();
        var decryption = new Decryption();

        decryption.setEncryptedText(digester.digest("secretText"));
        decryption.setType(Type.ONE_WAY);
        decryption.setMatchText("secretText");

        mockMvc.perform(post("/api/decrypt")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonHelper.toJson(decryption)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("data.result").value("Password Matched"));
    }

    @Test
    void testTwoWayEncryption() throws Exception {
        var encryption = new Encryption();
        encryption.setPlainText("secretText");
        encryption.setType(Type.TWO_WAY);
        encryption.setSecretKey("key");

        mockMvc.perform(post("/api/encrypt")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonHelper.toJson(encryption)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("data.result").exists());
    }

    @Test
    void testTwoWayDecryption() throws Exception {
        var encryptor = new StandardPBEStringEncryptor();
        var decryption = new Decryption();

        encryptor.setPassword("key");
        decryption.setEncryptedText(encryptor.encrypt("secretText"));
        decryption.setType(Type.TWO_WAY);
        decryption.setSecretKey("key");
        decryption.setMatchText("secretText");

        mockMvc.perform(post("/api/decrypt")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonHelper.toJson(decryption)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("data.result").value("secretText"));
    }

    @Test
    void testInvalidTypeEncryption() throws Exception {
        var encryption = new Encryption();
        encryption.setPlainText("secretText");

        mockMvc.perform(post("/api/encrypt")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonHelper.toJson(encryption)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors.type").value(Matchers.contains("Invalid Encryption or Decryption Type")));
    }

    @Test
    void testInvalidTypeDecryption() throws Exception {
        var decryption = new Decryption();
        decryption.setEncryptedText("test");

        mockMvc.perform(post("/api/decrypt")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonHelper.toJson(decryption)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors.type").value(Matchers.contains("Invalid Encryption or Decryption Type")));
    }

    @Test
    void testEmptyOrNullMatchTextOneWayDecryption() throws Exception {
        var decryption = new Decryption();
        decryption.setEncryptedText("test");
        decryption.setType(Type.ONE_WAY);
        decryption.setMatchText(null);

        mockMvc.perform(post("/api/decrypt")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonHelper.toJson(decryption)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors.decryption").value(Matchers.contains("Empty Match Text")));
    }

    @Test
    void testEmptyOrNullSecretTextTwoWayEncryption() throws Exception {
        var encryption = new Encryption();
        encryption.setPlainText("test");
        encryption.setType(Type.TWO_WAY);
        encryption.setSecretKey(null);

        mockMvc.perform(post("/api/encrypt")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonHelper.toJson(encryption)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors.encryption").value(Matchers.contains("Empty Secret Key")));
    }

    @Test
    void testEmptyOrNullSecretTextTwoWayDecryption() throws Exception {
        var decryption = new Decryption();
        decryption.setEncryptedText("test");
        decryption.setType(Type.TWO_WAY);
        decryption.setSecretKey(null);

        mockMvc.perform(post("/api/decrypt")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonHelper.toJson(decryption)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors.decryption").value(Matchers.contains("Empty Secret Key")));
    }

    @Test
    void testOneWayDecryptionDoNotMatch() throws Exception {
        var digester = new StandardStringDigester();
        var decryption = new Decryption();

        decryption.setEncryptedText(digester.digest("secretText"));
        decryption.setType(Type.ONE_WAY);
        decryption.setMatchText("test");

        mockMvc.perform(post("/api/decrypt")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonHelper.toJson(decryption)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("data.result").value("Password do not match"));
    }
}
