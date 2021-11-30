package com.excalibur.functest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jakewharton.retrofit2.adapter.reactor.ReactorCallAdapterFactory;
import io.micronaut.context.annotation.Factory;
import jakarta.inject.Singleton;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import org.jetbrains.annotations.NotNull;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Factory
public class RetrofitFactory {

    @Singleton
    public OkHttpClient okHttpClient() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new RequestBodyLoggingInterceptor());
        return httpClient.build();
    }

    @Singleton
    public Retrofit retrofit(ClientConfig config, ObjectMapper objectMapper, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
            .baseUrl(config.getApplicationURL())
            .client(okHttpClient)
            .addCallAdapterFactory(ReactorCallAdapterFactory.create())
            .addConverterFactory(JacksonConverterFactory.create(objectMapper))
            .build();
    }

    public static class RequestBodyLoggingInterceptor implements Interceptor {

        @NotNull
        @Override
        public Response intercept(@NotNull Chain chain) throws IOException {
            Request original = chain.request();
            String requestBody = bodyToString(original);
            System.out.println(requestBody);
            return chain.proceed(original);
        }

        private static String bodyToString(final Request request) {

            try {
                final Request copy = request.newBuilder().build();
                String s = copy.url().encodedPath();
                final Buffer buffer = new Buffer();
                RequestBody body = copy.body();
                if (body != null) {
                    body.writeTo(buffer);
                }
                System.out.println(s);
                String requestBody = buffer.readUtf8();

                return String.format("[Path: %s, Body: (%s)]", s, requestBody);
            } catch (final IOException e) {
                return "did not work";
            }
        }
    }

    @Singleton
    public HealthStatusClient healthStatusClient(Retrofit retrofit) {
        return retrofit.create(HealthStatusClient.class);
    }

    @Singleton
    public ProductClient productClient(Retrofit retrofit) {
        return retrofit.create(ProductClient.class);
    }

}
