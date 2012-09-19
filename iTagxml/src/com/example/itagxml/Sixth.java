package com.example.itagxml;

import java.util.ArrayList;
import java.util.HashMap;

import com.lovedayluk.database.DataBaseHelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class Sixth extends BaseActivity{
	
	private Button back = null;
	private Button b3 = null;
	private Button b4 = null;
	private Button b6 = null;
	private String tag = "";
	private String purl = "";
	private DataBaseHelper database;
	private Cursor cursor;
	ArrayList<HashMap<String, Object>> listData;
	SimpleAdapter adapter;
	private ListView listview=null;
	private EditText et = null;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO �Զ����ɵķ������
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sixth);
		Toast.makeText(Sixth.this, "���Tag����", Toast.LENGTH_LONG).show();
		Bundle bundle = getIntent().getExtras();
		tag = bundle.getString("tag");
		purl = bundle.getString("purl");
		Log.v("purl", purl);
		
		database = new DataBaseHelper(this);
		getTags();
		back = (Button) findViewById(R.id.button1);
		b3 = (Button) findViewById(R.id.button3);
		b4 = (Button) findViewById(R.id.button4);
		b6 = (Button) findViewById(R.id.button6);
		
		back.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				bundle.putString("tag", tag);
				bundle.putString("purl", purl);
				intent.putExtras(bundle);
				intent.setClass(Sixth.this, Four.class);
				startActivity(intent);
				finish();
			}
		});
		
		//listview = (ListView) findViewById(R.id.listView1);
		setListView();
		listview.setAdapter(adapter);
		
	}
	
	/**
	 * ͨ��purl��ȡȫ����tag
	 * @param null
	 * @return string
	 */
	public String[] getTags(){
		Cursor c = database.select_uri(purl);
		startManagingCursor(c);
		listData = new ArrayList<HashMap<String, Object>>();
		String temp_tags[]=new String[10];
		String temp ="";
		int i=0;
		while (c.moveToNext()) {
			HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("_id", c.getInt(c.getColumnIndex("_id")));
				map.put("tag", c.getString(c.getColumnIndex("tag")));
				map.put("uri", c.getString(c.getColumnIndex("uri")));
			Log.v("tag", c.getString(c.getColumnIndex("tag")));
			listData.add(map);
			temp_tags[i] = c.getString(c.getColumnIndex("tag"));
			i++;
		}
//		String s = temp_tags.toString();
//		Log.v("tags", s);
//		return temp_tags.toString();
		return temp_tags;	
	}
	
	public void setListView(){
		listview = (ListView) findViewById(R.id.listView1);
		adapter = new SimpleAdapter(this,
			listData,// ����Դ
			R.layout.gridrow,// ListItem��XMLʵ��
			// ��̬������ImageItem��Ӧ������
			new String[] {"tag"},
			// ImageItem��XML�ļ������һ��ImageView,����TextView ID
			new int[] { R.id.textView1});
		listview.setAdapter(adapter);
		
		//���������¼�
		listview.setOnItemClickListener(new OnItemClickListener(){
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
//				EditText et = null;
				AlertDialog.Builder builder = new AlertDialog.Builder(Sixth.this);
				builder.setTitle("�������µ�tag")
				.setIcon(android.R.drawable.ic_dialog_info)
				.setView(et =new EditText(Sixth.this));
//				.setPositiveButton("ȷ��", null)
//				.setNegativeButton("ȡ��", null)
//				.show();
				builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int whichButton) {
	                //�¼�����
	                	String new_tag = et.getText().toString();
	                	Log.v("old tag", tag);
	                	Log.v("new_tag", new_tag);
	                	database.changeTag(tag, new_tag);
	                	tag = new_tag;
	                	Toast.makeText(Sixth.this, "�ɹ�����", Toast.LENGTH_LONG).show();
	                }
	            });
	            builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int whichButton) {

	                }
	            });
	           builder.show();
			}
			
		});
	}

}
