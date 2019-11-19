package xact.idea.attendancesystem.Database.DataSources;

import java.util.List;

import io.reactivex.Flowable;
import xact.idea.attendancesystem.Database.Model.SetUp;

public class SetUpDataRepository implements ISetUpDataSource {
    public ISetUpDataSource iSetUpDataSource;
    public SetUpDataRepository(ISetUpDataSource iSetUpDataSource){
        this.iSetUpDataSource=iSetUpDataSource;
    }
    private static  SetUpDataRepository instance;
    public static SetUpDataRepository getInstance(ISetUpDataSource iSetUpDataSource){
        if(instance==null)
            instance= new SetUpDataRepository(iSetUpDataSource);
        return instance;

    }
    
    @Override
    public SetUp getSetUpItems() {
        return iSetUpDataSource.getSetUpItems();
    }

    @Override
    public Flowable<List<SetUp>> getSetUpItemById(int cartItemId) {
        return iSetUpDataSource.getSetUpItemById(cartItemId);
    }

    @Override
    public void emptyCart() {
        iSetUpDataSource.emptyCart();
    }

    @Override
    public int size() {
        return iSetUpDataSource.size();
    }

    @Override
    public void insertToSetUp(SetUp... SetUps) {
        iSetUpDataSource.insertToSetUp(SetUps);
    }

    @Override
    public void updateSetUp(SetUp... SetUps) {
        iSetUpDataSource.updateSetUp(SetUps);
    }

    @Override
    public void deleteSetUpItem(SetUp... SetUps) {
        iSetUpDataSource.deleteSetUpItem(SetUps);
    }
}
