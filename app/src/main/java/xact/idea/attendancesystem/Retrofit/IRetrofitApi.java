package xact.idea.attendancesystem.Retrofit;

import java.util.ArrayList;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Field;
import xact.idea.attendancesystem.Entity.AllUserListEntity;
import xact.idea.attendancesystem.Entity.DepartmentListEntity;
import xact.idea.attendancesystem.Entity.LeaveApprovalListEntity;
import xact.idea.attendancesystem.Entity.LeaveSummaryEntity;
import xact.idea.attendancesystem.Entity.LoginEntity;
import xact.idea.attendancesystem.Entity.PunchInOutPostEntity;
import xact.idea.attendancesystem.Entity.PunchInOutResponseEntity;
import xact.idea.attendancesystem.Entity.SetUpDataEntity;
import xact.idea.attendancesystem.Entity.UnitListEntity;
import xact.idea.attendancesystem.Entity.UserActivityEntity;
import xact.idea.attendancesystem.Entity.UserActivityListEntity;
import xact.idea.attendancesystem.Entity.UserActivityPostEntity;
import xact.idea.attendancesystem.Entity.UserDetailsEntity;
import xact.idea.attendancesystem.Entity.UserListEntity;
import xact.idea.attendancesystem.Entity.UserTotalLeaveEntity;
import xact.idea.attendancesystem.Entity.LoginPostEntity;

public interface IRetrofitApi {
    @GET("setup/data.php")
    io.reactivex.Observable<SetUpDataEntity> getSetUpData();
    @GET("tvbo4")
    io.reactivex.Observable<UserTotalLeaveEntity> getTotalLeave();
    @GET("gl55w")
    io.reactivex.Observable<ArrayList<LeaveSummaryEntity>> getLeaveSummary();
    @GET("1fjadg")
    io.reactivex.Observable<ArrayList<LeaveApprovalListEntity>> getLeaveApproval();

    @GET("eu7j3")
    io.reactivex.Observable<ArrayList<UserActivityEntity>> getUserActivity();

    @POST("unit/unit_list.php")
    io.reactivex.Observable<UnitListEntity> getUnitList();

    @POST("department/department_list.php")
    io.reactivex.Observable<DepartmentListEntity> getDepartmentList();
    @GET("1h2jdb")
    io.reactivex.Observable<UserDetailsEntity> getProfileDetails();


    @POST("auth/auth.php")
    io.reactivex.Observable<LoginEntity> Login(@Body LoginPostEntity loginPostEntity);
    @POST("user/user_list.php")
    io.reactivex.Observable<AllUserListEntity> getUserList();
    @POST("user/user_activity.php")
    io.reactivex.Observable<UserActivityListEntity> getUserActivityList(@Body UserActivityPostEntity userActivityPostEntity);
    @POST("user_activity/actions.php")
    io.reactivex.Observable<PunchInOutResponseEntity> postPunch(@Body PunchInOutPostEntity punchInOutPostEntity);

//    @FormUrlEncoded
//    @POST("server/category/add_category.php")
//    io.reactivex.Observable<String> addNewCategory(@Field("name") String name, @Field("imgPath") String imgPath);
//    @Multipart
//    @POST("server/category/upload_category_img.php")
//    Call<String> uploadCategoryFile(@Part MultipartBody.Part file);
//
//    @FormUrlEncoded
//    @POST("server/category/update_category.php")
//    io.reactivex.Observable<String> updateCategory(@Field("id") int id,@Field("name") String name,@Field("imgPath") String imgPath);
//    @FormUrlEncoded
//    @POST("server/category/delete_category.php")
//    io.reactivex.Observable<String> deleteCategory(@Field("id") int id);
//    @FormUrlEncoded
//    @POST("getDrink.php")
//    io.reactivex.Observable<List<Drink>> getDrink(@Field("menuid") int menuid);
//
//    @FormUrlEncoded
//    @POST("server/product/add_product.php")
//    io.reactivex.Observable<String> addNewProduct(@Field("name") String name,
//                                                  @Field("imgPath") String imgPath,
//                                                  @Field("price") String price,
//                                                  @Field("menuId") int menuId);
//    @Multipart
//    @POST("server/product/upload_product_img.php")
//    Call<String> uploadProductFile(@Part MultipartBody.Part file);
//
//
//
//    @FormUrlEncoded
//    @POST("server/product/update_product.php")
//    io.reactivex.Observable<String> updateProduct(@Field("id") String id,@Field("name") String name,@Field("imgPath") String imgPath ,@Field("price") String price, @Field("menuId") String menuId);
//    @FormUrlEncoded
//    @POST("server/product/delete_product.php")
//    io.reactivex.Observable<String> deleteProduct(@Field("id") String id);
//
//    @FormUrlEncoded
//    @POST("server/order/getOrder.php")
//    io.reactivex.Observable<List<Order>> getOrder(
//            @Field("status") String status);
//    @FormUrlEncoded
//    @POST("updatetoken.php")
//    Call<String> updatetoken(@Field("phone") String phone,
//                             @Field("token") String token, @Field("isServerToken") String isServerToken);
//
//
//    @FormUrlEncoded
//    @POST("server/order/update_order_status.php")
//    io.reactivex.Observable<String> update_order_status(
//            @Field("status") int status,
//            @Field("phone") String phone,
//            @Field("order_id") long order_id);
//
//    @FormUrlEncoded
//    @POST("getToken.php")
//    Call<Token> getToken(@Field("phone") String phone,
//                         @Field("isServerToken") String isServerToken);
}
