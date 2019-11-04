package xact.idea.attendancesystem.Database.Local;

import java.util.List;

import io.reactivex.Flowable;
import xact.idea.attendancesystem.Database.DataSources.IRemainingLeaveDataSource;
import xact.idea.attendancesystem.Database.Model.RemainingLeave;

public class RemainingLeaveDataSource implements IRemainingLeaveDataSource {
    private RemainingLeaveDao remainingLeaveDao;
    private static RemainingLeaveDataSource instance;

    public RemainingLeaveDataSource(RemainingLeaveDao remainingLeaveDao){
        this.remainingLeaveDao=remainingLeaveDao;
    }
    public static RemainingLeaveDataSource getInstance(RemainingLeaveDao remainingLeaveDao){
        if(instance==null)
            instance = new RemainingLeaveDataSource(remainingLeaveDao);
        return instance;

    }
    @Override
    public Flowable<List<RemainingLeave>> getRemainingLeaveItems() {
        return remainingLeaveDao.getRemainingLeaveItems();
    }

    @Override
    public Flowable<List<RemainingLeave>> getRemainingLeaveItemById(int RemainingLeaveItemId) {
        return remainingLeaveDao.getRemainingLeaveItemById(RemainingLeaveItemId);
    }

    @Override
    public Flowable<List<RemainingLeave>> getRemainingLeave(int favoriteid) {
        return remainingLeaveDao.getRemainingLeave();
    }

    @Override
    public void emptyRemainingLeave() {
        remainingLeaveDao.emptyRemainingLeave();
    }

    @Override
    public void insertToRemainingLeave(RemainingLeave... RemainingLeaves) {
        remainingLeaveDao.insertToRemainingLeave(RemainingLeaves);
    }

    @Override
    public void updateRemainingLeave(RemainingLeave... RemainingLeaves) {
        remainingLeaveDao.updateRemainingLeave(RemainingLeaves);
    }

    @Override
    public void deleteRemainingLeaveItem(RemainingLeave... RemainingLeaves) {
        remainingLeaveDao.deleteRemainingLeaveItem(RemainingLeaves);
    }
}
