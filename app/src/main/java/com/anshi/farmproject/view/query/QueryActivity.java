package com.anshi.farmproject.view.query;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.anshi.farmproject.R;
import com.anshi.farmproject.adapter.ExampleListTreeAdapter;
import com.anshi.farmproject.base.BaseActivity;
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
        getTreeCount();
    }

    private void initView() {
        TextView titleTv = findViewById(R.id.title_tv);
        titleTv.setText("除治信息查询");
        mListView = findViewById(R.id.tree_recycler);
        mListView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mListView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatusBarUtils.setWindowStatusBarColor(this,R.color.white);
        StatusBarUtils.setStatusTextColor(true,this);
    }
    private void initData(){
//        ExampleListTreeAdapter.ContactInfo contact;
//        contact = new ExampleListTreeAdapter.ContactInfo("林业局","数量:3000");
//        //创建后台数据：一棵树
//        //创建组们，是root node，所有parent为null
//        ListTree.TreeNode groupNode1 = tree.addNode(null, contact, R.layout.contacts_group_item);
//        //第二层
//        contact = new ExampleListTreeAdapter.ContactInfo( "点军街办", "数量:1000");
//        ListTree.TreeNode contactNode1 = tree.addNode(groupNode1, contact, R.layout.contacts_group_item);
//        //再添加一个
//        contact = new ExampleListTreeAdapter.ContactInfo( "土城乡", "数量:1000");
//        ListTree.TreeNode contactNode2 = tree.addNode(groupNode1, contact, R.layout.contacts_group_item);
//        //再添加一个
//        contact = new ExampleListTreeAdapter.ContactInfo( "桥边镇", "数量:1000");
//        ListTree.TreeNode contactNode3 = tree.addNode(groupNode1, contact, R.layout.contacts_group_item);
//        //第三层
//        contact = new ExampleListTreeAdapter.ContactInfo("牛扎坪村", "数量:1000");
//        ListTree.TreeNode n = tree.addNode(contactNode1, contact, R.layout.contacts_group_item);
//        //第4层
//        contact = new ExampleListTreeAdapter.ContactInfo("思路除治队", "数量:1000");
//        ListTree.TreeNode n1 = tree.addNode(n, contact, R.layout.contacts_group_item);
//        //第5层
//        contact = new ExampleListTreeAdapter.ContactInfo("油锯1", "数量:500");
//        ListTree.TreeNode n2 = tree.addNode(n1, contact, R.layout.contacts_contact_item);
//        n2.setShowExpandIcon(false);
//        contact = new ExampleListTreeAdapter.ContactInfo("油锯2", "数量:500");
//        ListTree.TreeNode n3 = tree.addNode(n1, contact, R.layout.contacts_contact_item);
//        n3.setShowExpandIcon(false);

        adapter = new ExampleListTreeAdapter(this, tree, new ListTreeAdapter.ChildClick() {
            @Override
            public void childClick(int position) {
                ListTree.TreeNode node = tree.getNodeByPlaneIndex(position);
                ExampleListTreeAdapter.ContactInfo contactInfo = (ExampleListTreeAdapter.ContactInfo) node.getData();
                Log.e("xxx","xx"+contactInfo.getId());
                Intent intent = new Intent(mContext, QueryListActivity.class);
                intent.putExtra("id",contactInfo.getId());
                mContext.startActivity(intent);
            }
        });

        mListView.setAdapter(adapter);
    }
    private KProgressHUD commonLoadDialog;
    private void getTreeCount(){
        int deptId = SharedPreferenceUtils.getInt(this, "deptId");
        if (!isFinishing()){
            commonLoadDialog = DialogBuild.getBuild().createCommonLoadDialog(this,"正在加载");
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("deptId",deptId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
        mService.getTreeDeptCount(requestBody)
                .map(new Func1<ResponseBody, ResponseBody>() {
                    @Override
                    public ResponseBody call(ResponseBody responseBody) {
                        return responseBody;
                    }
                }).subscribeOn(Schedulers.io())
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
                                                    TreeCountEntry.DataBean.ChildDeptsBeanXXX childDeptsBean = dataBean.getChildDepts().get(j);
                                                    ExampleListTreeAdapter.ContactInfo contactTwo = new ExampleListTreeAdapter.ContactInfo(childDeptsBean.getDeptName(),"数量:"+childDeptsBean.getAmount());
                                                    ListTree.TreeNode groupNodeOne = tree.addNode(groupNode, contactTwo, R.layout.contacts_group_item);
                                                    if (childDeptsBean.getChildDepts()!=null&&childDeptsBean.getChildDepts().size()>0){
                                                        for (int k = 0; k <childDeptsBean.getChildDepts().size() ; k++) {
                                                            TreeCountEntry.DataBean.ChildDeptsBeanXXX.ChildDeptsBeanXX childDeptsBeanX = childDeptsBean.getChildDepts().get(k);
                                                            ListTree.TreeNode groupNodeTwo;
                                                            if (childDeptsBeanX.getChainsaw()!=null){
                                                                ExampleListTreeAdapter.ContactInfo contactFour = new ExampleListTreeAdapter.ContactInfo(childDeptsBeanX.getChainsaw(),"数量:"+childDeptsBeanX.getAmounts());
                                                                contactFour.setId(childDeptsBeanX.getBranchId());
                                                                groupNodeTwo = tree.addNode(groupNodeOne, contactFour, R.layout.contacts_group_item);
                                                                groupNodeTwo.setShowExpandIcon(false);
                                                                continue;
                                                            }else {
                                                                ExampleListTreeAdapter.ContactInfo contactThree = new ExampleListTreeAdapter.ContactInfo(childDeptsBeanX.getDeptName(),"数量:"+childDeptsBeanX.getAmount());
                                                                groupNodeTwo = tree.addNode(groupNodeOne, contactThree, R.layout.contacts_group_item);
                                                            }

                                                            if (childDeptsBeanX.getChildDepts()!=null&&childDeptsBeanX.getChildDepts().size()>0){
                                                                for (int l = 0; l <childDeptsBeanX.getChildDepts().size(); l++) {
                                                                    ListTree.TreeNode groupNodeThree;
                                                                    TreeCountEntry.DataBean.ChildDeptsBeanXXX.ChildDeptsBeanXX.ChildDeptsBeanX childDeptsBean1 = childDeptsBeanX.getChildDepts().get(l);
                                                                    if (childDeptsBean1.getChainsaw()!=null){
                                                                        ExampleListTreeAdapter.ContactInfo contactFour = new ExampleListTreeAdapter.ContactInfo(childDeptsBean1.getChainsaw(),"数量:"+childDeptsBean1.getAmounts());
                                                                        contactFour.setId(childDeptsBean1.getBranchId());
                                                                        groupNodeThree = tree.addNode(groupNodeTwo, contactFour, R.layout.contacts_group_item);
                                                                        groupNodeThree.setShowExpandIcon(false);
                                                                        continue;
                                                                    }else {
                                                                        ExampleListTreeAdapter.ContactInfo contactFour = new ExampleListTreeAdapter.ContactInfo(childDeptsBean1.getDeptName(),"数量:"+childDeptsBean1.getAmount());
                                                                        groupNodeThree = tree.addNode(groupNodeTwo, contactFour, R.layout.contacts_group_item);
                                                                    }
                                                                    if (childDeptsBean1.getChildDepts()!=null&&childDeptsBean1.getChildDepts().size()>0){
                                                                        for (int m = 0; m <childDeptsBean1.getChildDepts().size() ; m++) {
                                                                            TreeCountEntry.DataBean.ChildDeptsBeanXXX.ChildDeptsBeanXX.ChildDeptsBeanX.ChildDeptsBean countListBean = childDeptsBean1.getChildDepts().get(m);
                                                                            Log.e("xxx",countListBean.getChainsaw());
                                                                            ExampleListTreeAdapter.ContactInfo contactFive = new ExampleListTreeAdapter.ContactInfo(countListBean.getChainsaw(),"数量:"+countListBean.getAmounts());
                                                                            contactFive.setId(countListBean.getBranchId());
                                                                            ListTree.TreeNode groupNodeFour = tree.addNode(groupNodeThree, contactFive, R.layout.contacts_group_item);
                                                                            groupNodeFour.setShowExpandIcon(false);
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
