package com.caocao.client.http.entity;

/**
 * @ProjectName: Caocao
 * @Package: com.caocao.client.http
 * @ClassName: BaseRequest
 * @Description: 网络请求返回的数据的基类 可根据实际返回接口修改
 * @Author: XuYu
 * @CreateDate: 2020/8/3 10:02
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/8/3 10:02
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class BaseResp<T> {

    private int    code;
    private String msg;
    private int page;
    private T      data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String message) {
        this.msg = message;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public T getData() {
        return data;
    }


    @Override
    public String toString() {
        return "AppResponseBody{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", page='" + page + '\'' +
                ", data=" + data +
                '}';
    }
}
