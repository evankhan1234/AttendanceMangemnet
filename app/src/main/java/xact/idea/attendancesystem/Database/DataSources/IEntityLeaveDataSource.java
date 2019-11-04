package xact.idea.attendancesystem.Database.DataSources;

import java.util.List;

import io.reactivex.Flowable;
import xact.idea.attendancesystem.Database.Model.EntityLeave;

public interface IEntityLeaveDataSource {
    Flowable<List<EntityLeave>> getEntityLeaveItems();


    Flowable<List<EntityLeave>> getEntityLeaveItemById(int EntityLeaveItemId);


    Flowable<List<EntityLeave>> getEntityLeave(int favoriteid);

    void emptyEntityLeave();


    void insertToEntityLeave(EntityLeave... EntityLeaves);


    void updateEntityLeave(EntityLeave... EntityLeaves);


    void deleteEntityLeaveItem(EntityLeave... EntityLeaves);
}
