package majesticham.mealcreator;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
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
		if(itemId == 1) //Increment
		{
			AdapterContextMenuInfo aInfo = (AdapterContextMenuInfo) item.getMenuInfo();
			curList.getItems().get(aInfo.position).setQuantity(curList.getItems().get(aInfo.position).getQuantity()+1);
			lAdpt.notifyDataSetChanged();
		}
		if(itemId == 2) //Delete 
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
				String name = (String)item.get("name");
				int quantity = Integer.parseInt((String)item.get("quantity"));
				String unit = (String)item.get("unit");
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
	public void readStream(InputStream in)
	{
		BufferedReader reader = null;
		  try {
		    reader = new BufferedReader(new InputStreamReader(in));
		    String line = "";
		    while ((line = reader.readLine()) != null) {
		      System.out.println(line);
		    }
		  } catch (IOException e) {
		    e.printStackTrace();
		  } finally {
		    if (reader != null) {
		      try {
		        reader.close();
		      } catch (IOException e) {
		        e.printStackTrace();
		        }
		    }
		  }
	}
	public void searchClick(View view)
	{
		
		HttpClient httpClient = new DefaultHttpClient();  
		String url = "http://crabdancemc.com/foodapp/get_recipes/%5B";
		String query = "";
		//%5B%22american%20cheese%22%2C%22bread%22%2C%22garlic%22%5D
		
		for(int i = 0; i < curList.getItems().size(); i++)
		{
			
			if(i < curList.getItems().size() - 1)
			{
				String temp = URLEncoder.encode(curList.getItems().get(i).getName()) + "%22,";
				query = query + "%22" + temp.replace("+", "%20");
			}
			else
			{
				String temp = URLEncoder.encode(curList.getItems().get(i).getName()) + "%22";
				query = query + "%22" + temp.replace("+", "%20");
			}
		}
		url = url + query + "%5D";
		HttpGet httpGet = new HttpGet(url);
		try {
		    HttpResponse response = httpClient.execute(httpGet);
		    StatusLine statusLine = response.getStatusLine();
		    if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
		        HttpEntity entity = response.getEntity();
		        ByteArrayOutputStream out = new ByteArrayOutputStream();
		        entity.writeTo(out);
		        out.close();
		        String responseStr = out.toString();
		        if(responseStr.length() > 2)
		        {
		        	Intent intent = new Intent(ItemListActivity.this, RecipeListActivity.class);
			        intent.putExtra("PassedString", responseStr);
			        startActivity(intent);
		        }
		        else
		        {
		        	Toast.makeText(ItemListActivity.this, "No recipes found" , Toast.LENGTH_LONG).show();
		        }
		    } else {
		    	Toast.makeText(ItemListActivity.this, "BAD RESPONSE", Toast.LENGTH_LONG).show();
		    }
		} catch (ClientProtocolException e) {
			Toast.makeText(ItemListActivity.this, "Client Protocol EXCEPTION", Toast.LENGTH_LONG).show();
		} catch (IOException e) {
			Toast.makeText(ItemListActivity.this, "IO EXCEPTION", Toast.LENGTH_LONG).show();
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.item_list, menu);
		return true;
	}

}
