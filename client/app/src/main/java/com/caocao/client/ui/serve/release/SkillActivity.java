package com.caocao.client.ui.serve.release;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.caocao.client.R;
import com.caocao.client.base.BaseActivity;
import com.caocao.client.base.app.BaseApplication;
import com.caocao.client.databinding.ActivitySkillBinding;
import com.caocao.client.http.entity.request.SettleApplyReq;
import com.caocao.client.http.entity.respons.SortResp;
import com.caocao.client.navigationBar.DefaultNavigationBar;
import com.caocao.client.ui.adapter.AddPhotoAdapter;
import com.caocao.client.ui.adapter.EditToolAdapter;
import com.caocao.client.ui.adapter.GridImageAdapter;
import com.caocao.client.ui.bean.CheckBean;
import com.caocao.client.ui.demand.OnSortCallBackListener;
import com.caocao.client.ui.image.UploadViewModel;
import com.caocao.client.ui.me.address.OnAddressCallBackListener;
import com.caocao.client.ui.serve.ServeViewModel;
import com.caocao.client.ui.serve.bean.EditToolEntity;
import com.caocao.client.ui.serve.bean.ToolType;
import com.caocao.client.ui.wrapper.TextWatcherWrapper;
import com.caocao.client.utils.CheckNotNullUtils;
import com.caocao.client.utils.LocalParseUtils;
import com.caocao.client.utils.location.RxPermissionListener;
import com.caocao.client.utils.location.RxPermissionManager;
import com.caocao.client.view.ColorPickerView;
import com.caocao.client.weight.DividerItemDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.luck.picture.lib.PictureSelectionModel;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.caocao.client.ui.bean.CheckBean.CheckType.INTEGER;

public class SkillActivity extends BaseActivity implements
        OnSortCallBackListener, OnAddressCallBackListener, RxPermissionListener {

    private ActivitySkillBinding  binding;
    private LocalParseUtils       localParseUtils;
    private SettleApplyReq        applyReq;
    private ServeViewModel        serveVM;
    private PictureSelectionModel pictureSelectionModel;
    private GridImageAdapter      addBannerAdapter;
    private GridImageAdapter      addServeAdapter;
    private UploadViewModel       uploadVM;

    private int IMAGE_SOURCE;


    boolean isAnimating = false;

    int TextSizeDef = 1;

    int ColorType;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        localParseUtils = LocalParseUtils.getInstance(getApplicationContext());
        localParseUtils.initAddressData();
        super.onCreate(savedInstanceState);

        uploadVM.initPermission(this, this, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        applyReq = new SettleApplyReq();
        applyReq.type = 1;
    }


    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this).setTitle("发布技能").builder();
    }

    @Override
    protected void initView() {

        initColorPicker();


        binding.stvSort.setRightTextOnClickListener(v ->
                localParseUtils.showSortDialog(SkillActivity.this, SkillActivity.this));

        binding.stvServeIntro.setRightEditTextWatcher(new TextWatcherWrapper() {
            @Override
            public void afterTextChanged(Editable s) {
                applyReq.goodsTitle = s.toString();
            }
        });

        binding.etServeContent.setContentTextWatcher(new TextWatcherWrapper() {
            @Override
            public void afterTextChanged(Editable s) {
                applyReq.goodsDetail = s.toString();
            }
        });

        binding.stvMakeAddress.setRightTextOnClickListener(v ->
                localParseUtils.showAddressDialog(SkillActivity.this, SkillActivity.this));

        binding.etDetailAddress.addTextChangedListener(new TextWatcherWrapper() {
            @Override
            public void afterTextChanged(Editable s) {
                applyReq.addressDetail = s.toString();
            }
        });


        serveShowImage();

        bannerImage();

        binding.tvNext.setOnClickListener(v -> toNext());

        editToolView();
    }


    @SuppressLint("NewApi")
    private void toNext() {

        if (addBannerAdapter.getData() != null) {
            List<String> bannerList = new ArrayList<>();
            for (LocalMedia localMedia : addBannerAdapter.getData()) {
                bannerList.add(localMedia.getPath());
            }
            applyReq.bannerImage = String.join(",", bannerList);
        }

        if (addServeAdapter.getData() != null) {
            applyReq.showImage = addServeAdapter.getData().get(0).getPath();
        }

        applyReq.detailImage = binding.richEditor.getHtml();

        Map<CheckBean, String> checkParam = new LinkedHashMap();
        checkParam.put(new CheckBean(INTEGER, applyReq.cateId), "请选择技能分类");
        checkParam.put(new CheckBean(applyReq.goodsTitle), "请填写技能标题");
        checkParam.put(new CheckBean(applyReq.goodsDetail), "请填写服务介绍");
        checkParam.put(new CheckBean(applyReq.merchantProvince), "请选择服务省份");
        checkParam.put(new CheckBean(applyReq.merchantCity), "请选择服务城市");
        checkParam.put(new CheckBean(applyReq.merchantDistrict), "请选择服务区域");
        checkParam.put(new CheckBean(applyReq.addressDetail), "请填写服务详情地址");
        checkParam.put(new CheckBean(applyReq.showImage), "请上传服务展示图");
        checkParam.put(new CheckBean(applyReq.bannerImage), "请上传顶部轮播图");
        checkParam.put(new CheckBean(applyReq.detailImage), "请填写服务详细信息");

        String msg = CheckNotNullUtils.checkNotNull(checkParam);
        if (!StringUtils.isEmpty(msg)) {
            ToastUtils.showShort(msg);
            return;
        }

        Bundle bundle = new Bundle();
        bundle.putParcelable("apply", applyReq);
        ActivityUtils.startActivity(bundle, AddSpecActivity.class);
    }

    //顶部轮播图片
    private void bannerImage() {
        binding.rvBannerPhoto.setLayoutManager(new GridLayoutManager(this, 3));
        binding.rvBannerPhoto.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST, 0, 5));
        addBannerAdapter = new GridImageAdapter(null, 3);

        addBannerAdapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onTakePhotoClick(View view, int position) {
                photoSelect(2);
            }

            @Override
            public void onItemClick(View view, int position) {
            }

            @Override
            public void onItemDelClick(View view, int position) {
                addBannerAdapter.getData().remove(position);
                addBannerAdapter.notifyDataSetChanged();
            }
        });
        binding.rvBannerPhoto.setAdapter(addBannerAdapter);
    }

    private void photoSelect(int source) {
        boolean granted = uploadVM.isCameraGranted();
        if (!granted) {
            uploadVM.initPermission(this, this, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            return;
        }
        pictureSelectionModel = uploadVM.pictureSelection(this, new OnResultCallbackListener<LocalMedia>() {
            @Override
            public void onResult(List<LocalMedia> result) {
                IMAGE_SOURCE = source;
                String path = null;
                int skdCode = DeviceUtils.getSDKVersionCode();

                if (skdCode > 28) {
                    path = result.get(0).getAndroidQToPath();
                } else {
                    path = result.get(0).getPath();
                }

                uploadVM.uploadPhoto(path);
            }

            @Override
            public void onCancel() {
            }
        });

    }


    //服务展示图
    private void serveShowImage() {
        binding.rvServePhoto.setLayoutManager(new GridLayoutManager(this, 3));
        addServeAdapter = new GridImageAdapter(null, 1);
        addServeAdapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onTakePhotoClick(View view, int position) {
                photoSelect(1);
            }

            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onItemDelClick(View view, int position) {
                addServeAdapter.setNewData(null);
            }
        });
        binding.rvServePhoto.setAdapter(addServeAdapter);
    }

    //文本编辑器工具
    private void editToolView() {
        binding.rvEtTool.setLayoutManager(new GridLayoutManager(this, 7));
        AddPhotoAdapter addAdapter = new AddPhotoAdapter(this, null);
        binding.rvEtTool.setAdapter(addAdapter);

        EditToolAdapter editToolAdapter = new EditToolAdapter(R.layout.adapter_edit_tool_item);
        binding.rvEtTool.setAdapter(editToolAdapter);

        editToolAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                EditToolEntity toolEntity = editToolAdapter.getItem(position);
                switch (ToolType.valueOf(toolEntity.des)) {
                    case Image:
                        photoSelect(3);
                        break;
                    case NewLine:
                        binding.richEditor.setNewLine();
                        break;
                    case TextColor:
                        ColorType = 1;
                        colorPicker();
                        break;
                    case TextSizeAdd:
                        if (TextSizeDef == 6) {
                            return;
                        }
                        binding.richEditor.setFontSize(TextSizeDef++);
                        break;
                    case TextSizeMin:
                        if (TextSizeDef == 1) {
                            return;
                        }
                        binding.richEditor.setFontSize(TextSizeDef--);
                        break;
                    case Blod:
                        binding.richEditor.setBold();
                        break;
                    case Italic:
                        binding.richEditor.setItalic();
                        break;
                    case Subscript:
                        binding.richEditor.setSubscript();
                        break;
                    case Superscript:
                        binding.richEditor.setSuperscript();
                        break;
                    case Strikethrough:
                        binding.richEditor.setStrikeThrough();
                        break;
                    case Underline:
                        binding.richEditor.setUnderline();
                        break;
                    case JustifyLeft:
                        binding.richEditor.setAlignLeft();
                        break;
                    case JustifyCenter:
                        binding.richEditor.setAlignCenter();
                        break;
                    case JustifyRight:
                        binding.richEditor.setAlignRight();
                        break;
                    case Blockquote:
                        binding.richEditor.setBlockquote();
                        break;
                    case Undo:
                        binding.richEditor.undo();
                        break;
                    case Redo:
                        binding.richEditor.redo();
                        break;
                    case Indent:
                        binding.richEditor.setIndent();
                        break;
                    case Outdent:
                        binding.richEditor.setOutdent();
                        break;
                    case Checkbox:
                        binding.richEditor.insertTodo();
                        break;
                    case TextBackgroundColor:
                        ColorType = 2;
                        colorPicker();
                        break;
                    case UnorderedList:
                        binding.richEditor.setBullets();
                        break;
                    case OrderedList:
                        binding.richEditor.setNumbers();
                        break;
                }
            }
        });
    }


    private void colorPicker() {
        if (isAnimating)
            return;
        //如果动画没在执行,走到这一步就将isAnimating制为true , 防止这次动画还没有执行完毕的
        //情况下,又要执行一次动画,当动画执行完毕后会将isAnimating制为false,这样下次动画又能执行
        isAnimating = true;
        if (binding.llMainColor.getVisibility() == View.GONE) {
            //打开动画
            animateOpen(binding.llMainColor);
        } else {
            //关闭动画
            animateClose(binding.llMainColor);
        }
    }

    /**
     * 获取控件的高度
     */
    private int getViewMeasureHeight() {
        //获取像素密度
        float mDensity = getResources().getDisplayMetrics().density;
        //获取布局的高度
        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        binding.llMainColor.measure(w, h);
        int height = binding.llMainColor.getMeasuredHeight();
        return (int) (mDensity * height + 0.5);
    }

    /**
     * 开启动画
     *
     * @param view 开启动画的view
     */
    private void animateOpen(LinearLayout view) {
        view.setVisibility(View.VISIBLE);
        ValueAnimator animator = createDropAnimator(view, 0, getViewMeasureHeight());
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                isAnimating = false;
            }
        });
        animator.start();
    }

    /**
     * 关闭动画
     *
     * @param view 关闭动画的view
     */
    private void animateClose(final LinearLayout view) {
        int origHeight = view.getHeight();
        ValueAnimator animator = createDropAnimator(view, origHeight, 0);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.GONE);
                isAnimating = false;
            }
        });
        animator.start();
    }


    private ValueAnimator createDropAnimator(final View view, int start, int end) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.height = value;
                view.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }

    @Override
    protected void initData() {
        serveVM = getViewModel(ServeViewModel.class);

        uploadVM = getViewModel(UploadViewModel.class);

        serveVM.cateList();
        serveVM.sortLiveData.observe(this, sortResp -> {
            localParseUtils.buildSortData(sortResp);
        });


        uploadVM.uploadLiveData.observe(this, uploadRes -> {

            LocalMedia localMedia = new LocalMedia();
            localMedia.setPath(uploadRes.getData().uploadUrl);
            if (IMAGE_SOURCE == 1) {
                addServeAdapter.addData(localMedia);
            } else if (IMAGE_SOURCE == 2) {
                addBannerAdapter.addData(localMedia);
            } else if (IMAGE_SOURCE == 3) {
                binding.richEditor.insertImage(BaseApplication.HOST_PATH + uploadRes.getData().uploadUrl);
            }
        });
    }

    @Override
    public View initLayout() {
        binding = ActivitySkillBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onSort(SortResp sort1, SortResp sort2, SortResp sort3) {
        applyReq.cateId = sort3.id;
        binding.stvSort.getRightTextView().setText(sort3.cateName);
    }

    @Override
    public void onAddress(String province, String city, String area) {
        applyReq.merchantProvince = province;
        applyReq.merchantCity = city;
        applyReq.merchantDistrict = area;
        binding.stvMakeAddress.getRightTextView().setText(province + city + area);
    }

    @Override
    public void accept() {
    }

    @Override
    public void refuse() {
    }

    @Override
    public void noAsk(String permissionName) {
        RxPermissionManager.showPermissionDialog(this, permissionName);
    }

    /**
     * 初始化颜色选择器
     */
    private void initColorPicker() {
        binding.cpvMainColor.setOnColorPickerChangeListener(new ColorPickerView.OnColorPickerChangeListener() {
            @Override
            public void onColorChanged(ColorPickerView picker, int color) {
                if (ColorType == 1) {
                    binding.richEditor.setTextColor(color);
                } else {
                    binding.richEditor.setTextBackgroundColor(color);
                }

            }


            @Override
            public void onStartTrackingTouch(ColorPickerView picker) {
            }

            @Override
            public void onStopTrackingTouch(ColorPickerView picker) {
            }
        });
    }
}
