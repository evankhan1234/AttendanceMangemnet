package xact.idea.attendancesystem.Database.DataSources;

import java.util.List;

import io.reactivex.Flowable;
import xact.idea.attendancesystem.Database.Model.EntityLeave;

public class EntityLeaveRepository implements IEntityLeaveDataSource{
    public IEntityLeaveDataSource iEntityLeaveDataSource;
    public EntityLeaveRepository(IEntityLeaveDataSource iEntityLeaveDataSource){
        this.iEntityLeaveDataSource=iEntityLeaveDataSource;
    }
    private static  EntityLeaveRepository instance;

    public static EntityLeaveRepository getInstance(IEntityLeaveDataSource iEntityLeaveDataSource){
        if(instance==null)
            instance= new EntityLeaveRepository(iEntityLeaveDataSource);
        return instance;

    }

    @Override
    public Flowable<List<EntityLeave>> getEntityLeaveItems() {
        return iEntityLeaveDataSource.getEntityLeaveItems();
    }

    @Override
    public Flowable<List<EntityLeave>> getEntityLeaveItemById(int EntityLeaveItemId) {
        return iEntityLeaveDataSource.getEntityLeaveItemById(EntityLeaveItemId);
    }

    @Override
    public Flowable<List<EntityLeave>> getEntityLeave(int favoriteid) {
        return iEntityLeaveDataSource.getEntityLeave(favoriteid);
    }

    @Override
    public void emptyEntityLeave() {
        iEntityLeaveDataSource.emptyEntityLeave();
    }

    @Override
    public void insertToEntityLeave(EntityLeave... EntityLeaves) {
        iEntityLeaveDataSource.insertToEntityLeave(EntityLeaves);
    }

    @Override
    public void updateEntityLeave(EntityLeave... EntityLeaves) {
        iEntityLeaveDataSource.updateEntityLeave(EntityLeaves);
    }

    @Override
    public void deleteEntityLeaveItem(EntityLeave... EntityLeaves) {
        iEntityLeaveDataSource.deleteEntityLeaveItem(EntityLeaves);
    }
}
