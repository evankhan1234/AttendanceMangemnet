package xact.idea.attendancesystem.Retrofit;

import java.util.ArrayList;

import retrofit2.http.GET;
import xact.idea.attendancesystem.Entity.DepartmentListEntity;
import xact.idea.attendancesystem.Entity.LeaveApprovalListEntity;
import xact.idea.attendancesystem.Entity.LeaveSummaryEntity;
import xact.idea.attendancesystem.Entity.UnitListEntity;
import xact.idea.attendancesystem.Entity.UserActivityEntity;
import xact.idea.attendancesystem.Entity.UserDetailsEntity;
import xact.idea.attendancesystem.Entity.UserListEntity;
import xact.idea.attendancesystem.Entity.UserTotalLeaveEntity;

public interface IRetrofitApi {
    @GET("16jtsp")
    io.reactivex.Observable<ArrayList<UserListEntity>> getUser();
    @GET("tvbo4")
    io.reactivex.Observable<UserTotalLeaveEntity> getTotalLeave();
    @GET("gl55w")
    io.reactivex.Observable<ArrayList<LeaveSummaryEntity>> getLeaveSummary();
    @GET("1fjadg")
    io.reactivex.Observable<ArrayList<LeaveApprovalListEntity>> getLeaveApproval();

    @GET("eu7j3")
    io.reactivex.Observable<ArrayList<UserActivityEntity>> getUserActivity();
    @GET("lr4y7")
    io.reactivex.Observable<ArrayList<UnitListEntity>> getUnitList();
    @GET("1620of")
    io.reactivex.Observable<ArrayList<DepartmentListEntity>> getDepartmentList();
    @GET("1h2jdb")
    io.reactivex.Observable<UserDetailsEntity> getProfileDetails();
//
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
