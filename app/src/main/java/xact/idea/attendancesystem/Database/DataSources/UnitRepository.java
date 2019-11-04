package xact.idea.attendancesystem.Database.DataSources;

import java.util.List;

import io.reactivex.Flowable;
import xact.idea.attendancesystem.Database.Model.Unit;

public class UnitRepository implements IUnitDataSource {
    public IUnitDataSource iUnitDataSource;
    public UnitRepository(IUnitDataSource iUnitDataSource){
        this.iUnitDataSource=iUnitDataSource;
    }
    private static  UnitRepository instance;
    public static UnitRepository getInstance(IUnitDataSource iUnitDataSource){
        if(instance==null)
            instance= new UnitRepository(iUnitDataSource);
        return instance;

    }
    @Override
    public Flowable<List<Unit>> getUnitItems() {
        return iUnitDataSource.getUnitItems();
    }

    @Override
    public Flowable<List<Unit>> getUnitItemById(int cartItemId) {
        return iUnitDataSource.getUnitItemById(cartItemId);
    }

    @Override
    public void emptyCart() {
        iUnitDataSource.emptyCart();
    }

    @Override
    public int size() {
        return iUnitDataSource.size();
    }

    @Override
    public void insertToUnit(Unit... units) {
        iUnitDataSource.insertToUnit(units);
    }

    @Override
    public void updateUnit(Unit... units) {
        iUnitDataSource.updateUnit(units);
    }

    @Override
    public void deleteUnitItem(Unit... units) {
        iUnitDataSource.deleteUnitItem(units);
    }
}
