package xact.idea.attendancesystem.Database.Local;

import java.util.List;

import io.reactivex.Flowable;
import xact.idea.attendancesystem.Database.DataSources.IDepartmentDataSource;
import xact.idea.attendancesystem.Database.Model.Department;

public class DepartmentDataSource implements IDepartmentDataSource {

    private DepartmentDao DepartmentDao;
    private static DepartmentDataSource instance;

    public DepartmentDataSource(DepartmentDao DepartmentDao){
        this.DepartmentDao=DepartmentDao;
    }
    public static DepartmentDataSource getInstance(DepartmentDao DepartmentDao){
        if(instance==null)
            instance = new DepartmentDataSource(DepartmentDao);
        return instance;

    }
    @Override
    public Flowable<List<Department>> getCartItems() {
        return DepartmentDao.getDepartmentItems();
    }

    @Override
    public Flowable<List<Department>> getCartItemById(int cartItemId) {
        return DepartmentDao.getDepartmentItemById(cartItemId);
    }

    @Override
    public Flowable<List<Department>> getCart(int favoriteid) {
        return DepartmentDao.getDepartment();
    }


    @Override
    public void emptyCart() {
        DepartmentDao.emptyDepartment();
    }

    @Override
    public void insertToDepartment(Department... carts) {

        DepartmentDao.insertToDepartment(carts);
    }

    @Override
    public void updateDepartment(Department... carts) {

        DepartmentDao.updateDepartment(carts);
    }

    @Override
    public void deleteDepartmentItem(Department... carts) {

        DepartmentDao.deleteDepartmentItem(carts);
    }
}
