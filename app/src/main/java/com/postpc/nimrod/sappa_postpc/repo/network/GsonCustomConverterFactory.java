package com.postpc.nimrod.sappa_postpc.repo.network;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GsonCustomConverterFactory extends Converter.Factory{

    static GsonCustomConverterFactory create(Gson gson) {
        return new GsonCustomConverterFactory(gson);
    }

    private final Gson gson;
    private final GsonConverterFactory gsonConverterFactory;

    private GsonCustomConverterFactory(Gson gson) {
        if (gson == null) throw new NullPointerException("gson == null");
        this.gson = gson;
        this.gsonConverterFactory = GsonConverterFactory.create(gson);
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        if (type.equals(String.class))
            return new GsonCustomConverterFactory.GsonResponseBodyConverterToString<>(gson, type);
        else
            return gsonConverterFactory.responseBodyConverter(type, annotations, retrofit);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return gsonConverterFactory.requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit);
    }

    private class GsonResponseBodyConverterToString<T> implements Converter<ResponseBody, T> {
        private final Gson gson;
        private final Type type;

        GsonResponseBodyConverterToString(Gson gson, Type type) {
            this.gson = gson;
            this.type = type;
        }

        @SuppressWarnings("unchecked")
        @Override
        public T convert(ResponseBody value) throws IOException {
            String returned = value.string();
            try {
                return gson.fromJson(returned, type);
            } catch (JsonParseException e) {
                return (T) returned;
            }
        }
    }
}
