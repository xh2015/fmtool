package com.xh2015.fmtool;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.takephoto.PhotoPickerManager;
import com.lzy.imagepicker.takephoto.SelectDialog;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;

import java.util.ArrayList;
import java.util.List;

public class PhotoActivity extends AppCompatActivity implements ImagePickerAdapter.OnRecyclerViewItemClickListener {

    public static final int IMAGE_ITEM_ADD = -1;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;

    private ImagePickerAdapter adapter;
    private ArrayList<ImageItem> selImageList; //当前选择的所有图片
    private int maxImgCount = 8;               //允许选择图片最大数
    private ArrayList<ImageItem> images = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        selImageList = new ArrayList<>();
        adapter = new ImagePickerAdapter(this, selImageList, maxImgCount);
        adapter.setOnItemClickListener(this);

//        recyclerView.setLayoutManager(new GridLayoutManager(_mActivity, 4));
//        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
            case IMAGE_ITEM_ADD:
                List<String> names = new ArrayList<>();
                names.add("拍照");
                names.add("相册");
                names.add("清除所有");
                showDialog(new SelectDialog.SelectDialogListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        switch (position) {
                            case 0:
                                PhotoPickerManager.takePhoto(PhotoActivity.this, "徐浩徐浩徐浩徐浩",
                                        REQUEST_CODE_SELECT);
                                break;
                            case 1:
                                PhotoPickerManager.pickerPhoto(PhotoActivity.this,"Gary",
                                        maxImgCount - selImageList.size(),
                                        REQUEST_CODE_SELECT);
                                break;
                            case 2:
                                selImageList.clear();
                                adapter.setImages(selImageList);
                                PhotoPickerManager.clearImageCache();
                                break;
                        }


                    }
                }, names);
                break;
            default:
                //打开预览
                Intent intentPreview = new Intent(this, ImagePreviewDelActivity.class);
                intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) adapter.getImages());
                intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
                intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
                startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            //添加图片返回
            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images != null) {
                    selImageList.addAll(images);
                    adapter.setImages(selImageList);
                }
            }
        } else if (data != null && requestCode == REQUEST_CODE_PREVIEW) {
            ////预览图片返回
            images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
            if (images != null) {
                selImageList.clear();
                selImageList.addAll(images);
                adapter.setImages(selImageList);
            }
        }
    }

    private SelectDialog showDialog(SelectDialog.SelectDialogListener listener, List<String> names) {
        SelectDialog dialog = new SelectDialog(this, R.style.transparentFrameWindowStyle,
                listener, names);
        if (!this.isFinishing()) {
            dialog.show();
        }
        return dialog;
    }
}
