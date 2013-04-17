package majesticham.mealcreator;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ItemListActivity extends Activity {

	ItemList curList;
	IngredientAdapter lAdpt;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.item_list_display); 
		Intent intent = getIntent();
		curList = loadIngredients(intent.getStringExtra("PassedList"));
		final ListView lv = (ListView) findViewById(R.id.listView);

 		lAdpt = new IngredientAdapter(curList.getItems(), this);
 
		lv.setAdapter(lAdpt);
		ImageView close = (ImageView)lv.findViewById(R.id.img);
		
		
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			 
		     public void onItemClick(AdapterView<?> parentAdapter, View v, int position,
		                             long id) {
		    	// lAdpt.remove(curList.getItems().get(position));
		    	// lAdpt.notifyDataSetChanged();
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
		Ingredient ing =  lAdpt.getItem(aInfo.position);

		menu.setHeaderTitle("Options for " + ing.getName());
		menu.add(1, 1, 1, "Increment");
		menu.add(1, 2, 2, "Delete");
	}
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		if(itemId == 1)
		{
			AdapterContextMenuInfo aInfo = (AdapterContextMenuInfo) item.getMenuInfo();
			curList.getItems().get(aInfo.position).setQuantity(curList.getItems().get(aInfo.position).getQuantity()+1);
			lAdpt.notifyDataSetChanged();
		}
		if(itemId == 2)
		{
			AdapterContextMenuInfo aInfo = (AdapterContextMenuInfo) item.getMenuInfo();
			curList.removeItem(curList.getItems().get(aInfo.position));
			lAdpt.notifyDataSetChanged();
		}
		return true;
	}
	
	public ItemList loadIngredients(String listJSON)
	{
		ItemList retList = new ItemList("BLA");
		try {
			JSONArray listArr = new JSONArray(listJSON);
			
			JSONObject listObj = listArr.getJSONObject(0);
			String listName = (String)listObj.get("name");
			retList = new ItemList(listName);
			JSONArray listItems = (JSONArray)listObj.get("items");
			for(int i = 0; i < listItems.length(); i++)
			{
				Ingredient myIng;
				JSONObject item = listItems.getJSONObject(i);
				//Ingredient(String name, int quantity, int goodLength, String unit, Date addDate)
				String name = (String)item.get("name");
				int quantity = Integer.parseInt((String)item.get("quantity"));
				String unit = (String)item.get("unit");
//TODO: this is more than min viable 
//				int goodLength = Integer.parseInt((String)item.get("goodLength"));
//				Date addDate = (Date)item.get("date");
				myIng = new Ingredient(name, quantity, unit);
				retList.addItem(myIng);
			}
		} catch (JSONException e) {
			 Toast.makeText(ItemListActivity.this, "ItemListActivity JSON EXCEPTION", Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
		return retList;
	}
	public void addIngredientClick(View view)
	{
		
		final Dialog d = new Dialog(this);
    	d.setContentView(R.layout.add_ingredient_dialog);
    	d.setTitle("Add Ingredient");
    	d.setCancelable(true);
    	final EditText ingName = (EditText) d.findViewById(R.id.editIngredientName);
    	final EditText ingQuant = (EditText) d.findViewById(R.id.editIngredientQuant);//TODO wire
    	Button b = (Button) d.findViewById(R.id.submit);
    	b.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				String name = ingName.getText().toString();
				String sQuant = ingQuant.getText().toString();
				int quant;
				if(sQuant.equals("") || sQuant.equals("0"))
					quant = 1;
				else
					quant = Integer.parseInt(sQuant);
				if(name.equals("") == false)
				{
					Ingredient ing = new Ingredient(name, quant);
					int ingIndex = curList.contains(ing);
					if(ingIndex != -1) //item already exists, increment by quant
						ItemListActivity.this.curList.getItems().get(ingIndex).setQuantity(ItemListActivity.this.curList.getItems().get(ingIndex).getQuantity()+quant);
					else //item doesn't exist, add it
						ItemListActivity.this.curList.addItem(ing);
					ItemListActivity.this.lAdpt.notifyDataSetChanged(); 
				}
				d.dismiss();
			}
		});
    	d.show();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.item_list, menu);
		return true;
	}

}
