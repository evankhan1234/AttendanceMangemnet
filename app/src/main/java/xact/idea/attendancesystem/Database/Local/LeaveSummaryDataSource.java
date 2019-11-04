package xact.idea.attendancesystem.Database.Local;

import java.util.List;

import io.reactivex.Flowable;
import xact.idea.attendancesystem.Database.DataSources.ILeaveSummaryDataSource;
import xact.idea.attendancesystem.Database.DataSources.LeaveSummaryRepository;
import xact.idea.attendancesystem.Database.Model.LeaveSummary;
import xact.idea.attendancesystem.Entity.LeaveEntity;

public class LeaveSummaryDataSource implements ILeaveSummaryDataSource {
    public LeaveSummaryDao LeaveSummaryDao;
    public LeaveSummaryDataSource(LeaveSummaryDao leaveSummaryDao){
        this.LeaveSummaryDao=leaveSummaryDao;
    }
    private static LeaveSummaryDataSource instance;

    public static LeaveSummaryDataSource getInstance(LeaveSummaryDao leaveSummaryDao){
        if(instance==null)
            instance= new LeaveSummaryDataSource(leaveSummaryDao);
        return instance;

    }

    @Override
    public Flowable<List<LeaveSummary>> getLeaveSummaryItems() {
        return LeaveSummaryDao.getLeaveSummaryItems();
    }

    @Override
    public Flowable<List<LeaveSummary>> getLeaveSummaryItemById(int LeaveSummaryItemId) {
        return LeaveSummaryDao.getLeaveSummaryItemById(LeaveSummaryItemId);
    }

    @Override
    public Flowable<List<LeaveEntity>> getLeaveSummary() {
        return LeaveSummaryDao.getLeaveSummary();
    }

    @Override
    public void emptyLeaveSummary() {
        LeaveSummaryDao.emptyLeaveSummary();
    }

    @Override
    public void insertToLeaveSummary(LeaveSummary... LeaveSummarys) {
        LeaveSummaryDao.insertToLeaveSummary(LeaveSummarys);
    }

    @Override
    public void updateLeaveSummary(LeaveSummary... LeaveSummarys) {
        LeaveSummaryDao.updateLeaveSummary(LeaveSummarys);
    }

    @Override
    public void deleteLeaveSummaryItem(LeaveSummary... LeaveSummarys) {
        LeaveSummaryDao.deleteLeaveSummaryItem(LeaveSummarys);
    }
}
