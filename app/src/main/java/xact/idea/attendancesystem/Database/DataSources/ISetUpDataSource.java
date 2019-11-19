package xact.idea.attendancesystem.Database.DataSources;

import java.util.List;

import io.reactivex.Flowable;
import xact.idea.attendancesystem.Database.Model.SetUp;

public interface ISetUpDataSource {
    SetUp getSetUpItems();


    Flowable<List<SetUp>> getSetUpItemById(int cartItemId);



    void emptyCart();
    int size();


    void insertToSetUp(SetUp... SetUps);


    void updateSetUp(SetUp... SetUps);


    void deleteSetUpItem(SetUp... SetUps);
}
