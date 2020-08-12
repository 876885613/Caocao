package com.caocao.client.http.entity.respons;

import android.os.Parcel;
import android.os.Parcelable;

import com.caocao.client.http.entity.BaseResp;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @ProjectName: Caocao
 * @Package: com.caocao.client.http.entity.respons
 * @ClassName: AddressResp
 * @Description: 地址
 * @Author: XuYu
 * @CreateDate: 2020/8/12 15:47
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/8/12 15:47
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class AddressResp extends BaseResp<List<AddressResp>> implements Parcelable {



    public int id;
    public String name;
    public String phone;
    public String province;
    public String city;
    public String area;
    public String detail;
    @SerializedName("is_default")
    public int    isDefault;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.phone);
        dest.writeString(this.province);
        dest.writeString(this.city);
        dest.writeString(this.area);
        dest.writeString(this.detail);
        dest.writeInt(this.isDefault);
    }

    public AddressResp() {
    }

    protected AddressResp(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.phone = in.readString();
        this.province = in.readString();
        this.city = in.readString();
        this.area = in.readString();
        this.detail = in.readString();
        this.isDefault = in.readInt();
    }

    public static final Creator<AddressResp> CREATOR = new Creator<AddressResp>() {
        @Override
        public AddressResp createFromParcel(Parcel source) {
            return new AddressResp(source);
        }

        @Override
        public AddressResp[] newArray(int size) {
            return new AddressResp[size];
        }
    };
}
