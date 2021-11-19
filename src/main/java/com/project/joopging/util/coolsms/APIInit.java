package com.project.joopging.util.coolsms;

import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.apache.commons.codec.binary.Hex;
import org.ini4j.Ini;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class APIInit {

    private static Retrofit retrofit;
    private static CoolsmsMsgV4 messageService;

    @Value("${sms.api.key}")
    private String api_key;

    @Value("${sms.api.secret}")
    private String api_secret;

    @Value("${sms.server.domain}")
    private String server_domain;

    @Value("${sms.server.protocol}")
    private String server_protocol;

    @Value("${sms.server.prefix}")
    private String server_prefix;


    public String getHeaders() {
        try {
            String apiKey = api_key;
            String apiSecret = api_secret;
            String salt = UUID.randomUUID().toString().replaceAll("-","");
            String date = ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toString().split("\\[")[0];

            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(apiSecret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            String signature = new String(Hex.encodeHex(sha256_HMAC.doFinal((date + salt).getBytes(StandardCharsets.UTF_8))));
            return "HMAC-SHA256 ApiKey=" + apiKey + ", Date=" + date + ", salt=" + salt + ", signature=" + signature;
        } catch (InvalidKeyException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public CoolsmsMsgV4 getAPI() {
        if (messageService == null) {
            setRetrofit();
            messageService = retrofit.create(CoolsmsMsgV4.class);
        }
        return messageService;
    }

    public void setRetrofit() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        Request 시 로그가 필요하면 추가하세요.
//        interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        String domain = "api.coolsms.co.kr";
        String protocol = "https";
        String prefix = "/";

        if (server_domain != null) domain = server_domain;
        if (server_protocol != null) protocol = server_protocol;
        if (server_prefix != null) prefix = server_prefix;


        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(protocol + "://" + domain + prefix)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

}
