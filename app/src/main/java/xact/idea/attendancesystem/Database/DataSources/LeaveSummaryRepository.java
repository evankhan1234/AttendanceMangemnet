package xact.idea.attendancesystem.Database.DataSources;

import java.util.List;

import io.reactivex.Flowable;
import xact.idea.attendancesystem.Database.Model.LeaveSummary;
import xact.idea.attendancesystem.Entity.LeaveEntity;

public class LeaveSummaryRepository implements ILeaveSummaryDataSource {
    public ILeaveSummaryDataSource iLeaveSummaryDataSource;
    public LeaveSummaryRepository(ILeaveSummaryDataSource iLeaveSummaryDataSource){
        this.iLeaveSummaryDataSource=iLeaveSummaryDataSource;
    }
    private static  LeaveSummaryRepository instance;

    public static LeaveSummaryRepository getInstance(ILeaveSummaryDataSource iLeaveSummaryDataSource){
        if(instance==null)
            instance= new LeaveSummaryRepository(iLeaveSummaryDataSource);
        return instance;

    }

    @Override
    public Flowable<List<LeaveSummary>> getLeaveSummaryItems() {
        return iLeaveSummaryDataSource.getLeaveSummaryItems();
    }

    @Override
    public Flowable<List<LeaveSummary>> getLeaveSummaryItemById(int LeaveSummaryItemId) {
        return iLeaveSummaryDataSource.getLeaveSummaryItemById(LeaveSummaryItemId);
    }

    @Override
    public Flowable<List<LeaveEntity>> getLeaveSummary() {
        return iLeaveSummaryDataSource.getLeaveSummary();
    }

    @Override
    public void emptyLeaveSummary() {
        iLeaveSummaryDataSource.emptyLeaveSummary();
    }

    @Override
    public void insertToLeaveSummary(LeaveSummary... LeaveSummarys) {
        iLeaveSummaryDataSource.insertToLeaveSummary(LeaveSummarys);
    }

    @Override
    public void updateLeaveSummary(LeaveSummary... LeaveSummarys) {
        iLeaveSummaryDataSource.updateLeaveSummary(LeaveSummarys);
    }

    @Override
    public void deleteLeaveSummaryItem(LeaveSummary... LeaveSummarys) {
        iLeaveSummaryDataSource.deleteLeaveSummaryItem(LeaveSummarys);
    }
}
