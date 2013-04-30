package majesticham.mealcreator;

import java.util.ArrayList;
import java.util.List;


import android.os.Bundle;
import android.os.Parcelable;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ItemListListActivity extends Activity {
	List<ItemList> localLists = new ArrayList<ItemList>();
	ItemListAdapter lAdpt;
	ItemList temp = null; //If they merge the first one they select will be stored here
	
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
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {

		super.onCreateContextMenu(menu, v, menuInfo);
		AdapterContextMenuInfo aInfo = (AdapterContextMenuInfo) menuInfo;

		// We know that each row in the adapter is a Map
		ItemList list =  lAdpt.getItem(aInfo.position);

		menu.setHeaderTitle("Options for " + list.getName());
		menu.add(1, 1, 1, "Delete");
		menu.add(1, 2, 2, "Merge");
	}
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		AdapterContextMenuInfo aInfo = (AdapterContextMenuInfo) item.getMenuInfo();
		if(itemId == 1) //delete
		{
			localLists.remove(localLists.get(aInfo.position));
			lAdpt.notifyDataSetChanged();
		}
		if(itemId == 2) //merge
		{
			if(null == temp) //they haven't selected a list to merge with
			{
				temp = localLists.get(aInfo.position);
			}
			else //they have selected a list to merge with
			{
				if(temp.getName() != localLists.get(aInfo.position).getName()) //only if different lists are selected
				{
					merge(temp, localLists.get(aInfo.position));
				}
				else
				{
					String sameListError = "Can't merge list's with the same name.";
					Toast.makeText(ItemListListActivity.this, sameListError, Toast.LENGTH_LONG).show();
				}
			}
			//TODO
		}
		return true;
	}
	public void merge(ItemList from, ItemList to)
	{
		List<Ingredient> fromItems = from.getItems();
		List<Ingredient> toItems = to.getItems();
		for(int i = 0; i < fromItems.size(); i++)
		{
			boolean existsInTo = false;
			Ingredient curFrom = fromItems.get(i);
			for(int j = 0; j < toItems.size(); j++)
			{
				Ingredient curTo = toItems.get(j);
				if(curFrom.getName() == curTo.getName()) //same item name
				{
					existsInTo = true;
					toItems.get(j).setQuantity(toItems.get(j).getQuantity() + curFrom.getQuantity());
					break;
				}
			}
			if(!existsInTo) //curFrom doesn't exist in to list
			{
				to.addItem(curFrom);
			}
		}
		
		localLists.remove(localLists.indexOf(from));
		lAdpt.notifyDataSetChanged();
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
		
		ItemList pantry = new ItemList("Pantry", 0);
		ItemList shopping = new ItemList("Shopping", 0);
	
		Ingredient testItem1 = new  Ingredient("American Cheese", 3);
		Ingredient testItem2 = new Ingredient("Bread",5);
		Ingredient testItem3 = new Ingredient("Garlic", 12);
		pantry.addItem(testItem1);
		pantry.addItem(testItem2);		
		pantry.addItem(testItem3);
		shopping.addItem(testItem1);
		shopping.addItem(testItem2);
		shopping.addItem(testItem3);
		
		localLists.add(pantry);
		localLists.add(shopping);    	
    }
}
