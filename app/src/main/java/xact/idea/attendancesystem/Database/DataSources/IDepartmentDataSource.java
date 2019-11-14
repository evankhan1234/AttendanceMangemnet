package xact.idea.attendancesystem.Database.DataSources;

import java.util.List;

import io.reactivex.Flowable;
import xact.idea.attendancesystem.Database.Model.Department;

public interface IDepartmentDataSource {
    Flowable<List<Department>> getCartItems();


    Flowable<List<Department>> getCartItemById(int cartItemId);


    Flowable<List<Department>> getCart(int favoriteid);

    void emptyCart();
    int size();


    void insertToDepartment(Department... carts);


    void updateDepartment(Department... carts);


    void deleteDepartmentItem(Department... carts);
}
