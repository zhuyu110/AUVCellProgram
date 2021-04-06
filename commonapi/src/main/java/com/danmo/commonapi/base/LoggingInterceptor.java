package com.danmo.commonapi.base;

import android.content.Context;
import com.blankj.utilcode.util.LogUtils;
import okhttp3.*;
import okio.Buffer;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;

import static com.danmo.commonapi.base.Constant.Authorization;
import static com.danmo.commonapi.base.Constant.Scret_key;

public class LoggingInterceptor implements Interceptor {
    Context context;
    public LoggingInterceptor(Context context){
        this.context=context;
    }
    @Override
    public Response intercept(Chain chain) throws IOException {
        //这个chain里面包含了request和response，所以你要什么都可以从这里拿

        Request request = chain.request()
                .newBuilder()
                .addHeader("Content-Type","application/json;charset=UTF-8")
                .addHeader("Secret-Key", Scret_key)
                .addHeader("Authorization", Authorization)
                .build();
        long t1 = System.nanoTime();//请求发起的时间
        LogUtils.i(String.format("发送请求 %s ",
                request.url()));
        LogUtils.i(String.format("发送请求Head Authorization= %s ",
                Authorization));

        RequestBody requestBody=request.body();
        if(requestBody!=null){
            LogUtils.i(String.format("发送请求数据")+requestBody.contentType());
            Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);
            Charset charset = Charset.forName("UTF-8");
            MediaType contentType = requestBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(Charset.forName("UTF-8"));
            }

            LogUtils.i("");
            if (isPlaintext(buffer)) {
                LogUtils.i(buffer.readString(charset));
                LogUtils.i("--> END " + request.method()
                        + " (" + requestBody.contentLength() + "-byte body)");
            } else {
                LogUtils.i("--> END " + request.method() + " (binary "
                        + requestBody.contentLength() + "-byte body omitted)");
            }
        }

        Response response = chain.proceed(request);

        long t2 = System.nanoTime();//收到响应的时间

        //这里不能直接使用response.body().string()的方式输出日志
        //因为response.body().string()之后，response中的流会被关闭，程序会报错，我们需要创建出一
        //个新的response给应用层处理
        ResponseBody responseBody = response.peekBody(1024 * 1024);

        LogUtils.i(String.format("接收响应: [%s] %n返回json:【%s】 %.1fms%n%s",
                response.request().url(),
                responseBody.string(),
                (t2 - t1) / 1e6d,
                response.headers()));

        return response;
    }


    static boolean isPlaintext(Buffer buffer) {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false; // Truncated UTF-8 sequence.
        }
    }
}
