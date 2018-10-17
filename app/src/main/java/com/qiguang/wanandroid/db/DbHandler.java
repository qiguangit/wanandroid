package com.qiguang.wanandroid.db;

import android.database.Cursor;
import android.database.SQLException;

import com.qiguang.wanandroid.App;
import com.qiguang.wanandroid.bean.HistoryBean;
import com.qiguang.wanandroid.bean.HotSearchBean;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * @Author: 齐光
 * @Email: qiguangit@foxmail.com
 * @Date: 18-9-14 下午4:56
 * @Description:
 * @UpdateDescription:
 * @UpdateAuthor:
 */
public class DbHandler {
    private DbHelper mHelper = new DbHelper(App.getContext());

    private DbHandler() {

    }

    private static class Db {
        private static DbHandler sHandler = new DbHandler();
    }

    public static DbHandler getInstance() {
        return Db.sHandler;
    }

    public Observable<Boolean> addRecord(final String key) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                try {
                    mHelper.getWritableDatabase().execSQL("insert into history values(null,?,?)", new Object[]{System.currentTimeMillis(), key});
                    emitter.onNext(true);
                } catch (SQLException e) {
                    emitter.onNext(false);
                    e.printStackTrace();
                } finally {
                    emitter.onComplete();
                }

            }
        });

    }

    public Observable<Boolean> findRecord(final String key) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                boolean isExist = false;

                Cursor cursor = mHelper.getReadableDatabase().rawQuery("select * from history where search_key = ?", new String[]{key});
                if (cursor != null && cursor.moveToFirst()) {
                    isExist = true;
                }
                if (cursor != null && !cursor.isClosed()) {
                    cursor.close();
                }
                emitter.onNext(isExist);
                emitter.onComplete();
            }
        });

    }

    public Observable<Boolean> changeTimestamp(final String key) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                try {
                    mHelper.getWritableDatabase().execSQL("update history set time=? where search_key = ?", new Object[]{System.currentTimeMillis(), key});
                    emitter.onNext(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                    emitter.onNext(false);
                } finally {
                    emitter.onComplete();
                }
            }
        });

    }

    public Observable<Boolean> removeRecord(final String key) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                try {
                    mHelper.getWritableDatabase().execSQL("delete from history where _id=?", new String[]{key});
                    emitter.onNext(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                    emitter.onNext(false);
                } finally {
                    emitter.onComplete();
                }
            }
        });

    }

    public Observable<Boolean> removeAllRecord() {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                try {
                    mHelper.getWritableDatabase().execSQL("delete from history");
                    emitter.onNext(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                    emitter.onNext(false);
                } finally {
                    emitter.onComplete();
                }
            }
        });

    }

    public Observable<ArrayList<HistoryBean>> queryAllRecord() {
        return Observable.create(new ObservableOnSubscribe<ArrayList<HistoryBean>>() {
            @Override
            public void subscribe(ObservableEmitter<ArrayList<HistoryBean>> emitter) throws Exception {
                Cursor cursor = mHelper.getReadableDatabase().rawQuery("select * from history", new String[]{});
                ArrayList<HistoryBean> dataBeans = new ArrayList<>();
                while (cursor != null && cursor.moveToNext()) {
                    HistoryBean bean = new HistoryBean();
                    bean.setKey(cursor.getString(2));
                    dataBeans.add(bean);
                }
                if (cursor != null && !cursor.isClosed()) {
                    cursor.close();
                }
                emitter.onNext(dataBeans);
                emitter.onComplete();
            }
        });
    }

}
