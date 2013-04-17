package majesticham.mealcreator;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ItemListAdapter extends ArrayAdapter<ItemList>{
	private List<ItemList> allLists;
	private Context context;
	
	public ItemListAdapter(List<ItemList> allLists, Context ctx){
		super(ctx, R.layout.list_row_layout, allLists);
		this.allLists = allLists;
		this.context = ctx;
	}
	public int getCount() {	
		return allLists.size();
	}
	public ItemList getItem(int position) {
		return allLists.get(position);
	}
	public long getItemId(int position) {
		return allLists.get(position).hashCode();
	}
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View v = convertView;
		ItemListHolder holder = new ItemListHolder();
		if (convertView == null) {
			// This a new view we inflate the new layout
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.list_row_layout, null);
			// Now we can fill the layout with the right values
			TextView lnv = (TextView) v.findViewById(R.id.name);
			TextView niv = (TextView) v.findViewById(R.id.numItems);

			
			holder.listNameView = lnv;
			holder.numItemsView = niv;
			
			v.setTag(holder);
		}
		else 
			holder = (ItemListHolder) v.getTag();
		
		ItemList p = allLists.get(position);
		holder.listNameView.setText(p.getName());
		holder.numItemsView.setText("" + p.getNumItems());
		
		
		return v;
	}
	
	private static class ItemListHolder{
		public TextView listNameView;
		public TextView numItemsView;
	}
}
