package com.jike.cashocean.ui.find.presenter;

import android.content.Context;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSCustomSignerCredentialProvider;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.EncodeUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jike.cashocean.BuildConfig;
import com.jike.cashocean.model.NormalBean;
import com.jike.cashocean.model.OssBackEntity;
import com.jike.cashocean.net.BaseObserver;
import com.jike.cashocean.net.Key;
import com.jike.cashocean.net.RequestApi;
import com.jike.cashocean.net.RxSchedulers;
import com.jike.cashocean.net.URL;
import com.jike.cashocean.ui.base.BasePresenter;
import com.jike.cashocean.ui.find.CardTestActivity;
import com.jike.cashocean.ui.find.contract.CardTestContract;
import com.jike.cashocean.util.MapUrlTools;

import java.util.Map;
import java.util.TreeMap;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author Ming
 * @Date on 2019/12/6
 * @Description
 */
public class CardTestPresenter extends BasePresenter<CardTestActivity> implements CardTestContract.Presenter {

    private RequestApi requestApi;
    private Context _context;

    @Inject
    public CardTestPresenter(RequestApi requestApi, Context context) {
        this.requestApi = requestApi;
        this._context = context;
    }

    @Override
    public void saveInfo(Map<String, String> mapInfo) {
        requestApi.postRequest(URL.FACE_DIFF, mapInfo)
                .compose(RxSchedulers.applySchedulers())
                .compose(mView.bindToLife())
                .subscribe(new BaseObserver<NormalBean>() {
                    @Override
                    public void onSuccess(NormalBean normalBean) throws Exception {
                        mView.loadSaveInfoSuccess(normalBean);
                    }

                    @Override
                    public void onFail(Throwable throwable) throws ClassNotFoundException {
                        mView.loadSaveInfoSuccess(null);
                    }
                });
    }


    public void pushPhoto(String photoName, String uploadFilePath, boolean isIdPhoto) {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> observableEmitter) throws Exception {
                String endpoint = "http://oss-ap-southeast-5.aliyuncs.com";
                OSSCredentialProvider credentialProvider = new OSSCustomSignerCredentialProvider() {
                    @Override
                    public String signContent(String s) {
                        //从服务器获取token
                        final String[] signNature = new String[1];
                        if (BuildConfig.DEBUG) {
                            LogUtils.e("OSS返回的String值是   " + s.toString());
                        }
                        TreeMap<String, String> paramsMap = new TreeMap<>();
                        paramsMap.put(Key.VERSION, String.valueOf(AppUtils.getAppVersionCode()));
                        paramsMap.put("content", EncodeUtils.base64Encode2String(s.getBytes()));
                        paramsMap.put(Key.TOKEN, SPUtils.getInstance().getString(Key.TOKEN));
                        paramsMap.put(Key.SIGN, MapUrlTools.getSign(paramsMap));
                        requestApi.getOssToken(paramsMap)
                                .compose(mView.bindToLife())
                                .subscribe(new BaseObserver<OssBackEntity>() {
                                    @Override
                                    public void onSuccess(OssBackEntity ossBackEntity) throws Exception {
                                        if (ossBackEntity.getRet() == 200) {
                                            if (ossBackEntity.getData().getCode() == 100) {
                                                if (BuildConfig.DEBUG) {
                                                    LogUtils.e("从服务器获取到的值XXXXX",
                                                            ossBackEntity.getData().getDatas().getAli_token().toString());
                                                }
                                                signNature[0] =
                                                        ossBackEntity.getData().getDatas().getAli_token().toString();
                                            } else {
                                                signNature[0] = "";
//                                                observableEmitter.onError(new Throwable
//                                                (ossBackEntity.getData().getMsg()));
                                            }
                                        } else {
                                            signNature[0] = "";
//                                            observableEmitter.onError(new Throwable
//                                            (ossBackEntity.getMsg()));
                                        }
                                    }

                                    @Override
                                    public void onFail(Throwable throwable) throws ClassNotFoundException {
                                        signNature[0] = "";
//                                        observableEmitter.onError(throwable);
                                    }
                                });
                        if (BuildConfig.DEBUG) {
                            LogUtils.e("从服务器获取到的值", signNature[0]);
                        }
                        return signNature[0];
                    }
                };
                OSS oss = new OSSClient(_context, endpoint, credentialProvider);
                PutObjectRequest putObjectRequest = new PutObjectRequest("yndc", photoName,
                        uploadFilePath);
                try {
                    PutObjectResult putResult = oss.putObject(putObjectRequest);
                    if (BuildConfig.DEBUG) {
                        LogUtils.e("PutObject", "UploadSuccess");
                        LogUtils.e("ETag", putResult.getETag());
                        LogUtils.e("RequestId", putResult.getRequestId());
                    }
                    observableEmitter.onNext(photoName);
                    observableEmitter.onComplete();
                } catch (ClientException e1) {
                    // 本地异常，如网络异常等。
                    e1.printStackTrace();
                    observableEmitter.onError(e1);
                } catch (ServiceException e2) {
                    // 服务异常。
                    e2.printStackTrace();
                    if (BuildConfig.DEBUG) {
                        LogUtils.e("RequestId", e2.getRequestId());
                        LogUtils.e("ErrorCode", e2.getErrorCode());
                        LogUtils.e("HostId", e2.getHostId());
                        LogUtils.e("RawMessage", e2.getRawMessage());
                    }
                    observableEmitter.onError(e2);
                }
            }
        })
                .compose(RxSchedulers.<String>applySchedulers())
                .compose(mView.<String>bindToLife())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String name) {
                        LogUtils.e("执行了吗");
                        if (isIdPhoto) {
                            mView.loadScuccessIDPhoto(name);
                        } else {
                            mView.loadSuccessFacePhoto(name);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showLong(e.getMessage());
                        LogUtils.e("报错", e.getMessage());
                        if (isIdPhoto) {
                            mView.loadScuccessIDPhoto(null);
                        } else {
                            mView.loadSuccessFacePhoto(null);
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
