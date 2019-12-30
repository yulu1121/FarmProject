package com.anshi.farmproject.view.query;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.anshi.farmproject.R;
import com.anshi.farmproject.adapter.ExampleListTreeAdapter;
import com.anshi.farmproject.base.BaseActivity;
import com.anshi.farmproject.entry.DetailQueryEntry;
import com.anshi.farmproject.entry.TreeCountEntry;
import com.anshi.farmproject.utils.Constants;
import com.anshi.farmproject.utils.DialogBuild;
import com.anshi.farmproject.utils.SharedPreferenceUtils;
import com.anshi.farmproject.utils.StatusBarUtils;
import com.anshi.farmproject.utils.Utils;
import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.niuedu.ListTree;
import com.niuedu.ListTreeAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class QueryActivity extends BaseActivity {
    private RecyclerView mListView;
    private ListTree tree = new ListTree();
    //从ListTreeAdapter派生的Adapter
    private ExampleListTreeAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);
        initView();
        initData();
        String dataScope = SharedPreferenceUtils.getString(this, "dataScope");
        if (dataScope.equals("5")){
            getFellingList();
        }else {
            getTreeCount();
        }
    }

    private void initView() {
        TextView titleTv = findViewById(R.id.title_tv);
        titleTv.setText("除治信息查询");
        mListView = findViewById(R.id.tree_recycler);
        mListView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mListView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
    }

    private void getFellingList(){
        if (!isFinishing()){
            commonLoadDialog = DialogBuild.getBuild().createCommonLoadDialog(this,"正在加载中");
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userId", SharedPreferenceUtils.getInt(mContext,"userId"));
            jsonObject.put("branchId",SharedPreferenceUtils.getInt(mContext,"userId"));
            jsonObject.put("pageSize",10);
            jsonObject.put("pageNum",1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("xxx",jsonObject.toString());
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
        mService.getFelingList(requestBody)
                .map(new Func1<ResponseBody, ResponseBody>() {
                    @Override
                    public ResponseBody call(ResponseBody responseBody) {
                        return responseBody;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody responseBody) {
                        if (null!=commonLoadDialog){
                            commonLoadDialog.dismiss();
                        }
                        try {
                            String string = responseBody.string();
                            if (Utils.isGoodJson(string)){
                                Gson gson = new Gson();
                                DetailQueryEntry detailQueryEntry = gson.fromJson(string, DetailQueryEntry.class);
                                if (detailQueryEntry.getCode()== Constants.SUCCESS_CODE){
                                    int total = detailQueryEntry.getData().getTotal();
                                    String userName = SharedPreferenceUtils.getString(mContext, "userName");
                                    ExampleListTreeAdapter.ContactInfo contact = new ExampleListTreeAdapter.ContactInfo(SharedPreferenceUtils.getString(mContext,"deptName")," ");
                                    //创建后台数据：一棵树
                                    //创建组们，是root node，所有parent为null
                                    ListTree.TreeNode groupNode = tree.addNode(null, contact, R.layout.contacts_group_item);
                                    ExampleListTreeAdapter.ContactInfo contactTwo = new ExampleListTreeAdapter.ContactInfo(userName,"数量:"+total);
                                    contactTwo.setId(SharedPreferenceUtils.getInt(mContext,"userId"));
                                    contactTwo.setTotal(total);
                                    //创建后台数据：一棵树
                                    //创建组们，是root node，所有parent为null
                                    ListTree.TreeNode treeNode = tree.addNode(groupNode, contactTwo, R.layout.contacts_contact_item);
                                    treeNode.setShowExpandIcon(false);
                                    adapter.notifyDataSetChanged();
                                } else{
                                    Toast.makeText(mContext, detailQueryEntry.getMsg(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (null!=commonLoadDialog){
                            commonLoadDialog.dismiss();
                        }

                        throwable.printStackTrace();
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatusBarUtils.setWindowStatusBarColor(this,R.color.white);
        StatusBarUtils.setStatusTextColor(true,this);
    }
    private void initData(){
        adapter = new ExampleListTreeAdapter(this, tree, new ListTreeAdapter.ChildClick() {
            @Override
            public void childClick(int position) {
                ListTree.TreeNode node = tree.getNodeByPlaneIndex(position);
                ExampleListTreeAdapter.ContactInfo contactInfo = (ExampleListTreeAdapter.ContactInfo) node.getData();
                Log.e("xxx","xx"+contactInfo.getId());
                Intent intent = new Intent(mContext, QueryListActivity.class);
                intent.putExtra("title",contactInfo.getTitle());
                intent.putExtra("id",contactInfo.getId());
                intent.putExtra("total",contactInfo.getTotal());
                mContext.startActivity(intent);
            }
        });
        mListView.setAdapter(adapter);

    }
    private KProgressHUD commonLoadDialog;
    private void getTreeCount(){
        int deptId = SharedPreferenceUtils.getInt(this, "deptId");
        int roleId = SharedPreferenceUtils.getInt(this, "roleId");
        String dataScope = SharedPreferenceUtils.getString(this, "dataScope");
        // String type = SharedPreferenceUtils.getString(this, "type");
        if (!isFinishing()){
            commonLoadDialog = DialogBuild.getBuild().createCommonLoadDialog(this,"正在加载");
        }
        JSONObject jsonObject = new JSONObject();
        try {
//            if (type.equals("4")){
//                jsonObject.put("branchId",SharedPreferenceUtils.getInt(this,"userId"));
//            }else {
                jsonObject.put("deptId",deptId);
                jsonObject.put("userId",SharedPreferenceUtils.getInt(mContext,"userId"));
                if (!TextUtils.isEmpty(dataScope)){
                    jsonObject.put("roleId",roleId);
                    jsonObject.put("dataScope",dataScope);
                }
          //  }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("xxx",jsonObject.toString());
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
        mService.getTreeDeptCount(requestBody)
                .map(new Func1<ResponseBody, ResponseBody>() {
                    @Override
                    public ResponseBody call(ResponseBody responseBody) {
                        return responseBody;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody responseBody) {
                        if (null!=commonLoadDialog){
                            commonLoadDialog.dismiss();
                        }
                        try {
                            String string = responseBody.string();
                            if (Utils.isGoodJson(string)){
                                Gson gson = new Gson();
                                TreeCountEntry treeCountEntry = gson.fromJson(string, TreeCountEntry.class);
                                if (treeCountEntry.getCode()== Constants.SUCCESS_CODE){
                                    if (treeCountEntry.getData()!=null&&treeCountEntry.getData().size()>0){
                                        for (int i = 0; i <treeCountEntry.getData().size() ; i++) {
                                            TreeCountEntry.DataBean dataBean = treeCountEntry.getData().get(i);
                                            ExampleListTreeAdapter.ContactInfo contact = new ExampleListTreeAdapter.ContactInfo(dataBean.getDeptName(),"数量:"+dataBean.getAmount());
                                            //创建后台数据：一棵树
                                            //创建组们，是root node，所有parent为null
                                            ListTree.TreeNode groupNode = tree.addNode(null, contact, R.layout.contacts_group_item);
                                            if (dataBean.getChildDepts()!=null&&dataBean.getChildDepts().size()>0){
                                                for (int j = 0; j <dataBean.getChildDepts().size() ; j++) {
                                                    TreeCountEntry.DataBean.ChildDeptsBeanXXXX childDeptsBeanXXXX = dataBean.getChildDepts().get(j);
                                                    ListTree.TreeNode groupNodeOne;
                                                    if (childDeptsBeanXXXX.getChainsaw()!=null){
                                                        ExampleListTreeAdapter.ContactInfo contactTwo = new ExampleListTreeAdapter.ContactInfo(childDeptsBeanXXXX.getChainsaw(),"数量:"+childDeptsBeanXXXX.getAmounts());
                                                        contactTwo.setId(childDeptsBeanXXXX.getBranchId());
                                                        contactTwo.setTotal(childDeptsBeanXXXX.getAmounts());
                                                        groupNodeOne = tree.addNode(groupNode, contactTwo, R.layout.contacts_group_item);
                                                        groupNodeOne.setShowExpandIcon(false);
                                                        continue;
                                                    }else {
                                                        ExampleListTreeAdapter.ContactInfo contactTwo = new ExampleListTreeAdapter.ContactInfo(childDeptsBeanXXXX.getDeptName(),"数量:"+childDeptsBeanXXXX.getAmount());
                                                        groupNodeOne = tree.addNode(groupNode, contactTwo, R.layout.contacts_group_item);
                                                    }
                                                    if (childDeptsBeanXXXX.getChildDepts()!=null&&childDeptsBeanXXXX.getChildDepts().size()>0){
                                                        for (int k = 0; k <childDeptsBeanXXXX.getChildDepts().size() ; k++) {
                                                            TreeCountEntry.DataBean.ChildDeptsBeanXXXX.ChildDeptsBeanXXX childDeptsBeanXXX = childDeptsBeanXXXX.getChildDepts().get(k);
                                                            ListTree.TreeNode groupNodeTwo;
                                                            if (childDeptsBeanXXX.getChainsaw()!=null){
                                                                ExampleListTreeAdapter.ContactInfo contactFour = new ExampleListTreeAdapter.ContactInfo(childDeptsBeanXXX.getChainsaw(),"数量:"+childDeptsBeanXXX.getAmounts());
                                                                contactFour.setId(childDeptsBeanXXX.getBranchId());
                                                                contactFour.setTotal(childDeptsBeanXXX.getAmounts());
                                                                groupNodeTwo = tree.addNode(groupNodeOne, contactFour, R.layout.contacts_group_item);
                                                                groupNodeTwo.setShowExpandIcon(false);
                                                                continue;
                                                            }else {
                                                                ExampleListTreeAdapter.ContactInfo contactThree = new ExampleListTreeAdapter.ContactInfo(childDeptsBeanXXX.getDeptName(),"数量:"+childDeptsBeanXXX.getAmount());
                                                                groupNodeTwo = tree.addNode(groupNodeOne, contactThree, R.layout.contacts_group_item);
                                                            }

                                                            if (childDeptsBeanXXX.getChildDepts()!=null&&childDeptsBeanXXX.getChildDepts().size()>0){
                                                                for (int l = 0; l <childDeptsBeanXXX.getChildDepts().size(); l++) {
                                                                    ListTree.TreeNode groupNodeThree;
                                                                    TreeCountEntry.DataBean.ChildDeptsBeanXXXX.ChildDeptsBeanXXX.ChildDeptsBeanXX childDeptsBeanXX = childDeptsBeanXXX.getChildDepts().get(l);
                                                                    if (childDeptsBeanXX.getChainsaw()!=null){
                                                                        ExampleListTreeAdapter.ContactInfo contactFour = new ExampleListTreeAdapter.ContactInfo(childDeptsBeanXX.getChainsaw(),"数量:"+childDeptsBeanXX.getAmounts());
                                                                        contactFour.setId(childDeptsBeanXX.getBranchId());
                                                                        contactFour.setTotal(childDeptsBeanXX.getAmounts());
                                                                        groupNodeThree = tree.addNode(groupNodeTwo, contactFour, R.layout.contacts_group_item);
                                                                        groupNodeThree.setShowExpandIcon(false);
                                                                        continue;
                                                                    }else {
                                                                        ExampleListTreeAdapter.ContactInfo contactFour = new ExampleListTreeAdapter.ContactInfo(childDeptsBeanXX.getDeptName(),"数量:"+childDeptsBeanXX.getAmount());
                                                                        groupNodeThree = tree.addNode(groupNodeTwo, contactFour, R.layout.contacts_group_item);
                                                                    }
                                                                    if (childDeptsBeanXX.getChildDepts()!=null&&childDeptsBeanXX.getChildDepts().size()>0){
                                                                        for (int m = 0; m <childDeptsBeanXX.getChildDepts().size() ; m++) {
                                                                            TreeCountEntry.DataBean.ChildDeptsBeanXXXX.ChildDeptsBeanXXX.ChildDeptsBeanXX.ChildDeptsBeanX childDeptsBeanX = childDeptsBeanXX.getChildDepts().get(m);
                                                                            ListTree.TreeNode groupNodeFour;
                                                                            if (childDeptsBeanX .getChainsaw()!=null){
                                                                                ExampleListTreeAdapter.ContactInfo contactFive = new ExampleListTreeAdapter.ContactInfo(childDeptsBeanX.getChainsaw(),"数量:"+childDeptsBeanX.getAmounts());
                                                                                contactFive.setId(childDeptsBeanX.getBranchId());
                                                                                contactFive.setTotal(childDeptsBeanX.getAmounts());
                                                                                groupNodeFour = tree.addNode(groupNodeThree, contactFive, R.layout.contacts_group_item);
                                                                                groupNodeFour.setShowExpandIcon(false);
                                                                                continue;
                                                                            }else {
                                                                                ExampleListTreeAdapter.ContactInfo contactFive = new ExampleListTreeAdapter.ContactInfo(childDeptsBeanX.getDeptName(),"数量:"+childDeptsBeanX.getAmount());
                                                                                groupNodeFour = tree.addNode(groupNodeThree, contactFive, R.layout.contacts_group_item);
                                                                            }
                                                                            if ( childDeptsBeanX .getChildDepts()!=null&& childDeptsBeanX.getChildDepts().size()>0){
                                                                                for (int n = 0; n <childDeptsBeanX.getChildDepts().size() ; n++) {
                                                                                    TreeCountEntry.DataBean.ChildDeptsBeanXXXX.ChildDeptsBeanXXX.ChildDeptsBeanXX.ChildDeptsBeanX.ChildDeptsBean childDeptsBean = childDeptsBeanX.getChildDepts().get(n);
                                                                                    if (childDeptsBean.getChainsaw()!=null){
                                                                                        ExampleListTreeAdapter.ContactInfo contactFive = new ExampleListTreeAdapter.ContactInfo(childDeptsBean.getChainsaw(),"数量:"+childDeptsBean.getAmounts());
                                                                                        contactFive.setId(childDeptsBean.getBranchId());
                                                                                        contactFive.setTotal(childDeptsBean.getAmounts());
                                                                                        ListTree.TreeNode groupNodeFive = tree.addNode(groupNodeFour, contactFive, R.layout.contacts_group_item);
                                                                                        groupNodeFive.setShowExpandIcon(false);
                                                                                    } else {
                                                                                        ExampleListTreeAdapter.ContactInfo contactFive = new ExampleListTreeAdapter.ContactInfo(childDeptsBean.getDeptName(),"数量:"+childDeptsBean.getAmount());
                                                                                        tree.addNode(groupNodeFour, contactFive, R.layout.contacts_group_item);
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }

                                                }

                                            }
                                        }
                                        adapter.notifyDataSetChanged();
                                    }

                                }else {
                                    Toast.makeText(mContext, treeCountEntry.getMsg(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (null!=commonLoadDialog){
                            commonLoadDialog.dismiss();
                        }
                        throwable.printStackTrace();
                    }
                });
    }

}
