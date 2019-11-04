package xact.idea.attendancesystem.Database.Local;

import java.util.List;

import io.reactivex.Flowable;
import xact.idea.attendancesystem.Database.DataSources.IUnitDataSource;
import xact.idea.attendancesystem.Database.Model.Unit;

public class UnitDataSource implements IUnitDataSource {

    private UnitDao UnitDao;
    private static UnitDataSource instance;

    public UnitDataSource(UnitDao UnitDao){
        this.UnitDao=UnitDao;
    }
    public static UnitDataSource getInstance(UnitDao UnitDao){
        if(instance==null)
            instance = new UnitDataSource(UnitDao);
        return instance;

    }


    @Override
    public Flowable<List<Unit>> getUnitItems() {
        return UnitDao.getUnitItems();
    }

    @Override
    public Flowable<List<Unit>> getUnitItemById(int cartItemId) {
        return UnitDao.getUnitItemById(cartItemId);
    }

    @Override
    public void emptyCart() {
        UnitDao.emptyUnit();
    }

    @Override
    public int size() {
        return UnitDao.value();
    }

    @Override
    public void insertToUnit(Unit... carts) {

        UnitDao.insertToUnit(carts);
    }

    @Override
    public void updateUnit(Unit... carts) {

        UnitDao.updateUnit(carts);
    }

    @Override
    public void deleteUnitItem(Unit... carts) {

        UnitDao.deleteUnitItem(carts);
    }
}
