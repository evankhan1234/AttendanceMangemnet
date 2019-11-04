package xact.idea.attendancesystem.Database.Local;

import java.util.List;

import io.reactivex.Flowable;
import xact.idea.attendancesystem.Database.DataSources.IEntityLeaveDataSource;
import xact.idea.attendancesystem.Database.Model.EntityLeave;

public class EntityLeaveDataSource implements IEntityLeaveDataSource {

    private EntityLeaveDao EntityLeaveDao;
    private static EntityLeaveDataSource instance;

    public EntityLeaveDataSource(EntityLeaveDao EntityLeaveDao){
        this.EntityLeaveDao=EntityLeaveDao;
    }
    public static EntityLeaveDataSource getInstance(EntityLeaveDao EntityLeaveDao){
        if(instance==null)
            instance = new EntityLeaveDataSource(EntityLeaveDao);
        return instance;

    }

    @Override
    public Flowable<List<EntityLeave>> getEntityLeaveItems() {
        return EntityLeaveDao.getEntityLeaveItems();
    }

    @Override
    public Flowable<List<EntityLeave>> getEntityLeaveItemById(int EntityLeaveItemId) {
        return EntityLeaveDao.getEntityLeaveItemById(EntityLeaveItemId);
    }

    @Override
    public Flowable<List<EntityLeave>> getEntityLeave(int favoriteid) {
        return EntityLeaveDao.getEntityLeave();
    }

    @Override
    public void emptyEntityLeave() {
        EntityLeaveDao.emptyEntityLeave();
    }

    @Override
    public void insertToEntityLeave(EntityLeave... EntityLeaves) {
        EntityLeaveDao.insertToEntityLeave(EntityLeaves);
    }

    @Override
    public void updateEntityLeave(EntityLeave... EntityLeaves) {
        EntityLeaveDao.updateEntityLeave(EntityLeaves);
    }

    @Override
    public void deleteEntityLeaveItem(EntityLeave... EntityLeaves) {
        EntityLeaveDao.deleteEntityLeaveItem(EntityLeaves);
    }
}
