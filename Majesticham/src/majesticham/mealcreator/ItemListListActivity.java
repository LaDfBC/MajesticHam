package majesticham.mealcreator;

import java.util.ArrayList;
import java.util.List;


import android.os.Bundle;
import android.os.Parcelable;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ItemListListActivity extends Activity {
	List<ItemList> localLists = new ArrayList<ItemList>();
	ItemListAdapter lAdpt;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_list_display);
		
		initList();
		final ListView lv = (ListView) findViewById(R.id.listView);
		lAdpt = new ItemListAdapter(localLists, this);
		lv.setAdapter(lAdpt);
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			 
		     public void onItemClick(AdapterView<?> parentAdapter, View view, int position,
		                             long id) {
		         ItemList clickedList = (ItemList) lv.getItemAtPosition(position);
		         Intent intent = new Intent(ItemListListActivity.this, ItemListActivity.class);
		         intent.putExtra("PassedList", clickedList.toJSON());
		         startActivity(intent);
		 
		     }
		});
		registerForContextMenu(lv);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void addItemListClick(View view)
	{
		final Dialog d = new Dialog(this);
    	d.setContentView(R.layout.add_item_list_dialog);
    	d.setTitle("Create List");
    	d.setCancelable(true);
    	final EditText edit = (EditText) d.findViewById(R.id.editListName);
    	Button b = (Button) d.findViewById(R.id.button1);
    	b.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				String listName = edit.getText().toString();
				if(listName.equals("") == false)
				{
					ItemListListActivity.this.localLists.add(new ItemList(listName, 0));
					ItemListListActivity.this.lAdpt.notifyDataSetChanged(); // We notify the data model is changed
				}
				d.dismiss();
			}
		});
    	d.show();
	}
	private void initList() {
		localLists.add(new ItemList("Pantry", 0));
		localLists.add(new ItemList("Shopping", 0));    	
    }
}
