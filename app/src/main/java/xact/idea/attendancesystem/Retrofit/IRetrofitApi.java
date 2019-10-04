package xact.idea.attendancesystem.Retrofit;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import xact.idea.attendancesystem.Entity.UserActivityEntity;
import xact.idea.attendancesystem.Entity.UserListEntity;

public interface IRetrofitApi {
    @GET("16jtsp")
    io.reactivex.Observable<ArrayList<UserListEntity>> getUser();

    @GET("eu7j3")
    io.reactivex.Observable<ArrayList<UserActivityEntity>> getUserActivity();
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
