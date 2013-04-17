package majesticham.mealcreator;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class ItemList {
	//IF YOU ADD MORE ENCODE THE JSON AT BOTTOM
	private String name;
	private boolean owned;
	private int numItems;
	private boolean auto;
	private List<Ingredient> items;
	
	public ItemList(String name, int numItems, boolean owned, boolean auto)
	{
		this.name = name;
		this.numItems = numItems;
		this.owned = owned;
		this.auto = auto;
		this.items = new ArrayList<Ingredient>();
	}
	public ItemList(String name, int numItems)
	{
		this.name = name;
		this.numItems = numItems;
		this.owned = false;
		this.auto = false;
		this.items = new ArrayList<Ingredient>();
	}
	public ItemList(String name)
	{
		this.name = name;
		this.numItems = 0;
		this.owned = false;
		this.auto = false;
		this.items = new ArrayList<Ingredient>();
	}
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	public boolean isOwned() {
		return owned;
	}
	public void setOwned(boolean owned) {
		this.owned = owned;
	}
	public int getNumItems() {
		return numItems;
	}
	public void setNumItems(int numItems) {
		this.numItems = numItems;
	}
	public boolean isAuto() {
		return auto;
	}
	public void setAuto(boolean auto) {
		this.auto = auto;
	}
	public List<Ingredient> getItems() {
		return items;
	}
	public void setItems(List<Ingredient> items) {
		this.items = items;
	}
	public void addItem(Ingredient item){
		int ingIndex = this.items.indexOf(item);
		if(ingIndex == -1) //Item isn't in list, add it
		{
			this.items.add(item);
		}
		else  //Item is already in the list, increment the quantity by one
		{
			//should probably change the addDate and goodLength but nahhh
			this.items.get(ingIndex).setQuantity(this.items.get(ingIndex).getQuantity()+1);
		}
	}
	public void removeItem(Ingredient item){
		int ingIndex = this.items.indexOf(item);
		this.items.remove(ingIndex);
	}
	public int contains(Ingredient item)
	{
		for(int i = 0; i < items.size(); i++)
		{
			if(item.getName().equals(items.get(i).getName()))
			{
				return i;
			}
		}
		return -1;
	}
	public String toJSON(){
		
		String retStr;
		retStr = "[{" +
				     "\"name\": \"" + name + "\"," +
				     "\"owned\": \"" + owned + "\"," +
				     "\"numItems\": \"" + numItems + "\"," +
				     "\"auto\": \"" + auto + "\"," +
				     "\"items\": [" ;

		for(int i = 0; i < items.size(); i++)
		{
			if(i != items.size() - 1)
				retStr += items.get(i).toJSON() + ",";
			else
				retStr += items.get(i).toJSON();
		}


		retStr += "]}]";
		return retStr;
	}
}

