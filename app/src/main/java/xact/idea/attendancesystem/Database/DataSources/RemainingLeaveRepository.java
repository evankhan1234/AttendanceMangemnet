package xact.idea.attendancesystem.Database.DataSources;

import java.util.List;

import io.reactivex.Flowable;
import xact.idea.attendancesystem.Database.Model.RemainingLeave;

public class RemainingLeaveRepository implements IRemainingLeaveDataSource{
    public IRemainingLeaveDataSource iRemainingLeaveDataSource;
    public RemainingLeaveRepository(IRemainingLeaveDataSource iEntityLeaveDataSource){
        this.iRemainingLeaveDataSource=iEntityLeaveDataSource;
    }
    private static  RemainingLeaveRepository instance;

    public static RemainingLeaveRepository getInstance(IRemainingLeaveDataSource iRemainingLeaveDataSource){
        if(instance==null)
            instance= new RemainingLeaveRepository(iRemainingLeaveDataSource);
        return instance;

    }

    @Override
    public Flowable<List<RemainingLeave>> getRemainingLeaveItems() {
        return iRemainingLeaveDataSource.getRemainingLeaveItems();
    }

    @Override
    public Flowable<List<RemainingLeave>> getRemainingLeaveItemById(int RemainingLeaveItemId) {
        return iRemainingLeaveDataSource.getRemainingLeaveItemById(RemainingLeaveItemId);
    }

    @Override
    public Flowable<List<RemainingLeave>> getRemainingLeave(int favoriteid) {
        return iRemainingLeaveDataSource.getRemainingLeave(favoriteid);
    }

    @Override
    public void emptyRemainingLeave() {
        iRemainingLeaveDataSource.emptyRemainingLeave();
    }

    @Override
    public void insertToRemainingLeave(RemainingLeave... RemainingLeaves) {
        iRemainingLeaveDataSource.insertToRemainingLeave(RemainingLeaves);
    }

    @Override
    public void updateRemainingLeave(RemainingLeave... RemainingLeaves) {
        iRemainingLeaveDataSource.updateRemainingLeave(RemainingLeaves);
    }

    @Override
    public void deleteRemainingLeaveItem(RemainingLeave... RemainingLeaves) {
        iRemainingLeaveDataSource.deleteRemainingLeaveItem(RemainingLeaves);
    }
}
