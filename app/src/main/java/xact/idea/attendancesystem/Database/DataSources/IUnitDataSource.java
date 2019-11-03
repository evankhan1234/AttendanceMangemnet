package xact.idea.attendancesystem.Database.DataSources;

import java.util.List;

import io.reactivex.Flowable;

import xact.idea.attendancesystem.Database.Model.Unit;

public interface IUnitDataSource {
    Flowable<List<Unit>> getUnitItems();


    Flowable<List<Unit>> getUnitItemById(int cartItemId);



    void emptyCart();


    void insertToUnit(Unit... units);


    void updateUnit(Unit... units);


    void deleteUnitItem(Unit... units);
}
