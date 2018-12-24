package com.example.baseapplication;

import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

//import com.cn.wanbosports.mode.BaseActivity;
//import com.cn.wanbosports.mode.HomeCreateService;
//import com.cn.wanbosports.net.HttpsHandler;
//import com.cn.wanbosports.net.TaskObserver;
//import com.cn.wanbosports.net.message.FailureMessage;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.InputStream;

//import cn.jpush.android.api.JPushInterface;
//import io.reactivex.android.schedulers.AndroidSchedulers;
//import io.reactivex.disposables.Disposable;
//import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/5/10.
 */

public class WelcomeActivity extends BaseActivity {
    private Handler handler;
    private InitializationBean goodBean;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_welcome_maybe;
    }

    @Override
    protected void onViewCreated(Bundle savedInstanceState) {
//        JPushInterface.setDebugMode(true);
//        JPushInterface.init(this);
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                useHttpClientGetThread();
            }
        }, 2000);
    }

    /**
     * HttpClient GET请求网络
     */
    private void useHttpClientGetThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
//                useHttpClientGet("https://860790.com/v1/com.kwbty816.sy26");
                userHttpGet("https://860790.com/v1/"+getPackageName());
//                useHttpClientGet("http://www.baidu.com");
            }
        }).start();
    }

    private void userHttpGet(String url){
        try {
            //第一步：得到HttpClient对象，代表一个Http客户端
            HttpClient getClient = new DefaultHttpClient();

//第二步：得到HttpGet对象，代表请求的具体内容
            HttpGet request = new HttpGet(url);
            //第三步:执行请求。使用HttpClient的execute方法，执行刚才构建的请求
            HttpResponse response = getClient.execute(request);
            //判断请求是否成功
            int code = response.getStatusLine().getStatusCode();
            if(response.getStatusLine().getStatusCode()== HttpStatus.SC_OK){

                JSONObject data = HttpEntityUtils.GetHttpEntity(response);
                goodBean = GsonUtils.parseTObject(data.toString(), null,
                        new TypeToken<InitializationBean>() {
                        }.getType());
                Log.i("wangshu", "请求服务器端成功"+"请求状态码:" + code + "\n请求结果:\n" + goodBean.getData().getRurl());
                starActivity(goodBean);
                //获得输入流
//第四步: 获取HttpResponse中的数据
                InputStream inStrem = response.getEntity().getContent();
                int result = inStrem.read();
                while (result != -1){
                    System.out.print((char)result);
                    result = inStrem.read();
                }
                //关闭输入流
                inStrem.close();
            }else {
                Log.i("wangshu", "请求服务器端失败");
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void startMainActivity() {
//        HttpsHandler.create(HomeCreateService.class)
//                .getInitializationList( TextUtils.isEmpty(BuildConfig.appid)?BuildConfig.APPLICATION_ID:BuildConfig.appid)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new TaskObserver<InitializationBean>() {
//
//                    @Override
//                    public void onSuccess(InitializationBean value) {
//                        starActivity(value);
//                    }
//
//                    @Override
//                    public void onFailure(FailureMessage fail) {
//                        starActivity(null);
//                    }
//
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                    }
//
//                });
    }
    /**
     * 跳转方法  只要本地有Url  就跳转到 网页 本地没有的话就跳转主页  请求 成功的话 让跳转就跳转 不让跳转 本地有Url 还是要跳转
     */
    private void starActivity(InitializationBean value) {
        Intent intent;
        SharedPreferences setting = getSharedPreferences("FIRST", 0);
        String url = setting.getString("URL", "");
        if (null != value) {
            if ("1".equals(value.getData().getRflag()) && value.getData().getRurl() != null) {
                setting.edit().putString("URL", value.getData().getRurl()).apply();
                intent = new Intent(WelcomeActivity.this, GameActivity.class);
                intent.putExtra("url", value.getData().getRurl());
                startActivity(intent);
                finish();
            }
        }
        if (!TextUtils.isEmpty(url)) {
            intent = new Intent(WelcomeActivity.this, GameActivity.class);
            intent.putExtra("url", url);
            startActivity(intent);
            finish();
        } else {
            Intent localIntent = new Intent();
            localIntent.setComponent(new ComponentName(getPackageName(), "com.cxzg.platform.activity.InitActivity"));
            localIntent.putExtra("url", url);
            startActivity(localIntent);
            finish();

//            intent = new Intent(WelcomeActivity.this, HomeActivity.class);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}