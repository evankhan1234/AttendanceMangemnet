package xact.idea.attendancesystem.Database.DataSources;

import java.util.List;

import io.reactivex.Flowable;

import xact.idea.attendancesystem.Database.Model.LeaveSummary;
import xact.idea.attendancesystem.Entity.LeaveEntity;

public interface ILeaveSummaryDataSource {
    Flowable<List<LeaveSummary>> getLeaveSummaryItems();


    Flowable<List<LeaveSummary>> getLeaveSummaryItemById(int LeaveSummaryItemId);


    Flowable<List<LeaveEntity>> getLeaveSummary();

    void emptyLeaveSummary();


    void insertToLeaveSummary(LeaveSummary... LeaveSummarys);


    void updateLeaveSummary(LeaveSummary... LeaveSummarys);


    void deleteLeaveSummaryItem(LeaveSummary... LeaveSummarys);
}
