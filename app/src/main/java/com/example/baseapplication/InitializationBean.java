package com.example.baseapplication;

//import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2018/5/11.
 */

public class InitializationBean {

    /**
     * errno : 000
     * errmsg :
     * result : {"is_wap":1,"wap":"https://www.55355tt.com/4048","redirect":"https://www.55355tt.com/4048","upgrade":""}
     */


    private String errno;
    private String errmsg;
    //    @SerializedName("result")
    private ResultBean data;

    public String getErrno() {
        return errno;
    }

    public void setErrno(String errno) {
        this.errno = errno;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public ResultBean getData() {
        return data;
    }

    public void setData(ResultBean data) {
        this.data = data;
    }

    public static class ResultBean {
        /**
         * is_wap : 1
         * wap : https://www.55355tt.com/4048
         * redirect : https://www.55355tt.com/4048
         * upgrade :
         */

        private String rflag;
        private String rurl;
        private String uflag;
        private String uurl;

        public String getRflag() {
            return rflag;
        }

        public void setRflag(String rflag) {
            this.rflag = rflag;
        }

        public String getRurl() {
            return rurl;
        }

        public void setRurl(String rurl) {
            this.rurl = rurl;
        }

        public String getUflag() {
            return uflag;
        }

        public void setUflag(String uflag) {
            this.uflag = uflag;
        }

        public String getUurl() {
            return uurl;
        }

        public void setUurl(String uurl) {
            this.uurl = uurl;
        }

        //        private int is_wap;
//        private String wap;
//        private String redirect;
//        private String upgrade;
//
//        public int getIs_wap() {
//            return is_wap;
//        }
//
//        public void setIs_wap(int is_wap) {
//            this.is_wap = is_wap;
//        }
//
//        public String getWap() {
//            return wap;
//        }
//
//        public void setWap(String wap) {
//            this.wap = wap;
//        }
//
//        public String getRedirect() {
//            return redirect;
//        }
//
//        public void setRedirect(String redirect) {
//            this.redirect = redirect;
//        }
//
//        public String getUpgrade() {
//            return upgrade;
//        }
//
//        public void setUpgrade(String upgrade) {
//            this.upgrade = upgrade;
//        }
    }
}
