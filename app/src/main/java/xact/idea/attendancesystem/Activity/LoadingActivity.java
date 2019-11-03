package xact.idea.attendancesystem.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import xact.idea.attendancesystem.Adapter.UnitAdapter;
import xact.idea.attendancesystem.Database.DataSources.DepartmentRepository;
import xact.idea.attendancesystem.Database.DataSources.UnitRepository;
import xact.idea.attendancesystem.Database.Local.DepartmentDataSource;
import xact.idea.attendancesystem.Database.Local.MainDatabase;
import xact.idea.attendancesystem.Database.Local.UnitDataSource;
import xact.idea.attendancesystem.Database.Model.Department;
import xact.idea.attendancesystem.Database.Model.Unit;
import xact.idea.attendancesystem.Entity.DepartmentListEntity;
import xact.idea.attendancesystem.Entity.UnitListEntity;
import xact.idea.attendancesystem.R;
import xact.idea.attendancesystem.Retrofit.IRetrofitApi;
import xact.idea.attendancesystem.Utils.Common;
import xact.idea.attendancesystem.Utils.Constant;
import xact.idea.attendancesystem.Utils.Utils;

import static xact.idea.attendancesystem.Utils.Utils.dismissLoadingProgress;
import static xact.idea.attendancesystem.Utils.Utils.showLoadingProgress;

public class LoadingActivity extends AppCompatActivity {
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    IRetrofitApi mService;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        progressBar=findViewById(R.id.progress_bar);
        mService=Common.getApi();
        initDB();
        new Handler().postDelayed(new Runnable() {
                        @Override
            public void run() {
                            goToMainPage();

            }
        }, 3000);

    }


    private void initDB() {
        Common.mainDatabase = MainDatabase.getInstance(this);
        Common.departmentRepository = DepartmentRepository.getInstance(DepartmentDataSource.getInstance(Common.mainDatabase.departmentDao()));
        Common.unitRepository = UnitRepository.getInstance(UnitDataSource.getInstance(Common.mainDatabase.unitDao()));

    }

    private void goToMainPage() {

        Intent i = new Intent(LoadingActivity.this, MainActivity.class);
        startActivity(i);
        finish();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onStop() {
        super.onStop();
        compositeDisposable.clear();
    }
}
