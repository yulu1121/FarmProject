package com.anshi.farmproject.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.anshi.farmproject.greendao.DaoMaster;

import org.greenrobot.greendao.database.Database;

/**
 *
 * Created by yulu on 2017/9/20.
 */

public class MySqlOpenHelper extends DaoMaster.DevOpenHelper {

    public MySqlOpenHelper(Context context, String name) {
        super(context, name);
    }

    public MySqlOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        DaoMaster.createAllTables(db,true);
    }
}