package majesticham.mealcreator;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class RecipeListAdapter extends ArrayAdapter<ItemList>{
	private List<Recipe> allRecipes;
	private Context context;
	
	public RecipeListAdapter(List<Recipe> allRecipes, Context ctx){
		super(ctx, R.layout.recipe_row_layout);
		this.allRecipes = allRecipes;
		this.context = ctx;
	}
	public int getCount() {	
		return allRecipes.size();
	}
	/*
	public Recipe getItem(int position) {
		return allRecipes.get(position);
	}
	*/
	public long getItemId(int position) {
		return allRecipes.get(position).hashCode();
	}
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View v = convertView;
		RecipeListHolder holder = new RecipeListHolder();
		if (convertView == null) {
			// This a new view we inflate the new layout
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.recipe_row_layout, null);
			// Now we can fill the layout with the right values
			TextView rnv = (TextView) v.findViewById(R.id.recipeName);
			TextView fnv = (TextView) v.findViewById(R.id.footnote);

			
			holder.recipeNameView = rnv;
			holder.footnoteView = fnv;
			
			v.setTag(holder);
		}
		else 
			holder = (RecipeListHolder) v.getTag();
		
		Recipe p = allRecipes.get(position);
		holder.recipeNameView.setText(p.getName());
		holder.footnoteView.setText(p.getFootnote());
		
		
		return v;
	}
	
	private static class RecipeListHolder{
		public TextView recipeNameView;
		public TextView footnoteView;
	}
}
