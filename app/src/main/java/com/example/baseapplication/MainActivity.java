package com.example.baseapplication;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         useHttpClientGetThread();
//        useHttpUrlConnectionGetThread();
    }

    /**
     * HttpClient GET请求网络
     */
    private void useHttpClientGetThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
//                useHttpClientGet("https://860790.com/v1/com.kwbty816.sy26");
                userHttpGet("https://860790.com/v1/com.kwbty816.sy26");
//                useHttpClientGet("http://www.baidu.com");
            }
        }).start();
    }

    /**
     * HttpUrlConnection POST请求网络
     */
    private void useHttpUrlConnectionGetThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                useHttpUrlConnectionPost("http://www.baidu.com");
            }
        }).start();
    }


    /**
     * 设置默认请求参数，并返回HttpClient
     *
     * @return HttpClient
     */
    private HttpClient createHttpClient() {
        HttpParams mDefaultHttpParams = new BasicHttpParams();
        //设置连接超时
        HttpConnectionParams.setConnectionTimeout(mDefaultHttpParams, 15000);
        //设置请求超时
        HttpConnectionParams.setSoTimeout(mDefaultHttpParams, 15000);
        HttpConnectionParams.setTcpNoDelay(mDefaultHttpParams, true);
        HttpProtocolParams.setVersion(mDefaultHttpParams, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(mDefaultHttpParams, HTTP.UTF_8);
        //持续握手
        HttpProtocolParams.setUseExpectContinue(mDefaultHttpParams, true);
        HttpClient mHttpClient = new DefaultHttpClient(mDefaultHttpParams);
        return mHttpClient;

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
                InitializationBean goodBean = GsonUtils.parseTObject(data.toString(), null,
                        new TypeToken<InitializationBean>() {
                        }.getType());
                Log.i("wangshu", "请求服务器端成功"+"请求状态码:" + code + "\n请求结果:\n" + goodBean.getData().getRurl());
                //获得输入流
//第四步: 获取HttpResponse中的数据
                InputStream  inStrem = response.getEntity().getContent();
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

    //得到HttpClient
    public HttpClient getHttpClient(){
        HttpParams mHttpParams=new BasicHttpParams();
        //设置网络链接超时
        //即:Set the timeout in milliseconds until a connection is established.
        HttpConnectionParams.setConnectionTimeout(mHttpParams, 20*1000);
        //设置socket响应超时
        //即:in milliseconds which is the timeout for waiting for data.
        HttpConnectionParams.setSoTimeout(mHttpParams, 20*1000);
        //设置socket缓存大小
        HttpConnectionParams.setSocketBufferSize(mHttpParams, 8*1024);
        //设置是否可以重定向
        HttpClientParams.setRedirecting(mHttpParams, true);

        HttpClient httpClient=new DefaultHttpClient(mHttpParams);
        return httpClient;
    }

    /**
     * 使用HttpClient的get请求网络
     *
     * @param url
     */

    private void useHttpClientGet(String url) {
        HttpGet mHttpGet = new HttpGet(url);
        mHttpGet.addHeader("Connection", "Keep-Alive");
        try {
            HttpClient mHttpClient = createHttpClient();
            HttpResponse mHttpResponse = mHttpClient.execute(mHttpGet);
            HttpEntity mHttpEntity = mHttpResponse.getEntity();
            int code = mHttpResponse.getStatusLine().getStatusCode();
            if (null != mHttpEntity) {
                InputStream mInputStream = mHttpEntity.getContent();
                String respose = converStreamToString(mInputStream);
                Log.i("wangshu", "请求状态码:" + code + "\n请求结果:\n" + respose);
                mInputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void useHttpClientPost(String url) {
        HttpPost mHttpPost = new HttpPost(url);
        mHttpPost.addHeader("Connection", "Keep-Alive");
        try {
            HttpClient mHttpClient = createHttpClient();
            List<NameValuePair> postParams = new ArrayList<>();
            //要传递的参数
            postParams.add(new BasicNameValuePair("username", "moon"));
            postParams.add(new BasicNameValuePair("password", "123"));
            mHttpPost.setEntity(new UrlEncodedFormEntity(postParams));
            HttpResponse mHttpResponse = mHttpClient.execute(mHttpPost);
            HttpEntity mHttpEntity = mHttpResponse.getEntity();
            int code = mHttpResponse.getStatusLine().getStatusCode();
            if (null != mHttpEntity) {
                InputStream mInputStream = mHttpEntity.getContent();
                String respose = converStreamToString(mInputStream);
                Log.i("wangshu", "请求状态码:" + code + "\n请求结果:\n" + respose);
                mInputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 将请求结果装潢为String类型
     *
     * @param is InputStream
     * @return String
     * @throws IOException
     */
    private String converStreamToString(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuffer sb = new StringBuffer();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line + "\n");
        }
        String respose = sb.toString();
        return respose;
    }

    private void useHttpUrlConnectionPost(String url) {
        InputStream mInputStream = null;
        HttpURLConnection mHttpURLConnection = UrlConnManager.getHttpURLConnection(url);
        try {
            List<NameValuePair> postParams = new ArrayList<>();
            //要传递的参数
            postParams.add(new BasicNameValuePair("username", "moon"));
            postParams.add(new BasicNameValuePair("password", "123"));
            UrlConnManager.postParams(mHttpURLConnection.getOutputStream(), postParams);
            mHttpURLConnection.connect();
            mInputStream = mHttpURLConnection.getInputStream();
            int code = mHttpURLConnection.getResponseCode();
            String respose = converStreamToString(mInputStream);
            Log.i("wangshu", "请求状态码:" + code + "\n请求结果:\n" + respose);
            mInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
