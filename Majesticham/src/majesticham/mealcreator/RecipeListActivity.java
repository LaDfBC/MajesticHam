package majesticham.mealcreator;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class RecipeListActivity extends Activity {
	
	List<Recipe> curRecipes;
	RecipeListAdapter rAdpt;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recipe_list_display);
		Intent intent = getIntent();
		curRecipes = loadRecipes(intent.getStringExtra("PassedString"));
		final ListView lv = (ListView) findViewById(R.id.listView);
		rAdpt = new RecipeListAdapter(curRecipes, this);
		lv.setAdapter(rAdpt);
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			 
		     public void onItemClick(AdapterView<?> parentAdapter, View view, int position,
		                             long id) {
		         Recipe clickedRecipe = (Recipe) lv.getItemAtPosition(position);
		         Toast.makeText(RecipeListActivity.this, clickedRecipe.getName(), Toast.LENGTH_LONG).show();

		         //Intent intent = new Intent(ItemListListActivity.this, ItemListActivity.class);
		         //intent.putExtra("PassedList", clickedList.toJSON());
		         //startActivity(intent);
		     }
		});		
		registerForContextMenu(lv);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.recipe_list, menu);
		return true;
	}
	public List<Recipe> loadRecipes(String recipeJSON)
	{
		ArrayList<Recipe> retList = new ArrayList<Recipe>();
		List<String> ingList = new ArrayList<String>();
		try {
			JSONArray listArr = new JSONArray(recipeJSON);
			for(int i = 0; i < listArr.length(); i++)
			{
				JSONObject listObj = listArr.getJSONObject(i);
				String dirObj = listObj.getString("directions");
				String footObj = listObj.getString("footnote");
				String prepObj = listObj.getString("prep");
				String nameObj = listObj.getString("name");
				JSONArray ingJSONArr = listObj.getJSONArray("ingredients");
				for(int j = 0; j < ingJSONArr.length(); j++)
				{
					JSONObject ingJSON = ingJSONArr.getJSONObject(j);
					String ingName = ingJSON.getString("i_name");
					String ingAmt = ingJSON.getString("amt");
					String ingStr = ingAmt + " " + ingName;
					ingList.add(ingStr);
				}
				Recipe r = new Recipe(dirObj, footObj, prepObj, nameObj, ingList);
				retList.add(r);
			}
			
		} catch (JSONException e) {
			Toast.makeText(RecipeListActivity.this, "RecipeListActivity JSON EXCEPTION", Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
		/*
		 List<String> tempList = new ArrayList<String>();
		tempList.add("BLAH");
		Recipe temp = new Recipe("DIRECTIONS", "FOOTNOTE", "PREP", "NAME", tempList);
		retList.add(temp);
		*/
		return retList;
	}
}
