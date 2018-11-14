package com.redread.rxbus.bean;

/**
 * Created by zhangshexin on 2018/9/14.
 */

public class FinishRX {

    public ActivityName getWhat() {
        return what;
    }

    public void setWhat(ActivityName what) {
        this.what = what;
    }

    private ActivityName what;




    public enum ActivityName {
        organizationLogin("Activity_organizationLogin.class", 0),generalLogin("Activity_generalLogin.class", 1);

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        private String name;
        private int code;

        ActivityName(String name, int code) {
            this.name = name;
            this.code = code;
        }
    }
}
