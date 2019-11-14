package xact.idea.attendancesystem.Database.DataSources;

import java.util.List;

import io.reactivex.Flowable;
import xact.idea.attendancesystem.Database.Model.Department;

public class DepartmentRepository implements IDepartmentDataSource {

    public IDepartmentDataSource iDepartmentDataSource;
    public DepartmentRepository(IDepartmentDataSource iDepartmentDataSource){
        this.iDepartmentDataSource=iDepartmentDataSource;
    }
    private static  DepartmentRepository instance;

    public static DepartmentRepository getInstance(IDepartmentDataSource iCartDataSource){
        if(instance==null)
            instance= new DepartmentRepository(iCartDataSource);
        return instance;

    }
    @Override
    public Flowable<List<Department>> getCartItems() {
        return iDepartmentDataSource.getCartItems();
    }

    @Override
    public Flowable<List<Department>> getCartItemById(int cartItemId) {
        return iDepartmentDataSource.getCartItemById(cartItemId);
    }
    @Override
    public int size() {
        return iDepartmentDataSource.size();
    }

    @Override
    public  Flowable<List<Department>> getCart(int favoriteid) {
        return iDepartmentDataSource.getCart(favoriteid);
    }

    @Override
    public void emptyCart() {
        iDepartmentDataSource.emptyCart();
    }

    @Override
    public void insertToDepartment(Department... carts) {
        iDepartmentDataSource.insertToDepartment(carts);
    }

    @Override
    public void updateDepartment(Department... carts) {
        iDepartmentDataSource.updateDepartment(carts);
    }

    @Override
    public void deleteDepartmentItem(Department... carts) {
        iDepartmentDataSource.deleteDepartmentItem(carts);
    }
}
