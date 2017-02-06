package com.skula.cts.activities.dialogs;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.skula.cts.R;
import com.skula.cts.activities.MainActivity;
import com.skula.cts.services.DatabaseService;

public class FavoriteDialog extends Dialog implements OnClickListener {
	public static final int MODIFY = 0;
	public static final int LIST = 1;

	private DatabaseService dbs;
	private int state;
	private String labelIn;

	private Button addBtn;
	private EditText label;
	private ListView favoriteList;
	private MainActivity parentActivity;

	public FavoriteDialog(MainActivity parentActivity, DatabaseService dbs, String label) {
		super(parentActivity);
		this.parentActivity = parentActivity;
		this.dbs = dbs;
		this.labelIn = label;
		this.state = label == null ? LIST : MODIFY;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.favorite_layout);

		if (state == LIST) {
			LinearLayout ll = (LinearLayout) findViewById(R.id.addlayout);
			ll.setVisibility(View.INVISIBLE);
		}

		addBtn = (Button) findViewById(R.id.favorite_add);
		addBtn.setOnClickListener(this);
		
		label = (EditText) findViewById(R.id.favorite_label);
		if(labelIn != null){
			label.setText(labelIn);
		}
		
		favoriteList = (ListView) findViewById(R.id.favorite_list);
		
		List<String> list = dbs.getFavorites();
		String itemArray[] = (String[]) list.toArray(new String[list.size()]);
		ArrayAdapter<String>  adapter = new ArrayAdapter<String>(parentActivity, android.R.layout.simple_list_item_1, list);  
		favoriteList.setAdapter(adapter);
		
		favoriteList.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
				if(getState() == MODIFY){
					String tmp = (String)favoriteList.getItemAtPosition(position);
					dbs.deleteFavorite(tmp);
				}else{
					String tmp = (String)favoriteList.getItemAtPosition(position);
					parentActivity.setLabel(tmp);
				}
				dismiss();
			}
		});

	}
	
	private int getState(){
		return state;
	}

	@SuppressLint("NewApi")
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.favorite_add:
			dbs.insertFavorite(label.getText().toString());
			dismiss();
			break;
		default:
			dismiss();
			break;
		}
	}
}
