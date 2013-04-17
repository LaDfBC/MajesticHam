package majesticham.mealcreator;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class IngredientAdapter extends ArrayAdapter<Ingredient>{
	private ItemList allItems;
	private Context context;
	private int pos;
	private static class IngredientListHolder{
		public TextView ingredientNameView;
		public TextView quantityView;
	}
	
	public IngredientAdapter(List<Ingredient> list, Context ctx){
		super(ctx, R.layout.ingredient_row_layout, list);
		allItems = new ItemList("list");
		this.allItems.setItems(list);
		this.context = ctx;
	}
	public Ingredient getItem(int position) {
		return allItems.getItems().get(position);
	}
	public long getItemId(int position) {
		return allItems.getItems().get(position).hashCode();
	}


	public View getView(int position, View convertView, ViewGroup parent)
	{
		View v = convertView;
		IngredientListHolder holder = new IngredientListHolder();
		if (convertView == null) {
			// This a new view we inflate the new layout
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.ingredient_row_layout, null);
			// Now we can fill the layout with the right values
			TextView inv = (TextView) v.findViewById(R.id.name);
			TextView qv = (TextView) v.findViewById(R.id.quantity);
			ImageView close = (ImageView) v.findViewById(R.id.img);
			pos = position;
			close.setOnClickListener(new OnClickListener() {

	            @Override
	            public void onClick(View view) {
	            	//allItems.removeItem(allItems.getItems().get(pos));
	          //  	remove(allItems.getItems().get(pos));
	           // 	notifyDataSetChanged();
	            }
	        });
			
			holder.ingredientNameView = inv;
			holder.quantityView = qv;
			
			v.setTag(holder);
		}
		else 
			holder = (IngredientListHolder) v.getTag();
		
		Ingredient ing = allItems.getItems().get(position);
		holder.ingredientNameView.setText(ing.getName());
		holder.quantityView.setText("" + ing.getQuantity());
		return v;
	}
	
}
