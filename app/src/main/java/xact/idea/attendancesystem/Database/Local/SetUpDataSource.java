package xact.idea.attendancesystem.Database.Local;

import java.util.List;

import io.reactivex.Flowable;
import xact.idea.attendancesystem.Database.DataSources.ISetUpDataSource;
import xact.idea.attendancesystem.Database.Model.SetUp;

public class SetUpDataSource implements ISetUpDataSource {
    private SetUpDao SetUpDao;
    private static SetUpDataSource instance;

    public SetUpDataSource(SetUpDao SetUpDao){
        this.SetUpDao=SetUpDao;
    }
    public static SetUpDataSource getInstance(SetUpDao SetUpDao){
        if(instance==null)
            instance = new SetUpDataSource(SetUpDao);
        return instance;

    }
    
    @Override
    public SetUp getSetUpItems() {
        return SetUpDao.getSetUpItems();
    }

    @Override
    public Flowable<List<SetUp>> getSetUpItemById(int cartItemId) {
        return SetUpDao.getSetUpItemById(cartItemId);
    }

    @Override
    public void emptyCart() {
        SetUpDao.emptySetUp();
    }

    @Override
    public int size() {
        return SetUpDao.value();
    }

    @Override
    public void insertToSetUp(SetUp... SetUps) {
        SetUpDao.insertToSetUp(SetUps);
    }

    @Override
    public void updateSetUp(SetUp... SetUps) {
        SetUpDao.updateSetUp(SetUps);
    }

    @Override
    public void deleteSetUpItem(SetUp... SetUps) {
        SetUpDao.deleteSetUpItem(SetUps);
    }
}
