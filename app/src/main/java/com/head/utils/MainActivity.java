package com.head.utils;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.head.crash.HeadConfig;
import com.head.json.JsonBean;
import com.head.json.JsonList;
import com.head.json.JsonMap;
import com.head.permission.HeadPermissions;
import com.head.permission.Permission;
import com.head.picker.utils.ImageSelector;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.functions.Consumer;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_CODE = 1001;
    ArrayList<String> selected=new ArrayList<>();
    protected TextView btCheckSingle;
    protected TextView btMultiple;
    protected TextView btCheckSingleDetail;
    protected TextView btCheckMultipleDetail;
    protected TextView btCheckMultipleMerge;
    protected TextView btStringTransformJsonMap;
    protected TextView btStringTransformJsonList;
    protected TextView btJsonMapTransformJavaBean;
    protected TextView btJsonBeanTransformJsonMap;
    protected TextView btCrash;
    protected TextView btSingleTrue;
    protected TextView btSingleFalse;
    protected TextView btMaxSelectCount;
    protected TextView btSingleCrop;
    protected TextView btTakePhoto;
    protected TextView btCropTakePhoto;

    String s1 = "{\"key\":\"DFG1H56EH5JN3DFA\",\"token\":\"124ASFD53SDF65aSF47fgT211\"}";
    String s2 = "[{\"answerId\":\"98\",\"questionDesc\":\"否\"},{\"answerId\":\"99\",\"questionDesc\":\"是\"}]";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main);
        initView();
        HeadConfig.Builder.create()
                .backgroundMode(HeadConfig.BACKGROUND_MODE_SILENT) //背景模式,开启沉浸式
                .enabled(true) //是否启动全局异常捕获
                .showErrorDetails(true) //是否显示错误详细信息
                .showRestartButton(true) //是否显示重启按钮
                .trackActivities(true) //是否跟踪Activity
                .minTimeBetweenCrashesMs(2000) //崩溃的间隔时间(毫秒)
                .errorDrawable(R.drawable.img_crash) //错误图标
//                    .restartActivity(MainActivity.class) //重新启动后的activity
//                .errorActivity(YourCustomErrorActivity.class) //崩溃后的错误activity
//                .eventListener(new YourCustomEventListener()) //崩溃后的错误监听
                .apply();

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btCheckSingle) {
            HeadPermissions permissions = new HeadPermissions(this);
            permissions.setLogging(true);
            permissions.request(Manifest.permission.READ_EXTERNAL_STORAGE)
                    .subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean aBoolean) throws Exception {
                            Log.e("TAG", "单个权限申请:" + aBoolean);
                        }
                    });

        } else if (view.getId() == R.id.btMultiple) {
            HeadPermissions permissions = new HeadPermissions(this);
            permissions.setLogging(true);
            permissions.request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_CALENDAR)
                    .subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean aBoolean) throws Exception {
                            Log.e("TAG", "多个权限申请:" + aBoolean);

                        }
                    });

        } else if (view.getId() == R.id.btCheckSingleDetail) {
            HeadPermissions permissions = new HeadPermissions(this);
            permissions.setLogging(true);
            permissions.requestEach(Manifest.permission.READ_EXTERNAL_STORAGE)
                    .subscribe(new Consumer<Permission>() {
                        @Override
                        public void accept(Permission permission) throws Exception {
                            Log.e("TAG", "申请单个权限，获得详细信息" + permission.name);
                            if (permission.name.equalsIgnoreCase(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                                if (permission.granted) {//同意后调用
                                    Log.e("TAG", "申请单个权限，获得详细信息" + "-READ_EXTERNAL_STORAGE-:" + true);
                                } else if (permission.shouldShowRequestPermissionRationale) {//禁止，但没有选择“以后不再询问”，以后申请权限，会继续弹出提示
                                    Log.e("TAG", "申请单个权限，获得详细信息" + "-READ_EXTERNAL_STORAGE-shouldShowRequestPermissionRationale:" + false);
                                } else {//禁止，但选择“以后不再询问”，以后申请权限，不会继续弹出提示
                                    Log.e("TAG", "申请单个权限，获得详细信息" + "-READ_EXTERNAL_STORAGE-:" + false);
                                }
                            }
                        }
                    });

        } else if (view.getId() == R.id.btCheckMultipleDetail) {
            HeadPermissions permissions = new HeadPermissions(this);
            permissions.setLogging(true);
            permissions.requestEach(Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_CALENDAR)
                    .subscribe(new Consumer<Permission>() {
                        @Override
                        public void accept(Permission permission) throws Exception {
                            Log.e("TAG", "checkPermissionRequestEach--:" + "-permission-:" + permission.name + "---------------");
                            if (permission.name.equalsIgnoreCase(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                                if (permission.granted) {//同意后调用
                                    Log.e("TAG", "checkPermissionRequestEach--:" + "-READ_EXTERNAL_STORAGE-:" + true);
                                } else if (permission.shouldShowRequestPermissionRationale) {//禁止，但没有选择“以后不再询问”，以后申请权限，会继续弹出提示
                                    Log.e("TAG", "checkPermissionRequestEach--:" + "-READ_EXTERNAL_STORAGE-shouldShowRequestPermissionRationale:" + false);
                                } else {//禁止，但选择“以后不再询问”，以后申请权限，不会继续弹出提示
                                    Log.e("TAG", "checkPermissionRequestEach--:" + "-READ_EXTERNAL_STORAGE-:" + false);
                                }
                            }
                            if (permission.name.equalsIgnoreCase(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                                if (permission.granted) {
                                    Log.e("TAG", "checkPermissionRequestEach--:" + "-WRITE_EXTERNAL_STORAGE-:" + true);
                                } else if (permission.shouldShowRequestPermissionRationale) {
                                    Log.e("TAG", "checkPermissionRequestEach--:" + "-READ_EXTERNAL_STORAGE-shouldShowRequestPermissionRationale:" + false);
                                } else {
                                    Log.e("TAG", "checkPermissionRequestEach--:" + "-WRITE_EXTERNAL_STORAGE-:" + false);
                                }
                            }
                            if (permission.name.equalsIgnoreCase(Manifest.permission.READ_CALENDAR)) {
                                if (permission.granted) {
                                    Log.e("TAG", "checkPermissionRequestEach--:" + "-READ_CALENDAR-:" + true);
                                } else if (permission.shouldShowRequestPermissionRationale) {
                                    Log.e("TAG", "checkPermissionRequestEach--:" + "-READ_EXTERNAL_STORAGE-shouldShowRequestPermissionRationale:" + false);
                                } else {
                                    Log.e("TAG", "checkPermissionRequestEach--:" + "-READ_CALENDAR-:" + false);
                                }
                            }
                        }
                    });
        } else if (view.getId() == R.id.btCheckMultipleMerge) {
            HeadPermissions permissions = new HeadPermissions(this);
            permissions.setLogging(true);
            permissions.requestEachCombined(Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_CALENDAR)
                    .subscribe(new Consumer<Permission>() {
                        @Override
                        public void accept(Permission permission) throws Exception {
                            Log.e("TAG", "checkPermissionRequestEachCombined--:" + "-permission-:" + permission.name + "---------------");
                            if (permission.granted) {//全部同意后调用
                                Log.e("TAG", "checkPermissionRequestEachCombined--:" + "-READ_EXTERNAL_STORAGE-:" + true);
                            } else if (permission.shouldShowRequestPermissionRationale) {//只要有一个选择：禁止，但没有选择“以后不再询问”，以后申请权限，会继续弹出提示
                                Log.e("TAG", "checkPermissionRequestEachCombined--:" + "-READ_EXTERNAL_STORAGE-shouldShowRequestPermissionRationale:" + false);
                            } else {//只要有一个选择：禁止，但选择“以后不再询问”，以后申请权限，不会继续弹出提示
                                Log.e("TAG", "checkPermissionRequestEachCombined--:" + "-READ_EXTERNAL_STORAGE-:" + false);
                            }
                        }
                    });

        } else if (view.getId() == R.id.btStringTransformJsonMap) {
            JsonMap data = JsonMap.parse(s1);
            Toast.makeText(this, "key=" + data.getString("key") + ";data=" + data.getString("data"), Toast.LENGTH_SHORT).show();

        } else if (view.getId() == R.id.btStringTransformJsonList) {
            User user = new User();
            user.setKey("qwer");
            user.setName("12321312");
            JsonList list = JsonList.parse(s2);
            list.add(user);
            list.getJsonMap(0).put("answerId", "shuwe");
            Toast.makeText(this, "answerId=" + list.getJsonMap(0).getString("answerId") + ";data=" + list.getJsonMap(0).getString("data"), Toast.LENGTH_SHORT).show();

        } else if (view.getId() == R.id.btJsonMapTransformJavaBean) {
            JsonMap data = JsonMap.parse(s1);
            User user = JsonBean.getBean(data, User.class);
            Toast.makeText(this, "key=" + user.getKey() + ";data=" + user.getName(), Toast.LENGTH_SHORT).show();

        } else if (view.getId() == R.id.btJsonBeanTransformJsonMap) {
            User user = new User();
            user.setKey("qwer");
            user.setName("12321312");
            JsonMap userJson = JsonBean.setBean(user);
            userJson.set("key", "11111");
            Toast.makeText(this, "key=" + userJson.getString("key") + ";data=" + userJson.getString("name"), Toast.LENGTH_SHORT).show();

        } else if (view.getId() == R.id.btCrash) {

            List<String> list = null;
            Toast.makeText(this, list.get(0), Toast.LENGTH_SHORT).show();
        } else if (view.getId() == R.id.btSingleTrue) {
            //单选
            ImageSelector.builder()
                    .useCamera(true) // 设置是否使用拍照
                    .setSingle(true)  //设置是否单选
                    .canPreview(true) //是否可以预览图片，默认为true
                    .start(this, REQUEST_CODE); // 打开相册

        } else if (view.getId() == R.id.btSingleFalse) {
            //限数量的多选(比如最多9张)
            ImageSelector.builder()
                    .useCamera(true) // 设置是否使用拍照
                    .setSingle(false)  //设置是否单选
                    .setMaxSelectCount(9) // 图片的最大选择数量，小于等于0时，不限数量。
                    .setSelected(selected) // 把已选的图片传入默认选中。
                    .canPreview(true) //是否可以预览图片，默认为true
                    .start(this, REQUEST_CODE); // 打开相册
        } else if (view.getId() == R.id.btMaxSelectCount) {
            //不限数量的多选
            ImageSelector.builder()
                    .useCamera(true) // 设置是否使用拍照
                    .setSingle(false)  //设置是否单选
                    .setMaxSelectCount(0) // 图片的最大选择数量，小于等于0时，不限数量。
                    .setSelected(selected) // 把已选的图片传入默认选中。
                    .canPreview(true) //是否可以预览图片，默认为true
                    .start(this, REQUEST_CODE); // 打开相册

        } else if (view.getId() == R.id.btSingleCrop) {
            //单选并剪裁
            ImageSelector.builder()
                    .useCamera(true) // 设置是否使用拍照
                    .setCrop(true)  // 设置是否使用图片剪切功能。
                    .setCropRatio(1.0f) // 图片剪切的宽高比,默认1.0f。宽固定为手机屏幕的宽。
                    .setSingle(true)  //设置是否单选
                    .canPreview(true) //是否可以预览图片，默认为true
                    .start(this, REQUEST_CODE); // 打开相册

        } else if (view.getId() == R.id.btTakePhoto) {
            //仅拍照
            ImageSelector.builder()
                    .onlyTakePhoto(true)  // 仅拍照，不打开相册
                    .start(this, REQUEST_CODE);

        } else if (view.getId() == R.id.btCropTakePhoto) {
            //拍照并剪裁
            ImageSelector.builder()
                    .setCrop(true) // 设置是否使用图片剪切功能。
                    .setCropRatio(1.0f) // 图片剪切的宽高比,默认1.0f。宽固定为手机屏幕的宽。
                    .onlyTakePhoto(true)  // 仅拍照，不打开相册
                    .start(this, REQUEST_CODE);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && data != null) {
            //获取选择器返回的数据
            ArrayList<String> images = data.getStringArrayListExtra(
                    ImageSelector.SELECT_RESULT);

            selected=images;
            /**
             * 是否是来自于相机拍照的图片，
             * 只有本次调用相机拍出来的照片，返回时才为true。
             * 当为true时，图片返回的结果有且只有一张图片。
             */
            boolean isCameraImage = data.getBooleanExtra(ImageSelector.IS_CAMERA_IMAGE, false);
        }
    }
    private void initView() {
        btCheckSingle = (TextView) findViewById(R.id.btCheckSingle);
        btCheckSingle.setOnClickListener(MainActivity.this);
        btMultiple = (TextView) findViewById(R.id.btMultiple);
        btMultiple.setOnClickListener(MainActivity.this);
        btCheckSingleDetail = (TextView) findViewById(R.id.btCheckSingleDetail);
        btCheckSingleDetail.setOnClickListener(MainActivity.this);
        btCheckMultipleDetail = (TextView) findViewById(R.id.btCheckMultipleDetail);
        btCheckMultipleDetail.setOnClickListener(MainActivity.this);
        btCheckMultipleMerge = (TextView) findViewById(R.id.btCheckMultipleMerge);
        btCheckMultipleMerge.setOnClickListener(MainActivity.this);
        btStringTransformJsonMap = (TextView) findViewById(R.id.btStringTransformJsonMap);
        btStringTransformJsonMap.setOnClickListener(MainActivity.this);
        btStringTransformJsonList = (TextView) findViewById(R.id.btStringTransformJsonList);
        btStringTransformJsonList.setOnClickListener(MainActivity.this);
        btJsonMapTransformJavaBean = (TextView) findViewById(R.id.btJsonMapTransformJavaBean);
        btJsonMapTransformJavaBean.setOnClickListener(MainActivity.this);
        btJsonBeanTransformJsonMap = (TextView) findViewById(R.id.btJsonBeanTransformJsonMap);
        btJsonBeanTransformJsonMap.setOnClickListener(MainActivity.this);
        btCrash = (TextView) findViewById(R.id.btCrash);
        btCrash.setOnClickListener(MainActivity.this);
        btSingleTrue = (TextView) findViewById(R.id.btSingleTrue);
        btSingleTrue.setOnClickListener(MainActivity.this);
        btSingleFalse = (TextView) findViewById(R.id.btSingleFalse);
        btSingleFalse.setOnClickListener(MainActivity.this);
        btMaxSelectCount = (TextView) findViewById(R.id.btMaxSelectCount);
        btMaxSelectCount.setOnClickListener(MainActivity.this);
        btSingleCrop = (TextView) findViewById(R.id.btSingleCrop);
        btSingleCrop.setOnClickListener(MainActivity.this);
        btTakePhoto = (TextView) findViewById(R.id.btTakePhoto);
        btTakePhoto.setOnClickListener(MainActivity.this);
        btCropTakePhoto = (TextView) findViewById(R.id.btCropTakePhoto);
        btCropTakePhoto.setOnClickListener(MainActivity.this);
    }
}
