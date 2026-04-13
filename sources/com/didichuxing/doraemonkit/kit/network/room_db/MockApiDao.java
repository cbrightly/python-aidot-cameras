package com.didichuxing.doraemonkit.kit.network.room_db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface MockApiDao {
    @Query("SELECT * FROM mock_intercept_api WHERE id = :id LIMIT 1")
    MockInterceptApiBean findInterceptApiById(String str);

    @Query("SELECT * FROM mock_intercept_api WHERE path LIKE :path")
    List<MockInterceptApiBean> findInterceptApiByPath(String str);

    @Query("SELECT * FROM mock_template_api WHERE id = :id LIMIT 1")
    MockTemplateApiBean findTemplateApiById(String str);

    @Query("SELECT * FROM mock_template_api WHERE path LIKE :path")
    List<MockTemplateApiBean> findTemplateApiByPath(String str);

    @Query("SELECT * FROM mock_intercept_api")
    List<MockInterceptApiBean> getAllInterceptApi();

    @Query("SELECT * FROM mock_template_api")
    List<MockTemplateApiBean> getAllTemplateApi();

    @Insert(onConflict = 2)
    void insertAllInterceptApi(List<MockInterceptApiBean> list);

    @Insert(onConflict = 2)
    void insertAllTemplateApi(List<MockTemplateApiBean> list);

    @Update(onConflict = 2)
    int updateInterceptApi(MockInterceptApiBean mockInterceptApiBean);

    @Update(onConflict = 2)
    int updateTemplateApi(MockTemplateApiBean mockTemplateApiBean);
}
