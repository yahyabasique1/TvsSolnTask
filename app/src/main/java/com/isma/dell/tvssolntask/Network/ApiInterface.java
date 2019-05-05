package com.isma.dell.tvssolntask.Network;

import com.isma.dell.tvssolntask.PojoClass.LoginInfoModel;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

//    @FormUrlEncoded
//    @POST("/reporting/vrm/api/test_new/int/gettabledata.php")
//    Observable<String> getTableData(LoginInfoModel loginInfoModel);

    @POST("/reporting/vrm/api/test_new/int/gettabledata.php")
    Observable<String> getTableData(@Body  LoginInfoModel loginInfoModel);


}
